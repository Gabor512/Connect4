package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private DatabaseHelper databaseHelper;

    @BeforeEach
    void setUp() {
        databaseHelper = new DatabaseHelper();
        game = new Game(6, 7, databaseHelper);
    }

    @Test
    void testSwitchPlayer() {
        assertEquals(Game.YELLOW, game.getCurrentPlayer());
        game.switchPlayer();
        assertEquals(Game.RED, game.getCurrentPlayer());
    }

    @Test
    void testStartGamePlayerVictory() {
        game.getBoard().placePiece(0, Game.YELLOW);
        game.getBoard().placePiece(1, Game.YELLOW);
        game.getBoard().placePiece(2, Game.YELLOW);
        game.getBoard().placePiece(3, Game.YELLOW);
        assertTrue(game.getBoard().checkVictory(Game.YELLOW));
    }

    @Test
    void testGameOverAfterVictory() {
        game.getBoard().placePiece(0, Game.YELLOW);
        game.getBoard().placePiece(1, Game.YELLOW);
        game.getBoard().placePiece(2, Game.YELLOW);
        game.getBoard().placePiece(3, Game.YELLOW);
        assertTrue(game.getBoard().checkVictory(Game.YELLOW));
        game.setGameOver(true);
        assertTrue(game.isGameOver());
    }

    @Test
    void testGameSaveAndLoad() throws IOException {
        game.getBoard().placePiece(0, Game.YELLOW);
        game.getBoard().saveToFile("game_state.txt");

        Game loadedGame = new Game(6, 7, databaseHelper);
        loadedGame.getBoard().loadFromFile("game_state.txt");
        assertEquals(Game.YELLOW, loadedGame.getBoard().getBoard()[5][0]);
    }

    @Test
    void testInvalidPlayerMove() {
        Scanner simulatedInput = new Scanner("-1\n"); // Szimulált bemenet: "-1" (érvénytelen lépés)
        Game testGame = new Game(6, 7, databaseHelper, simulatedInput);

        int move = testGame.getPlayerMove();
        assertEquals(-1, move); // Ellenőrizzük, hogy az érvénytelen lépés helyesen lett kezelve
    }

    @Test
    void testGameEndsAfterFourInRow() {
        game.getBoard().placePiece(0, Game.YELLOW);
        game.getBoard().placePiece(1, Game.YELLOW);
        game.getBoard().placePiece(2, Game.YELLOW);
        game.getBoard().placePiece(3, Game.YELLOW);
        assertTrue(game.getBoard().checkVictory(Game.YELLOW));
    }

    @Test
    void testAddWinAndRetrieveCurrentWins() {
        // Adatbázis tisztítása csak erre a tesztre
        databaseHelper.resetDatabase();
        String playerName = "Player1";

        // Első győzelem hozzáadása
        databaseHelper.addWin(playerName);
        int currentWins = databaseHelper.getCurrentWins(playerName);
        assertEquals(1, currentWins, "Az első győzelem nem lett helyesen rögzítve.");

        // Második győzelem hozzáadása
        databaseHelper.addWin(playerName);
        currentWins = databaseHelper.getCurrentWins(playerName);
        assertEquals(2, currentWins, "A második győzelem nem lett helyesen rögzítve.");
    }

    @Test
    void testAddWinForMultiplePlayers() {
        // Adatbázis tisztítása csak erre a tesztre
        databaseHelper.resetDatabase();
        databaseHelper.addWin("Player1");
        databaseHelper.addWin("Player2");
        databaseHelper.addWin("Player1");

        assertEquals(2, databaseHelper.getCurrentWins("Player1"), "Player1 győzelmei helytelenek.");
        assertEquals(1, databaseHelper.getCurrentWins("Player2"), "Player2 győzelmei helytelenek.");
    }
}
