/*This class from the Project Manager was created by thoiv at 07.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.commands;

import de.iv.manager.commands.subcommands.NoteCommand;
import de.iv.manager.commands.subcommands.NoteMenuCommand;
import de.iv.manager.commands.subcommands.Test;
import de.iv.manager.commands.subcommands.ToggleCommandLogging;
import de.iv.manager.core.ConfigManager;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.ManagerMenu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.utils.LoggableCommand;
import de.iv.manager.utils.Subcommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private List<Subcommand> subcommands = new ArrayList<>();

    public CommandManager() {
        subcommands.add(new ToggleCommandLogging());
        subcommands.add(new Test());
        subcommands.add(new NoteCommand());
        subcommands.add(new NoteMenuCommand());
    }

    FileConfiguration cfg = ConfigManager.getInstance().getMessages().toFileConfiguration();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length > 0) {

                for(int i = 0; i < getSubcommands().size(); i++) {
                    if(p.hasPermission(getSubcommands().get(i).getPermission())) {
                        if(getSubcommands().get(i).aliases() == null) return true;
                        if(args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                            //Execute command
                            getSubcommands().get(i).execute(p, args);
                            String name = getSubcommands().get(i).getName();
                            if(getSubcommands().get(i) instanceof LoggableCommand) {
                                Main.loggablePlayers.forEach(a -> {
                                        String replace = PlaceholderAPI.setPlaceholders(p, "%player_name%");
                                        a.sendMessage(Vars.color(Vars.SERVER_LOG + "Der Befehl &o" + name + "&7 wurde von&9 " + replace + " &7ausgeführt."));
                                });
                            }



                        }
                         //else p.sendMessage(Vars.color(Objects.requireNonNull(cfg.getString("Console.Commands.Exceptions.CommandNullpointerException"))));

                    } else {
                        p.sendMessage(Vars.color(Objects.requireNonNull(cfg.getString("Console.Commands.Exceptions.InvalidPermissionsException"))));
                        break;
                    }
                }
            } else {

                MenuManager.openMenu(ManagerMenu.class, p);

                /**
                    p.sendMessage("§8§m------------§8[§b§lMANAGER§8]§8§m---------------");
                    p.sendMessage("");

                    for(int i = 0; i < getSubcommands().size(); i++) {
                        p.sendMessage(ChatColor.GOLD + getSubcommands().get(i).getSyntax() + "§8 | " + ChatColor.GRAY + getSubcommands().get(i).getDescription());
                    }

                    p.sendMessage("");
                    p.sendMessage("§8§m------------------------------------");
                 */
            }


        }


        return true;
    }

    public List<Subcommand> getSubcommands() {
        return subcommands;
    }
}
