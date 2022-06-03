package de.iv.manager.menus.cc;

import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.settings.ServerSettingsMenu;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

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
            case BARRIER -> {
                MenuManager.openMenu(MenuManager.getPreviousMenu(), playerMenuUtility.getOwner());
            }
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build());
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
