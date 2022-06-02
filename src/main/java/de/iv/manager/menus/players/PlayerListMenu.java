/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.players;

import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PaginatedMenu;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerListMenu extends PaginatedMenu {

    public PlayerListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GOLD + "Player List";
    }

    @Override
    public int getSlots() {
        return 54;
    }


    @Override
    public void handleMenu(InventoryClickEvent e) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD:
                PlayerMenuUtility pmu = (PlayerMenuUtility) playerMenuUtility;
                pmu.setData("skullData", ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                MenuManager.openMenu(CurrentPlayerMenu.class, pmu.getOwner());

                break;

            case DARK_OAK_BUTTON:
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
            break;
            case BARRIER:
                playerMenuUtility.getOwner().closeInventory();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        setBorderItems();
        for(Player a : players) {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(a.getName()).setName(ChatColor.BLUE + a.getName()).build();
            inventory.addItem(item);
        }

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
