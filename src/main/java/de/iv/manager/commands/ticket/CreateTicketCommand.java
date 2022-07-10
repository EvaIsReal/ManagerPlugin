package de.iv.manager.commands.ticket;

import de.iv.manager.core.Vars;
import de.iv.manager.models.Ticket;
import de.iv.manager.tickets.TicketManager;
import de.iv.manager.utils.EnumTicketType;
import de.iv.manager.utils.Subcommand;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CreateTicketCommand extends Subcommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Use this command to create a ticket";
    }

    @Override
    public String getSyntax() {
        return "/ticket create <type> <title> <msg>";
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("cr");
    }

    @Override
    public void execute(Player p, String[] args) {
        // /Ticket create support Hilfe! Hallo, ich bräuchte ein wenig Hilfe
        if(args.length > 2) {
            String type = args[1];
            String msg = "";
            StringBuilder sb = new StringBuilder(msg);
            for(int i = 2; i < args.length; i++) {
                sb.append(args[i]);
            }
            msg = sb.toString().trim();
            Ticket ticket = new Ticket(p, EnumTicketType.valueOf(type.toUpperCase()));
            ticket.setMessage(msg);
            TicketManager.createTicket(ticket);
            p.sendMessage(Vars.color(Vars.PREFIX + "Your ticked was created."));

        }

    }
}
