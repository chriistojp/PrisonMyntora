package me.christo.prisoncore.events;


import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.myntora.core.core.util.Color;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FishCatchEvent implements Listener {


    // 99% chance
    // if(

    @EventHandler
    public void onFish(PlayerFishEvent e) {


        for (ProtectedRegion r : WGBukkit.getRegionManager(e.getPlayer().getWorld()).getApplicableRegions(e.getPlayer().getLocation())) {

            if (r.getId().startsWith("fishing")) {

                if (e.getCaught().getType() != null) {
                    //25 percent chance
                    if (ThreadLocalRandom.current().nextInt(100) <= 15) {
                        if (e.getPlayer().getInventory().firstEmpty() == -1) {
                            e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.RAW_FISH, 1, (byte) 3));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught a pufferfish!"));
                        } else {
                            e.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_FISH, 1, (byte) 3));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught a pufferfish! (Dropped on Ground)"));
                        }

                    }
                    //50/100 chance or 1/2 or 50%
                    if (ThreadLocalRandom.current().nextInt(100) <= 25) {
                        if (e.getPlayer().getInventory().firstEmpty() == -1) {
                            e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.RAW_FISH, 1, (byte) 2));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught a clownfish!"));
                        } else {
                            e.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_FISH, 1, (byte) 2));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught a clowfish! (Dropped on Ground)"));
                        }

                    }
                    if (ThreadLocalRandom.current().nextInt(100) <= 50) {
                        if (e.getPlayer().getInventory().firstEmpty() == -1) {
                            e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.RAW_FISH, 1, (byte) 1));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught a salmon!"));
                        } else {
                            e.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_FISH, 1, (byte) 1));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught a salmon! (Dropped on Ground)"));
                        }

                    }
                    if (ThreadLocalRandom.current().nextInt(100) <= 65) {
                        if (e.getPlayer().getInventory().firstEmpty() == -1) {
                            e.getPlayer().getLocation().getWorld().dropItem(e.getPlayer().getLocation(), new ItemStack(Material.RAW_FISH));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught another fish!"));
                        } else {
                            e.getPlayer().getInventory().addItem(new ItemStack(Material.RAW_FISH));
                            e.getPlayer().sendMessage(Color.prison("Fishing", "You got lucky and caught another fish! (Dropped on Ground)"));
                        }

                    }

                }
            }
        }
    }

}
