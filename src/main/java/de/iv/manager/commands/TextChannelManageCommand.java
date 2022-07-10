package de.iv.manager.commands;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.text.TextChannel;
import de.iv.manager.text.TextChannelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextChannelManageCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("manager.switchTc")) {
                if(args.length == 1) {
                    String tcName = args[0];
                    TextChannel channel = TextChannelManager.getTextChannelByName(tcName);
                    if(channel != null) {
                        channel.open(p);
                        p.sendMessage(Vars.color(Vars.PREFIX + "Switched text channel to " + tcName.toUpperCase()));
                        return false;
                    } else p.sendMessage(Vars.color(Vars.ERROR + "Text channel is null"));
                }
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        for (Class<? extends TextChannel> tcClass : TextChannelManager.tcClasses) {
            try {
                TextChannel channel = tcClass.getConstructor().newInstance();
                strings.add(channel.name());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if(args.length == 1) {
            return strings;
        }

        return null;
    }
}
