package de.iv.manager.events.custom;

import de.iv.manager.models.Ticket;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Handler;

public class PlayerTicketCreationEvent extends Event {

    private HandlerList handlers = new HandlerList();
    private Player creator;
    private Ticket ticket;

    public PlayerTicketCreationEvent(Player creator, Ticket ticket) {
        this.creator = creator;
        this.ticket = ticket;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public Player getCreator() {
        return creator;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
