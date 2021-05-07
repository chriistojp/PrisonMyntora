package me.christo.prisoncore.gambling.highlow;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HighLowGUI {

    static HashMap<Player, Double> amountHashmap = new HashMap<>();

    public static void openHighLow(Player p, double amount) {


        amountHashmap.put(p, amount);
        int profit;

        AtomicInteger random = new AtomicInteger();
        AtomicInteger nextRandom = new AtomicInteger();


        int multiplier = ThreadLocalRandom.current().nextInt(0, 5) / 10;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);


        Gui gui = new Gui("High or Low?",  45);

        int[] higherSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30};
        int[] lowerSlots = {14, 15, 16, 23, 24, 25, 32, 33, 34};
        int[] paperSlots = {22};
        int[] numberSlots = {13, 31};

        gui.fill(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        HeadDatabaseAPI api = new HeadDatabaseAPI();

        gui.i(higherSlots, new ItemStack(api.getItemHead("9868")), "&a&lHIGHER", "",
                "&7Click this if you think the next number will be", "&7higher than your current one");
        gui.i(lowerSlots, new ItemStack(api.getItemHead("9335")), "&c&lLOWER", "",
                "&7Click this if you think the next number will be", "&7lower than your current one");
        gui.i(paperSlots, new ItemStack(Material.PAPER), "&f&lCASH OUT", "", "&7Let's get out while we're ahead, right?", "", "&f&lBET: &7$" + amount
                , "", "&f&lPROFIT: &7None");



        gui.onOpen(e -> {
             random.set(ThreadLocalRandom.current().nextInt(0, 100));
             nextRandom.set(ThreadLocalRandom.current().nextInt(0, 100));
             gui.i(numberSlots, new ItemStack(api.getItemHead("15689")), "&e&lCURRENT NUMBER | &7" + random);

        });

        gui.onClick(e -> {


            e.setCancelled(true);

            for(int i : higherSlots) {
                if(e.getSlot() == i) {

                    if(nextRandom.get() > random.get()) {
                        p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);

                        nextRandom.set(ThreadLocalRandom.current().nextInt(0, 100));
                        random.set(ThreadLocalRandom.current().nextInt(0, 100));

                        amountHashmap.put(p, amountHashmap.get(p) * 1.04);

                        gui.i(numberSlots, new ItemStack(api.getItemHead("15689")), "&e&lCURRENT NUMBER | &7" + random.get());
                        gui.i(paperSlots, new ItemStack(Material.PAPER), "&f&lCASH OUT", "", "&7Let's get out while we're ahead, right?", "", "&f&lBET: &7$" + amount,
                                 "", "&f&lPROFIT: &7$" + (amountHashmap.get(p) - amount));

                        return;
                    }
                    if(nextRandom.get() == random.get()) {
                        p.getOpenInventory().close();
                        p.sendMessage(Color.prison("Gambling", "You number was the same as the next number, how unlucky."));
                    }
                    if(nextRandom.get() < random.get()) {
                        p.getOpenInventory().close();
                        p.sendMessage(Color.prison("Gambling", "Bust! Your number: " + random.get() +
                                " Next Number: " + nextRandom));

                    }

                }
            }

            for(int i : lowerSlots) {
                if(e.getSlot() == i) {

                    if(nextRandom.get() < random.get()) {
                        p.getWorld().playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
                        nextRandom.set(ThreadLocalRandom.current().nextInt(0, 100));
                        random.set(ThreadLocalRandom.current().nextInt(0, 100));

                        amountHashmap.put(p, amountHashmap.get(p) * 1.04);

                        Bukkit.broadcastMessage("-");
                        Bukkit.broadcastMessage("next random given number " + random.get());
                        Bukkit.broadcastMessage("next random unknown number " + nextRandom);

                        gui.i(numberSlots, new ItemStack(api.getItemHead("15689")), "&e&lCURRENT NUMBER | &7" + random.get());
                        gui.i(paperSlots, new ItemStack(Material.PAPER), "&f&lCASH OUT", "", "&7Let's get out while we're ahead, right?", "", "&f&lBET: &7$" + amount,
                                 "", "&f&lPROFIT: &7$" + (amountHashmap.get(p) - amount));

                        return;
                    }
                    if(nextRandom.get() == random.get()) {
                        p.getOpenInventory().close();
                        p.sendMessage(Color.prison("Gambling", "You number was the same as the next number, how unlucky."));
                    }
                    if(nextRandom.get() > random.get()) {
                        p.getOpenInventory().close();
                        p.sendMessage(Color.prison("Gambling", "Bust! Your number: " + random.get() +
                                " Next Number: " + nextRandom));
                        amountHashmap.remove(p);

                    }
                }
            }

            for(int i : paperSlots) {
                if(e.getSlot() == i) {
                    p.sendMessage(Color.prison("Gambling", "You hopped out, probably for the best. &a+ $" + (amountHashmap.get(p) - amount)));
                    profile.getData().getPrisonMoney().increaseAmount((int) (Math.round(amountHashmap.get(p)) + amount));
                    p.getOpenInventory().close();

                    amountHashmap.remove(p);

                }
            }

        });

        gui.show(p);




    }

}
