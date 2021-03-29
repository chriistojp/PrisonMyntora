package me.christo.prisoncore.pickaxe.events;


import me.christo.prisoncore.managers.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ToolRightClickEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(StarterPickaxe.holdingPickaxe(e.getPlayer())) {
                EnchantsGUI.showEnchantsGui(e.getPlayer());
            }
        }

    }

}
