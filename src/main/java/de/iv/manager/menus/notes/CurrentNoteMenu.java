/*This class from the Project Manager was created by thoiv at 13.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.notes;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.events.IConversationAbandonedListener;
import de.iv.manager.events.conversations.MessageCheckConversationCanceller;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.models.Note;
import de.iv.manager.utils.ItemBuilder;
import de.iv.manager.utils.NoteStorageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CurrentNoteMenu extends Menu {

    public CurrentNoteMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    PlayerMenuUtility pmu = (PlayerMenuUtility) playerMenuUtility;

    @Override
    public String getMenuName() {
        return "Note Konfigurieren";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        String noteID = (String) playerMenuUtility.getData("noteID");
        Note note = NoteStorageUtil.findNote(noteID);
        switch (e.getCurrentItem().getType()) {
            case BARRIER:
                MenuManager.openMenu(NoteListMenu.class, playerMenuUtility.getOwner());
                break;
            case WRITABLE_BOOK:
                playerMenuUtility.getOwner().closeInventory();
                ConversationFactory factory = new ConversationFactory(Main.getInstance())
                                .withFirstPrompt(new EditNoteFirstPrompt())
                                .withLocalEcho(false)
                        .addConversationAbandonedListener(new IConversationAbandonedListener())
                                .withConversationCanceller(new MessageCheckConversationCanceller())
                                .withEscapeSequence("exit").withTimeout(60);

                factory.buildConversation(playerMenuUtility.getOwner()).begin();


                break;
            case PAPER:
                assert note != null;
                printInformation(note);
                break;
            case LAVA_BUCKET:
                NoteStorageUtil.deleteNote(noteID);
                playerMenuUtility.getOwner().sendMessage(Vars.color(Vars.PREFIX + "Note &6" + note.getNoteID().substring(0, 10) + "... &7wurde gelöscht."));
                MenuManager.openMenu(NoteListMenu.class, playerMenuUtility.getOwner());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack edit, print, delete, back;
        edit = new ItemBuilder(Material.WRITABLE_BOOK).setName(Vars.color("&aNote bearbeiten")).build();
        print = new ItemBuilder(Material.PAPER).setName(Vars.color("&aInformationen anzeigen")).build();
        delete = new ItemBuilder(Material.LAVA_BUCKET).setName(Vars.color("&cNote löschen")).build();
        back = new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build();

        inventory.setItem(11, edit);
        inventory.setItem(13, print);
        inventory.setItem(15, delete);
        inventory.setItem(31, back);

        setFillerGlass();
    }

    public void printInformation(Note note) {
        playerMenuUtility.getOwner().sendMessage(Vars.color(Vars.PREFIX + "&6" + note.getNoteID().substring(0, 10) + "..." + " &7enthält folgenden Text:"));
        playerMenuUtility.getOwner().sendMessage(" ");
        playerMenuUtility.getOwner().sendMessage(Vars.color("&7" + note.getMessage()));
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }



    private class EditNoteFirstPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX + "Bitte gib den neuen Text der Note ein.\nDu kannst diese Konversation mit '&cexit' &7abbrechen.");
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
            NoteStorageUtil.updateNote((String) playerMenuUtility.getData("noteID"), new Note(playerMenuUtility.getOwner().getName(),
                    (String) context.getSessionData("noteText")));
            return Vars.color(Vars.PREFIX + ChatColor.GOLD + context.getSessionData("noteText") + " &7wurde als neuer Text gesetzt!");
        }
    }


}
