package de.iv.manager.events;

import de.iv.manager.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class LoginListener implements Listener {

    @EventHandler
    public void onPreLogin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        File playerFile = new File(Main.getInstance().getDataFolder() + "/userData", p.getName() + ".user");
        if(!playerFile.exists()) {
            playerFile.getParentFile().mkdirs();
            try {
                playerFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //write to file



    }

}
