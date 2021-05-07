package me.christo.prisoncore.gambling.mines;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Gui;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MinesGUI {

    static HashMap<Player, Integer> amountHash = new HashMap<>();

    public static void openGui(Player p, int bet) {

        Gui gui = new Gui("Mines", 54);
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        amountHash.put(p, bet);

        final int[] toAdd = {bet / 24};




        gui.fill(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15));


        int[] minesSlots = {11, 12, 13, 14, 15, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42};

        int random = minesSlots[new Random().nextInt(minesSlots.length)];

        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack mine = api.getItemHead("573");
        ItemStack cash = api.getItemHead("34670");
        ItemStack checkMark = api.getItemHead("21771");
        ItemStack bombItem = api.getItemHead("1274");

        gui.i(4, cash, "&a&lPROFIT: &f$" + (bet - toAdd[0]));
        gui.i(minesSlots, mine, "&e&lCLICK TO REVEAL", "", "&7Will this be the mine?");

        AtomicInteger count = new AtomicInteger();

        gui.onClick(e -> {
            e.setCancelled(true);

            if(e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {

                if(e.getSlot() == 4) {

                    p.getOpenInventory().close();
                    p.sendMessage(Color.prison("Gambling", "Probably for the best! + &a$" + toAdd[0]));

                }

                if(e.getSlot() != 4) {
                    if(e.getSlot() != random) {

                        gui.i(e.getSlot(), checkMark, "&a&LSAFE!", "", "&7This was not a mine!");
                        p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
                        amountHash.put(p, amountHash.get(p) + toAdd[0]);
                        gui.i(4, cash, "&a&lPROFIT: &f$" + (bet - amountHash.get(p)));


                        if(count.get() == 24) {
                            p.getOpenInventory().close();
                            p.sendMessage(Color.prison("Gambling", "YOU WON IT ALL!"));
                            profile.getData().getPrisonMoney().increaseAmount(toAdd[0]);
                        }
                    }
                    if(e.getSlot() == random) {

                        gui.i(e.getSlot(), bombItem, "&c&lITS A BOMB!", "", "&7Theres goes all your money :(");

                        for(int slot : minesSlots) {
                            gui.i(slot, bombItem, "&c&lITS A BOMB!", "", "&7Theres goes all your money :(");
                        }

                        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

                            p.getOpenInventory().close();

                        }, 100);


                        p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 1, 1);

                    }

                }

            }

        });

        gui.show(p);

    }

}
