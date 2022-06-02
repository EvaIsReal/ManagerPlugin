/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.regions;

import org.bukkit.Location;

public class Bound {

    private Location loc1, loc2;

    public Bound() {
    }

    public Bound(Location loc1, Location loc2) {
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public boolean isComplete() {
        return loc1 != null && loc2 != null;
    }

    public boolean isInBounds(Location loc) {
        int minX = loc1.getBlockX(), minY = loc1.getBlockY(), minZ = loc1.getBlockZ();
        int maxX = loc2.getBlockX() + 1, maxY = loc2.getBlockY() + 1, maxZ = loc2.getBlockZ() + 1;

        double x = loc.getX(), y = loc.getY(), z = loc.getZ();
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;

    }

    public void assignCorrectBounds() {
        int minX = loc1.getBlockX(), minY = loc1.getBlockY(), minZ = loc1.getBlockZ();
        int maxX = loc2.getBlockX(), maxY = loc2.getBlockY(), maxZ = loc2.getBlockZ();

        if (minX > maxX) {
            int tempX = minX;
            minX = maxX;
            maxX = tempX;
        }
        if (minY > maxY) {
            int tempY = minY;
            minY = maxY;
            maxY = tempY;
        }
        if (minZ > maxZ) {
            int tempZ = minZ;
            minZ = maxZ;
            maxZ = tempZ;
        }
        loc1 = new Location(loc1.getWorld(), minX, minY, minZ);
        loc2 = new Location(loc2.getWorld(), maxX, maxY, maxZ);
    }


    public Location getLocation1() {
        return loc1;
    }

    public Location getLocation2() {
        return loc2;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }

}
