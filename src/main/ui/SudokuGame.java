package ui;

import model.Event;
import model.EventLog;
import model.GameBoard;
import model.SudokuBook;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// sources: lab 3.2, jsonserializationdemo
// A sudoku player that allows the user to add sudoku games and play them
public class SudokuGame {
    private static final String JSON_STORE = "./data/sudokugame.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private SudokuBook boards;
    private int currentBoardIndex;

    private Scanner scanner;
    private boolean isProgramRunning; // Lab 3.2

    // Lab 3.2
    // EFFECTS: creates an instance of the SudokuGame console ui application
    public SudokuGame() {
        init();

        System.out.println("Welcome!");

        while (isProgramRunning) {
            handleMenu();
        }
    }

    // Lab 3.2
    // EFFECTS: initializes the application with the starting values
    private void init() {
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;

        this.boards = new SudokuBook("My book");
        this.currentBoardIndex = 0;

        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
    }

    // Lab 3.2
    // EFFECTS: displays and processes inputs for the main menu
    private void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuCommands(input);
    }

    // Lab 3.2
    // EFFECTS: displays a list of commands that can be used from the main menu
    private void displayMenu() {
        System.out.println("1. Add a puzzle");
        System.out.println("2. View all puzzles");
        System.out.println("3. Save boards to file");
        System.out.println("4. Load boards from file");
        System.out.println("0. Quit application\n");
        System.out.print("Select an option:  ");
    }

    // Lab 3.2
    // EFFECTS: processes the user's input in the main menu
    @SuppressWarnings("methodlength")
    private void processMenuCommands(String input) {
        switch (input) {
            case "0": {
                quitApplication();
                break;
            }
            case "1": {
                addBoard();
                break;
            }
            case "2": {
                viewBoards();
                break;
            }
            case "3": {
                saveBoards();
                break;
            }
            case "4": {
                loadBoards();
                break;
            }
            default:
                System.out.println("Invalid option, try again");
        }
    }

    // JsonSerializationDemo
    // EFFECTS: saves boards to file
    private void saveBoards() {
        try {
            jsonWriter.open();
            jsonWriter.write(boards);
            jsonWriter.close();
            System.out.println("Saved sudoku boards to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        }
    }

    // JsonSerializationDemo
    // EFFECTS: loads boards to file
    private void loadBoards() {
        try {
            boards = jsonReader.read();
            System.out.println("Loaded sudoku boards from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // Lab 3.2
    // EFFECTS: prints a closing message and marks the program as not running
    private void quitApplication() {
        printAllEvents();
        this.isProgramRunning = false;
    }

    // EFFECTS: prints all events that have been logged 
    //          since the application started to console
    private void printAllEvents() {
        EventLog el = EventLog.getInstance();
        for (Event event : el) {
            System.out.println(event.toString());
        }
    }


    // Lab 3.2 addNewFlashcard()
    // EFFECTS: adds a board to the sudokubook
    private void addBoard() {
        System.out.println("Please enter the boards numbers:");
        String board = this.scanner.nextLine();

        System.out.println("Please enter the puzzle's difficulty:");
        String difficulty = this.scanner.nextLine();

        GameBoard gameBoard = new GameBoard(board, difficulty);

        this.boards.addBoard(gameBoard);
        System.out.println("\nNew puzzle successfully created!");
    }

    // Lab 3.2 viewFlashcards()
    // EFFECTS: displays all boards one at a time
    private void viewBoards() {
        displayGivenBoards(this.boards);
    }

    // Lab 3.2
    // EFFECTS: displays the given sudokubook and handles inputs related to
    // viewing the boards
    private void displayGivenBoards(SudokuBook boards) {
        if (boards.isEmpty()) {
            System.out.println("No boards to play. Try adding a puzzle first!");
            return;
        }

        String input = "";
        while (!input.equals("0")) {
            GameBoard currentGameBoard = boards.get(this.currentBoardIndex);
            displayGameBoard(currentGameBoard);
            displayViewMenu();
            input = this.scanner.nextLine();
            handleViewCommands(input, boards);
        }
        this.currentBoardIndex = 0;
    }

    // Lab 3.2
    // EFFECTS: displays a list of commands that can be used in the view boards menu
    private void displayViewMenu() {
        System.out.println("1. Play this board");
        System.out.println("2. View next board");
        System.out.println("3. View previous board");
        System.out.println("4. Change current board difficulty");
        System.out.println("0. Return to menu");
        System.out.print("Select an option:  ");
    }

    // Lab 3.2
    // EFFECTS: displays the given board
    private void displayGameBoard(GameBoard gameBoard) {
        System.out.println("Board " + (this.currentBoardIndex + 1));
        System.out.println("Difficulty: " + gameBoard.getDifficulty());
        System.out.println("Completed: " + gameBoard.isCompleted());
        System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8\t9\t");
        System.out.println("    +-------+-------+-------+-------+-------+-------+-------+-------+-------+");
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + "   |");
            for (int j = 0; j < 9; j++) {
                System.out.print("\t" + gameBoard.getBoard().get(i).get(j) + "   |");
            }
            System.out.println();
            System.out.println("    +-------+-------+-------+-------+-------+-------+-------+-------+-------+");
        }

    }

    // Lab 3.2
    // EFFECTS: processes the user's input in the view boards menu
    private void handleViewCommands(String input, SudokuBook boards) {
        GameBoard currentGameBoard = boards.get(this.currentBoardIndex);
        switch (input) {
            case "1":
                playBoard(currentGameBoard);
                break;
            case "2":
                getNextBoard(boards);
                break;
            case "3":
                getPreviousBoard();
                break;
            case "4":
                changeDifficulty(currentGameBoard);
                break;
            case "0":
                System.out.println("Returning to the menu...");
                break;
            default:
                System.out.println("Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: prompts the user to change the given boards difficulty
    private void changeDifficulty(GameBoard board) {
        String input = "";
        System.out.print("Enter new difficulty: ");
        input = this.scanner.nextLine();
        board.setDifficulty(input);
    }

    // Lab 3.2
    // EFFECTS: if there is a new board to display, increments the current board
    // index
    private void getNextBoard(SudokuBook boards) {
        if (this.currentBoardIndex >= boards.numBoards() - 1) {
            System.out.println("Error: No more new flashcards to display!");
        } else {
            this.currentBoardIndex++;
        }
    }

    // Lab 3.2
    // EFFECTS: if there is a previous board to display, decrements the current
    // board index
    private void getPreviousBoard() {
        if (this.currentBoardIndex <= 0) {
            System.out.println("Error: No more previous flashcards to display!");
        } else {
            this.currentBoardIndex--;
        }
    }

    // EFFECTS: displays the given board and handles inputs related to playing the
    // board
    private void playBoard(GameBoard board) {
        String input = "";
        while (!input.equals("0") && !board.isCompleted()) {
            displayGameBoard(board);
            displayPlayMenu();
            input = this.scanner.nextLine();
            handlePlayCommands(input, board);
            board.checkCompletion();
        }
    }

    // EFFECTS: displays a list of commands that can be used in the play menu
    private void displayPlayMenu() {
        System.out.print("Enter column, row, number to add:  ");
    }

    // Lab 3.2
    // EFFECTS: processes the user's input in the play menu
    private void handlePlayCommands(String input, GameBoard board) {
        // check for invalid inputs
        board.add(input);
    }
}
