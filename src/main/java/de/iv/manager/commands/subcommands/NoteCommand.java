/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.commands.subcommands;

import de.iv.manager.core.Vars;
import de.iv.manager.utils.NoteStorageUtil;
import de.iv.manager.utils.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NoteCommand extends Subcommand {
    @Override
    public ArrayList<String> aliases() {
        return null;
    }

    @Override
    public String getName() {
        return "createnote";
    }

    @Override
    public String getPermission() {
        return "manager.generic_mod_permissions";
    }

    @Override
    public String getDescription() {
        return "Erstellt eine neue Note-Datei.";
    }

    @Override
    public String getSyntax() {
        return "/manager createnote <Message>";
    }

    @Override
    public void execute(Player p, String[] args) {
        if(args.length > 0) {
            StringBuilder builder = new StringBuilder();
            for(int i = 1; i < args.length - 1; i++) {
                builder.append(args[i]).append(" ");
            }
            builder.append(args[args.length -1]);
            NoteStorageUtil.createNote(p, builder.toString());
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            p.sendMessage(Vars.color(Vars.PREFIX + "Eine neue &9Note&7 mit folgendem Text wurde erstellt:"));
            p.sendMessage("");
            p.sendMessage(Vars.color("&7&o" + builder.toString()));
        }
    }
}
