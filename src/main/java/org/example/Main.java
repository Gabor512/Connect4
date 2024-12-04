package org.example;

import java.io.IOException;
import java.util.Scanner;

public final class Main {
    private Main() {
        // Használati osztály példányosításának megakadályozása.
    }

    public static void main(final String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DatabaseHelper databaseHelper = new DatabaseHelper(); // Adatbázis kapcsolat inicializálása

        while (true) {
            System.out.println("Válassz egy opciót:");
            System.out.println("1 - Új játék indítása");
            System.out.println("2 - High Score tábla megjelenítése");
            System.out.println("0 - Kilépés");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Eltávolítjuk az enter karaktert

            if (choice == 1) {
                Game game = new Game(6, 7, databaseHelper);
                game.startGame();
            } else if (choice == 2) {
                databaseHelper.displayScores(); // Megjelenítjük az adatbázisban tárolt győzelmeket
            } else if (choice == 0) {
                System.out.println("Kilépés...");
                break;
            } else {
                System.out.println("Érvénytelen választás, próbáld újra.");
            }
        }
    }
}
