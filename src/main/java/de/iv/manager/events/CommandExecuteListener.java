package de.iv.manager.events;

import de.iv.manager.core.FileManager;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.utils.LoggableCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class CommandExecuteListener implements Listener {

    @EventHandler
    public void onCommandExecution(PlayerCommandSendEvent e) {
        e.getCommands().forEach(c -> {
            if(c.equalsIgnoreCase("togglecommandlogging") | c.equalsIgnoreCase("tcl")) {
                Main.loggablePlayers.forEach(p -> p.sendMessage(Vars.color(Vars.PREFIX + Main.getInstance().getMessageConfig().get("ManagerCommandLogAlert"))
                        .replace("%cmd%", c).replace("%player%", e.getPlayer().getName())));
            }
        });
    }

}
