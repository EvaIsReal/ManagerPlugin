package de.iv.manager.menus.settings;

import de.iv.manager.core.ConfigManager;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
import de.iv.manager.menus.cc.ChatControlMenu;
import de.iv.manager.menus.plugins.PluginListMenu;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.conversations.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ServerSettingsMenu extends Menu {

    public ServerSettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GOLD + "ServerSettings";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        switch (e.getCurrentItem().getType()) {
            case WRITABLE_BOOK -> {
                playerMenuUtility.getOwner().closeInventory();
                ConversationFactory factory = new ConversationFactory(Main.getInstance())
                        .withFirstPrompt(new ChangeModtFirstPrompt())
                        .withEscapeSequence("exit")
                        .withLocalEcho(false);
                factory.buildConversation(playerMenuUtility.getOwner()).begin();
            }
            case PAPER -> {
                MenuManager.openMenu(PluginListMenu.class, playerMenuUtility.getOwner());
            }

            case NAME_TAG -> {
                MenuManager.openMenu(ChatControlMenu.class, playerMenuUtility.getOwner());
            }
        }
        if(e.getCurrentItem().equals(inventory.getItem(35))) {
            try {
                Desktop.getDesktop().browse(URI.create("https://www.youtube.com/watch?v=wdUCRDvFv3Q&ab_channel=稲葉曇"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(10, new ItemBuilder(Material.WRITABLE_BOOK).setName(Vars.color("&aÄndere die Modt")).setLore(Vars.color("&7Setze die Modt des Servers"),
                Vars.color("&7(Die Nachricht in der Server-Liste)"),
                "",
                "§7'" + (String) ConfigManager.getInstance().getMessages().toFileConfiguration().get("Out.Server.Motd") + "§7'").build());
        inventory.setItem(35, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(Vars.color("&r;)")).build());

        new BukkitRunnable() {
            @Override
            public void run() {
                long freeRAM = Runtime.getRuntime().freeMemory();
                long maxRAM = Runtime.getRuntime().maxMemory();
                long r = (maxRAM - freeRAM);
                    inventory.setItem(12, new ItemBuilder(Material.COMMAND_BLOCK).setName(Vars.color("&a&lRAM-Auslastung"))
                                    .setLore("", Vars.color("&7" + r/1000000 + "&a&l/&7" + maxRAM/1000000 + " &9&lMB"))
                                    .build());

            }
        }.runTaskTimer(Main.getInstance(), 0, 10);


        inventory.setItem(14, new ItemBuilder(Material.PAPER).setName(Vars.color("&aPlugins")).setLore(
                Vars.color("&7Sieh dir alle auf dem Server installierten Plugins an.")
        ).build());

        inventory.setItem(16, new ItemBuilder(Material.NAME_TAG).setName(Vars.color("&aChat Kontrolle")).setLore(
                Vars.color("&7Verwalte Wörter, die nichts im Chat verloren haben.")
        ).build());

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return super.inventory;
    }




    //Prompts-----------------------------------------------------------------------------------------------------------
    private class ChangeModtFirstPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX + "&7Bitte gib den Text ein, der als neue &9Motd &7gesetzt werden soll.\n" +
                    "&7Du kannst diese Konversation mit &c'exit' &7abbrechen.");
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            ConfigManager.getInstance().getMessages().toFileConfiguration().set("Out.Server.Motd", Vars.color(input));
            ConfigManager.getInstance().getMessages().save();
            context.setSessionData("newMotd", input);
            return new CompletionPromt();
        }
    }

    private class CompletionPromt extends MessagePrompt {
        @Nullable
        @Override
        protected Prompt getNextPrompt(@NotNull ConversationContext context) {
            return END_OF_CONVERSATION;
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX + "Die Motd des Servers wurde zu '"+Vars.color((String) context.getSessionData("newMotd"))+"&7' geändert.");
        }
    }
}
