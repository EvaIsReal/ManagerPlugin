package de.iv.manager.SQL;

import de.iv.manager.core.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {

    public static Connection connection;

    private static File dbFile = new File(Main.getInstance().getDataFolder() + "/data", "database.db");

    public static void connect() throws SQLException {
        if(connection != null) return;
        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();

                String jdbcUrl = "jdbc:sqlite:" + dbFile.getPath();
                connection = DriverManager.getConnection(jdbcUrl);

                Main.logInfo("SQLite verbunden!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("BEIM SCHLIEßEN DER DATENBANK IST EIN FEHLER AUFGETRETEN!");
                System.out.println(e.getMessage());
            }
        }
    }



}
