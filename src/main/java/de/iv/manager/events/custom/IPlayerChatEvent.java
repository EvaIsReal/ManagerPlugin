package de.iv.manager.events.custom;

import de.iv.manager.utils.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class IPlayerChatEvent extends PlayerEvent implements Cancellable {

    private boolean isCancelled;
    private static final HandlerList handlers = new HandlerList();

    public IPlayerChatEvent(@NotNull Player player, TextChannel channel) {
        super(player);
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
