/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Vars {


    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static FileConfiguration cfg = FileManager.getConfig("messages.yml");

    public static String PREFIX = cfg.getString("MainPrefix");
    public static String ERROR = cfg.getString("ErrorPrefix");
    public static String GENERIC_ERROR;
    public static String SERVER_LOG = cfg.getString("LogPrefix");



}
