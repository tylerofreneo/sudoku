package persistence;

import model.SudokuBook;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

// from: github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// test class for JsonReader
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SudokuBook gb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBoardList.json");
        try {
            SudokuBook gb = reader.read();
            assertEquals(0, gb.numBoards());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBoardList.json");
        try {
            SudokuBook gb = reader.read();
            assertEquals(2, gb.numBoards());
            checkBoard(
                    "123456789456789123789123456234567891567891234891234567345678912678912345912345678",
                    "Easy", false, gb.get(0));
            checkBoard(
                    "123456789456789123789123456123456789456789123789123456123456789456789123789123456",
                    "Hard", false, gb.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}