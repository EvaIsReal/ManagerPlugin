/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;

import de.iv.manager.SQL.Database;
import de.iv.manager.SQL.SQLite;
import de.iv.manager.commands.ChatHistoryCommand;
import de.iv.manager.commands.CommandManager;
import de.iv.manager.commands.CommandManagerTicket;
import de.iv.manager.commands.TextChannelManageCommand;
import de.iv.manager.events.*;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.cc.BlackListManager;
import de.iv.manager.menus.plugins.CurrentPluginMenu;
import de.iv.manager.regions.RegionManager;
import de.iv.manager.text.TextChannelManager;
import de.iv.manager.utils.DataManager;
import de.iv.manager.utils.NoteStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Main extends JavaPlugin {


    private String currentStorage;
    private static Main instance;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    public static final ArrayList<Player> loggablePlayers = new ArrayList<>();
    public static final ArrayList<Player> teamPlayers = new ArrayList<>();
    private HashMap<String, Object> dataContainer = new HashMap<>();
    //public static final ArrayList<Player> ticketPlayers = new ArrayList<>();

    private RegionManager regionManager = new RegionManager();
    public ArrayList<String> chat = new ArrayList<>();
    private String language;
    private FileConfiguration messages;
    private FileConfiguration guiYml;
    private FileConfiguration settings;

    Logger logger = this.getLogger();

    @Override
    public void onLoad() {
        logger.info("PLUGIN LOADING...");
    }

    public void onEnable() {
        //Plugin startup logic
        instance = this;
        //ConfigManager configManager = new ConfigManager();
        FileManager.setup();
        DataManager dataManager = new DataManager();
        TextChannelManager textChannelManager = new TextChannelManager();

        try {
            language = FileManager.getConfig("settings.yml").getString("Language");
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        try {
            settings = FileManager.getConfig("settings.yml");
            messages = FileManager.getConfig(getDataFolder() + "/lang/" + language + "/messages.yml");
            guiYml = FileManager.getConfig(getDataFolder() + "/lang/" + language + "/gui.yml");
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }



        //Connect SQLite
        try {
            if (!FileManager.getConfig("settings.yml").getBoolean("UseMysql")) {
                try {
                    SQLite.connect();
                    logInfo("NOT USING MYSQL, USING SQLITE INSTEAD");

                    Bukkit.getConsoleSender().sendMessage(Vars.color(Vars.PREFIX + getMessageConfig().getString("Console.MysqlHint")));
                    setCurrentStorage("SQLite");
                    Database.setup();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //Connect Mysql
            }



            sendColoredConsoleMessage("&8#=====[&b"+ getDescription().getName() +"&8]=====");
            sendColoredConsoleMessage("&8#  &9Plugin Information");
            sendColoredConsoleMessage("&8#      &9Name: &r" + getDescription().getName());
            sendColoredConsoleMessage("&8#      &9Authors: &r" + getDescription().getAuthors());
            sendColoredConsoleMessage("&8#      &9Api-Version: &r" + getServer().getBukkitVersion());
            sendColoredConsoleMessage("&8#      &9Plugin-Version: &r" + getDescription().getVersion());
            sendColoredConsoleMessage("&8#      &9Storage Type: &r" + currentStorage);
            sendColoredConsoleMessage("&8#      &9Language: &r" + language);
            sendColoredConsoleMessage("&8#  &9More information/Support");
            sendColoredConsoleMessage("&8#      Github: &r" + getDescription().getWebsite());
            sendColoredConsoleMessage("&8#  &9Twitter:");
            sendColoredConsoleMessage("&8#  &9Discord: " + "&riv#3654");
            sendColoredConsoleMessage("&8#=========================");

            sendColoredConsoleMessage("&6" + guiYml.getString("GUI.MainMenu.Notes.name"));

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
        try {
            if(FileManager.getConfig("settings.yml").getBoolean("LogChatAfterShutdown")) FileManager.createChatLogFile();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getConsoleSender().sendMessage(Vars.color(Vars.PREFIX + "&cPlugin stopped!"));

    }


    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("manager").setExecutor(new CommandManager());

        getCommand("chathistory").setExecutor(new ChatHistoryCommand());

        getCommand("textchannel").setExecutor(new TextChannelManageCommand());

        getCommand("tickets").setExecutor(new CommandManagerTicket());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new CommandExecuteListener(), instance);
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

    private void sendColoredConsoleMessage(String msg) {
        Bukkit.getConsoleSender().sendMessage(Vars.color(msg));
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


    public ArrayList<String> getChat() {
        return chat;
    }

    public FileConfiguration getMessageConfig() {
        return messages;
    }

    public String getLanguage() {
        return language;
    }

    public FileConfiguration getGuiConfig() {
        return guiYml;
    }

    public FileConfiguration getSettingsConfig() {
        return settings;
    }

    public HashMap<String, Object> getDataContainer() {
        return dataContainer;
    }
}
