package me.christo.prisoncore.events;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DeathEvent implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {


        List<ItemStack> drops = e.getDrops();
        e.getDrops().clear();

        drops.removeIf(i -> i.getType() == Material.DIAMOND_PICKAXE);
        drops.removeIf(i -> i.getType() == Material.DRAGON_EGG);

        for(ItemStack i : drops) {
            e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), i);
        }


    }

}
