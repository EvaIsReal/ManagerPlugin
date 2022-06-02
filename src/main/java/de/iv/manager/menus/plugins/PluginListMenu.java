package de.iv.manager.menus.plugins;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PaginatedMenu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.players.CurrentPlayerMenu;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PluginListMenu extends PaginatedMenu {

    public PluginListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return Vars.color("&9Installierte Plugins");
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ArrayList<Plugin> plugins = new ArrayList<>(Arrays.stream(Main.getInstance().getServer().getPluginManager().getPlugins()).toList());
        switch (e.getCurrentItem().getType()) {
            case PAPER:
                PlayerMenuUtility pmu = (PlayerMenuUtility) playerMenuUtility;
                pmu.setData("currentPlugin", ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName()));
                MenuManager.openMenu(CurrentPluginMenu.class, playerMenuUtility.getOwner());

                break;

            case DARK_OAK_BUTTON:
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("letzte Seite")) {
                    if (page == 0) {
                        return;
                    } else {
                        page -= 1;
                        super.open();
                    }
                } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("nächste Seite")) {
                    if (!((index + 1) >= plugins.size())) {
                        page += 1;
                        super.open();
                    }
                }
                break;
            case BARRIER:
                playerMenuUtility.getOwner().closeInventory();
                break;
        }
    }
    @Override
    public void setMenuItems () {
        ArrayList<Plugin> plugins = new ArrayList<>(Arrays.stream(Main.getInstance().getServer().getPluginManager().getPlugins()).toList());
        setBorderItems();
        for (Plugin a : plugins) {
            ItemStack item = new ItemBuilder(Material.PAPER).setSkullOwner(a.getName()).setName(ChatColor.BLUE + a.getName()).build();
            inventory.addItem(item);
        }

    }

    @NotNull
    @Override
    public Inventory getInventory () {
        return null;
    }
}

