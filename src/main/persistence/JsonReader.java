package persistence;

import model.GameBoard;
import model.SudokuBook;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// from: github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads sudokubook from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads sudokubook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SudokuBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBoards(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses sudokubook from JSON object and returns it
    private SudokuBook parseBoards(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        SudokuBook boards = new SudokuBook(name);
        addBoards(boards, jsonObject);
        return boards;
    }

    // MODIFIES: boards
    // EFFECTS: parses gameboards from JSON object and adds them to sudokubook
    private void addBoards(SudokuBook boards, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("boards");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addBoard(boards, nextThingy);
        }
    }

    // MODIFIES: boards
    // EFFECTS: parses gameboard from JSON object and adds it to sudokubook
    private void addBoard(SudokuBook boards, JSONObject jsonObject) {
        String board = jsonObject.getString("board");
        String difficulty = jsonObject.getString("difficulty");
        boolean completed = jsonObject.getBoolean("completed");

        GameBoard gb = new GameBoard(board, difficulty, completed);
        boards.addBoard(gb);
    }
}
