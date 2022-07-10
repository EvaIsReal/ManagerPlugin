package de.iv.manager.text;

import de.iv.manager.models.Ticket;
import de.iv.manager.text.fixed.TextChannelPublic;
import de.iv.manager.text.fixed.TextChannelTeam;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TextChannelManager {

    public static ArrayList<Class<?extends TextChannel>> tcClasses = new ArrayList<>();

    public TextChannelManager() {
        tcClasses.add(TextChannelTeam.class);
        tcClasses.add(TextChannelPublic.class);
    }

    public static void openTextChannel(Player p, Class<? extends TextChannel> textChannelClass) {
        try {
            TextChannel channel = textChannelClass.getConstructor().newInstance();
            channel.open(p);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }



    public static TextChannel getTextChannelByName(String name) {
        for (Class<? extends TextChannel> tcClass : tcClasses) {
            try {
                TextChannel channel = tcClass.getConstructor().newInstance();
                if(channel.name().equalsIgnoreCase(name)) return channel;

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    public static void openTicket(Player sender, Player acceptor) {

    }

    public static void addToTicket(Player playerToAdd, Ticket ticket) {

    }

}
