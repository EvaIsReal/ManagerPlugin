/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;


import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vars {


    public static String color(String s) {
        if(s != null)
            return ChatColor.translateAlternateColorCodes('&', s);
        else return null;
    }

    private static FileConfiguration cfg = Main.getInstance().getMessageConfig();

    public static String PREFIX = cfg.getString("MainPrefix");
    public static String ERROR = cfg.getString("ErrorPrefix");
    public static String GENERIC_ERROR = cfg.getString("ExceptionMessages.GenericError");
    public static String SERVER_LOG = cfg.getString("LogPrefix");

    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }






}
