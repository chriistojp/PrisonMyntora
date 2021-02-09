package me.christo.prisoncore.Goals.GUI;

import me.christo.prisoncore.Goals.Goals;
import me.christo.prisoncore.Utils.Gui;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InputRewardsGUI {

    public static Gui main(String goal) {

        Gui rewards = new Gui("Input Rewards", 54);

        //goals:
        //  mining:
        //    rewards:
        if(Goals.getFile().isSet("goals." + goal)) {
            int count = 0;
            for (String key : Goals.getFile().getConfigurationSection("goals." + goal + ".rewards").getKeys(false)) {
                rewards.i(count, Goals.getFile().getItemStack("goals." + goal + ".rewards." + key));

                count++;
            }
        }
        rewards.onClose(e -> {

            int count = 0;
            for(ItemStack i : rewards.getContents()) {

                if(i == null) {
                    continue;
                }
                System.out.println(i);
                Goals.getFile().set("goals." + goal + ".rewards." + count, i);
                e.getPlayer().sendMessage(Util.color(Goals.getFile().getString("messages.updatedRewards")
                .replaceAll("%goal%", goal)));
                Goals.save();
                count++;
            }

        });

        return rewards;

    }

}
