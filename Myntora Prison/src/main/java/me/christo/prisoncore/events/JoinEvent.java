package me.christo.prisoncore.events;


import me.christo.prisoncore.managers.Pets;
import me.christo.prisoncore.pickaxe.StarterPickaxe;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        if(!e.getPlayer().hasPlayedBefore()) {
            p.teleport(new Location(Bukkit.getWorld("prison_spawn"), 0, 100 ,0));
            p.sendMessage(Color.main("Join", "Welcome to Prison!"));
            e.getPlayer().getInventory().addItem(StarterPickaxe.getPlayersPickaxe(e.getPlayer()));
            Pets.giveItemStack(e.getPlayer());
        } else {
            boolean pickaxe = false;
            boolean pet = false;
            for(int i = 0; i < 36; i++) {
                if(e.getPlayer().getInventory().getItem(i) == null) {
                    continue;
                }
                if(e.getPlayer().getInventory().getItem(i).getType() == Material.DIAMOND_PICKAXE) {
                    pickaxe = true;
                }
                if(e.getPlayer().getInventory().getItem(i).getType() == Material.DRAGON_EGG) {
                    pet = true;
                }
            }
            if(!pet) {
                Pets.giveItemStack(e.getPlayer());
            }
            if(!pickaxe) {
                e.getPlayer().getInventory().addItem(StarterPickaxe.getPlayersPickaxe(e.getPlayer()));
            }
        }
    }
}
