package me.christo.prisoncore.CoreEvents;


import me.christo.prisoncore.Main;
import me.christo.prisoncore.PlayerDataConfig;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FirstJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if (!Util.players.containsKey(p.getUniqueId())) {
            System.out.println("test");
            Util.players.put(p.getUniqueId(), new PlayerDataConfig(Main.getInstance(), p.getUniqueId()));
        }

    }

}
