package de.iv.manager.commands.ticket;

import de.iv.manager.core.Vars;
import de.iv.manager.models.Ticket;
import de.iv.manager.tickets.TicketManager;
import de.iv.manager.utils.Subcommand;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TicketCloseCommand extends Subcommand {

    @Override
    public String getName() {
        return "close";
    }

    @Override
    public String getPermission() {
        return "manager.mod";
    }

    @Override
    public String getDescription() {
        return "closes a ticket";
    }

    @Override
    public String getSyntax() {
        return "/tickets close <ticket>";
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("");
    }

    @Override
    public void execute(Player p, String[] args) {
        // /ticket close <id>
        String identifier = args[1];
        Ticket ticket = TicketManager.getAcceptedTicket(identifier);
        if(ticket != null) {
            ticket.getReceivers().forEach(r -> r.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId())
                    + "The ticket from &9" + ticket.getSender().getName() + "&7 was closed&7.")));
            TicketManager.closeTicket(ticket);
        } else p.sendMessage(Vars.color(Vars.ERROR + "No ticket found from identifier " + identifier));

    }
}
