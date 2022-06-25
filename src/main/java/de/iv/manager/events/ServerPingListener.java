package de.iv.manager.events;

import de.iv.manager.core.FileManager;
import de.iv.manager.core.Vars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent e) {
        String motd = FileManager.getConfig("messages.yml").getString("ServerMotd");
        if(motd != null) e.setMotd(Vars.color(motd));
    }

}
