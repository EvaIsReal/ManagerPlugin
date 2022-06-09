package de.iv.manager.menus.plugins;

import com.google.gson.Gson;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.models.Note;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurrentPluginMenu extends Menu {

    public CurrentPluginMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static ArrayList<Plugin> disablesPlugins = new ArrayList<>();

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + (String) playerMenuUtility.getData("currentPlugin");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ArrayList<Plugin> plugins = new ArrayList<>(List.of(Main.getInstance().getServer().getPluginManager().getPlugins()));
        Plugin currentPlugin = Main.getInstance().getServer().getPluginManager().getPlugin((String) playerMenuUtility.getData("currentPlugin"));
        if (currentPlugin != null) {
            PluginDescriptionFile pdf = currentPlugin.getDescription();
            switch (e.getCurrentItem().getType()) {
                case EMERALD_BLOCK -> {
                    if(!Main.getInstance().getServer().getPluginManager().isPluginEnabled(currentPlugin)) {
                        Main.getInstance().getServer().getPluginManager().enablePlugin(currentPlugin);
                        e.getWhoClicked().sendMessage(Vars.color(Vars.PREFIX + ChatColor.BLUE + currentPlugin.getName() + " &7 wurde &aakiviert"));
                    } else e.getWhoClicked().sendMessage(Vars.color(Vars.ERROR + "Dieses Plugin ist bereits aktiviert."));
                }
                case PAPER -> {
                    Player p = (Player) e.getWhoClicked();
                    e.getWhoClicked().sendMessage(Vars.color(Vars.PREFIX + "Informationen zu &9" + currentPlugin.getName()));
                    p.sendMessage("");
                    p.sendMessage(Vars.color(Vars.PREFIX + "Name: &9" + pdf.getFullName()));
                    p.sendMessage(Vars.color(Vars.PREFIX + "Autor(en): &9" + pdf.getAuthors()));
                    p.sendMessage(Vars.color(Vars.PREFIX + "Version: &9" + pdf.getVersion()));
                    if(pdf.getWebsite() == null) p.sendMessage(Vars.color(Vars.PREFIX + "Website: &9&onicht angegeben"));
                    else p.sendMessage(Vars.color(Vars.PREFIX + "Website: &9" + pdf.getWebsite()));
                }
                case REDSTONE_BLOCK -> {
                    if(Main.getInstance().getServer().getPluginManager().isPluginEnabled(currentPlugin)) {
                        Main.getInstance().getServer().getPluginManager().disablePlugin(currentPlugin);
                        e.getWhoClicked().sendMessage(Vars.color(Vars.PREFIX + ChatColor.BLUE + currentPlugin.getName() + " &7 wurde &cdeakiviert"));
                    } else e.getWhoClicked().sendMessage(Vars.color(Vars.ERROR + "Dieses Plugin ist bereits deaktiviert."));
                }
                case BARRIER -> MenuManager.openMenu(MenuManager.getPreviousMenu(), playerMenuUtility.getOwner());

            }
        }

    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        /*inventory.setItem(11, new ItemBuilder(Material.EMERALD_BLOCK).setName(Vars.color("&aAktivieren"))
                        .setLore(Vars.color("&aAktiviere &7dieses Plugin"))
                        .build());
        */inventory.setItem(13, new ItemBuilder(Material.PAPER).setName(Vars.color("&aInformationen"))
                .setLore(Vars.color("&7Zeige Informationen zu diesem Plugin"), Vars.color("&7im Chat an"))
                .build());
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setName(Vars.color("&schließen")).build());
        /*inventory.setItem(15, new ItemBuilder(Material.REDSTONE_BLOCK).setName(Vars.color("&cDektivieren"))
                .setLore(Vars.color("&cDeaktiviere &7dieses Plugin"))
                .build());
        */
    }

    public static void saveDisabledPlugin(Plugin plugin) {
        disablesPlugins.add(plugin);
    }

    public static void pushToFile() throws IOException {
            Gson gson = new Gson();
            File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/persistentData/", "disabledPlugins.json");
            if(!file.exists()) file.createNewFile();
            Writer writer = new FileWriter(file, false);

            //Convert notes ArrayList into JSON-File:
            gson.toJson(disablesPlugins, writer);
            writer.flush();
            writer.close();

    }

    public static void loadDisabledPlugins() throws FileNotFoundException {
        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/persistentData/", "disabledPlugins.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            Plugin[] n = gson.fromJson(reader, Plugin[].class);
            disablesPlugins = new ArrayList<>(Arrays.asList(n));
        }
    }

    public static Plugin getPluginByName(String pluginName) {
        for(Plugin plugin : disablesPlugins) {
            if(plugin.getName().equalsIgnoreCase(pluginName)) {
                return plugin;
            }
        }
        return null;
    }


    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
