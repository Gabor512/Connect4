package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testSwitchPlayer() {
        Game game = new Game(6, 7);
        assertEquals(Game.YELLOW, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(Game.RED, game.getCurrentPlayer());
    }

    @Test
    void testStartGamePlayerVictory() {
        Game game = new Game(6, 7);
        game.getBoard().placePiece(0, Game.YELLOW);
        game.getBoard().placePiece(1, Game.YELLOW);
        game.getBoard().placePiece(2, Game.YELLOW);
        game.getBoard().placePiece(3, Game.YELLOW);
        assertTrue(game.getBoard().checkVictory(Game.YELLOW));
    }

//    @Test
//    void testGameOverAfterVictory() {
//        Game game = new Game(6, 7);
//        game.getBoard().placePiece(0, Game.YELLOW);
//        game.getBoard().placePiece(1, Game.YELLOW);
//        game.getBoard().placePiece(2, Game.YELLOW);
//        game.getBoard().placePiece(3, Game.YELLOW);
//        assertTrue(game.getBoard().checkVictory(Game.YELLOW));
//        assertTrue(game.isGameOver());
//    }

    @Test
    void testGameSaveAndLoad() throws IOException {
        Game game = new Game(6, 7);
        game.getBoard().placePiece(0, Game.YELLOW);
        game.getBoard().saveToFile("game_state.txt");
        Game loadedGame = new Game(6, 7);
        loadedGame.getBoard().loadFromFile("game_state.txt");
        assertEquals(Game.YELLOW, loadedGame.getBoard().getBoard()[5][0]);
    }

//    @Test
//    void testInvalidPlayerMove() {
//        Game game = new Game(6, 7);
//        game.switchPlayer();
//        assertEquals(Game.RED, game.getCurrentPlayer());
//        int invalidMove = -1;
//        assertEquals(invalidMove, game.getPlayerMove()); // Érvénytelen bemenet
//    }

    @Test
    void testSwitchPlayerMultipleTimes() {
        Game game = new Game(6, 7);
        assertEquals(Game.YELLOW, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(Game.RED, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(Game.YELLOW, game.getCurrentPlayer());
    }
}
