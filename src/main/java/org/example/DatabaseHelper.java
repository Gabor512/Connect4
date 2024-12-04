package org.example;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:connect4.db";
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS high_scores (" +
                    "player_name TEXT PRIMARY KEY, " +
                    "wins INTEGER)";
    private static final String INSERT_SCORE_SQL =
            "INSERT OR REPLACE INTO high_scores (player_name, wins) VALUES (?, ?)";
    private static final String SELECT_SCORES_SQL =
            "SELECT * FROM high_scores ORDER BY wins DESC";
    private static final String DELETE_ALL_SQL = "DELETE FROM high_scores";

    public DatabaseHelper() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(CREATE_TABLE_SQL); // Táblázat létrehozása, ha még nem létezik
            }
        } catch (SQLException e) {
            System.out.println("Hiba az adatbázis inicializálásakor: " + e.getMessage());
        }
    }

    // Új győzelem hozzáadása vagy meglévő frissítése
    public void addWin(String playerName) {
        int currentWins = getCurrentWins(playerName);
        System.out.println("Adding win for player: " + playerName + ", current wins: " + currentWins);
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SCORE_SQL)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, currentWins + 1);
            pstmt.executeUpdate();
            System.out.println("Win added successfully for player: " + playerName);
        } catch (SQLException e) {
            System.out.println("Hiba a győzelem hozzáadása során: " + e.getMessage());
        }
    }

    // Játékos aktuális győzelmeinek számának lekérdezése
    int getCurrentWins(String playerName) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT wins FROM high_scores WHERE player_name = ?")) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("wins");
            }
        } catch (SQLException e) {
            System.out.println("Hiba a győzelem lekérdezésekor: " + e.getMessage());
        }
        return 0; // Ha nem találjuk a játékost, visszaadjuk 0-t
    }

    // High score táblázat megjelenítése
    public void displayScores() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_SCORES_SQL)) {
            System.out.println("High Score Table:");
            while (rs.next()) {
                System.out.println(rs.getString("player_name") + ": " + rs.getInt("wins") + " wins");
            }
        } catch (SQLException e) {
            System.out.println("Hiba a high score táblázat megjelenítésekor: " + e.getMessage());
        }
    }

    // Adatbázis tartalmának törlése (tesztekhez hasznos)
    public void resetDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(DELETE_ALL_SQL);
            System.out.println("Adatbázis tartalma törölve teszteléshez.");
        } catch (SQLException e) {
            System.out.println("Hiba az adatbázis tisztítása során: " + e.getMessage());
        }
    }
}
