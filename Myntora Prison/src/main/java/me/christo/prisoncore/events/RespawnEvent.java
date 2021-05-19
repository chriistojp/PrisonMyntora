package me.christo.prisoncore.events;


import me.christo.prisoncore.pickaxe.StarterPickaxe;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {


    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {

        e.getPlayer().getInventory().addItem(StarterPickaxe.getPlayersPickaxe(e.getPlayer()));
        e.getPlayer().teleport(new Location(Bukkit.getWorld("prison_spawn"), 30, 98, -49));

    }

}
