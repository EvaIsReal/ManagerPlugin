/*This class from the Project Manager was created by thoiv at 07.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Subcommand {

    public abstract String getName();

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract ArrayList<String> aliases();


    public abstract void execute(Player p, String[] args);


}
