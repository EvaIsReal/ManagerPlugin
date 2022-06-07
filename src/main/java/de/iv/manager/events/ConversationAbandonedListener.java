package de.iv.manager.events;

import de.iv.manager.core.Vars;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ExactMatchConversationCanceller;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.jetbrains.annotations.NotNull;

public class ConversationAbandonedListener implements org.bukkit.conversations.ConversationAbandonedListener {

    @Override
    public void conversationAbandoned(@NotNull ConversationAbandonedEvent abandonedEvent) {
        if(abandonedEvent.getCanceller() instanceof ExactMatchConversationCanceller) {
            abandonedEvent.getContext().getForWhom().sendRawMessage(Vars.color(Vars.PREFIX + "&cConversation abgebrochen."));
        }
    }
}
