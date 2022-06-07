package de.iv.manager.menus.cc;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PaginatedMenu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.notes.CurrentNoteMenu;
import de.iv.manager.menus.settings.ServerSettingsMenu;
import de.iv.manager.models.BlacklistedPhrase;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhraseListMenu extends PaginatedMenu {

    public PhraseListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Verbotene Aussagen <" + (page + 1) + ">";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ArrayList<String> blacklistedPhrases = new ArrayList<>(BlackListManager.getBlacklistedPhrases());
        PlayerMenuUtility pmu = (PlayerMenuUtility) playerMenuUtility;
        if(e.getCurrentItem().getType() == Material.BARRIER) {
            MenuManager.openMenu(ServerSettingsMenu.class, playerMenuUtility.getOwner());

        } else if(e.getCurrentItem().getType()==Material.PAPER) {
            PersistentDataContainer container = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
            String phraseID = container.get(new NamespacedKey(Main.getInstance(), "phraseID"), PersistentDataType.STRING);
            pmu.setData("phraseID", phraseID);
            MenuManager.openMenu(CurrentPhraseMenu.class, pmu.getOwner());

        } else if(e.getCurrentItem().getType() == Material.DARK_OAK_BUTTON) {
            if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("letzte Seite")) {
                if(page == 0) {
                    return;
                } else {
                    page -= 1;
                    super.open();
                }
            } else if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("nächste Seite")) {
                if(!((index + 1) >= blacklistedPhrases.size())) {
                    page += 1;
                    super.open();
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        setBorderItems();
        ArrayList<String> blacklistedPhrases = new ArrayList<>(BlackListManager.getBlacklistedPhrases());

        for(int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if(index >= blacklistedPhrases.size()) break;
            if(blacklistedPhrases.get(index) != null) {
                ItemStack item = new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + blacklistedPhrases.get(index)).setLore(
                                Vars.color("&7Beinhaltet: " + ChatColor.GREEN + blacklistedPhrases.get(index))).build();
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "phraseID"), PersistentDataType.STRING, blacklistedPhrases.get(index).toString());
                item.setItemMeta(meta);

                inventory.addItem(item);
            }
        }

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
