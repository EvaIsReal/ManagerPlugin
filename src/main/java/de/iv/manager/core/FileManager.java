/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.core;

import de.iv.manager.config.ConfigurationFile;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {

    private static ArrayList<ConfigurationFile> configs = new ArrayList<>();


    public FileManager() {
        createPersistentDataFolder();
    }

    public void createPersistentDataFolder() {
        File folder = new File(Main.getInstance().getDataFolder(), "persistentData");
        if(!folder.exists()) folder.mkdirs();
    }

    public static void registerConfigs() {
        ConfigurationFile msg = new ConfigurationFile("messages.yml", Main.getInstance().getDataFolder());
        configs.add(msg);

        ConfigurationFile stt = new ConfigurationFile("settings.yml", Main.getInstance().getDataFolder());
        configs.add(stt);


    }
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
        for(File file : Arrays.stream(Main.getInstance().getDataFolder().listFiles()).toList()) {
            if(file.getName().equals(fileName)) {
                cfg = YamlConfiguration.loadConfiguration(file);
                return cfg;
            }
        }
        return null;
    }


    public static File getSource(String fileName) {
        File file;
        for(File n : Arrays.stream(Main.getInstance().getDataFolder().listFiles()).toList()) {
            if(n.getName().equals(fileName)) {
                file = n;
                return file;
            }
        }

        return null;
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

    public static ArrayList<ConfigurationFile> getConfigs() {
        return configs;
    }
}
