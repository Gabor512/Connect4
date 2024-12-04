package org.example;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public final class Game {
    private final Board board;
    private String currentPlayer;
    private boolean gameOver;
    private final Scanner scanner;
    private final DatabaseHelper databaseHelper;

    public static final String YELLOW = "Z";
    public static final String RED = "O";

    public Game(final int rows, final int columns, DatabaseHelper databaseHelper, Scanner scanner) {
        this.board = new Board(rows, columns);
        this.currentPlayer = YELLOW;
        this.gameOver = false;
        this.scanner = scanner;
        this.databaseHelper = databaseHelper;
    }

    public Game(final int rows, final int columns, DatabaseHelper databaseHelper) {
        this(rows, columns, databaseHelper, new Scanner(System.in));
    }

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
                System.out.println("Érvénytelen lépés. Válassz másikat.");
                continue;
            }

            if (board.checkVictory(currentPlayer)) {
                board.printBoard();
                System.out.println("Játékos " + currentPlayer + " nyert!");
                gameOver = true;
                if (currentPlayer.equals(YELLOW)) {
                    databaseHelper.addWin(playerName); // Csak az emberi játékos győzelme kerül mentésre
                }
            } else {
                switchPlayer();
            }
        }

        board.saveToFile("game_state.txt");
    }

    int getPlayerMove() {
        if (currentPlayer.equals(YELLOW)) {
            System.out.print("Válassz egy oszlopot (0-" + (board.getColumns() - 1) + "): ");
            try {
                int column = scanner.nextInt();
                if (column < 0 || column >= board.getColumns()) {
                    return -1; // Ha az oszlop kívül esik a tartományon, -1-et adunk vissza
                }
                return column;
            } catch (Exception e) {
                scanner.nextLine(); // Rossz bemenetet ürítjük
                return -1; // Ha a bemenet nem szám, -1-et adunk vissza
            }
        } else {
            Random random = new Random();
            int column = random.nextInt(board.getColumns());
            System.out.println("A számítógép az alábbi oszlopot választotta: " + column);
            return column;
        }
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals(YELLOW) ? RED : YELLOW;
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
