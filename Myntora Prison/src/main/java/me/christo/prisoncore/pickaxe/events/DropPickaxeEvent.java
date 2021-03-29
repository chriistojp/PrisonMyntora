package me.christo.prisoncore.pickaxe.events;


import me.christo.prisoncore.managers.StarterPickaxe;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropPickaxeEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

        if(StarterPickaxe.holdingPickaxe(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Color.prison("Pickaxe", "You cannot drop your pickaxe!"));
        }

    }

}
