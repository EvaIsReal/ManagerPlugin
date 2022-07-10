package de.iv.manager.text.fixed;

import de.iv.manager.core.Main;
import de.iv.manager.text.TextChannel;
import org.bukkit.entity.Player;

import java.util.Collection;

public class TextChannelTeam extends TextChannel {

    @Override
    public Collection<? extends Player> messgageReceivers() {
        return Main.teamPlayers;
    }

    @Override
    public String name() {
        return "team";
    }

    @Override
    public String prefix() {
        return "&8[&2&lTeamChat&8] &7%player% &8| &7";
    }
}
