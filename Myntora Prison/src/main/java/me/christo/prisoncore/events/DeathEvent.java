package me.christo.prisoncore.events;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        for(ItemStack i : e.getDrops()) {
            Bukkit.broadcastMessage(i.getType() + "");
        }

    }

}
