package de.iv.manager.menus.cc;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.events.IConversationAbandonedListener;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CurrentPhraseMenu extends Menu {

    public CurrentPhraseMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Vars.color("Aussage konfigurieren");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    String currentPhrase = (String) playerMenuUtility.getData("phraseID");

    @Override
    public void handleMenu(InventoryClickEvent e) {

        switch (e.getCurrentItem().getType()) {
            case WRITABLE_BOOK -> {
                playerMenuUtility.getOwner().closeInventory();
                ConversationFactory factory = new ConversationFactory(Main.getInstance())
                        .withLocalEcho(false)
                        .withFirstPrompt(new EditPhraseFirstPrompt())
                        .addConversationAbandonedListener(new IConversationAbandonedListener())
                        .withEscapeSequence("exit");
                factory.buildConversation(playerMenuUtility.getOwner()).begin();
            }
            case LAVA_BUCKET -> {
                if(BlackListManager.getBlacklistedPhrases().contains(currentPhrase)) {
                    BlackListManager.getBlacklistedPhrases().remove(currentPhrase);
                    playerMenuUtility.getOwner().closeInventory();
                    playerMenuUtility.getOwner().sendMessage(Vars.color(Vars.PREFIX + "Die Aussage &9'"+currentPhrase+"' &7wurde entsperrt."));
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(11, new ItemBuilder(Material.WRITABLE_BOOK).setName(Vars.color("&aAussage bearbeiten"))
                .setLore(Vars.color("&7Bearbeite diese Aussage")).build());
        inventory.setItem(15, new ItemBuilder(Material.LAVA_BUCKET).setName(Vars.color("&aAussage entfernen")).build());
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build());

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }

    private class EditPhraseFirstPrompt extends StringPrompt {

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX + "Gib die neue Aussage ein, die im Chat verboten werden soll." +
                    "\nDu kannst diese Konversation mit '&cexit&7' abbrechen");
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            context.setSessionData("newPhrase", input);
            return new EditPhraseCompletionPrompt();
        }
    }

    private class EditPhraseCompletionPrompt extends MessagePrompt {
        @Nullable
        @Override
        protected Prompt getNextPrompt(@NotNull ConversationContext context) {
            return END_OF_CONVERSATION;
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            BlackListManager.updatePhrase(currentPhrase, (String) context.getSessionData("newPhrase"));
            return Vars.color(Vars.PREFIX + "Die Aussage '&9" + currentPhrase +"&7' wurde zu '&9"+context.getSessionData("newPhrase")+"&7' geändert");
        }
    }
}
