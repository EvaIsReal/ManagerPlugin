package de.iv.manager.config;

import de.iv.manager.core.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.NoSuchFileException;

public class ConfigurationFile {

    private File source;
    private FileConfiguration cfg;

    public ConfigurationFile(String resourceName, File path) {
        try {
            createConfigFromResource(resourceName, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createConfigFromResource(String name, File path) throws IOException {
        InputStream in = Main.getInstance().getResource(name);
        source = new File(path, name);
        if(in == null) {
            throw new NoSuchFileException(name);
        }
        FileUtils.copyInputStreamToFile(in, source);


        if(!source.exists()) {
            source.getParentFile().mkdirs();
            Main.getInstance().saveResource(source.getPath(), false);
        }
        cfg = new YamlConfiguration();
        try {
            cfg.load(source);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            cfg.save(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createConfigFromSource(File source) throws IOException {
        if(!source.exists()) {
            source.getParentFile().mkdirs();
            Main.getInstance().saveResource(source.getPath(), false);
        }
        cfg = new YamlConfiguration();
        try {
            cfg.load(source);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }

    public File getSource() {
        return source;
    }

    public FileConfiguration toFileConfiguration() {
        return cfg;
    }
}
