/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.players;

import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CurrentPlayerMenu extends Menu {


    public CurrentPlayerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return (String) playerMenuUtility.getData("skullData");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case BARRIER -> {
                MenuManager.openMenu(MenuManager.getPreviousMenu(), playerMenuUtility.getOwner());
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build());


        setFillerGlass();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
