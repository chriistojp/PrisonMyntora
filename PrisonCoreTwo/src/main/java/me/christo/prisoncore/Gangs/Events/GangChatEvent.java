package me.christo.prisoncore.Gangs.Events;


import me.christo.prisoncore.Gangs.Gangs;
import me.christo.prisoncore.PrisonPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GangChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        PrisonPlayer player = new PrisonPlayer(p.getUniqueId());

        if(player.hasGangChat()) {
            e.setCancelled(true);
            Gangs.messageAllPlayers(player.getGang(), Gangs.getFile().getString("Messages.gangChatFormat").replaceAll("%player%", e.getPlayer().getName()).replaceAll("%message%", e.getMessage()));
        }

    }

}
