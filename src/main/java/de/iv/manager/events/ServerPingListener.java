package de.iv.manager.events;

import de.iv.manager.core.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent e) {
        String motd = ConfigManager.getInstance().getMessages().toFileConfiguration().getString("Out.Server.Motd");
        if(motd != null) e.setMotd(motd);
    }

}
