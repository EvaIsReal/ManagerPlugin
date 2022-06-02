/*This class from the Project Manager was created by thoiv at 15.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.menus.ban;

import de.iv.manager.core.Config;
import de.iv.manager.core.ConfigManager;
import de.iv.manager.core.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BanManager {

    static FileConfiguration cfg = ConfigManager.getInstance().getBannedPlayers().toFileConfiguration();

    public static void setTempbanned(String banningInstance, long time, String... reason) {
        cfg.options().copyDefaults(true);
        
    }


}
