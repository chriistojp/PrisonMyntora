package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class Casino {

    public void openSlotsSelectionGUI() {

        Gui gui = new Gui("Pick a glass.", 27);

        //magenta 2 - &d
        //lime 5 &a
        //red 14 &c
        //yellow 4 &e

        gui.i(11, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 2), "&d&lPink", "", "&7Click to select this color.");
        gui.i(13, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), "&a&lGreen", "", "&7Click to select this color.");
        gui.i(15, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), "&c&lRed", "", "&7Click to select this color.");
        gui.i(17, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 4), "&e&lYellow", "", "&7Click to select this color.");

        gui.onClick(e -> {

            e.setCancelled(true);

            if(e.getSlot() == 11) {
                openSlots("pink");
            }
            if (e.getSlot() == 13) {
                openSlots("green");
            }
            if(e.getSlot() == 15) {
                openSlots("red");
            }
            if(e.getSlot() == 17) {
                openSlots("yellow");
            }

        });

    }

    public void openSlots(String color) {

        Gui gui = new Gui("Slots", 54);

        gui.i(31, Material.DIAMOND_BLOCK, "&6&lCurrent Winner");

        gui.i(30, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        gui.i(32, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");

        gui.i(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        gui.i(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        gui.i(33, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");

        gui.i(39, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        gui.i(40, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        gui.i(41, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");




    }

    public static void openLavaWater(Player p) {


        Gui gui = new Gui("Lava and Water Gambling", 54);

        gui.onOpen(e -> {


            for (int i = 0; i < 54; i++) {
                int finalI = i;
                Bukkit.broadcastMessage(finalI + " ");
                Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

                    if (checkChance(50)) {
                        gui.i(finalI, Material.LAVA_BUCKET, "&c&lLAVA");
                    } else {
                        gui.i(finalI, Material.WATER_BUCKET, "&9&LWATER");
                    }


                }, 10 * i);
            }


        });
        gui.show(p);
    }

    public static boolean checkChance(int chance) {


        int random = ThreadLocalRandom.current().nextInt(0, 100);
        if(random <= chance) {
            return true;
        }
        return false;

    }

}
