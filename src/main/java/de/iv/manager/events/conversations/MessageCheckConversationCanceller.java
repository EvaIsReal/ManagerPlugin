package de.iv.manager.events.conversations;

import de.iv.manager.menus.cc.BlackListManager;
import de.iv.manager.models.BlacklistedPhrase;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.conversations.ConversationContext;
import org.jetbrains.annotations.NotNull;

public class MessageCheckConversationCanceller implements ConversationCanceller {

    @Override
    public void setConversation(@NotNull Conversation conversation) {

    }

    @Override
    public boolean cancelBasedOnInput(@NotNull ConversationContext context, @NotNull String input) {
        for (String blacklistedPhrase : BlackListManager.getBlacklistedPhrases()) {
            return input.contains(blacklistedPhrase);
        }
        return false;
    }

    @NotNull
    @Override
    public ConversationCanceller clone() {
        return new MessageCheckConversationCanceller();
    }
}
