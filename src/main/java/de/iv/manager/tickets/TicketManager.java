package de.iv.manager.tickets;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.events.ChatListener;
import de.iv.manager.models.Ticket;
import de.iv.manager.text.TextChannelManager;
import de.iv.manager.text.fixed.TextChannelPublic;
import de.iv.manager.utils.EnumTicketType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class TicketManager {

    private static TicketManager instance = null;
    public ArrayList<ArrayList<Player>> ticketConversations = new ArrayList<>();
    public static HashMap<Player, Ticket> availableTickets = new HashMap<>();

    public static Ticket getAvailableTicket(String title) {
        for(Ticket ticket : availableTickets.values()) {
            if(ticket.getTitle().equalsIgnoreCase(title)) {
                return ticket;
            }
        }
        return null;
    }

    public static Ticket getAcceptedTicket(String title) {
        for(Ticket ticket : ChatListener.ticketMap.values()) {
            if(ticket.getTitle().equalsIgnoreCase(title)) {
                return ticket;
            }
        }
        return null;
    }

    public static TicketManager get() {
        return (instance != null) ? instance : new TicketManager();
    }
    public static Ticket getTicket(int ticketId) {
        for(Ticket ticket : availableTickets.values()) {
            if(ticket.getTicketId().equalsIgnoreCase("TICKET_" + String.valueOf(ticketId))) {
                return ticket;
            }
        }
        return null;
    }


    //player cmd
    public static void createTicket(Player sender, EnumTicketType type) {
        Ticket ticket = new Ticket(sender, type);
        availableTickets.put(sender, ticket);
        if(Main.getInstance().getSettingsConfig().getBoolean("DebugMode")) Main.logInfo("TICKET '" + ticket.getTicketId() + "' WAS CREATED");
    }
    public static void createTicket(Ticket ticket) {
        if(availableTickets.containsValue(ticket.getSender())) {
            deleteTicket(ticket, ticket.getSender());
            return;
        }
        availableTickets.put(ticket.getSender(), ticket);
        ChatListener.ticketMap.put(ticket.getSender(), ticket);
        if(Main.getInstance().getSettingsConfig().getBoolean("DebugMode")) Main.logInfo("TICKET '" + ticket.getTicketId() + "' WAS CREATED");
    }

    public static void closeTicket(Ticket ticket) {
        TicketManager.availableTickets.remove(ticket.getSender());
        if(ChatListener.ticketMap.containsKey(ticket.getSender())) {
            ticket.getReceivers().forEach(n -> {
                ChatListener.ticketMap.remove(n);
                ChatListener.tcMap.put(n, TextChannelManager.getTextChannelByName("public"));
                n.sendMessage(Vars.color(Vars.PREFIX + "Switched text channel to public"));
            });
        }
        Main.logWarn("TRYING TO CLOSE NON EXISTING TICKET");
    }

    public static void joinTicket(Player player, Ticket ticket, boolean alert) {
        if(!ticket.getReceivers().contains(player)) {
            if(alert) {
                ticket.getReceivers().forEach(r -> {
                    r.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId()) + ChatColor.BLUE + player.getName()
                            + " joined this tickets text channel"));
                });
                ticket.getReceivers().add(player);
                ChatListener.ticketMap.put(player, ticket);
                player.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId()) + "you joined &4" + ticket.getTicketId() + "&7's text channel"));
            }
        } else player.sendMessage(Vars.color(Vars.ERROR + "You're already connected to this text channel"));


    }

    public static void leaveTicket(Player player, Ticket ticket, boolean alert) {
        if(ticket.getReceivers().contains(player)) {
            if(alert) {
                ticket.getReceivers().forEach(r -> {
                    r.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId()) + ChatColor.BLUE + player.getName()
                            + " left this tickets text channel"));
                });
                ticket.getReceivers().add(player);
                ChatListener.ticketMap.remove(player, ticket);
                ChatListener.tcMap.put(player, TextChannelManager.getTextChannelByName("public"));
                player.sendMessage(Vars.color(Vars.PREFIX + "Text channel switched to public."));
                //player.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId()) + "you left &4" + ticket.getTicketId() + "&7's text channel"));
            }
        } else player.sendMessage(Vars.color(Vars.ERROR + "You're not connected to this text channel"));


    }


    //Admin cmd
    public void acceptTicket(Player sender, Player acceptor) {
        Ticket ticket = availableTickets.get(sender);
        availableTickets.remove(sender);
        ticket.getReceivers().add(acceptor);
        ChatListener.ticketMap.put(acceptor, ticket);

        acceptor.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId())
                + "You accepted the ticket from &9" + sender.getName() + "&7. You and &9" + sender.getName()
                + " &7are now able to communicate via a text channel, so no prefix s needed. If you want to close this ticket, type &9/ticket " + sender.getName() + " close&8."));
        sender.sendMessage(Vars.color(Vars.TICKET_PREFIX.replace("%ticket_id%", ticket.getTicketId()) + "Your ticket was accepted by &9" + acceptor.getName()));
    }

    //admin cmd
    public static void deleteTicket(Ticket ticket, Player p) {
        availableTickets.remove(p);
    }
}
