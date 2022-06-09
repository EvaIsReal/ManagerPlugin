package de.iv.manager.events;

import de.iv.manager.core.Vars;
import de.iv.manager.events.conversations.MessageCheckConversationCanceller;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ExactMatchConversationCanceller;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IConversationAbandonedListener implements org.bukkit.conversations.ConversationAbandonedListener {

    @Override
    public void conversationAbandoned(@NotNull ConversationAbandonedEvent e) {
        TextComponent escape = new TextComponent();
        escape.setText("ESCAPE-SEQUENCE");
        escape.setColor(ChatColor.RED);
        escape.setItalic(true);
        escape.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Vars.color("&c&oEine Escape-Sequence wurde eingegeben.")).create()));

        TextComponent manual = new TextComponent();
        manual.setText("PLUGIN");
        manual.setColor(ChatColor.RED);
        manual.setItalic(true);
        manual.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Vars.color("&c&oCODE im Plugin wurde ausgeführt.")).create()));

        TextComponent mc = new TextComponent();
        mc.setText("MESSAGE-CHECK");
        mc.setColor(ChatColor.RED);
        mc.setItalic(true);
        mc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Vars.color("&c&oDer eingegebene String enthält verbotene Wörter")).create()));

        if(e.getCanceller() instanceof ExactMatchConversationCanceller) {
            if(e.getContext().getForWhom() instanceof Player) {
                ((Player) e.getContext().getForWhom()).spigot().sendMessage(new TextComponent(Vars.color(Vars.PREFIX +
                        "&cKonversation wurde durch ")), escape, new TextComponent(Vars.color(" &cabgebrochen")));
            }

        } else if(e.getCanceller() instanceof ManuallyAbandonedConversationCanceller) {
            if(e.getContext().getForWhom() instanceof Player) {
                ((Player) e.getContext().getForWhom()).spigot().sendMessage(new TextComponent(Vars.color(Vars.PREFIX +
                        "&cKonversation wurde durch ")), manual, new TextComponent(Vars.color(" &cabgebrochen")));
            }
        } else if(e.getCanceller() instanceof MessageCheckConversationCanceller) {
            if(e.getContext().getForWhom() instanceof Player) {
                ((Player) e.getContext().getForWhom()).spigot().sendMessage(new TextComponent(Vars.color(Vars.PREFIX +
                        "&cKonversation wurde durch ")), mc, new TextComponent(Vars.color(" &cabgebrochen")));
            }
        }
    }
}
