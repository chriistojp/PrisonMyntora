package me.christo.prisoncore.pickaxe.enchants;


import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import me.christo.prisoncore.shop.gui.ShopGUI;
import me.christo.prisoncore.shop.util.Items;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Explosive {

    //Honestly just start at $500 for all of them for level one and then up the cost by like $1000 or somethin and go to level 10


    public static int getUpgradeCost(int currentLevel) {
        int originalCost = 10000;
        return currentLevel * originalCost;
    }

    public static void addExplosive(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int cost = getUpgradeCost(profile.getData().getPrisonExplosiveLevel().getAmount());

        if (profile.getData().getPrisonExplosiveLevel().getAmount() == 10) {
            p.sendMessage(Color.prison("Enchants", "You've Reached Max Level!"));
            return;
        }
        if (profile.getData().getPrisonMoney().getAmount() >= cost) {
            profile.getData().getPrisonExplosiveLevel().setAmount(profile.getData().getPrisonExplosiveLevel().getAmount() + 1);
            profile.getData().save();

            p.setItemInHand(StarterPickaxe.getPlayersPickaxe(p));
            p.getOpenInventory().close();
            EnchantsGUI.showEnchantsGui(p);

        } else {
            p.sendMessage(Color.prison("Enchantments", "You can't afford that!"));
        }

        //8154128841

    }

    public static void executeExplosive(Player p, Block b) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        double toAdd = Items.getSellPrice(Items.translateFromMaterial(b.getType()));
        profile.getData().getPrisonMoney().increaseAmount((int) toAdd * 25);
        RegionManager manager = Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world"));
        ProtectedRegion r = null;
        for (ProtectedRegion rg : manager.getApplicableRegions(b.getLocation()).getRegions()) {
            if (rg.getId().contains("mine")) {
                r = rg;
                break;
            }
        }
        //double price = Items.getSellPrice(Objects.requireNonNull(Items.translateFromMaterial(b.getType())));
        for (int x = b.getLocation().getBlockX() - 5 / 2; x < b.getLocation().getBlockX() + 5 / 2 + 1; x++) {
            for (int y = b.getLocation().getBlockY() - 5 / 2; y < b.getLocation().getBlockY() + 5 / 2; y++) {
                for (int z = b.getLocation().getBlockZ() - 5 / 2; z < b.getLocation().getBlockZ() + 5 / 2
                        + 1; z++) {
                    if (r != null && r.contains(x, y, z)) {
                        Block block = new Location(b.getWorld(), x, b.getY(), z).getBlock();

                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
