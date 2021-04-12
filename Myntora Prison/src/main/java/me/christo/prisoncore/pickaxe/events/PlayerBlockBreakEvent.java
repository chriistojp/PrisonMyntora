package me.christo.prisoncore.pickaxe.events;

import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.pickaxe.enchants.Explosive;
import me.christo.prisoncore.pickaxe.enchants.JackHammer;
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
            if (e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(Util.color("&7Right"))) {

                       //     Explosive.executeExplosive(p, e.getBlock());
                            JackHammer.executeJackHammer(p, e.getBlock());
                            e.setCancelled(true);
                            profile.getData().getPrisonBlocksMined().increaseAmount(1);
                            profile.getData().save();
                            StarterPickaxe.update(p);
                            System.out.println(profile.getData().getPrisonBlocksMined().getAmount());


                            for(ItemStack i : e.getBlock().getDrops()) {
                                p.getInventory().addItem(i);
                                if(PMine.getFile().getConfigurationSection("mines." + p.getUniqueId() + ".drops") == null) {
                                    PMine.getFile().createSection("mines." + p.getUniqueId() + ".drops");
                                    PMine.save();
                                }
                                for(String key :  PMine.getFile().getConfigurationSection("mines." + p.getUniqueId() + ".drops").getKeys(false)) {
                                    ItemStack item = PMine.getFile().getItemStack("mines." + p.getUniqueId() + ".drops." + key);
                                    if(item == i) {
                                        Bukkit.broadcastMessage("t");
                                        item.setAmount(i.getAmount() + item.getAmount());
                                        PMine.getFile().set("mines." + p.getUniqueId() + ".drops." + key, item);
                                        PMine.save();
                                    }
                                }
                                //PMine.getFile().set("mines." + p.getUniqueId() + ".drops." + PMine.getFile().getConfigurationSection("mines." + p.getUniqueId() + ".drops").getKeys(false).size(), i);
                                PMine.save();
                            }

                            e.getBlock().setType(Material.AIR);



                        }
                    }
                }
            }
        }
    }

}
