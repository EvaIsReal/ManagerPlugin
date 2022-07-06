package de.iv.manager.commands;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TextChannelManageCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("manager.switchTextChannel")) {
                if(args.length == 1) {
                    String tc = args[0];
                    try {
                        TextChannel channel = TextChannel.valueOf(tc.toUpperCase());

                        Main.textChannelMap.put(p, channel);
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        p.sendMessage(Vars.color(Vars.PREFIX + "You are now chatting in the &9" + channel + " &7text channel"));
                    } catch (IllegalArgumentException e) {
                        //p.sendMessage(Vars.color(Vars.ERROR + Vars.GENERIC_ERROR + e.getMessage()));
                    }

                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1 && sender.hasPermission("manager.teamMember")) {
            List<String> tc = new ArrayList<>();
            for (TextChannel value : TextChannel.values()) {
                tc.add(value.toString());
            }
            return tc;
        }
        return null;
    }
}
