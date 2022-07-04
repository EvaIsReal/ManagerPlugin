/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.notes.NoteListMenu;
import de.iv.manager.menus.notes.NotesMenu;
import de.iv.manager.menus.players.PlayerListMenu;
import de.iv.manager.menus.regions.RegionsMenu;
import de.iv.manager.menus.settings.ServerSettingsMenu;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ManagerMenu extends Menu{

    private FileConfiguration cfg = Main.getInstance().getGuiConfig();

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
                MenuManager.openMenu(NotesMenu.class, playerMenuUtility.getOwner());
                break;

            case COMMAND_BLOCK:
                MenuManager.openMenu(ServerSettingsMenu.class, playerMenuUtility.getOwner());
                break;
        }
    }

    @Override
    public void setMenuItems() {
        String cfgPath = "GUI.MainMenu.";
        ItemStack notes, players, settings, regions;

        notes = new ItemBuilder(Material.valueOf(cfg.getString(cfgPath + "Notes.type"))).setName(Vars.color(cfg.getString(cfgPath + "Notes.name")))
                .setLore((List<String>) cfg.get(cfgPath + "Notes.lore")).build();
        players = new ItemBuilder(Material.valueOf(cfg.getString(cfgPath + "Player.type"))).setSkullOwner(playerMenuUtility.getOwner().getName())
                .setName(Vars.color(cfg.getString(cfgPath + "Player.name")))
                .setLore((List<String>) cfg.get("Player.lore")).build();
        settings = new ItemBuilder(Material.valueOf(cfg.getString(cfgPath + "Settings.type")))
                .setName(ChatColor.GREEN + "Server Settings")
                .setLore((List<String>) cfg.get(cfgPath + "Settings.lore")).build();
        regions = new ItemBuilder(Material.SCAFFOLDING).setName(ChatColor.GREEN + "Regionen").setLore(Vars.color("&7Verwalte von dir erstellte Regionen")).build();


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
