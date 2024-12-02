package org.example;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Board board;
    private String currentPlayer;
    private boolean gameOver;
    private final Scanner scanner;

    public Game(int rows, int columns) {
        this.board = new Board(rows, columns);
        this.currentPlayer = "Y"; // Sárga kezd
        this.gameOver = false;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.print("Add meg a játékosok nevét (Sárga játékos): ");
        String playerName = scanner.nextLine();
        System.out.println("A gép piros játékosként fog játszani.");

        while (!gameOver) {
            board.printBoard();
            System.out.println("Játékos " + currentPlayer + " lépése következik.");

            int column = -1;
            if (currentPlayer.equals("Y")) {
                System.out.print("Válassz egy oszlopot (0-" + (board.getColumns() - 1) + "): ");
                column = scanner.nextInt();
            } else {
                Random random = new Random();
                column = random.nextInt(board.getColumns());
                System.out.println("A gép választotta az oszlopot: " + column);
            }

            if (column < 0 || column >= board.getColumns()) {
                System.out.println("Érvénytelen oszlop. Kérlek, válassz egy érvényes oszlopot.");
                continue;
            }

            boolean success = board.placePiece(column, currentPlayer);
            if (!success) {
                System.out.println("Az oszlop tele van, próbálj meg egy másik oszlopot.");
                continue;
            }

            if (board.checkVictory(currentPlayer)) {
                board.printBoard();
                System.out.println("Játékos " + currentPlayer + " nyert!");
                gameOver = true;
            } else {
                // Váltás a következő játékosra
                currentPlayer = currentPlayer.equals("Y") ? "R" : "Y";
            }
        }

        // Játék állapot mentése
        board.saveToFile("game_state.txt");
    }
}
