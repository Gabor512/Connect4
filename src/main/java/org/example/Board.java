package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Board {
    private final int rows;
    private final int columns;
    private final String[][] board;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = " "; // Üres mező
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
        }
    }

    public boolean placePiece(int column, String piece) {
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][column].equals(" ")) {
                board[i][column] = piece;
                return true;
            }
        }
        return false;
    }

    public boolean checkVictory(String piece) {
        // Ellenőrzés vízszintesen, függőlegesen, átlósan
        return checkHorizontal(piece) || checkVertical(piece) || checkDiagonal(piece);
    }

    private boolean checkHorizontal(String piece) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (board[i][j].equals(piece) &&
                        board[i][j + 1].equals(piece) &&
                        board[i][j + 2].equals(piece) &&
                        board[i][j + 3].equals(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(String piece) {
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j].equals(piece) &&
                        board[i + 1][j].equals(piece) &&
                        board[i + 2][j].equals(piece) &&
                        board[i + 3][j].equals(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(String piece) {
        // Főátló
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (board[i][j].equals(piece) &&
                        board[i + 1][j + 1].equals(piece) &&
                        board[i + 2][j + 2].equals(piece) &&
                        board[i + 3][j + 3].equals(piece)) {
                    return true;
                }
            }
        }
        // Mellékátló
        for (int i = 3; i < rows; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (board[i][j].equals(piece) &&
                        board[i - 1][j + 1].equals(piece) &&
                        board[i - 2][j + 2].equals(piece) &&
                        board[i - 3][j + 3].equals(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    writer.write(board[i][j]);
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Hiba a fájl mentésekor: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < rows; i++) {
                String line = reader.readLine();
                for (int j = 0; j < columns; j++) {
                    board[i][j] = String.valueOf(line.charAt(j));
                }
            }
        } catch (IOException e) {
            System.out.println("Hiba a fájl betöltésekor: " + e.getMessage());
        }
    }
}
