/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.commands.subcommands;


import de.iv.manager.menus.MenuManager;
import de.iv.manager.menus.notes.NotesMenu;
import de.iv.manager.utils.Subcommand;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NoteMenuCommand extends Subcommand {

    @Override
    public ArrayList<String> aliases() {
        return null;
    }

    @Override
    public String getName() {
        return "notes";
    }

    @Override
    public String getPermission() {
        return "manager.showNoteMenu";
    }

    @Override
    public String getDescription() {
        return "Öffnet das Note-Menu";
    }

    @Override
    public String getSyntax() {
        return "/manager notes";
    }

    @Override
    public void execute(Player p, String[] args) {
        MenuManager.openMenu(NotesMenu.class, p);
    }
}
