package persistence;

import model.GameBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

// from: github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// test class for Json classes
public class JsonTest {
    protected void checkBoard(String board, String difficulty, boolean completed, GameBoard gb) {
        assertEquals(board, gb.getBoardAsString());
        assertEquals(difficulty, gb.getDifficulty());
        assertEquals(completed, gb.isCompleted());
    }
}
