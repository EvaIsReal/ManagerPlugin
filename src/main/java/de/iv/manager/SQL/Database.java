package de.iv.manager.SQL;


import de.iv.manager.core.FileManager;
import de.iv.manager.core.Main;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.sql.*;

/**
 * This Class provides Methods for communicating with java.sql based Databases
 * <br>
 * Can be used on both Mysql and SQLite
 */
public class Database {


    private static Connection connection() {
        Connection connection;
        try {
            return (!FileManager.getConfig("settings.yml").getBoolean("Settings.Data.useMysql") ? connection = SQLite.connection : null);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static Statement statement;

    static {
        try {
            statement = SQLite.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void update(String sql) {
        try {
            statement.execute(sql);
            FileManager.dbLog(sql);
        } catch (SQLException e) {
            Main.logWarn(e.getMessage());
        }
    }

    public static ResultSet query(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public static void setup() {
        update("CREATE TABLE IF NOT EXISTS users(userName VARCHAR(16))");
    }

    public static PreparedStatement prepareStatement(String sql) {
        try {
            return connection().prepareStatement(sql);
        } catch (SQLException e) {
            Main.logWarn(e.getMessage());
        }
        return null;
    }

}
