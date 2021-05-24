package me.christo.prisoncore.pickaxe.events;

import com.boydti.fawe.bukkit.regions.Worldguard;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.pickaxe.enchants.Explosive;
import me.christo.prisoncore.pickaxe.enchants.JackHammer;
import me.christo.prisoncore.shop.ShopManager;
import me.christo.prisoncore.shop.commands.ShopCommand;
import me.christo.prisoncore.shop.util.Items;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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

        RegionManager manager = Prison.getWorldGuard().getRegionManager(p.getWorld());
        for (ProtectedRegion rg : manager.getApplicableRegions(p.getLocation()).getRegions()) {

            if(!rg.getId().contains("mine")) {
                return;
            }

        }
        Collection<ItemStack> drops = e.getBlock().getDrops();
        e.getBlock().getDrops().clear();



        if(e.getPlayer().getItemInHand() != null) {
            if (e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(Util.color("&7Right"))) {

                            Bukkit.broadcastMessage("true");

                       //     Explosive.executeExplosive(p, e.getBlock());
                            JackHammer.executeJackHammer(p, e.getBlock());
                            e.setCancelled(true);
                            profile.getData().getPrisonBlocksMined().increaseAmount(1);

                            StarterPickaxe.update(p);

                            Bukkit.broadcastMessage("0");
                            for(ItemStack i : drops) {
                                Bukkit.broadcastMessage("1");
                                if(Items.isSellableItem(i)) {
                                    Bukkit.broadcastMessage("2");
                                    Items item = Items.translateFromMaterial(i.getType());
                                    Bukkit.broadcastMessage("3");
                                    assert item != null;
                                    double price = Items.getSellPrice(item);
                                    profile.getData().getPrisonMoney().increaseAmount((int) price);
                                }
                            }



                            e.getBlock().setType(Material.AIR);



                        }
                    }
                }
            }
        }
    }

}
