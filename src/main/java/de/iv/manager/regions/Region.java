/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.regions;

import org.bukkit.ChatColor;

import java.io.Serializable;

public class Region implements Serializable {

    private Bound bound;
    private String rawName, displayName;
    private boolean isSafe;

    public Region(Bound bound, String displayName, boolean isSafe) {
        this.bound = bound;
        this.rawName = ChatColor.stripColor(displayName);
        this.displayName = displayName;
        this.isSafe = isSafe;

        RegionManager.serializeRegion(this);
    }

    public Bound getBound() {
        return bound;
    }

    public void setBound(Bound bound) {
        this.bound = bound;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }
}
