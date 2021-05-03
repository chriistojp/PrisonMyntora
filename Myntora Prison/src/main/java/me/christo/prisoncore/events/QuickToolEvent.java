package me.christo.prisoncore.events;

import me.christo.prisoncore.managers.QuickTool;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class QuickToolEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getPlayer().getItemInHand() != null) {
                if(e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
                if(e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Util.color("&d&lQuick Tool")))
                            QuickTool.openQuickTool(e.getPlayer());
                    }
                    }
                }
            }
        }

    }

}
