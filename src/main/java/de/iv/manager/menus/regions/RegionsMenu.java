/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.regions;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.PaginatedMenu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.regions.Bound;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class RegionsMenu extends Menu {

    Map<UUID, Bound> regionSetup = Main.getInstance().getRegionManager().getRegionSetup();

    public RegionsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "Regions";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = playerMenuUtility.getOwner();
        switch (e.getCurrentItem().getType()) {
            case DIAMOND_PICKAXE:
                p.closeInventory();
                if(!regionSetup.containsKey(p.getUniqueId())) {
                    regionSetup.put(p.getUniqueId(), new Bound());
                    p.sendTitle(Vars.color("&6Setup-Mode"), Vars.color("&aSetup-Mode aktiviert"), 10, 30, 10);
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                    p.sendMessage(Vars.PREFIX + Vars.color("Klicke einen Block &nlinks&7, um Position #1 zu setzten."));
                    p.sendMessage(Vars.PREFIX + Vars.color("Klicke einen Block &nrechts&7, um Position #2 zu setzten."));
                }else {
                    regionSetup.remove(p.getUniqueId());
                    p.sendTitle(Vars.color("&6Setup-Mode"), Vars.color("&cSetup-Mode deaktiviert"), 10, 30, 10);
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                }
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack create, edit, delete, list;

        create = new ItemBuilder(Material.DIAMOND_PICKAXE).setName(ChatColor.BLUE + "Region erstellen").build();
        edit = new ItemBuilder(Material.WRITABLE_BOOK).setName(ChatColor.BLUE + "Region bearbeiten").build();
        delete = new ItemBuilder(Material.LAVA_BUCKET).setName(ChatColor.BLUE + "Region löschen").build();
        list = new ItemBuilder(Material.PAPER).setName(ChatColor.BLUE + "Regionen anzeigen").build();

        inventory.setItem(10, create);
        inventory.setItem(12, edit);
        inventory.setItem(14, delete);
        inventory.setItem(16, list);

        setFillerGlass();

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
