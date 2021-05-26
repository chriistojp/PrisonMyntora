package me.christo.prisoncore.managers;

import me.christo.prisoncore.utils.Gui;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class FarmShop {

    public static void openGui(Player p) {

        Gui gui = new Gui("Farming", 45);
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        int balance = profile.getData().getPrisonMoney().getAmount();

        //sand
        //soulsand
        //dirt
        //waterbuckets

        int sandPrice = 0;
        int soulSandPrice = 0;
        int dirtPrice = 0;
        int waterBucketPrice = 0;

        for (int i = 0; i < 45; i++) {

            int random = ThreadLocalRandom.current().nextInt(0, 2);
            if (random == 1) {
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 12));
            } else {
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0));
            }
        }

        gui.i(12, Material.SAND, "&e&lSAND", "", "&7Price: &a$" + sandPrice, "", "&7Left-Click for 1", "&7Right-Click for 64");
        gui.i(14, Material.SOUL_SAND, "&e&lSOUL SAND", "", "&7Price: &a$" + soulSandPrice, "", "&7Left-Click for 1", "&7Right-Click for 64");
        gui.i(30, Material.DIRT, "&e&lDIRT", "", "&7Price: &a$" + dirtPrice, "", "&7Left-Click for 1", "&7Right-Click for 64");
        gui.i(32, Material.WATER_BUCKET, "&e&lWATER BUCKET", "", "&7Price: &a$" + waterBucketPrice);


        gui.onClick(e -> {

            e.setCancelled(true);

            if (e.getSlot() == 12) {
                if (e.isLeftClick()) {
                    if (balance >= sandPrice) {
                        profile.getData().getPrisonMoney().decreaseAmount(sandPrice);
                        p.getInventory().addItem(new ItemStack(Material.SAND));
                    }
                } else {
                    if (balance >= sandPrice * 64) {
                        p.getInventory().addItem(new ItemStack(Material.SAND, 64));
                        profile.getData().getPrisonMoney().decreaseAmount(sandPrice);
                    }
                }
            }
            if (e.getSlot() == 14) {
                if (e.isLeftClick()) {
                    if (balance >= soulSandPrice) {
                        profile.getData().getPrisonMoney().decreaseAmount(soulSandPrice);
                        p.getInventory().addItem(new ItemStack(Material.SOUL_SAND));
                    }
                } else {
                    if (balance >= soulSandPrice * 64) {
                        p.getInventory().addItem(new ItemStack(Material.SOUL_SAND, 64));
                        profile.getData().getPrisonMoney().decreaseAmount(soulSandPrice);
                    }
                }
            }
            if (e.getSlot() == 30) {
                if (e.isLeftClick()) {
                    if (balance >= dirtPrice) {
                        profile.getData().getPrisonMoney().decreaseAmount(dirtPrice);
                        p.getInventory().addItem(new ItemStack(Material.DIRT));
                    }
                } else {
                    if (balance >= dirtPrice * 64) {
                        p.getInventory().addItem(new ItemStack(Material.DIRT, 64));
                        profile.getData().getPrisonMoney().decreaseAmount(dirtPrice);
                    }
                }
            }
            if (e.getSlot() == 32) {

                if(balance >= waterBucketPrice) {
                    p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                    profile.getData().getPrisonMoney().decreaseAmount(waterBucketPrice);
                }

            }


        });

        gui.show(p);


    }

}
