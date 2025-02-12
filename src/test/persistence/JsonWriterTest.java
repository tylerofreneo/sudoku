package persistence;

import model.GameBoard;
import model.SudokuBook;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

// from: github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// test class for jsonwriter class
class JsonWriterTest extends JsonTest {
    // NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter
    // is to
    // write data to a file and then use the reader to read it back in and check
    // that we
    // read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            SudokuBook gb = new SudokuBook("My book");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            SudokuBook gb = new SudokuBook("My book");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBoardList.json");
            writer.open();
            writer.write(gb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBoardList.json");
            gb = reader.read();
            assertEquals(0, gb.numBoards());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            SudokuBook gb = new SudokuBook("My book");
            gb.addBoard(new GameBoard(
                    "123456789456789123789123456234567891567891234891234567345678912678912345912345678", "Easy"));
            gb.addBoard(new GameBoard(
                    "123456789456789123789123456123456789456789123789123456123456789456789123789123456", "Hard"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBoardList.json");
            writer.open();
            writer.write(gb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBoardList.json");
            gb = reader.read();
            assertEquals(2, gb.numBoards());
            checkBoard(
                    "123456789456789123789123456234567891567891234891234567345678912678912345912345678",
                    "Easy", false, gb.get(0));
            checkBoard(
                    "123456789456789123789123456123456789456789123789123456123456789456789123789123456",
                    "Hard", false, gb.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}