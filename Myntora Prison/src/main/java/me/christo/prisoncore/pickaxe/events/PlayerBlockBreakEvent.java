package me.christo.prisoncore.pickaxe.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.managers.StarterPickaxe;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class PlayerBlockBreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        if(e.getPlayer().getItemInHand().getType() == null) {
            return;
        }

        e.getBlock().getDrops().clear();

      //  Collection<ItemStack> drops = e.getBlock().getDrops();

        if(e.getPlayer().getItemInHand() != null) {
            if (StarterPickaxe.holdingPickaxe(p)) {
                profile.getData().getPrisonBlocksMined().increaseAmount(1);
                profile.getData().save();
                StarterPickaxe.update(p);


//                for(ItemStack i : drops) {
//                    e.getPlayer().getInventory().addItem(i);
//                }

            }
        }
    }
    @EventHandler
    public void onDrop(BlockDropItemEvent e) {

        if(StarterPickaxe.holdingPickaxe(e.getPlayer())) {
            List<Item> items = e.getItems();
            e.getItems().clear();
        }


    }

}
