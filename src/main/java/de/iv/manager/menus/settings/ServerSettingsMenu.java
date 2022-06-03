package de.iv.manager.menus.settings;

import de.iv.manager.core.ConfigManager;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.Menu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.PlayerMenuUtility;
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

        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(10, new ItemBuilder(Material.WRITABLE_BOOK).setName(Vars.color("&aÄndere die Modt")).setLore(Vars.color("&7Setze die Modt des Servers"),
                Vars.color("&7(Die Nachricht in der Server-Liste)"),
                "",
                (String) ConfigManager.getInstance().getMessages().toFileConfiguration().get("Out.Server.Motd")).build());

        new BukkitRunnable() {
            @Override
            public void run() {
                long freeRAM = Runtime.getRuntime().freeMemory();
                long maxRAM = Runtime.getRuntime().maxMemory();
                long r = (maxRAM - freeRAM);
                    inventory.setItem(13, new ItemBuilder(Material.COMMAND_BLOCK).setName(Vars.color("&a&lRAM-Auslastung"))
                                    .setLore("", Vars.color("&a" + r/1000000 + "&7/&a" + maxRAM/1000000 + " &9&lMB"))
                                    .build());

            }
        }.runTaskTimer(Main.getInstance(), 0, 10);


        inventory.setItem(16, new ItemBuilder(Material.PAPER).setName(Vars.color("&aPlugins")).setLore(
                Vars.color("&7Sieh dir alle auf dem Server installierten Plugins an.")
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
