package de.iv.manager.commands;

import de.iv.manager.core.Vars;
import de.iv.manager.menus.cc.BlackListManager;
import de.iv.manager.security.AdminSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChatHistoryCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender.hasPermission("manager.seeChatHistory")) {
            if(args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);
                if(AdminSettings.getChatHistory(target) != null) {
                    if (args.length == 1) {
                        sender.sendMessage(Vars.color(Vars.SERVER_LOG + "Der Spieler &9" + target.getName() + " &7hat seit dem letzten Serverrestart folgende Nachrichten geschickt:"), "");
                        for (int i = 0; i < AdminSettings.getChatHistory(target).size(); i++) {
                            if (!BlackListManager.isBlacklisted(AdminSettings.getChatHistory(target).get(i)))
                                sender.sendMessage(Vars.color(Vars.SERVER_LOG + AdminSettings.getChatHistory(target).get(i)));
                            else
                                sender.sendMessage(Vars.color(Vars.SERVER_LOG + ChatColor.RED + AdminSettings.getChatHistory(target).get(i)));
                        }
                    } else if (args.length == 2) {
                        try {
                            int maxLines = Integer.parseInt(args[1]);
                            sender.sendMessage(Vars.color(Vars.SERVER_LOG + "Der Spieler &9" + target.getName() + " &7hat seit dem letzten Serverrestart folgende Nachrichten geschickt:"), Vars.color("&7Limit: &9" + maxLines), "");
                            for (int i = 0; i < AdminSettings.getChatHistory(target, maxLines).size(); i++) {
                                sender.sendMessage(Vars.color(Vars.SERVER_LOG + AdminSettings.getChatHistory(target, maxLines).get(i)));
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            sender.sendMessage(Vars.color(Vars.ERROR + Vars.GENERIC_ERROR + e.getMessage()));
                        }

                    }
                } else sender.sendMessage(Vars.color(Vars.ERROR + "Es befinden sich keine Nachrichten dieses Spielers im Chat-Log"));

            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 2) {
            return List.of("<int Limit>");
        }

        return null;
    }
}
