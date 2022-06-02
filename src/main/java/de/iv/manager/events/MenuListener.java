/*This class from the Project Manager was created by thoiv at 11.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.events;

import de.iv.manager.menus.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * This class builds on mechanics developed by <a href="https://github.com/KodySimpson">https://github.com/KodySimpson</a>
 * <br>Sorry for not using your API. It constantly throws a ClassNotFoundException while starting. I've tried to fix it but
 * I've lost hope eventually.
 */
public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if(holder instanceof Menu) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) {
                return;
            }
            ((Menu) holder).handleMenu(e);
        }
    }



}
