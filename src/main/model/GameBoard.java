package model;

import java.util.ArrayList;
import java.util.List;

import persistence.Writable;
import org.json.JSONObject;

// Represents a Sudoku game board, with its board state, difficulty, and if it's completed
public class GameBoard implements Writable {

    private List<List<String>> board;
    private String difficulty;
    private boolean completed;

    /*
     * REQUIRES: board is 81 digits
     * EFFECTS:  board is made into 9x9 sudoku grid (0 is empty);
     *           difficulty is set to difficulty; completed set to false
     */
    public GameBoard(String board, String difficulty) {
        board = board.replace("0", " ");
        this.board = new ArrayList<List<String>>();
        this.difficulty = difficulty;
        this.completed = false;
        for (int i = 0; i < 9; i++) {
            this.board.add(new ArrayList<String>());
            for (int j = 0; j < 9; j++) {
                this.board.get(i).add((board.substring(i * 9 + j, i * 9 + j + 1)));
            }
        }
    }

    /*
     * REQUIRES: board is 81 digits
     * EFFECTS:  board is made into 9x9 sudoku grid (0 is empty);
     *           difficulty is set to difficulty; completed set to completed
     */
    public GameBoard(String board, String difficulty, boolean completed) {
        board = board.replace("0", " ");
        this.board = new ArrayList<List<String>>();
        this.difficulty = difficulty;
        this.completed = completed;
        for (int i = 0; i < 9; i++) {
            this.board.add(new ArrayList<String>());
            for (int j = 0; j < 9; j++) {
                this.board.get(i).add((board.substring(i * 9 + j, i * 9 + j + 1)));
            }
        }
    }

    /*
     * REQUIRES: input length = 3 and digits only and specified coordinates empty
     * MODIFIES: this
     * EFFECTS: last digit is added to (first, second)
     */
    public void add(String input) {
        int col = Integer.parseInt(input.substring(0, 1));
        int row = Integer.parseInt(input.substring(1, 2));
        String num = input.substring(2, 3);
        EventLog.getInstance().logEvent(new Event("Added number: " + num 
                + "\n\tAt (col, row): (" + col + ", " + row + ") to " + difficulty + " board."));
        board.get(row - 1).set(col - 1, num);
    }

    // MODIFIES: this
    // EFFECTS: if board is completed, sets completed to true and returns true
    public boolean checkCompletion() {
        if (isFull() && checkGrids() && checkRows() && checkColumns()) {
            this.completed = true;
            EventLog.getInstance().logEvent(new Event("Completed " + difficulty + " board."));
            return true;
        }
        return false;
    }

    // EFFECTS: returns true if board contains no empty space
    private boolean isFull() {
        for (List<String> row : board) {
            if (row.contains(" ")) { 
                return false; 
            }
        }
        return true;
    }

    // EFFECTS: returns true if every 3x3 grid in board is valid
    private boolean checkGrids() {
        for (int i = 0; i < 9; i++) {
            List<List<String>> grid = new ArrayList<List<String>>();
            for (int j = 0; j < 3; j++) {
                List<String> row = new ArrayList<String>();
                for (int k = 0; k < 3; k++) {
                    row.add(board.get(j + 3 * (int)Math.floor(i / 3)).get(k + 3 * (i % 3)));
                }
                grid.add(row);
            }
            if (!checkGrid(grid)) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: grid is 3x3
    // EFFECTS: returns true if 3x3 grid is valid
    private boolean checkGrid(List<List<String>> grid) {
        List<String> seen = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (seen.contains(grid.get(i).get(j))) {
                    return false;
                }
                seen.add(grid.get(i).get(j));
            }
        }
        return true;
    }

    // EFFECTS: returns true if all rows in board is valid
    private boolean checkRows() {
        for (int i = 0; i < 9; i++) {
            List<String> seen = new ArrayList<String>();
            for (int j = 0; j < 9; j++) {
                if (seen.contains(board.get(i).get(j))) {
                    return false;
                }
                seen.add(board.get(i).get(j));
            }
        }
        return true;
    }

    // EFFECTS: returns true if all columns in board is valid
    private boolean checkColumns() {
        for (int i = 0; i < 9; i++) {
            List<String> seen = new ArrayList<String>();
            for (int j = 0; j < 9; j++) {
                if (seen.contains(board.get(j).get(i))) {
                    return false;
                }
                seen.add(board.get(j).get(i));
            }
        }
        return true;
    }

    // EFFECTS: converts List<List<String>> board; returns String board
    private String boardToString() {
        String result = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result += board.get(i).get(j);
            }
        }
        result = result.replace(" ", "0");
        return result;
    }




    // MODIFIES: this
    // EFFECTS: sets difficulty to given difficulty
    public void setDifficulty(String difficulty) {
        EventLog.getInstance().logEvent(new Event("Set " + this.difficulty + " board to difficulty: " + difficulty));
        this.difficulty = difficulty;
    }

    // MODIFIES: this
    // EFFECTS: sets completion status to given status
    public void setCompleted(boolean completed) {
        EventLog.getInstance().logEvent(new Event("Set board to completion status: " + completed));
        this.completed = completed;
    }


    //EFFECTS: converts gameboard object to jsonobject, returns jsonobject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("board", boardToString());
        json.put("difficulty", difficulty);
        json.put("completed", completed);
        return json;
    }





    // EFFECTS: returns board
    public List<List<String>> getBoard() {
        return board;
    }

    // EFFECTS: returns board as string ????
    public String getBoardAsString() {
        return boardToString();
    }
    
    // EFFECTS: returns difficulty
    public String getDifficulty() {
        return difficulty;
    }

    // EFFECTS: returns true if completed
    public boolean isCompleted() {
        return completed;
    }
}

