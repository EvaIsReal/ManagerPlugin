/*This class from the Project Manager was created by thoiv at 13.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.events;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.cc.BlackListManager;
import de.iv.manager.security.AdminSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ChatListener implements Listener {

    @EventHandler
    public void onMessageSent(AsyncPlayerChatEvent e) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        Main.getInstance().getChat().add( "[" + formatter.format(date) + "] " + e.getPlayer().getName() + " -> " +  ChatColor.stripColor(e.getMessage()));
        e.setCancelled(true);

        AdminSettings.handleHistory(e);

        for (String s : BlackListManager.getBlacklistedPhrases()) {
            if (e.getMessage().toLowerCase().contains(s.toLowerCase())) {
                e.getPlayer().sendMessage(Vars.color(Vars.ERROR + "Deine Nachricht enthält Aussagen, die im Chat verboten sind."));
                return;
            }
        }
        Bukkit.broadcastMessage(Vars.color("&a" + e.getPlayer().getName() + " &8| &7" + e.getMessage()));
        if (e.getMessage().equalsIgnoreCase(".setup")) e.setCancelled(true);
    }


}
