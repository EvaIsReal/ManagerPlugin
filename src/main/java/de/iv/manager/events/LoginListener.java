package de.iv.manager.events;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.text.TextChannelManager;
import de.iv.manager.text.fixed.TextChannelPublic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        TextChannelManager.openTextChannel(p, TextChannelPublic.class);

        System.out.println(ChatListener.tcMap);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            Vars.sendActionBar(ChatListener.tcMap.get(p).name(), p);
        }, 0, 1);


        if(p.hasPermission("manager.teamMember")) Main.teamPlayers.add(p);



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
