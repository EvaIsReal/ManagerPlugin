package de.iv.manager.commands.ticket;

import de.iv.manager.core.Vars;
import de.iv.manager.events.ChatListener;
import de.iv.manager.models.Ticket;
import de.iv.manager.tickets.TicketManager;
import de.iv.manager.utils.Subcommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketListCommand extends Subcommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "manager.mod";
    }

    @Override
    public String getDescription() {
        return "lists all available tickets";
    }

    @Override
    public String getSyntax() {
        return "/ticket list <available/not available>";
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("-l");
    }

    @Override
    public void execute(Player p, String[] args) {
        // /ticket -l -a

        if(args.length == 2) {
            String selector = args[1];
            if(selector.equalsIgnoreCase("-a") | selector.equalsIgnoreCase("available")) {
                if(!TicketManager.availableTickets.isEmpty()) {
                    p.sendMessage("");
                    TicketManager.availableTickets.keySet().forEach(k -> {
                        Ticket current = TicketManager.availableTickets.get(k);
                        p.sendMessage(Vars.color("&4&l" + current.getTicketId() + " &8| " +
                                "&7Sender: &9" + k.getName() + " &8| &7Type: &4&l" + current.getType()));
                    });
                    p.sendMessage("");
                } else p.sendMessage(Vars.color(Vars.ERROR + "No tickets available"));
            } else if(selector.equalsIgnoreCase("-!a") | selector.equalsIgnoreCase("accepted")) {
                if(!ChatListener.ticketMap.isEmpty()) {
                    p.sendMessage("");
                    ChatListener.ticketMap.keySet().forEach(k -> {
                        Ticket current = ChatListener.ticketMap.get(k);
                        p.sendMessage(Vars.color("&4&l" + current.getTicketId() + " &8| " +
                                "&7Sender: &9" + k.getName() + " &8| &7Type: &4&l" + current.getType()));
                    });
                    p.sendMessage("");
                } else p.sendMessage(Vars.color(Vars.ERROR + "No accepted tickets available"));
            }
        } else p.sendMessage(Vars.color(Vars.ERROR + "Please specify between available(-a) and not available(-!a)"));

    }
}
