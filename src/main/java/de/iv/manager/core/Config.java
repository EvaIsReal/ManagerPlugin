/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private FileConfiguration cfg;
    private File file;
    private String name;
    Main instance = Main.getInstance();

    public Config(String name, File path) {
        file = new File(path, name);

        if(!file.exists()) {
            path.mkdir();
            try {
                file.createNewFile();
            }catch (IOException e) {
                instance.getLogger().warning(e.getMessage());
            }
        }

        cfg = new YamlConfiguration();

        try {
            cfg.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            instance.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }

    }

    public void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            instance.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            cfg.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            // TODO Auto-generated catch block
            instance.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }
    }

    public FileConfiguration toFileConfiguration() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }


}