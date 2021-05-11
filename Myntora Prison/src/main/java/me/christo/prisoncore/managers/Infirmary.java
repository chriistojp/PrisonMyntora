package me.christo.prisoncore.managers;

import me.christo.prisoncore.utils.Gui;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class Infirmary {

    public static void openGui(Player p) {

        Gui gui = new Gui("Infirmary", 45);

        gui.c();
        for(int i = 0; i < 45; i++) {

            int random = ThreadLocalRandom.current().nextInt(0, 2);
            if(random == 1)
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0), " ");
            else
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6), " ");

        }

        gui.i(20, new ItemStack(Material.POTION, 1, (byte) 16421), "&d&lSplash Potion of Healing II",
                "", "&7For some fast health. Let's try not", "&7to die again.", "", "&a&lPrice: &7$10");

        gui.i(24, Material.GOLDEN_APPLE, "&e&lGolden Apple","", "&7Nom nom, we love health.", "", "&a&lPrice: &7$20");


        gui.onClick(e -> {

            Profile profile = Core.getInstance().getProfileManager().getProfile(p);

            int potionPrice = 10;
            int applePrice = 20;

            if(e.getSlot() == 20) {
                if(profile.getData().getPrisonMoney().getAmount() >= potionPrice) {
                    p.getInventory().addItem(new ItemStack(Material.POTION, 1, (byte) 16421));
                    p.sendMessage(Color.prison("Infirmary", "&7-" + potionPrice));
                    profile.getData().getPrisonMoney().decreaseAmount(potionPrice);
                } else {
                    p.getOpenInventory().close();
                    p.sendMessage(Color.prison("Infirmary", "&7You can't afford that!"));
                }
            }
            if(e.getSlot() == 24) {
                if(profile.getData().getPrisonMoney().getAmount() >= applePrice) {
                    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    p.sendMessage(Color.prison("Infirmary", "&7-" + applePrice));
                    profile.getData().getPrisonMoney().decreaseAmount(applePrice);
                } else {
                    p.getOpenInventory().close();
                    p.sendMessage(Color.prison("Infirmary", "&7You can't afford that"));
                }
            }

        });

        gui.show(p);

    }

}
