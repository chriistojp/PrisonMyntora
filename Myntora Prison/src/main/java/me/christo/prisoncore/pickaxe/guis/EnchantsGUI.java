package me.christo.prisoncore.pickaxe.guis;


import me.christo.prisoncore.pickaxe.enchants.*;
import me.christo.prisoncore.utils.Gui;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EnchantsGUI implements Listener {




    public static void showEnchantsGui(Player p) {

        Gui gui = new Gui("Enchants", 45);


        for(int i = 0; i < 45; i++) {
            int random = ThreadLocalRandom.current().nextInt(0, 2);
            if(random == 1) {
                gui.i(i, Material.STAINED_GLASS_PANE, " ");
            } else {
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 2), " ");
            }
        }

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        int efficiencyCurrent = profile.getData().getPrisonEfficiencyLevel().getAmount();
        int fortuneCurrent = profile.getData().getPrisonFortuneLevel().getAmount();
//        int crateFinderCurrent = profile.getData().getPrisonCr().getAmount();
        int currentSpeed = profile.getData().getPrisonSpeedLevel().getAmount();
        int currentJump = profile.getData().getPrisonSpeedLevel().getAmount();
        int currentHaste = profile.getData().getPrisonHasteLevel().getAmount();
        int currentLaser = profile.getData().getPrisonLaserLevel().getAmount();
        int currentExplosive = profile.getData().getPrisonExplosiveLevel().getAmount();
        int currentJackHammer = profile.getData().getPrisonJackHammerLevel().getAmount();

        int efficiencyLevel = Efficiency.getUpgradeCost(profile.getData().getPrisonEfficiencyLevel().getAmount() + 1);
        int fortuneLevel = Fortune.getUpgradeCost(profile.getData().getPrisonFortuneLevel().getAmount() + 1);
//        int crateFinderLevel = CrateFinder.getUpgradeCost(profile.getData().getPrisonCrateFinder().getAmount());
        int speedLevel = Speed.getUpgradeCost(profile.getData().getPrisonSpeedLevel().getAmount() + 1);
        int jumpLevel = Jump.getUpgradeCost(profile.getData().getPrisonJumpLevel().getAmount() + 1);
        int hasteLevel = Haste.getUpgradeCost(profile.getData().getPrisonHasteLevel().getAmount() + 1);
        int laserLevel = Laser.getUpgradeCost(profile.getData().getPrisonLaserLevel().getAmount() + 1);
        int explosiveLevel = Explosive.getUpgradeCost(profile.getData().getPrisonExplosiveLevel().getAmount() + 1);
        int jackHammerLevel = JackHammer.getUpgradeCost(profile.getData().getPrisonJackHammerLevel().getAmount() + 1);

        gui.i(12, Material.FEATHER, "&f&lEfficiency",  "", "&f&l&m»&r &7Info", "", "&7 ⭐ Cost: &f" + efficiencyLevel, "&7 ⭐ Current Level: &f" + efficiencyCurrent, "", " &7&oClick to upgrade your &f&oefficiency &7&olevel!");
        gui.i(13, Material.EXP_BOTTLE, "&e&lFortune",  "", "&e&l&m»&r &7Info", "","&7 ⭐ Cost: &e" + fortuneLevel, "&7 ⭐ Current Level: &e" + fortuneCurrent, "", " &7&oClick to upgrade your &e&ofortune &7&olevel!");
//        gui.i(14, Material.CHEST, "&6&lCrate Finder",  "", "&6&l&m»&r &7Info", "","&7 ⭐ Cost: &6" + crateFinderLevel, "&7 ⭐ Current Level: &6" + crateFinderCurrent, "", " &7&oClick to upgrade your &6&ocrate finder &7&olevel!");
        gui.i(21, Material.SUGAR, "&f&lSpeed",  "", "&f&l&m»&r &7Info", "","&7 ⭐ Cost: &f" + speedLevel, "&7 ⭐ Current Level: &f" + currentSpeed, "", " &7&oClick to upgrade your &f&ospeed &7&olevel!");
        gui.i(22, Material.PISTON_BASE, "&2&lJump",  "", "&2&l&m»&r &7Info", "","&7 ⭐ Cost: &2" + jumpLevel, "&7 ⭐ Current Level: &2" + currentJump, "", " &7&oClick to upgrade your &2&ojump &7&olevel!");
        gui.i(23, Material.GOLD_PICKAXE, "&e&lHaste",  "", "&e&l&m»&r &7Info", "","&7 ⭐ Cost: &e" + hasteLevel, "&7 ⭐ Current Level: &e" + currentHaste, "", " &7&oClick to upgrade your &ehaste &7&olevel!");
        gui.i(30, Material.BLAZE_ROD, "&c&lLaser",  "", "&c&l&m»&r &7Info", "","&7 ⭐ Cost: &c" + laserLevel, "&7 ⭐ Current Level: &c" + currentLaser, "", " &7&oClick to upgrade your &c&olaser &7&olevel!");
        gui.i(31, Material.ARROW, "&3&lJack Hammer",  "", "&3&l&m»&r &7Info","", "&7 ⭐ Cost: &3" + jackHammerLevel, "&7 ⭐ Current Level: &3" + currentExplosive, "", " &7&oClick to upgrade your &3&ojack hammer &7&olevel!");
        gui.i(32, Material.TNT, "&4&lExplosive",  "", "&4&l&m»&r &7Info", "","&7 ⭐ Cost: &4" + explosiveLevel, "&7 ⭐ Current Level: &4" + currentJackHammer, "", " &7&oClick to upgrade your &4&oexplosive &7&olevel!");

        gui.show(p);


        gui.onClick(e -> {

            e.setCancelled(true);
            if(e.getSlot() == 12) {
                Efficiency.addEfficiency(p);
            }
            if(e.getSlot() == 13) {
                Fortune.addFortune(p);
            }
            if(e.getSlot() == 14) {
//                CrateFinder.addCrateFinder(p);
            }
            if(e.getSlot() == 21) {
                Speed.addSpeed(p);
            }
            if(e.getSlot() == 22) {
                Jump.addJump(p);
            }
            if(e.getSlot() == 23) {
                Haste.addHaste(p);
            }
            if(e.getSlot() == 30) {
                Laser.addLaser(p);
            }
            if(e.getSlot() == 31) {
                JackHammer.addJackHammer(p);
            }
            if(e.getSlot() == 32) {
                Explosive.addExplosive(p);
            }

        });

    }



}
