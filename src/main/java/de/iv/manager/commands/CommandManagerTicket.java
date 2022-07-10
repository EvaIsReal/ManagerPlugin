package de.iv.manager.commands;

import de.iv.manager.commands.subcommands.NoteCommand;
import de.iv.manager.commands.subcommands.NoteMenuCommand;
import de.iv.manager.commands.subcommands.Test;
import de.iv.manager.commands.subcommands.ToggleCommandLogging;
import de.iv.manager.commands.ticket.*;
import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.menus.ManagerMenu;
import de.iv.manager.menus.MenuManager;
import de.iv.manager.utils.LoggableCommand;
import de.iv.manager.utils.Subcommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManagerTicket implements CommandExecutor {

    private List<Subcommand> subcommands = new ArrayList<>();

    public CommandManagerTicket() {
        subcommands.add(new CreateTicketCommand());
        subcommands.add(new TicketListCommand());
        subcommands.add(new TicketAcceptCommand());
        subcommands.add(new TicketCloseCommand());
        subcommands.add(new TicketJoinCommand());
        subcommands.add(new TicketLeaveCommand());
    }

    FileConfiguration cfg = Main.getInstance().getMessageConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length > 0) {

                for(int i = 0; i < getSubcommands().size(); i++) {
                    if(p.hasPermission(getSubcommands().get(i).getPermission())) {
                        if(getSubcommands().get(i).aliases() == null) return true;
                        if(args[0].equalsIgnoreCase(getSubcommands().get(i).getName()) | getSubcommands().get(i).aliases().contains(args[0])) {
                            //Execute command
                            getSubcommands().get(i).execute(p, args);
                            String name = getSubcommands().get(i).getName();
                            if(getSubcommands().get(i) instanceof LoggableCommand) {
                                Main.loggablePlayers.forEach(a -> {
                                    String replace = PlaceholderAPI.setPlaceholders(p, "%player_name%");
                                    a.sendMessage(Vars.color(Vars.SERVER_LOG + Objects.requireNonNull(cfg.getString("Commands.ManagerCommandLogAlert"))
                                            .replace("%cmd%", ChatColor.BLUE + command.getName()).replace("%sender%", ChatColor.BLUE + sender.getName())));
                                });
                            }
                        } //else p.sendMessage(Vars.color(Objects.requireNonNull(cfg.getString(Vars.ERROR + "ExceptionMessages.CommandNullpointerException"))));
                    } else {
                        p.sendMessage(Vars.color(cfg.getString(Vars.ERROR + "ExceptionMessages.CommandNoPermissionException")));
                        break;
                    }
                }
            } else if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
                p.sendMessage(Vars.color(Vars.PREFIX + cfg.getString("Commands.ManagerHelpCallback")));
            } else {

                 p.sendMessage("§8§m------------§8[§b§lTICKET-SYSTEM§8]§8§m---------------");
                 p.sendMessage("");

                 for(int i = 0; i < getSubcommands().size(); i++) {
                    p.sendMessage(ChatColor.GOLD + getSubcommands().get(i).getSyntax() + "§8 | " + ChatColor.GRAY + getSubcommands().get(i).getDescription());
                 }

                 p.sendMessage("");
                 p.sendMessage("§8§m------------------------------------");
            }
        }
        return true;
    }

    public List<Subcommand> getSubcommands() {
        return subcommands;
    }
}

