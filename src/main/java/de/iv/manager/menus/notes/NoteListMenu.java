/*This class from the Project Manager was created by thoiv at 13.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.notes;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PaginatedMenu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.models.Note;
import de.iv.manager.utils.ItemBuilder;
import de.iv.manager.utils.NoteStorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoteListMenu extends PaginatedMenu {

    public NoteListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Liste an Notes <" + (page + 1) + ">";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ArrayList<Note> players = new ArrayList<>(NoteStorageUtil.getNotes());
        PlayerMenuUtility pmu = (PlayerMenuUtility) playerMenuUtility;
        if(e.getCurrentItem().getType() == Material.BARRIER) {
            MenuManager.openMenu(MenuManager.getPreviousMenu(), playerMenuUtility.getOwner());
        } else if(e.getCurrentItem().getType()==Material.PAPER) {
            PersistentDataContainer container = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
            String noteID = container.get(new NamespacedKey(Main.getInstance(), "noteID"), PersistentDataType.STRING);
            pmu.setData("noteID", noteID);
            MenuManager.openMenu(CurrentNoteMenu.class, pmu.getOwner());
        } else if(e.getCurrentItem().getType() == Material.DARK_OAK_BUTTON) {
            if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("letzte Seite")) {
                if(page == 0) {
                    return;
                } else {
                    page -= 1;
                    super.open();
                }
            } else if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("nächste Seite")) {
                if(!((index + 1) >= players.size())) {
                    page += 1;
                    super.open();
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        setBorderItems();
        ArrayList<Note> players = new ArrayList<>(NoteStorageUtil.getNotes());

        if(!players.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= players.size()) break;
                if(players.get(index) != null) {
                    ItemStack item = new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + players.get(index).getNoteID()).setLore(
                            Vars.color("&7Erstellt von: " + ChatColor.GREEN + players.get(index).getPlayerName()),
                            Vars.color("&7Erstellungsdatum: " + ChatColor.GREEN + players.get(index).getDateCreated() + " Uhr"))
                            .build();
                    ItemMeta meta = item.getItemMeta();
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "noteID"), PersistentDataType.STRING, players.get(index).getNoteID());
                    item.setItemMeta(meta);

                    inventory.addItem(item);
                }
            }
        }
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
