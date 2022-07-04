/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.core;

import de.iv.manager.config.ConfigurationFile;
import de.iv.manager.utils.DataManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FileManager {

    private static ArrayList<ConfigurationFile> configs = new ArrayList<>();

    static LocalDate date = LocalDate.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);



    public static String getLocalizedDate() {
        return formatter.format(date).replace(".", "_");
    }

    public static void setup() {
        createPersistentDataFolder();
        initLog();
        registerConfigs();
    }

    public static void createPersistentDataFolder() {
        File folder = new File(Main.getInstance().getDataFolder(), "persistentData");
        if(!folder.exists()) folder.mkdirs();
    }

    public static void registerConfigs() {
        ConfigurationFile msgEn = new ConfigurationFile("lang/en/messages.yml", Main.getInstance().getDataFolder());
        configs.add(msgEn);
        ConfigurationFile msgDe = new ConfigurationFile("lang/de/messages.yml", Main.getInstance().getDataFolder());
        configs.add(msgDe);

        ConfigurationFile stt = new ConfigurationFile("settings.yml", Main.getInstance().getDataFolder());
        configs.add(stt);

        ConfigurationFile guiEn = new ConfigurationFile("lang/en/gui.yml", Main.getInstance().getDataFolder());
        configs.add(guiEn);
        ConfigurationFile guiDe = new ConfigurationFile("lang/de/gui.yml", Main.getInstance().getDataFolder());
        configs.add(guiDe);

    }
    //Not using it but too afraid to delete ,_,

    /*
    public static FileConfiguration getConfig(String fileName) {
        for (ConfigurationFile config : configs) {
            if(config.getSource().getName().equals(fileName)) {
                return config.toFileConfiguration();
            }
        }
        return null;
    }
     */

    public static FileConfiguration getConfig(String fileName) throws IOException, InvalidConfigurationException {
        FileConfiguration cfg;
        String lang = Main.getInstance().getLanguage();
        File langFile;

        if(fileName.contains("messages.yml")) {
            if(Main.getInstance().getLanguage().equals("en")) {
                langFile = new File(Main.getInstance().getDataFolder() + "/lang/en", "messages.yml");
                cfg = YamlConfiguration.loadConfiguration(langFile);
                return cfg;

            } else if(Main.getInstance().getLanguage().equals("de")) {
                langFile = new File(Main.getInstance().getDataFolder() + "/lang/de", "messages.yml");
                cfg = YamlConfiguration.loadConfiguration(langFile);
                return cfg;
            }

        } else if(fileName.contains("gui.yml")) {
            if(Main.getInstance().getLanguage().equals("en")) {
                langFile = new File(Main.getInstance().getDataFolder() + "/lang/en", "gui.yml");
                cfg = YamlConfiguration.loadConfiguration(langFile);
                return cfg;

            } else if(Main.getInstance().getLanguage().equals("de")) {
                langFile = new File(Main.getInstance().getDataFolder() + "/lang/de", "gui.yml");
                cfg = YamlConfiguration.loadConfiguration(langFile);
                return cfg;
            }

        }

        for(File file : Arrays.stream(Main.getInstance().getDataFolder().listFiles()).toList()) {
            if(file.getName().equals(fileName)) {
                cfg = YamlConfiguration.loadConfiguration(file);
                return cfg;
            }
        }
        return null;
    }


    public static File getSource(String path, String fileName) {
        return new File(path, fileName);
    }


    public static void save(String fileName) {
        FileConfiguration cfg;
        for(File file : Arrays.stream(Main.getInstance().getDataFolder().listFiles()).toList()) {
            if(file.getName().equals(fileName)) {
                cfg = YamlConfiguration.loadConfiguration(file);
                try {
                    cfg.save(file);
                    System.out.println("SAVED CONFIG " + file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void initLog() {
        Date date = new Date(System.currentTimeMillis());
        File logFile = new File(DataManager.SER_PATH, "db.log_" + getLocalizedDate());
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void dbLog(String sql) {
        File logFile = new File(DataManager.SER_PATH, "db.log_" + getLocalizedDate());
        try {
            FileWriter writer = new FileWriter(logFile);
            writer.write( "[" + getLocalizedDate() + "]: "+ sql + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createChatLogFile() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        File chatLog = new File(DataManager.SER_PATH, "chat_log_" + formatter.format(date).replace(".", "_") + "_" + Main.getInstance().getChat().size() + ".log");

        try {
            if(!chatLog.exists()) chatLog.createNewFile();
            if(Main.getInstance().getChat().isEmpty()) return;
            FileWriter writer = new FileWriter(chatLog);
            for(String s : Main.getInstance().getChat()) {
                writer.write(s + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static ArrayList<ConfigurationFile> getConfigs() {
        return configs;
    }
}
