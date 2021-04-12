package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Casino {

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
