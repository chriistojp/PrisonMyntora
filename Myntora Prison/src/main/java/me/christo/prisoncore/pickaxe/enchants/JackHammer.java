package me.christo.prisoncore.pickaxe.enchants;


import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import me.christo.prisoncore.shop.util.Items;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class JackHammer {

    public static int getUpgradeCost(int currentLevel) {
        int originalCost = 1000;
        return currentLevel * originalCost;
    }

    public static void addJackHammer(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int cost = getUpgradeCost(profile.getData().getPrisonJackHammerLevel().getAmount());

        if(profile.getData().getPrisonJackHammerLevel().getAmount() == 10) {
            p.sendMessage(Color.prison("Enchants", "You've Reached Max Level!"));
            return;
        }
        if(profile.getData().getPrisonMoney().getAmount() >= cost) {
            profile.getData().getPrisonJackHammerLevel().setAmount(profile.getData().getPrisonJackHammerLevel().getAmount() + 1);


            p.setItemInHand(StarterPickaxe.getPlayersPickaxe(p));
            p.getOpenInventory().close();
            EnchantsGUI.showEnchantsGui(p);

        } else {
            p.sendMessage(Color.prison("Enchantments", "You can't afford that!"));
        }

        //8154128841

    }

    public static void executeJackHammer(Player p, Block b) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        double toAdd = Items.getSellPrice(Items.translateFromMaterial(b.getType()));
        profile.getData().getPrisonMoney().increaseAmount((int) toAdd * 900);
        RegionManager manager = Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world"));
        ProtectedRegion r = null;
        for (ProtectedRegion rg : manager.getApplicableRegions(b.getLocation()).getRegions()) {
            if (rg.getId().contains("mine")) {
                r = rg;
                break;
            }
        }
        //double price = Items.getSellPrice(Objects.requireNonNull(Items.translateFromMaterial(b.getType())));
        int size = 100;
        if (r != null) {
            for (int x = b.getX() - size; x <= b.getX() + size; x++) {
                for (int z = b.getZ() - size; z <= b.getZ() + size; z++) {
                    if (r.contains(x, b.getY(), z)) {
                        Block block = new Location(b.getWorld(), x, b.getY(), z).getBlock();
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

}
