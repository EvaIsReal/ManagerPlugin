package de.iv.manager.commands.ticket;

import de.iv.manager.core.Vars;
import de.iv.manager.models.Ticket;
import de.iv.manager.tickets.TicketManager;
import de.iv.manager.utils.Subcommand;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TicketJoinCommand extends Subcommand {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getPermission() {
        return "manager.admin";
    }

    @Override
    public String getDescription() {
        return "makes you join an active ticket's text channel";
    }

    @Override
    public String getSyntax() {
        return "/tickets join <identifier>";
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("-j");
    }

    @Override
    public void execute(Player p, String[] args) {
        if(args.length >= 2) {
            Ticket ticket = TicketManager.getAcceptedTicket(args[1]);
            if(ticket != null) {
                if(args[2] != null) {
                    boolean alert = Boolean.parseBoolean(args[2]);
                    TicketManager.joinTicket(p, ticket, alert);
                } else TicketManager.joinTicket(p, ticket, false);
            }  else p.sendMessage(Vars.color(Vars.ERROR + "No ticket found from identifier " + args[1]));
        }
    }
}
