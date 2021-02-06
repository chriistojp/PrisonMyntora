package me.christo.prisoncore.Gangs.Events;


import me.christo.prisoncore.Gangs.Gangs;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FriendlyFireEvent implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        Player entity = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();

        PrisonPlayer player = new PrisonPlayer(entity.getUniqueId());
        PrisonPlayer reciever = new PrisonPlayer(damager.getUniqueId());

        if(player.hasGang()) {
            if(reciever.hasGang()) {
                if(player.getGang().equals(reciever.getGang())) {
                    if(Gangs.getFile().getBoolean("Gangs." + player.getGang() + ".friendlyFire")) {
                        e.setCancelled(true);
                        reciever.sendMessage(Util.color(Gangs.getFile().getString("Messages.friendlyFire")));
                    }
                }
            }
        }

    }

    //lol

}
