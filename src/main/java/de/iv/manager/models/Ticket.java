package de.iv.manager.models;

import de.iv.manager.core.Vars;
import de.iv.manager.utils.EnumTicketType;
import org.bukkit.entity.Player;

public class Ticket {

    private String title;
    private String message;
    private String dateCreated;
    private EnumTicketType type;
    private Player sender;
    private String ticketId;

    public Ticket(Player sender, EnumTicketType type) {
        this.type = type;
        this.sender = sender;
        this.ticketId = "TICKET_" + Vars.padInt(Vars.randomNumber(1, 1000), 4);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTicketId() {
        return ticketId;
    }
}
