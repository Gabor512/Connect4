package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testPlacePiece() {
        Board board = new Board(6, 7);
        assertTrue(board.placePiece(0, "Z"));
        assertFalse(board.placePiece(7, "Z")); // Érvénytelen oszlop
        assertFalse(board.placePiece(-1, "Z")); // Érvénytelen oszlop
    }

    @Test
    void testCheckVictoryHorizontal() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 4; i++) {
            board.placePiece(i, "Z");
        }
        assertTrue(board.checkVictory("Z"));
    }

    @Test
    void testCheckVictoryVertical() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 4; i++) {
            board.placePiece(0, "Z");
        }
        assertTrue(board.checkVictory("Z"));
    }

    @Test
    void testCheckVictoryDiagonal() {
        Board board = new Board(6, 7);
        board.placePiece(0, "Z");
        board.placePiece(1, "Z");
        board.placePiece(1, "Z");
        board.placePiece(2, "Z");
        board.placePiece(2, "Z");
        board.placePiece(2, "Z");
        board.placePiece(3, "Z");

        assertTrue(board.checkVictory("Z"));
    }

    @Test
    void testSaveAndLoad() throws IOException {
        Board board = new Board(6, 7);
        board.placePiece(0, "Z");
        board.saveToFile("test_save.txt");
        Board loadedBoard = new Board(6, 7);
        loadedBoard.loadFromFile("test_save.txt");
        assertEquals("Z", loadedBoard.getBoard()[5][0]);
    }

//    @Test
//    void testSaveFileIOException() {
//        Board board = new Board(6, 7);
//        try {
//            board.saveToFile("invalid_path/test_save.txt"); // Érvénytelen útvonal
//            fail("IOException várható");
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Hiba a játék mentésekor"));
//        }
//    }
//
//    @Test
//    void testLoadFileIOException() {
//        Board board = new Board(6, 7);
//        try {
//            board.loadFromFile("invalid_path/test_load.txt"); // Nem létező fájl
//            fail("IOException várható");
//        } catch (Exception e) {
//            assertTrue(e.getMessage().contains("Hiba a játék betöltésekor"));
//        }
//    }
}
