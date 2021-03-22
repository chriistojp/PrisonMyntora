package me.christo.prisoncore.shop.gui;

import me.christo.prisoncore.shop.ShopManager;
import me.christo.prisoncore.utils.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ShopGUI {
    public static Gui getMainShopGui() {

        Gui main = new Gui(color("&5&lSHOP"), 27);

        createItem(main, 10, Material.LOG, "&5&LWOOD");
        createItem(main, 12, Material.DIAMOND_PICKAXE, "&5&lMINING");
        createItem(main, 14, Material.RAW_FISH, "&5&lFISHING");
        createItem(main, 16, Material.WHEAT, "&5&lFARMING");

        main.onClick(e -> {
            e.setCancelled(true);

            if (e.getSlot() == 10) {
                ShopManager.openShop((Player) e.getWhoClicked(), "WOOD", "wood");
            }
            if (e.getSlot() == 12) {
                ShopManager.openShop((Player) e.getWhoClicked(), "MINING", "mining");
            }
            if (e.getSlot() == 14) {
                ShopManager.openShop((Player) e.getWhoClicked(), "FISHING", "fishing");
            }
            if (e.getSlot() == 16) {
                ShopManager.openShop((Player) e.getWhoClicked(), "FARMING", "farming");
            }

        });

        main.onOpen(e -> {

            for (int i = 0; i < 27; i++) {

                if (main.getInventory().getItem(i) == null) {

                    int chance = ThreadLocalRandom.current().nextInt(0, 2);

                    if(chance == 1) {
                        main.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10), " ");
                    } else {
                        main.i(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6), " ");
                    }
                }

            }

        });
        return main;

    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void createItem(Gui gui, int slot, Material m, String name) {

        gui.i(slot, m, color(name), "&r", "&7This is our &5&n" + ChatColor.stripColor(name.toLowerCase()) + "&r &7shop.", "&r", "&7&o(( Left-Click to Open ))");

    }

}
