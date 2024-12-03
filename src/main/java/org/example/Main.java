package org.example;

import java.io.IOException;

/**
 * A Connect4 játék belépési pontja.
 */
public final class Main {
    private Main() {
        // Használati osztály példányosításának megakadályozása.
    }

    /**
     * Fő metódus, amely elindítja az alkalmazást.
     *
     * @param args a parancssori argumentumok
     */
    public static void main(final String[] args) throws IOException {
        final int rows = 6; // Alapértelmezett sorok száma
        final int columns = 7; // Alapértelmezett oszlopok száma
        Game game = new Game(rows, columns);
        game.startGame();
    }
}
