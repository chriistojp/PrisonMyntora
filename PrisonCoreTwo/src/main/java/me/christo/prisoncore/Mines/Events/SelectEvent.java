package me.christo.prisoncore.Mines.Events;


import me.christo.prisoncore.Mines.Mines;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class SelectEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = (Player) e.getPlayer();



        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            //Check if player is holding selector
            if(Mines.isSelector(p) && p.isOp()) {
                Mines.positionOne.put(p, Objects.requireNonNull(e.getClickedBlock()).getLocation());
                String send = msg("selected").replaceAll("%pos%", "1").replaceAll("%x%", String.valueOf(e.getClickedBlock().getLocation().getX())
                ).replaceAll("%y%", String.valueOf(e.getClickedBlock().getLocation().getY())
                ).replaceAll("%z%", String.valueOf(e.getClickedBlock().getLocation().getZ()));
                p.sendMessage(send);
            }
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            //Check if player is holding selector
            if(Mines.isSelector(p) && p.isOp()) {
                Mines.positionTwo.put(p, Objects.requireNonNull(e.getClickedBlock()).getLocation());
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
