package me.christo.prisoncore.events;


import me.christo.prisoncore.managers.FarmCells;
import me.christo.prisoncore.managers.Mines;
import me.christo.prisoncore.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class FarmMineSelectorEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            //Check if player is holding selector
            if(FarmCells.isSelector(p) && p.isOp()) {
                FarmCells.setLocation(p, Objects.requireNonNull(e.getClickedBlock()).getLocation(), 1);
                String send = msg("selected").replaceAll("%pos%", "1").replaceAll("%x%", String.valueOf(e.getClickedBlock().getLocation().getX())
                ).replaceAll("%y%", String.valueOf(e.getClickedBlock().getLocation().getY())
                ).replaceAll("%z%", String.valueOf(e.getClickedBlock().getLocation().getZ()));
                p.sendMessage(send);
            }
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            //Check if player is holding selector
            if(FarmCells.isSelector(p) && p.isOp()) {
                FarmCells.setLocation(p, Objects.requireNonNull(e.getClickedBlock()).getLocation(), 2);
                String send = msg("selected").replaceAll("%pos%", "2").replaceAll("%x%", String.valueOf(e.getClickedBlock().getLocation().getX())
                ).replaceAll("%y%", String.valueOf(e.getClickedBlock().getLocation().getY())
                ).replaceAll("%z%", String.valueOf(e.getClickedBlock().getLocation().getZ()));
                p.sendMessage(send);
            }
        }

    }

    public String msg(String path) {



        return Util.color(Mines.getFile().getString("messages." + path));

    }


}
