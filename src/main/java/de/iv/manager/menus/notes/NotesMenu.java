/*This class from the Project Manager was created by thoiv at 11.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.notes;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.ManagerMenu;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.models.Note;
import de.iv.manager.utils.ItemBuilder;
import de.iv.manager.utils.NoteStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NotesMenu extends Menu {

    public NotesMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Note Menu";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case PAPER:
            case LAVA_BUCKET:
                MenuManager.openMenu(NoteListMenu.class, playerMenuUtility.getOwner());
                break;
            case WRITABLE_BOOK:
                playerMenuUtility.getOwner().closeInventory();
                ConversationFactory factory = new ConversationFactory(Main.getInstance())
                        .withFirstPrompt(new CreateNoteFirstPrompt())
                        .withLocalEcho(false)
                        .withEscapeSequence("exit").withTimeout(60);

                factory.buildConversation(playerMenuUtility.getOwner()).begin();
                break;
            case BARRIER:
                MenuManager.openMenu(ManagerMenu.class, playerMenuUtility.getOwner());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack create, list, delete, close;
        create = new ItemBuilder(Material.WRITABLE_BOOK).setName(Vars.color("&aNote erstellen")).build();
        list = new ItemBuilder(Material.PAPER).setName(Vars.color("&aNotes anzeigen")).build();
        delete = new ItemBuilder(Material.LAVA_BUCKET).setName(Vars.color("&cNote löschen")).build();
        close = new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build();

        inventory.setItem(11, create);
        inventory.setItem(15, list);
        //inventory.setItem(15, delete);
        inventory.setItem(31, close);

        setFillerGlass();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }

    private class CreateNoteFirstPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX + "Bitte gib den Text der Note ein.\nDu kannst diese Konversation mit '&cexit&7' abbrechen.");
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            context.setSessionData("noteText", input);
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
            //Do something when first text has been put in.
            NoteStorageUtil.createNote(playerMenuUtility.getOwner(), (String) context.getSessionData("noteText"));
            for(Player a : Bukkit.getOnlinePlayers()) {
                if(a.hasPermission("manager.seeNoteCreationLog")) {
                    a.sendMessage(Vars.color(Vars.SERVER_LOG + "Eine neue Note wurde von &9" + playerMenuUtility.getOwner() + "&7erstellt."));
                }
            }
            return Vars.color(Vars.PREFIX + "Eine neue Note mit dem Text &6'" + context.getSessionData("noteText") + "' &7wurde erstellt.");
        }
    }

}

