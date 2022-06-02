/*This class from the Project Manager was created by thoiv at 10.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.core;

import java.io.File;

public class FileManager {

    public FileManager() {
        createPersistentDataFolder();
    }

    public void createPersistentDataFolder() {
        File folder = new File(Main.getInstance().getDataFolder(), "persistentData");
        if(!folder.exists()) folder.mkdirs();
    }


}
