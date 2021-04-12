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

public class PVP {

    public static boolean isEnabled = false;

    public static boolean isActive() {

        return isEnabled;
    }

    public static void setActive() {

        String type = "PVP";
        Bukkit.broadcastMessage(Color.prison("Boosters", "A &c&l" + type + " &7booster has been activated for the next 20m!"));
        isEnabled = true;

        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            Bukkit.broadcastMessage(Color.prison("Boosters", "The &c&l" + type + " &7booster has ended!"));
            isEnabled = false;

        }, 20 * 60 * 20);
    }

    public static ItemStack getItemStack() {
        String type = "PVP";
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&c&l" + type + " BOOSTER"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&c&lDURATION: &715m"));
        lore.add("");
        lore.add(Util.color("&7Right click to activate a &cpvp &7booster. This booster"));
        lore.add(Util.color("&7will grant $1,000 per kill, and provide"));
        lore.add(Util.color("&7increasingly more rewarding perks."));
        meta.setLore(lore);
        item.setItemMeta(meta);

        Util.addGlow(item);

        return item;
    }

}