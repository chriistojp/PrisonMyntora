package me.christo.prisoncore.events;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.managers.StarterPickaxe;
import me.christo.prisoncore.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        if(!e.getPlayer().hasPlayedBefore()) {
            StarterPickaxe.givePickaxe(p);
        }

    }

}
