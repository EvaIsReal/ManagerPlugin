/*This class from the Project Manager was created by thoiv at 08.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.commands.subcommands;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.utils.Subcommand;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ToggleCommandLogging extends Subcommand {

    @Override
    public ArrayList<String> aliases() {
        ArrayList<String> list = new ArrayList<>();
        list.add("tcl");
        list.add("togglecmdlog");
        return list;
    }

    @Override
    public String getName() {
        return "togglecommandlogging";
    }

    @Override
    public String getPermission() {
        return "manager.seeCommandLog";
    }

    @Override
    public String getDescription() {
        return "Aktiviere- oder deaktiviere das Anzeigen von ausgeführten Befehlen.";
    }

    @Override
    public String getSyntax() {
        return "/manager togglecommandlogging";
    }

    @Override
    public void execute(Player p, String[] args) {
        if(Main.loggablePlayers.contains(p)) {
            Main.loggablePlayers.remove(p);
            p.sendMessage(Vars.color(Vars.SERVER_LOG + "Ausgeführte Befehle werden &cnicht &7mehr angezeigt."));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0);
        } else {
            Main.loggablePlayers.add(p);
            p.sendMessage(Vars.color(Vars.SERVER_LOG + "Ausgeführte Befehle werden angezeigt."));
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
        }
    }
}
