package me.christo.prisoncore.events;

import me.christo.prisoncore.managers.StarterPickaxe;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBlockBreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);


        if(e.getPlayer().getItemInHand().getType() == null) {
            return;
        }

        if(e.getPlayer().getItemInHand() != null) {
            if (StarterPickaxe.holdingPickaxe(p)) {
                profile.getData().getPrisonBlocksMined().increaseAmount(1);
                StarterPickaxe.update(p);
            }
        }
    }

}
