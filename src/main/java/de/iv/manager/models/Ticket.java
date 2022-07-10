package de.iv.manager.models;

import de.iv.manager.core.Vars;
import de.iv.manager.events.custom.PlayerTicketCreationEvent;
import de.iv.manager.utils.EnumTicketType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Ticket {

    private String title;
    private String message;
    private String dateCreated;
    private EnumTicketType type;
    private ArrayList<Player> receivers;
    private Player sender;
    private String ticketId;

    public Ticket(Player sender, EnumTicketType type) {
        this.type = type;
        this.sender = sender;
        this.title = sender.getName();
        this.receivers = new ArrayList<>(Collections.singleton(sender));
        this.ticketId = "TICKET_" + Vars.padInt(Vars.randomNumber(1, 1000), 4);

        Bukkit.getServer().getPluginManager().callEvent(new PlayerTicketCreationEvent(sender, this));
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

    public String getDateCreated() {
        return dateCreated;
    }

    public EnumTicketType getType() {
        return type;
    }

    public Player getSender() {
        return sender;
    }

    public ArrayList<Player> getReceivers() {
        return receivers;
    }
}
