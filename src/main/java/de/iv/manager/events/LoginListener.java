package de.iv.manager.events;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class LoginListener implements Listener {

    @EventHandler
    public void onPreLogin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Main.textChannelMap.put(p, TextChannel.PUBLIC);


        if(p.hasPermission("manager.teamMember")) Main.teamPlayers.add(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                Vars.sendActionBar(Vars.color(Main.textChannelMap.get(p).toString()), p);
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);


        Main.teamPlayers.forEach(a -> System.out.println(a.getName()));

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
