package me.christo.prisoncore.events;


import me.christo.prisoncore.managers.Gangs;
import me.christo.prisoncore.PrisonPlayer;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GangChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();
        PrisonPlayer player = new PrisonPlayer(p.getUniqueId());
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if(player.hasGangChat()) {
            e.setCancelled(true);
            Gangs.messageAllPlayers(player.getGang(), Gangs.getFile().getString("Messages.gangChatFormat").replaceAll("%player%", e.getPlayer().getName()).replaceAll("%message%", e.getMessage()));
        }

    }

}
