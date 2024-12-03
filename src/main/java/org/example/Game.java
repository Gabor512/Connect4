package org.example;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * A Connect4 játék logikáját valósítja meg.
 */
public final class Game {
    /**
     * A játék táblája.
     */
    private final Board board;
    /**
     * Az aktuális játékos, aki lép.
     */
    private String currentPlayer;
    /**
     * Jelzi, hogy véget ért-e a játék.
     */
    private boolean gameOver;
    /**
     * Felhasználói bemenet olvasására szolgáló Scanner.
     */
    private final Scanner scanner;

    /**
     * A sárga játékos bábuját jelölő szimbólum.
     */
    public static final String YELLOW = "Z";
    /**
     * A piros játékos (számítógép) bábuját jelölő szimbólum.
     */
    public static final String RED = "O";

    /**
     * Új játék inicializálása a megadott tábla méretekkel.
     *
     * @param rows    a tábla sorainak száma
     * @param columns a tábla oszlopainak száma
     */
    public Game(final int rows, final int columns) {
        this.board = new Board(rows, columns);
        this.currentPlayer = YELLOW; // A sárga játékos kezd
        this.gameOver = false;
        this.scanner = new Scanner(System.in);
    }

    /**
     * A Connect4 játék ciklusának indítása.
     */
    public void startGame() throws IOException {
        System.out.print("Add meg a sárga játékos nevét: ");
        String playerName = scanner.nextLine();
        System.out.println("A számítógép piros játékosként fog játszani.");

        while (!gameOver) {
            board.printBoard();
            System.out.println("Játékos " + currentPlayer + " lép.");

            int column = getPlayerMove();
            if (column == -1) {
                System.out.println("Érvénytelen lépés. Próbáld újra.");
                continue;
            }

            if (!board.placePiece(column, currentPlayer)) {
                System.out.println("Ez az oszlop tele van. Válassz másikat.");
                continue;
            }

            if (board.checkVictory(currentPlayer)) {
                board.printBoard();
                System.out.println("Játékos " + currentPlayer + " nyert!");
                gameOver = true;
            } else {
                switchPlayer();
            }
        }

        board.saveToFile("game_state.txt");
    }

    /**
     * Lekéri az aktuális játékos lépését.
     *
     * @return a választott oszlop vagy -1, ha a bemenet érvénytelen
     */
    int getPlayerMove() {
        if (currentPlayer.equals(YELLOW)) {
            System.out.print("Válassz egy oszlopot (0-" + (board.getColumns() - 1) + "): ");
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine(); // Hibás bemenet törlése
                return -1;
            }
        } else {
            Random random = new Random();
            int column = random.nextInt(board.getColumns());
            System.out.println("A számítógép az alábbi oszlopot választotta: " + column);
            return column;
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getPlayerMoveForTest() {
        return getPlayerMove();
    }

    /**
     * Átvált a másik játékosra.
     */
    protected void switchPlayer() {
        currentPlayer = currentPlayer.equals(YELLOW) ? RED : YELLOW;
    }
}
