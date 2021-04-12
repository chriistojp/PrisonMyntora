package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.player.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Pets {

    public static void giveItemStack(Player p) {

        ItemStack i = new ItemStack(Material.DRAGON_EGG);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(Util.color("&d&lINVENTORY PET"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&7The purpose of this pet is to passively earn you tokens."));
        lore.add(Util.color("&7This pet can be upgraded using &3shards. &7Shards can be"));
        lore.add(Util.color("&7obtained by the compressor enchant on your &3pickaxe."));
        lore.add("");
        lore.add(Util.color("&7This pet will currently earn you &d[amount] &7tokens per minute"));
        meta.setLore(lore);
        i.setItemMeta(meta);

        p.getPlayer().getInventory().addItem(i);

    }

    public static void openGui(Player p) {
        Gui gui = new Gui(Util.color("&d&lPet"), 27);
        gui.c();
        for(int i = 0; i < 27; i++) {

            int random = ThreadLocalRandom.current().nextInt(0, 2);
            if(random == 1)
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0), " ");
            else
                gui.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6), " ");

        }

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        gui.i(11, Material.DIAMOND_SPADE, "&3&lShard Digger", "", "&7This enchant will passively earn you shards every minute. Shards", "&7can be spent to upgrade your pet. Shards can also",
        "&7be obtained by the &3\"compressor\" &7enchant", "&7on your pickaxe.", "", "&3⭐ &7Current Information:", "", "&3⭐ &7Current Level: &3" + profile.getData().getPrisonPetShardDiggerLevel().getAmount(),
        "&3⭐ &7Cost to Upgrade: &3" + (profile.getData().getPrisonPetShardDiggerLevel().getAmount() + 1) * 1000);
        gui.i(13, Material.DRAGON_EGG, "&d&lPET INFO", "", "&d⭐ &7Pet Information", "", "&d⭐ &7Shard Digger: &d" + profile.getData().getPrisonPetShardDiggerLevel().getAmount() + "&7/min", "&d⭐ &7Token Fetcher: &d" + profile.getData().getPrisonPetTokenFetcherLevel().getAmount() + "&7/min");
        gui.i(15, Material.MAGMA_CREAM, "&e&lToken Fetcher", "", "&7This enchant will passively earn you tokens every minute. Tokens", "&7can be spent to upgrade your pickaxe. Tokens can",
                "&7be obtained by selling items in the &eshop.", "", "&e⭐ &7Current Information:", "", "&e⭐ &7Current Level: &e" + profile.getData().getPrisonPetTokenFetcherLevel().getAmount(),
                "&e⭐ &7Cost to Upgrade: &e" + (profile.getData().getPrisonPetTokenFetcherLevel().getAmount() + 1) * 1000);

        gui.onClick(e -> {

            if(e.getSlot() == 11) {

                int level = profile.getData().getPrisonPetShardDiggerLevel().getAmount();
                double price = ((level + 1) * 1000) * 1.5;

                if(level == 10) {
                    p.sendMessage(Color.prison("Pets", "You've reached the max level!"));
                    p.getOpenInventory().close();
                    return;
                }

                if(profile.getData().getPrisonShards().getAmount() >= price) {

                    profile.getData().getPrisonShards().decreaseAmount((int) price);
                    profile.getData().getPrisonPetShardDiggerLevel().increaseAmount(1);
                    p.getOpenInventory().close();
                    openGui(p);
                    p.sendMessage(Color.prison("Pets", "You successfully upgraded your &dshard digger &7level to &d" + (level + 1)));

                } else {
                    p.getOpenInventory().close();
                    p.sendMessage(Color.prison("Pets", "You cannot afford that enchant."));
                }
                profile.getData().save();

            }
            if(e.getSlot() == 15) {

                int level = profile.getData().getPrisonPetTokenFetcherLevel().getAmount();
                double price = ((level + 1) * 1000) * 1.5;

                if(level == 50) {
                    p.sendMessage(Color.prison("Pets", "You've reached the max level!"));
                    p.getOpenInventory().close();
                    return;
                }
                if(profile.getData().getPrisonShards().getAmount() >= price) {

                    profile.getData().getPrisonShards().decreaseAmount((int) price);
                    profile.getData().getPrisonPetTokenFetcherLevel().increaseAmount(1);
                    p.getOpenInventory().close();
                    openGui(p);
                    p.sendMessage(Color.prison("Pets", "You successfully upgraded your &dtoken fetcher &7level to &d" + (level + 1)));

                } else {
                    p.getOpenInventory().close();
                    p.sendMessage(Color.prison("Pets", "You cannot afford that enchant."));
                }

                profile.getData().save();

            }

        });

        gui.show(p);


        // 1 shard digger = 25 shards/min
        // 1 token fetcher = 2500 tokens/min




    }

}
