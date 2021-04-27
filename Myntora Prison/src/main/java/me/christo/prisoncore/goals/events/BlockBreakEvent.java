package me.christo.prisoncore.goals.events;


import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void onBreak(org.bukkit.event.block.BlockBreakEvent e) {

        Profile profile = Core.getInstance().getProfileManager().getProfile(e.getPlayer());



    }

}
