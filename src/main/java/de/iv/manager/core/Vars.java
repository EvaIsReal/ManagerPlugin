/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;

public class Vars {


    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String PREFIX = ConfigManager.getInstance().getMessages().toFileConfiguration().getString("Console.Prefix.Main_Prefix");
    public static String ERROR = ConfigManager.getInstance().getMessages().toFileConfiguration().getString("Console.Commands.Exceptions.Prefix");
    public static String SERVER_LOG = ConfigManager.getInstance().getMessages().toFileConfiguration().getString("Console.Prefix.CommandLogger");



}
