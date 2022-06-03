/*This class from the Project Manager was created by thoiv at 13.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus;

import de.iv.manager.menus.Menu;
import de.iv.manager.menus.PlayerMenuUtility;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuManager {

    private static final HashMap<String, Menu> REGISTERED_MENUS = new HashMap<>();
    private static final HashMap<Player, PlayerMenuUtility> pmuMap = new HashMap<>();
    private static final ArrayList<Class<? extends Menu>> HISTORY = new ArrayList<>();


    public static void openMenu(Class<? extends Menu> menuClass, Player p) {
        try {
            //Gets constructor from Menu class and parses in the needed PMU, calls open() method
            menuClass.getConstructor(PlayerMenuUtility.class).newInstance(getPlayerMenuUtility(p)).open();
            HISTORY.add(menuClass);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Menu getMenuByName(String paramName) {
        return REGISTERED_MENUS.get(paramName);
    }

    public static Class<? extends Menu> getPreviousMenu() {
        return HISTORY.get(HISTORY.size()-2);
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if(pmuMap.containsKey(p)) {
            return pmuMap.get(p);
        } else {
            playerMenuUtility = new PlayerMenuUtility(p);
            pmuMap.put(p, playerMenuUtility);
            return playerMenuUtility;
        }

    }


}
