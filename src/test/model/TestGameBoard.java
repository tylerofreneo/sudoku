package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Test class for GameBoard class
public class TestGameBoard {
    GameBoard testGameBoard;

    @BeforeEach
    void runBefore() {
        testGameBoard = new GameBoard(
            "301279406040409406257103295909466581903103924611822732838087223379681345969230600", "Easy");
    }

    @Test
    void testConstructor() {
        assertEquals("Easy", testGameBoard.getDifficulty());

        assertFalse(testGameBoard.isCompleted());
        assertEquals("3", testGameBoard.getBoard().get(0).get(0));
        assertEquals(" ", testGameBoard.getBoard().get(8).get(8));
    }

    @Test
    void testAdd() {
        testGameBoard.add("219");
        assertEquals("9", testGameBoard.getBoard().get(0).get(1));
    }

    @Test
    void testSetDifficulty() {
        assertEquals("Easy", testGameBoard.getDifficulty());
        testGameBoard.setDifficulty("Medium");
        assertEquals("Medium", testGameBoard.getDifficulty());
    }

    @Test
    void testSetCompleted() {
        assertFalse(testGameBoard.isCompleted());
        testGameBoard.setCompleted(true);
        assertTrue(testGameBoard.isCompleted());
        testGameBoard.setCompleted(true);
        assertTrue(testGameBoard.isCompleted());
        testGameBoard.setCompleted(false);
        assertFalse(testGameBoard.isCompleted());

    }

    @Test
    void testCheckCompletionCompleted() {
        testGameBoard = new GameBoard(
            "123456789456789123789123456234567891567891234891234567345678912678912345912345678", "Easy");
        assertTrue(testGameBoard.checkCompletion());
    }

    @Test
    void testCheckCompletionEmptySpaceFail() {
        testGameBoard = new GameBoard(
            "123456789456789123789123456234507891567891234891234567345678912678912345912345678", "Easy");
        assertFalse(testGameBoard.checkCompletion());
    }

    @Test
    void testCheckCompletionBlockFail() {
        testGameBoard = new GameBoard(
            "123456789416789123789123456234567891567891234891234567345678912678912345912345678", "Easy");
        assertFalse(testGameBoard.checkCompletion());
    }

    @Test
    void testCheckCompletionRowFail() {
        testGameBoard = new GameBoard(
            "123123123456456456789789789123123123456456456789789789123123123456456456789789789", "Easy");
        assertFalse(testGameBoard.checkCompletion());
    }

    @Test
    void testCheckCompletionColumnFail() {
        testGameBoard = new GameBoard(
            "123456789456789123789123456123456789456789123789123456123456789456789123789123456", "Easy");
        assertFalse(testGameBoard.checkCompletion());
    }
}
