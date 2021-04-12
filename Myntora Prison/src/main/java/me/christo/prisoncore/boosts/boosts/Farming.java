package me.christo.prisoncore.boosts.boosts;

import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.util.Color;
import net.myntora.core.core.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Farming {

    public static boolean isEnabled = false;

    public static boolean isActive() {

        return isEnabled;
    }

    public static void setActive() {

        String type = "FARMING";
        Bukkit.broadcastMessage(Color.prison("Boosters", "A &a&l" + type + " &7booster has been activated for the next 15m!"));
        isEnabled = true;

        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            Bukkit.broadcastMessage(Color.prison("Boosters", "The &a&l" + type + " &7booster has ended!"));
            isEnabled = false;

        }, 20 * 60 * 15);
    }

    public static ItemStack getItemStack() {
        String type = "FARMING";
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&a&l" + type + " BOOSTER"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&a&lDURATION: &715m"));
        lore.add("");
        lore.add(Util.color("&7Right click to activate a &afarming &7booster. This booster"));
        lore.add(Util.color("&7will allow crops to grow faster, and allow "));
        lore.add(Util.color("&72x drops from crops."));
        meta.setLore(lore);
        item.setItemMeta(meta);

        Util.addGlow(item);

        return item;
    }


}