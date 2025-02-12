package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// from: jsonserializationdemo
// Represents a sudokubook having a collection of gameboards
public class SudokuBook implements Writable {
    private String name;
    private List<GameBoard> boards;

    // EFFECTS: constructs sudokubook with a name and empty list of boards
    public SudokuBook(String name) {
        this.name = name;
        boards = new ArrayList<GameBoard>();
    }

    // EFFECTS: converts sudokubook to jsonobject, and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("boards", boardsToJson());
        return json;
    }

    // EFFECTS: returns boards in this sudokubook as a JSON array
    private JSONArray boardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (GameBoard gb : boards) {
            jsonArray.put(gb.toJson());
        }

        return jsonArray;
    }


    // EFFECTS: returns gameboard from given index in boards
    public GameBoard get(int index) {
        return boards.get(index);
    }

    // EFFECTS: returns true if book is empty
    public boolean isEmpty() {
        return boards.isEmpty();
    }

    // MODIFIES: this
    // EFFECTS: adds board to this sudokubook
    public void addBoard(GameBoard board) {
        EventLog.getInstance().logEvent(new Event("Added board to sudoku book with difficulty "
                + board.getDifficulty()));
        boards.add(board);
    }

    // EFFECTS: returns number of boards in this sudokubook
    public int numBoards() {
        return boards.size();
    }

    // EFFECTS: returns name of sudokubook
    public String getName() {
        return name;
    }

}
