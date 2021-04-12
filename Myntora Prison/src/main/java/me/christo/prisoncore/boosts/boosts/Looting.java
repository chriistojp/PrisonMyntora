package me.christo.prisoncore.boosts.boosts;

import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Looting {

    public static boolean isEnabled = false;

    public static boolean isActive() {

        return isEnabled;
    }

    public static void setActive() {

        String type = "LOOTING";
        Bukkit.broadcastMessage(Color.prison("Boosters", "A &b&l" + type + " &7booster has been activated for the next 20m!"));
        isEnabled = true;

        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            Bukkit.broadcastMessage(Color.prison("Boosters", "The &b&l" + type + " &7booster has ended!"));
            isEnabled = false;

        }, 20 * 60 * 20);
    }

    public static ItemStack getItemStack() {
        String type = "LOOTING";
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&b&l" + type + " BOOSTER"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&b&lDURATION: &715m"));
        lore.add("");
        lore.add(Util.color("&7Right click to activate a &blooting &7booster. This booster"));
        lore.add(Util.color("&7will double loot found in zones, and grant"));
        lore.add(Util.color("&7a higher chance for rare loot to spawn."));
        meta.setLore(lore);
        item.setItemMeta(meta);

        Util.addGlow(item);

        return item;
    }




}