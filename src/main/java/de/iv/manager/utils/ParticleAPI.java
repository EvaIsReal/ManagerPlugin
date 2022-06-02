package de.iv.manager.utils;/*This class from the Project Manager was created by thoiv at 14.04.2022
*GitHub: iv221
*Discord: iv#3654

This is a non-commercial project. Don't use, or distribute 
this project without permission!

*/

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParticleAPI {

    public static void sendParticle(Player p, Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed) {
        p.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, speed);
    }

    public static void sendBlockCrackParticle(Player p, Material material, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed) {
        BlockData blockCrack = material.createBlockData();
        p.spawnParticle(Particle.BLOCK_CRACK, x, y, z, count, offsetX, offsetY, offsetZ, speed, blockCrack);
    }

    public static void sendDirectionalParticle(Player p, Particle particle, double x, double y, double z, double dirX, double dirY, double dirZ, double extra) {
        Vector loc = new Vector(x,y,z).normalize();
        p.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), 0, dirX, dirY, dirZ, extra);
    }

    public static void sendColoredParticle(Player p, org.bukkit.Color color, double x, double y, double z, double offX, double offY, double offZ, int count, double speed, float size) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, size);
        p.spawnParticle(Particle.REDSTONE, z, y, z, count, offX, offY, offZ, speed, dustOptions);
    }


}
