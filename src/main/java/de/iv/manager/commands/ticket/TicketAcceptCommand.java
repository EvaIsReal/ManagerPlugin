package de.iv.manager.commands.ticket;

import de.iv.manager.models.Ticket;
import de.iv.manager.tickets.TicketManager;
import de.iv.manager.utils.Subcommand;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TicketAcceptCommand extends Subcommand {

    @Override
    public String getName() {
        return "accept";
    }

    @Override
    public String getPermission() {
        return "manager.mod";
    }

    @Override
    public String getDescription() {
        return "accepts a ticket";
    }

    @Override
    public String getSyntax() {
        return "/tickets accept <ticket_id/sender_name>";
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("-a");
    }

    @Override
    public void execute(Player p, String[] args) {
        if(args.length == 2) {
            // /ticket accept <Ticket id.>
            String identifier = args[1];
            Ticket ticket = TicketManager.getAvailableTicket(identifier);
            TicketManager.get().acceptTicket(ticket.getSender(), p);
        }

    }
}
