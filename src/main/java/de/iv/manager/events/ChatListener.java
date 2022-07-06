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
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;

public class ChatListener implements Listener {


    @EventHandler
    public void onMessageSent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        HashMap<Player, TextChannel> tcMap = Main.textChannelMap;

        handleChat(e, tcMap.get(p));
        /*
            // Team tc
            if(e.getMessage().startsWith(".") && Main.teamPlayers.contains(p)) {
                for (Player n : Main.teamPlayers) {
                    n.sendMessage(Vars.color(Vars.TEAM_PREFIX + e.getMessage().substring(1)).replace("%player%", ChatColor.GRAY + p.getName()));
                }
            } else {
                //  Public tc
                Bukkit.broadcastMessage(Vars.color(ChatColor.GREEN + e.getPlayer().getName() + "&8 | &7" + e.getMessage()));
            }
         */
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        Main.getInstance().getChat().add( "[" + formatter.format(date) + "] " + e.getPlayer().getName() + " -> " +  ChatColor.stripColor(e.getMessage()));

        AdminSettings.handleHistory(e);

        for (String s : BlackListManager.getBlacklistedPhrases()) {
            if (e.getMessage().toLowerCase().contains(s.toLowerCase())) {
                e.getPlayer().sendMessage(Vars.color(Vars.ERROR + "Deine Nachricht enthält Aussagen, die im Chat verboten sind."));
                return;
            }
        }
        if (e.getMessage().equalsIgnoreCase(".setup")) e.setCancelled(true);

    }

    public static void handleChat(AsyncPlayerChatEvent e, TextChannel channel) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        channel.getRecipients().forEach(n -> {
            n.sendMessage(Vars.color(channel.getPrefix().replace("%player%", p.getName()) + e.getMessage()));
        });


    }


}
