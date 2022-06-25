package de.iv.manager.SQL;


import de.iv.manager.core.FileManager;
import de.iv.manager.core.Main;

import java.sql.*;

/**
 * This Class provides Methods for communicating with java.sql based Databases
 * <br>
 * Can be used on both Mysql and SQLite
 */
public class Database {

    private static Statement statement;

    private static Connection connection() {
        Connection connection;
        return (!FileManager.getConfig("settings.yml").getBoolean("Settings.Data.useMysql") ? connection = SQLite.connection : null);
    }

    public static void update(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            Main.logWarn(e.getMessage());
        }
    }

    public static ResultSet query(String sql) throws SQLException {
        return statement.executeQuery(sql);
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
