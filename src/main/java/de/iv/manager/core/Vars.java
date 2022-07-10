/*Plugin was created by Iv
 *GitHub:
 *Discord:
 */

package de.iv.manager.core;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Vars {


    public static String color(String s) {
        if(s != null)
            return ChatColor.translateAlternateColorCodes('&', s);
        else return null;
    }

    public static int randomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max +1);
    }

    public static String padInt(int i, int length) {
        return String.format("%0" + length + "d", i);
    }

    private static FileConfiguration cfg = Main.getInstance().getMessageConfig();

    public static String PREFIX = cfg.getString("MainPrefix");
    public static String TICKET_PREFIX = cfg.getString("TicketPrefix");
    public static String ERROR = cfg.getString("ErrorPrefix");
    public static String GENERIC_ERROR = cfg.getString("ExceptionMessages.GenericError");
    public static String SERVER_LOG = cfg.getString("LogPrefix");
    public static String TEAM_PREFIX = cfg.getString("TeamPrefix");

    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }


    public static void sendActionBar(String message, Player... players) {
        for (Player player : players) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }


}
