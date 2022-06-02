/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

package de.iv.manager.regions;

import de.iv.manager.core.Main;
import de.iv.manager.core.Vars;
import de.iv.manager.utils.ParticleAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RegionListener implements Listener {

    private Map<UUID, Bound> regionSetup = Main.getInstance().getRegionManager().getRegionSetup();
    private Map<UUID, Region> playerRegion = new HashMap<>();
    private Map<String, Object> transferObjects = new HashMap<>();

    public RegionListener(Main pluginInstance) {

        new BukkitRunnable() {

            @Override
            public void run() {
                for(UUID uuid : regionSetup.keySet()) {
                    Player p = Bukkit.getPlayer(uuid);
                    if(p == null) continue;
                    Bound b = regionSetup.get(uuid);
                    if(!b.isComplete()) continue;

                    Bound newBound = new Bound(b.getLocation1(), b.getLocation2());
                    newBound.assignCorrectBounds();
                    Location loc1 = newBound.getLocation1(), loc2 = newBound.getLocation2();

                    for(int x = loc1.getBlockX(); x <= loc2.getBlockX() +1; x++) {
                        for(int y = loc1.getBlockY(); y <= loc2.getBlockY() +1; y++) {
                            for(int z = loc1.getBlockZ(); z <= loc2.getBlockZ() +1; z++) {
                                if(x == loc1.getBlockX() || x == loc2.getBlockX() +1 || y == loc1.getBlockY() || y == loc2.getBlockY()+1 || z == loc1.getBlockZ() || z == loc2.getBlockZ()+1) {
                                    ParticleAPI.sendParticle(p, Particle.VILLAGER_HAPPY, x, y, z, 1, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                }

            }
        }.runTaskTimer(pluginInstance, 0, 7);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        List<Player> playerRegion = new ArrayList<>();

        Region pRegion = RegionManager.getRegion(p.getLocation());
        for(int i = 0; i < RegionManager.getRegions().size(); i++) {
            Region region = RegionManager.getRegions().get(i);

            if(region == pRegion && !playerRegion.contains(p)) {
                playerRegion.add(p);
                p.sendMessage("Test");
            }
            playerRegion.remove(p);
        }

    }

    @EventHandler
    public void onSetupCall(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(regionSetup.containsKey(p.getUniqueId())) {
           e.setCancelled(true);
            Bound bound = regionSetup.get(p.getUniqueId());
           if(e.getMessage().equalsIgnoreCase(".setup") && bound.isComplete()) {
               //Start setup conv
               ConversationFactory factory = new ConversationFactory(Main.getInstance())
                       .withTimeout(60)
                       .withPrefix(new RegionSetupPrefix())
                       .withFirstPrompt(new RegionSetupNamePrompt())
                       .withEscapeSequence("exit")
                       .withLocalEcho(false)
                       .thatExcludesNonPlayersWithMessage("No Consoles!");
               factory.buildConversation(p).begin();


           }
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!regionSetup.containsKey(p.getUniqueId())) return;
        Bound b = regionSetup.get(p.getUniqueId());
        transferObjects.put("regionBound", b);

        Block block = e.getClickedBlock();
        if(block == null) return;
        switch(e.getAction()) {

            case LEFT_CLICK_BLOCK:
                e.setCancelled(true);
                b.setLoc1(block.getLocation());
                p.sendMessage(Vars.PREFIX + Vars.color("&aLocation #1 gesetzt!"));
                break;

            case RIGHT_CLICK_BLOCK:
                e.setCancelled(true);
                b.setLoc2(block.getLocation());
                p.sendMessage(Vars.PREFIX + Vars.color("&aLocation #2 gesetzt!"));
                break;

        }

    }

    private class RegionSetupPrefix implements ConversationPrefix {

        @NotNull
        @Override
        public String getPrefix(@NotNull ConversationContext context) {
            return Vars.color(Vars.PREFIX);
        }
    }

    private class RegionSetupNamePrompt extends StringPrompt {

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            context.setSessionData("regionName", input);
            context.setSessionData("regionRawName", ChatColor.stripColor(input));
            return new RegionSetupBoolPrompt();
        }

        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color("&7Bitte wähle einen Namen für die Region." +
                    "\nDiesen kannst du mit den Minecraft-Formatting-Codes formatieren." +
                    "\nDu kannst diese Konversation mit '&cexit&7' abbrechen.");
        }
    }

    private class RegionSetupBoolPrompt extends StringPrompt {
        @NotNull
        @Override
        public String getPromptText(@NotNull ConversationContext context) {
            return Vars.color("&7Soll die Region PvP aktiviert oder deaktiviert haben? Entscheide mit &9true &7oder &9false&7.");
        }

        @Nullable
        @Override
        public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
            Region regionCreate = new Region(regionSetup.get(transferObjects.get("regionBound")), (String) context.getSessionData("regionName"),
                    Boolean.valueOf(input));
            context.getForWhom().sendRawMessage(Vars.color(Vars.PREFIX + "Die Region &9" + context.getSessionData("regionName") + " &7wurde erfolgreich erstellt."));
            Player p = (Player) context.getForWhom();
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            regionSetup.remove(p.getUniqueId());
            return END_OF_CONVERSATION;
        }
    }

}
