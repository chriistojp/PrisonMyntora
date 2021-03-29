package me.christo.prisoncore.events;


import me.christo.prisoncore.managers.Mines;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.command.Command;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class MineSelectorEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
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
