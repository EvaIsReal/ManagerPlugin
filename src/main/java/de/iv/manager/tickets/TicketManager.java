package de.iv.manager.tickets;

import de.iv.manager.core.Main;
import de.iv.manager.models.Ticket;
import de.iv.manager.utils.EnumTicketType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TicketManager {

    private static TicketManager instance = null;
    public ArrayList<ArrayList<Player>> ticketConversations = new ArrayList<>();
    public HashMap<Ticket, Player> availableTickets = new HashMap<>();

    public static TicketManager get() {
        return (instance != null) ? instance : new TicketManager();
    }

    //player cmd
    public void createTicket(Player sender, EnumTicketType type) {
        Ticket ticket = new Ticket(sender, type);
        if(availableTickets.containsValue(sender)) {
            deleteTicket(ticket, sender);
            return;
        }
        availableTickets.put(ticket, sender);
        if(Main.getInstance().getSettingsConfig().getBoolean("DebugMode")) Main.logInfo("TICKET '" + ticket.getTicketId() + "' WAS CREATED");
    }

    //admin cmd
    public void acceptTicket(Ticket ticket, Player acceptor) {
        ArrayList<Player> currentTicket = new ArrayList<>(Collections.singleton(availableTickets.get(ticket)));
        currentTicket.add(acceptor);
        ticketConversations.add(currentTicket);
        Main.getInstance().getDataContainer().put(availableTickets.get(ticket).getName(), ticket);

        TextChannel channel = 

    }

    //Player && admin cmd
    public void closeTicket(Ticket ticket, Player sender) {
        Ticket currentTicket = (Ticket) Main.getInstance().getDataContainer().get(sender.getName());
        deleteTicket(ticket, sender);
    }

    public void deleteTicket(Ticket t, Player sender) {
        availableTickets.remove(t);
    }



    public HashMap<Ticket, Player> getAvailableTickets() {
        return availableTickets;
    }
}
