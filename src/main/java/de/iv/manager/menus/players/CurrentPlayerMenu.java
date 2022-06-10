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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
            case ENDER_PEARL -> {
                playerMenuUtility.getOwner().teleport(Objects.requireNonNull(Bukkit.getPlayer((String) playerMenuUtility.getData("skullData"))));
                playerMenuUtility.getOwner().sendMessage(Vars.color(Vars.PREFIX + "Du wurdest zu &9" + playerMenuUtility.getData("skullData") + " &7teleportiert"));
            }
            case ENDER_EYE -> {
                Bukkit.getPlayer((String) playerMenuUtility.getData("skullData")).teleport(playerMenuUtility.getOwner());
                playerMenuUtility.getOwner().sendMessage(Vars.color(Vars.PREFIX + ChatColor.BLUE + playerMenuUtility.getData("skullData") + " &7 wurde zu dir teleportiert"));
            }
            case STRUCTURE_VOID -> {
                Bukkit.getPlayer((String) playerMenuUtility.getData("skullData")).kickPlayer(Vars.color("&cDu wurdest vom Server gekickt."));
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build());
        inventory.setItem(10, new ItemBuilder(Material.ENDER_PEARL).setName(Vars.color("&aZum Spieler teleportieren")).build());
        inventory.setItem(12, new ItemBuilder(Material.ENDER_EYE).setName(Vars.color("&aSpieler zu dir teleportieren")).build());
        inventory.setItem(14, new ItemBuilder(Material.IRON_PICKAXE).setName(Vars.color("&aSpielmodus ändern")).build());
        inventory.setItem(16, new ItemBuilder(Material.STRUCTURE_VOID).setName(Vars.color("&aKicken")).build());


        setFillerGlass();
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
