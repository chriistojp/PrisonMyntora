package me.christo.prisoncore.events;

import me.christo.prisoncore.managers.Goals;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockGoalEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player player = e.getPlayer();
        PrisonPlayer p = new PrisonPlayer(player);

        if (p.hasActiveGoal("mining")) {
            if(p.getGoalProgress("mining") == 10000) {
                p.sendMessage(Util.color(Goals.getFile().getString("messages.miningCompleted")));
                Goals.giveGoalRewards("mining", player);
            }
            p.addGoalProgress("mining", 1);

        }

    }

}
