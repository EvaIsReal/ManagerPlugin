/*This class from the Project Manager was created by thoiv at 13.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.events;

import de.iv.manager.core.ConfigManager;
import de.iv.manager.core.Vars;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.imageio.stream.FileCacheImageInputStream;

public class ChatFormatListener implements Listener {

    @EventHandler
    public void onMessageSent(AsyncPlayerChatEvent e) {
        FileConfiguration cfg = ConfigManager.getInstance().getMessages().toFileConfiguration();
        e.setCancelled(true);

        Bukkit.broadcastMessage(Vars.color("&a" + e.getPlayer().getName() + " &8| &7" + e.getMessage()));
        if(e.getMessage().equalsIgnoreCase(".setup")) e.setCancelled(true);
    }


}
