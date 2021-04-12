package me.christo.prisoncore.pickaxe.events;


import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropPickaxeEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

            if(e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_PICKAXE)) {
                if (e.getItemDrop().getItemStack().hasItemMeta()) {
                    if (e.getItemDrop().getItemStack().getItemMeta().hasDisplayName()) {
                        if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains(Util.color("&7Right"))) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(Color.prison("Pickaxe", "You cannot drop your pickaxe!"));
                        }
                    }
                }
            }
    }

}
