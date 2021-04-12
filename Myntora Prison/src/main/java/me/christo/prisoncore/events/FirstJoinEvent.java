package me.christo.prisoncore.events;


import me.christo.prisoncore.pickaxe.StarterPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        if(!e.getPlayer().hasPlayedBefore()) {
            StarterPickaxe.getPlayersPickaxe(p);
        }

    }

}
