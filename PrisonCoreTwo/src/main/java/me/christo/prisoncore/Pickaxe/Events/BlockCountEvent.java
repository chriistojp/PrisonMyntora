package me.christo.prisoncore.Pickaxe.Events;

import me.christo.prisoncore.Pickaxe.StarterPickaxe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockCountEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if(StarterPickaxe.holdingPickaxe(p)) {
            StarterPickaxe.tryToUpgrade(p);
        }

    }

}
