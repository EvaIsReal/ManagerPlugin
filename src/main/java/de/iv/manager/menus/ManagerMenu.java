/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus;

import de.iv.manager.core.Vars;
import de.iv.manager.menus.notes.NoteListMenu;
import de.iv.manager.menus.players.PlayerListMenu;
import de.iv.manager.menus.regions.RegionsMenu;
import de.iv.manager.menus.settings.ServerSettingsMenu;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ManagerMenu extends Menu{

    public ManagerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GOLD + "Manager Menu";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case PLAYER_HEAD:
                MenuManager.openMenu(PlayerListMenu.class, playerMenuUtility.getOwner());
                break;
            case PAPER:
                MenuManager.openMenu(NoteListMenu.class, playerMenuUtility.getOwner());
                break;

            case COMMAND_BLOCK:
                MenuManager.openMenu(ServerSettingsMenu.class, playerMenuUtility.getOwner());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack notes, players, settings, regions;
        notes = new ItemBuilder(Material.PAPER).setName(ChatColor.GREEN + "Notes").setLore(Vars.color("&7Sieh dir Notizen an,"),
                Vars.color("&7die von anderen Teamlern erstellt wurden")).build();
        players = new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(playerMenuUtility.getOwner().getName()).setName(ChatColor.GREEN + "Spieler").
                setLore(Vars.color("&7Eine Liste aller Spieler, \n die sich aktuell auf dem Server befinden")).build();
        settings = new ItemBuilder(Material.COMMAND_BLOCK).setName(ChatColor.GREEN + "Server Settings").setLore(Vars.color("&7Manage Servereigenschaften und"),
                Vars.color("&7verändere diese")).build();
        //regions = new ItemBuilder(Material.SCAFFOLDING).setName(ChatColor.GREEN + "Regionen").setLore(Vars.color("&7Verwalte von dir erstellte Regionen")).build();


        inventory.setItem(10, notes);
        inventory.setItem(12, players);
        inventory.setItem(14, settings);
        //inventory.setItem(16, regions);

        setFillerGlass();

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
