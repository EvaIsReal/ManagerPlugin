package de.iv.manager.events;

import de.iv.manager.core.FileManager;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.IOException;

public class ServerPingListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent e) {
        String motd = null;
        try {
            motd = FileManager.getConfig("lang/en/messages.yml").getString("ServerMotd");
        } catch (IOException | InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }
        if(motd != null) e.setMotd(Vars.color(motd));



    }

}
