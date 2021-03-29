package me.christo.prisoncore.events;


import me.christo.prisoncore.managers.Gangs;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GangChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if(Gangs.gangChat.contains(e.getPlayer())) {
            e.setCancelled(true);
            Profile profile = Core.getInstance().getProfileManager().getProfile(e.getPlayer());
            Gangs.gangChatMessage(e.getPlayer(), profile.getData().getPrisonGangName().getCell(), e.getMessage());

        }
    }

}
