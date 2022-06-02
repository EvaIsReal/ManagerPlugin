/*This class from the Project Manager was created by thoiv at 13.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus;

import de.iv.manager.core.Vars;
import de.iv.manager.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu extends Menu{

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage = maxItemsPerPage;
    }


    protected int page = 0;

    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void setBorderItems() {
        ItemStack left, right, close;

        right = new ItemBuilder(Material.DARK_OAK_BUTTON).setName(Vars.color("&anächste Seite")).build();
        left = new ItemBuilder(Material.DARK_OAK_BUTTON).setName(Vars.color("&aletzte Seite")).build();
        close = new ItemBuilder(Material.BARRIER).setName(Vars.color("&4schließen")).build();

        inventory.setItem(48, left);
        inventory.setItem(49, close);
        inventory.setItem(50, right);

        for(int i = 0; i < 10; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);

        for(int i = 44; i < 54; i++) {
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }


    }

}
