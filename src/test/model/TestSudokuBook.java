package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Test class for sudokubook class
public class TestSudokuBook {
    SudokuBook testSudokuBook;
    GameBoard testGameBoard;

    @BeforeEach
    void runBefore() {
        testSudokuBook = new SudokuBook("My book");
        testGameBoard = new GameBoard(
            "301279406040409406257103295909466581903103924611822732838087223379681345969230600", "Easy");
    }

    @Test
    void testConstructor() {
        assertEquals("My book", testSudokuBook.getName());
        assertEquals(0, testSudokuBook.numBoards());
    }

    @Test
    void testIsEmpty() {
        assertTrue(testSudokuBook.isEmpty());
        testSudokuBook.addBoard(testGameBoard);
        assertFalse(testSudokuBook.isEmpty());
    }

    @Test
    void testAddBoard() {
        assertEquals(0, testSudokuBook.numBoards());
        testSudokuBook.addBoard(testGameBoard);
        assertEquals(1, testSudokuBook.numBoards());
        assertEquals(testGameBoard, testSudokuBook.get(0));
    }
}