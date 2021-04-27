package me.christo.prisoncore.events;


import me.christo.cooldown.api.API;
import me.christo.prisoncore.managers.Pets;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.util.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PetsClickEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

            if(p.getInventory().getItemInHand() != null) {
                if(p.getInventory().getItemInHand().getType() == Material.DRAGON_EGG) {
                    if(p.getInventory().getItemInHand().hasItemMeta()) {
                        if(p.getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
                            if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Util.color("&d&lINVENTORY PET"))) {
                                e.setCancelled(true);
                                    Pets.openGui(p);


                            }
                        }
                    }
                }
            }

        }

    }


}
