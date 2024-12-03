package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A Connect4 játék tábláját képviseli.
 */
public final class Board {
    /**
     * A tábla üres mezőit jelölő szimbólum.
     */
    private static final String EMPTY_CELL = " ";
    /**
     * A győzelemhez szükséges egyforma bábuk száma.
     */
    private static final int WINNING_SEQUENCE_LENGTH = 4;

    /**
     * A tábla sorainak száma.
     */
    private final int rows;
    /**
     * A tábla oszlopainak száma.
     */
    private final int columns;
    /**
     * A tábla belső reprezentációja.
     */
    private final String[][] board;

    /**
     * Inicializálja a táblát a megadott méretekkel.
     *
     * @param rows    a tábla sorainak száma
     * @param columns a tábla oszlopainak száma
     */
    public Board(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new String[rows][columns];
        initializeBoard();
    }

    /**
     * Kitölti a táblát üres mezőkkel.
     */
    private void initializeBoard() {
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                board[rowIndex][colIndex] = EMPTY_CELL;
            }
        }
    }

    /**
     * Visszaadja a tábla sorainak számát.
     *
     * @return a sorok száma
     */
    public int getRows() {
        return rows;
    }

    /**
     * Visszaadja a tábla oszlopainak számát.
     *
     * @return az oszlopok száma
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Kiírja a tábla aktuális állapotát a konzolra.
     */
    public void printBoard() {
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                System.out.print("|" + board[rowIndex][colIndex]);
            }
            System.out.println("|");
        }
    }

    /**
     * Elhelyez egy bábut a megadott oszlopban.
     *
     * @param column az oszlop, ahova a bábut helyezni kell
     * @param piece  a lehelyezendő bábú
     * @return igaz, ha sikerült elhelyezni a bábut, egyébként hamis
     */
    public boolean placePiece(final int column, final String piece) {
        if (column < 0 || column >= columns) {  // Ellenőrzés, hogy az oszlopindex érvényes-e
            return false; // Ha érvénytelen oszlopot adunk meg, false-t adunk vissza
        }
        for (int rowIndex = rows - 1; rowIndex >= 0; rowIndex--) {
            if (board[rowIndex][column].equals(EMPTY_CELL)) {
                board[rowIndex][column] = piece;
                return true;
            }
        }
        return false;
    }

    /**
     * Ellenőrzi, hogy az adott bábú győzelmet ért-e el.
     *
     * @param piece a vizsgálandó bábú
     * @return igaz, ha győzelem történt, különben hamis
     */
    public boolean checkVictory(final String piece) {
        return checkHorizontal(piece) || checkVertical(piece) || checkDiagonal(piece);
    }

    private boolean checkHorizontal(final String piece) {
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int colIndex = 0; colIndex <= columns - WINNING_SEQUENCE_LENGTH; colIndex++) {
                if (isWinningSequence(piece, board[rowIndex][colIndex], board[rowIndex][colIndex + 1],
                        board[rowIndex][colIndex + 2], board[rowIndex][colIndex + 3])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(final String piece) {
        for (int rowIndex = 0; rowIndex <= rows - WINNING_SEQUENCE_LENGTH; rowIndex++) {
            for (int colIndex = 0; colIndex < columns; colIndex++) {
                if (isWinningSequence(piece, board[rowIndex][colIndex], board[rowIndex + 1][colIndex],
                        board[rowIndex + 2][colIndex], board[rowIndex + 3][colIndex])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal(final String piece) {
        for (int rowIndex = 0; rowIndex <= rows - WINNING_SEQUENCE_LENGTH; rowIndex++) {
            for (int colIndex = 0; colIndex <= columns - WINNING_SEQUENCE_LENGTH; colIndex++) {
                if (isWinningSequence(piece,
                        board[rowIndex][colIndex],
                        board[rowIndex + 1][colIndex + 1],
                        board[rowIndex + 2][colIndex + 2],
                        board[rowIndex + 3][colIndex + 3])) {
                    return true;
                }
            }
        }
        for (int rowIndex = WINNING_SEQUENCE_LENGTH - 1; rowIndex < rows; rowIndex++) {
            for (int colIndex = 0; colIndex <= columns - WINNING_SEQUENCE_LENGTH; colIndex++) {
                if (isWinningSequence(piece,
                        board[rowIndex][colIndex],
                        board[rowIndex - 1][colIndex + 1],
                        board[rowIndex - 2][colIndex + 2],
                        board[rowIndex - 3][colIndex + 3])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isWinningSequence(final String piece, final String... cells) {
        for (String cell : cells) {
            if (!cell.equals(piece)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mentés fájlba a tábla állapotáról.
     *
     * @param filename a fájl neve
     */
    public void saveToFile(final String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
                for (int colIndex = 0; colIndex < columns; colIndex++) {
                    writer.write(board[rowIndex][colIndex]);
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Hiba a játék mentésekor: " + e.getMessage());
            throw e;  // Az IOException újradobása

        }
    }

    /**
     * A tábla állapotának betöltése fájlból.
     *
     * @param filename a fájl neve
     */
    public void loadFromFile(final String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
                String line = reader.readLine();
                for (int colIndex = 0; colIndex < columns; colIndex++) {
                    board[rowIndex][colIndex] = String.valueOf(line.charAt(colIndex));
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba a játék betöltésekor: " + e.getMessage());
            throw e;  // Az IOException újradobása

        }
    }

    public String[][] getBoard() {
        return board;
    }
}
