package me.christo.prisoncore.guis;

import me.christo.prisoncore.managers.Goals;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import org.bukkit.inventory.ItemStack;

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
