/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static ConfigManager instance;

    public ConfigManager() {
        instance = this;
        loadFiles();
    }


    private Config messages = new Config("messages.yml", Main.getInstance().getDataFolder());
    private Config mysql = new Config("MySQL.yml", Main.getInstance().getDataFolder());
    private Config settings = new Config("settings.yml", Main.getInstance().getDataFolder());

    public Config getBannedPlayers() {
        return bannedPlayers;
    }

    private Config bannedPlayers = new Config("bannedPlayers.yml", Main.getInstance().getDataFolder());


    private void loadSettings() {
        FileConfiguration cfg = settings.toFileConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("Settings.threading.updatePersistentData", 1);
        cfg.addDefault("Settings.Data.useMysql", false);

        settings.save();
    }

    private void loadMessages() {
        FileConfiguration cfg = messages.toFileConfiguration();
        cfg.options().copyDefaults(true);
    //Console-----------------------------------------------------------------------------------------
        cfg.addDefault("Console.Prefix.Main_Prefix", "&b&lServer Manager &8| &7 ");
        cfg.addDefault("Console.Prefix.CommandLogger", "&9&lServer Log &8|&7 ");


        cfg.addDefault("Console.Commands.Exceptions.Prefix", "&4&lERROR &8| &c");
        cfg.addDefault("Console.Commands.Exceptions.InvalidArgumentLengthException",
                "&4&lERROR &8| &cDie angegebene Argumentenzahl ist nicht korrekt! Bitte überprüfe die Syntax.");
        cfg.addDefault("Console.Commands.Exceptions.InvalidPermissionsException",
                "&4&lERROR &8| &cDazu hast du keine Berechtigung!");
        cfg.addDefault("Console.Commands.Exceptions.PlayerNullpointerException",
                "&4&lERROR &8| &cDer angegebene Spieler wurde nicht gefunden.");
        cfg.addDefault("Console.Commands.Exceptions.CommandNullpointerException",
                "&4&lERROR &8| &cDer angegebene Befehl wurde nicht gefunden!");

    //Game---------------------------------------------------------------------------------------------

        cfg.addDefault("Game.Chat", "chatFormat");
        cfg.addDefault("Game.Chat.enableChatFormat", true);

        cfg.addDefault("Game.Chat.chatFormat.player.value", "&7%player_name% &8| &7");

        cfg.addDefault("Game.Chat.chatFormat.mod.value", "&2%player_name% &8| &7");

        cfg.addDefault("Game.Chat.chatFormat.admin.value", "&4%player_name% &8| &7");

        cfg.addDefault("Game.Chat.chatFormat.example1.value", "[Example1] %player_name% &8| &7");
        cfg.addDefault("Game.Chat.chatFormat.example2.value", "[Example2] %player_name% &8| &7");
        cfg.addDefault("Game.Chat.chatFormat.example3.value", "[Example3] %player_name% &8| &7");

    //Out------------------------------------------------------------------------------------------------

        cfg.addDefault("Out.Server.Motd", "ServerManager by Iv");

        messages.save();
    }

    private void loadMySQL() {
        FileConfiguration cfg = mysql.toFileConfiguration();
        cfg.options().copyDefaults(true);

        cfg.addDefault("Host", "localhost");
        cfg.addDefault("User", "root");
        cfg.addDefault("Database", "manager");
        cfg.addDefault("Password", "admin");
        cfg.addDefault("Port", 3306);

        mysql.save();
    }

    public void loadFiles() {
        loadMySQL();
        loadMessages();
        loadSettings();
    }

    public Config getMysql() {
        return mysql;
    }


    public Config getMessages() {
        return messages;
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public Config getSettings() {
        return settings;
    }
}
