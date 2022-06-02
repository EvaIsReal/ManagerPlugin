/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.regions;

import de.iv.manager.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RegionManager {

    private Map<String, Region> regionMap = new HashMap<>();
    private Map<UUID, Bound> regionSetup = new HashMap<>();

    public static void serializeRegion(Region region) {
        try {
            DataManager.serialize(region, region.getRawName(), "serializedData");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public static Region getRegion(String name) {
        File fileDeser = new File(DataManager.SER_PATH.getPath() + "/" + name + "_serialized.ser");
        if(!fileDeser.exists()) return null;
        try {
            return (Region) DataManager.deserialize(fileDeser);
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static Region getRegion(Location loc) {
         for(Region r : getRegions()) {
             String rawName = r.getRawName();
             if(!r.getBound().isInBounds(loc)) continue;
             Region region = getRegion(rawName);
             double minX = region.getBound().getLocation1().getX(); double minY = region.getBound().getLocation1().getY(); double minZ = region.getBound().getLocation1().getZ();
             double maxX = region.getBound().getLocation2().getX(); double maxY = region.getBound().getLocation2().getY(); double maxZ = region.getBound().getLocation2().getZ();

             Location loc1 = new Location(Bukkit.getWorld("world"), minX, minY, minZ);
             Location loc2 = new Location(Bukkit.getWorld("world"), maxX, maxY, maxZ);
             boolean isSafe = false;
             Bound resBound = new Bound(loc1, loc2);

             if(region.isSafe()) isSafe = true;

             Region resRegion = new Region(resBound, rawName, isSafe);
         }
         return null;

    }

    public static List<Region> getRegions() {
        List<Region> regions = new ArrayList<>();
        for(File file : DataManager.listDirectoryFiles(DataManager.SER_PATH)) {
            try {
                Region r = (Region) DataManager.deserialize(file);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return regions;
    }


    public Map<String, Region> getRegionMap() {
        return regionMap;
    }

    public Map<UUID, Bound> getRegionSetup() {
        return regionSetup;
    }
}
