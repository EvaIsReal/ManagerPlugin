package de.iv.manager.text;

import de.iv.manager.events.ChatListener;
import org.bukkit.entity.Player;

import java.util.Collection;

public abstract class TextChannel {

    public abstract Collection<? extends Player> messgageReceivers();
    public abstract String name();
    public abstract String prefix();

    public TextChannel() {
    }

    public void open(Player p) {
        ChatListener.tcMap.put(p, this);
    }

}
