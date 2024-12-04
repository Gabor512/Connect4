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

    public Game(final int rows, final int columns, DatabaseHelper databaseHelper) {
        this.board = new Board(rows, columns);
        this.currentPlayer = YELLOW;
        this.gameOver = false;
        this.scanner = new Scanner(System.in);
        this.databaseHelper = databaseHelper;
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
                System.out.println("Ez az oszlop tele van. Válassz másikat.");
                continue;
            }

            if (board.checkVictory(currentPlayer)) {
                board.printBoard();
                System.out.println("Játékos " + currentPlayer + " nyert!");
                gameOver = true;
                databaseHelper.addWin(playerName);  // Hozzáadjuk a győztest az adatbázishoz
            } else {
                switchPlayer();
            }
        }

        board.saveToFile("game_state.txt");
    }

    private int getPlayerMove() {
        if (currentPlayer.equals(YELLOW)) {
            System.out.print("Válassz egy oszlopot (0-" + (board.getColumns() - 1) + "): ");
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                return -1;
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
}
