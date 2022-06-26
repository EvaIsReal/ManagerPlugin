/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;

import de.iv.manager.SQL.SQLite;
import de.iv.manager.commands.ChatHistoryCommand;
import de.iv.manager.commands.CommandManager;
import de.iv.manager.events.*;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.cc.BlackListManager;
import de.iv.manager.menus.plugins.CurrentPluginMenu;
import de.iv.manager.regions.RegionManager;
import de.iv.manager.utils.DataManager;
import de.iv.manager.utils.NoteStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Main extends JavaPlugin {


    private String currentStorage;
    private static Main instance;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    public static final ArrayList<Player> loggablePlayers = new ArrayList<>();

    private RegionManager regionManager = new RegionManager();

    Logger logger = this.getLogger();

    @Override
    public void onLoad() {
        logger.info("PLUGIN LOADING...");
    }

    public void onEnable() {
        //Plugin startup logic
        instance = this;
        //ConfigManager configManager = new ConfigManager();
        FileManager fileManager = new FileManager();
        DataManager dataManager = new DataManager();

        FileManager.registerConfigs();


        //Connect SQLite
        try {
            if (!FileManager.getConfig("settings.yml").getBoolean("UseMysql")) {
                try {
                    SQLite.connect();
                    logInfo("NOT USING MYSQL, USING SQLITE INSTEAD");
                    Bukkit.getConsoleSender().sendMessage(Vars.color(Vars.PREFIX + "&7Falls du eine Mysql-Datenbank benutzen willst, kannst du diese in der " +
                            "&9&oSettings.yml&7-Datei aktivieren."));
                    Bukkit.getConsoleSender().sendMessage(Vars.color(Vars.PREFIX + "&7Trage die Anmeldedaten in die &9&oMysql.yml&7-Datei ein."));
                    setCurrentStorage("SQLite");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //Connect Mysql
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        //Load dirs in ServerManager/persistentData/
        try {
            NoteStorageUtil.loadNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            CurrentPluginMenu.loadDisabledPlugins();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BlackListManager.loadPhrases();

        //Setting saving timer for persistent data
        try {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        NoteStorageUtil.saveNotesPersistently();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskTimer(instance, 0, 20L * 60 * FileManager.getConfig("settings.yml")
                            .getInt("UpdatePersistentData"));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        //Registering commands and listeners
        registerCommands();
        registerListeners();

        Bukkit.getConsoleSender().sendMessage(Vars.color(Vars.PREFIX + "&aPlugin erfolgreich geladen!"));
    }


    public void onDisable() {
        //Plugin shutdown logic
        SQLite.disconnect();
        try {
            NoteStorageUtil.saveNotesPersistently();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            CurrentPluginMenu.pushToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlackListManager.storeBlacklist();

    }


    private void registerCommands() {
        getCommand("manager").setExecutor(new CommandManager());

        getCommand("chathistory").setExecutor(new ChatHistoryCommand());
        getCommand("chathistory").setTabCompleter(new ChatHistoryCommand());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new MenuListener(), instance);
        pm.registerEvents(new ChatListener(), instance);
        //pm.registerEvents(new RegionListener(instance), instance);
        pm.registerEvents(new ServerPingListener(), instance);
        pm.registerEvents(new LoginListener(), instance);

    }

    //Utility methods

    public static void logInfo(String x) {
        getInstance().getLogger().info(x);
    }

    public static void logWarn(String x) {
        getInstance().getLogger().warning(x);
    }


    public static Main getInstance() {
        return instance;
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (playerMenuUtilityMap.containsKey(p)) {
            return playerMenuUtilityMap.get(p);
        } else {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);
            return playerMenuUtility;
        }

    }

    public String getCurrentStorage() {
        return currentStorage;
    }

    public void setCurrentStorage(String currentStorage) {
        this.currentStorage = currentStorage;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }
}
