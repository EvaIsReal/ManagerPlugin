package de.iv.manager.menus.cc;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.events.IConversationAbandonedListener;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.notes.NoteListMenu;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatControlMenu extends Menu {

    public ChatControlMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Vars.color("&9Chat Kontrolle");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case PAPER -> {
                MenuManager.openMenu(PhraseListMenu.class, playerMenuUtility.getOwner());
            }

            case LAVA_BUCKET -> {
                MenuManager.openMenu(NoteListMenu.class, playerMenuUtility.getOwner());
            }

            case WRITABLE_BOOK -> {
                playerMenuUtility.getOwner().closeInventory();
                ConversationFactory factory = new ConversationFactory(Main.getInstance())
                        .withFirstPrompt(new ChatControlMenu.CreatePhraseFirstPrompt())
                        .withLocalEcho(false)
                        .addConversationAbandonedListener(new IConversationAbandonedListener())
                        .withEscapeSequence("exit").withTimeout(60);

                factory.buildConversation(playerMenuUtility.getOwner()).begin();
            }

            case BARRIER -> {
                MenuManager.openMenu(MenuManager.getPreviousMenu(), playerMenuUtility.getOwner());
            }

        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(15, new ItemBuilder(Material.PAPER).setName(Vars.color("&aVerbotene Aussagen anzeigen"))
                .setLore(Vars.color("&7Siehe alle im Chat verbotenen"), Vars.color("&7Aussagen ein.")).build());
        inventory.setItem(11, new ItemBuilder(Material.WRITABLE_BOOK).setName(Vars.color("&aAussage hinzufügen"))
                .setLore(Vars.color("&7Füge eine neue Aussage zu"), Vars.color("&7den verbotenen Aussagen hinzu.")).build());
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build());
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }

    private class CreatePhraseFirstPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX + "Bitte gib den Text ein, der für den Chat gesperrt werden soll." +
                "\nDu kannst diese Konversation mit '&cexit&7' abbrechen.");
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            context.setSessionData("blacklistedPhrase", input);
            return new CompletionPrompt();
        }
    }

    private class CompletionPrompt extends MessagePrompt {

        @Nullable
        @Override
        protected Prompt getNextPrompt(@NotNull ConversationContext context) {
            return END_OF_CONVERSATION;
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {

            BlackListManager.getBlacklistedPhrases().add((String) context.getSessionData("blacklistedPhrase"));
            for(Player a : Bukkit.getOnlinePlayers()) {
                if(a.hasPermission("manager.seeNoteCreationLog")) {
                    a.sendMessage(Vars.color(Vars.SERVER_LOG + "Eine neue Note wurde von &9" + playerMenuUtility.getOwner() + "&7erstellt."));
                }
            }
            return Vars.color(Vars.PREFIX + "Die folgende Aussage ist ab jetzt für den Chat gesperrt:\n" +
                    "'&9"+ context.getSessionData("blacklistedPhrase") +"&7'");
        }
    }
}
