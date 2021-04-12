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

public class Mega {

    public static boolean isEnabled = false;

    public static boolean isActive() {

        return isEnabled;
    }

    public static void setActive() {

        String type = "mega";
        Bukkit.broadcastMessage(Color.prison("Boosters", "A &4&lM&c&lE&e&lG&a&lA&7 &9&lB&1&lO&5&lO&4&lS&c&lT&e&lE&a&lR &7booster has been activated for the next &4&l1&c&lH!"));
        Bukkit.broadcastMessage(Color.prison("Boosters", "A &4&lM&c&lE&e&lG&a&lA&7 &9&lB&1&lO&5&lO&4&lS&c&lT&e&lE&a&lR &7booster has been activated for the next &4&l1&c&lH!"));
        Bukkit.broadcastMessage(Color.prison("Boosters", "A &4&lM&c&lE&e&lG&a&lA&7 &9&lB&1&lO&5&lO&4&lS&c&lT&e&lE&a&lR &7booster has been activated for the next &4&l1&c&lH!"));
        isEnabled = true;

        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            Bukkit.broadcastMessage(Color.prison("Boosters", "The &d" + type + " &7booster has ended!"));
            isEnabled = false;

        }, 20 * 60 * 60);
    }

    //red.lightred,yellow,green,&b&1&5

    public static ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color("&4&lM&c&lE&e&lG&a&lA&7 &9&lB&1&lO&5&lO&4&lS&c&lT&e&lE&a&lR"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&4&lD&c&lU&e&lR&a&lA&9&lT&1&lI&5&lO&4&lN&c&l: &71h"));
        lore.add("");
        lore.add(Util.color("&7Right click to activate a &4&lM&c&lE&e&lG&a&lA &9&lB&1&lO&5&lO&4&lS&c&lT&e&lE&a&lR.&7 This booster"));
        lore.add(Util.color("&7will rotate through all booster types, with a "));
        lore.add(Util.color("&720m duration for each booster."));
        meta.setLore(lore);
        item.setItemMeta(meta);

        Util.addGlow(item);

        return item;
    }

}