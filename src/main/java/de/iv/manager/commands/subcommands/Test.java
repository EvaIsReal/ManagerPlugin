/*This class from the Project Manager was created by thoiv at 08.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.commands.subcommands;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.utils.LoggableCommand;
import de.iv.manager.utils.Subcommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Test extends Subcommand implements LoggableCommand {

    @Override
    public ArrayList<String> aliases() {
        return null;
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getPermission() {
        return "manager.test";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getSyntax() {
        return "/test";
    }

    @Override
    public void execute(Player p, String[] args) {
        p.sendMessage(Vars.color("&6This is a test!"));
        p.sendMessage(Main.getInstance().getDataFolder().getAbsolutePath());
    }
}
