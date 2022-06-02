/*This class from the Project Server Manager was created by thoiv at 05.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Stack;

public class PlayerMenuUtility {

    private Player owner;
    private final Stack<Menu> history = new Stack<>();
    private final HashMap<String, Object> multiMenuContainer = new HashMap<>();

    public void setData(String key, Object data) {
        this.multiMenuContainer.put(key, data);
    }

    public Object getData(String key) {
        return multiMenuContainer.get(key);
    }

    public <T> T getData(String key, Class<T> classRef) {
        Object o = this.multiMenuContainer.get(key);
        if(o != null) {
            return classRef.cast(o);
        } else return null;
    }

    public Menu lastMenu() {
        this.history.pop();
        return this.history.pop();
    }

    public void pushMenu(Menu menu) {
        this.history.push(menu);
    }

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }



    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
