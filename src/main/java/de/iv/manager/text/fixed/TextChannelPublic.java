package de.iv.manager.text.fixed;

import de.iv.manager.text.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class TextChannelPublic extends TextChannel {

    @Override
    public Collection<? extends Player> messgageReceivers() {
        return Bukkit.getOnlinePlayers();
    }

    @Override
    public String name() {
        return "public";
    }

    @Override
    public String prefix() {
        return "&a%player% &8| &7";
    }
}
