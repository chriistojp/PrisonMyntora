package me.christo.prisoncore.boosts.events;


import me.christo.prisoncore.boosts.boosts.Mega;
import me.christo.prisoncore.boosts.boosts.PVP;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillEvent implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event) {

        if(PVP.isActive() || Mega.isActive()) {
            Profile profile = Core.getInstance().getProfileManager().getProfile(event.getEntity().getKiller());
            profile.getData().getPrisonMoney().increaseAmount(1000);

            event.getEntity().getKiller().sendMessage(Color.prison("Kill", "+$1000 from PVP Booster!"));

        }
    }


}
