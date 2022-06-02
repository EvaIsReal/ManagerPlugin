/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;

import de.iv.manager.SQL.SQLite;
import de.iv.manager.commands.CommandManager;
import de.iv.manager.events.ChatFormatListener;
import de.iv.manager.events.MenuListener;
import de.iv.manager.events.LoginListener;
import de.iv.manager.events.ServerPingListener;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.plugins.CurrentPluginMenu;
import de.iv.manager.regions.RegionListener;
import de.iv.manager.regions.RegionManager;
import de.iv.manager.utils.DataManager;
import de.iv.manager.utils.NoteStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        ConfigManager configManager = new ConfigManager();
        FileManager fileManager = new FileManager();
        DataManager dataManager = new DataManager();


        //Connect SQLite
        if(!ConfigManager.getInstance().getSettings().toFileConfiguration().getBoolean("Settings.Data.useMysql")) {
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

        //Setting saving timer for persistent data
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    NoteStorageUtil.saveNotesPersistently();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(instance, 0, 20L *60*ConfigManager.
                getInstance().getSettings().toFileConfiguration().getInt("Settings.threading.updatePersistentData"));


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

    }


    private void registerCommands() {
        getCommand("manager").setExecutor(new CommandManager());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new MenuListener(), instance);
        pm.registerEvents(new ChatFormatListener(), instance);
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
        if(playerMenuUtilityMap.containsKey(p)) {
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
