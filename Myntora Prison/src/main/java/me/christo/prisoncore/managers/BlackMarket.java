package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BlackMarket {

    private static File marketFile;
    private static FileConfiguration market;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return market;

    }


    public static void loadFile() {
        marketFile = new File(Prison.getInstance().getDataFolder(), "market.yml");
        if (!marketFile.exists()) {
            Prison.getInstance().saveResource("market.yml", false);
        }
        market = YamlConfiguration.loadConfiguration(marketFile);

        try {
            market.save(marketFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfExists(String gang) {

        if (market.getConfigurationSection("Gangs") == null) {
            return false;
        }

        for (String key : market.getConfigurationSection("Gangs").getKeys(false)) {
            if (gang.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public static void save() {
        try {
            market.save(marketFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static void addItem(String name, ItemStack i, int price) {
        int items;
        if (getFile().getConfigurationSection("items") == null) {
            items = 0;
        } else {
            items = getFile().getConfigurationSection("items").getKeys(false).size();
        }

        getFile().set("items." + items + ".seller", name);
        getFile().set("items." + items + ".item", i);
        getFile().set("items." + items + ".price", price);

        save();

    }

    //items:
    //  1:
    //    item:
    //    price:
    public static void showMarketGui(Player p) {

        Gui gui = new Gui("Black Market", 54);

        gui.fillRow(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), " ");
        gui.i(48, Material.DOUBLE_PLANT, "&e&lBlack Market Info", "&7Welcome to the black market!", "", "&7This is where players can buy and sell", "&7items listed by other players.");
        gui.i(50, Material.PAPER, "&f&lBlack Market Commands", "&7Here you can find the command to properly", "&7use the black market. &7You may also use", "&7the command /bm.", "", "&f- /blackmarket sell (amount)", "",
                "&7Make sure you are holding the item you want to sell.");

        int count = 0;


        for (String key : getFile().getConfigurationSection("items").getKeys(false)) {

            int price = getFile().getInt("items." + key + ".price");

            ItemStack item = getFile().getItemStack("items." + key + ".item");
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                lore.add("");
                lore.add(Util.color("&7Price: &a$" + price));
                lore.add(Util.color("&7&o(( Click to Buy! ))"));
                meta.setLore(lore);
                item.setItemMeta(meta);

                gui.i(count, item);
            } else {
                item = new ItemStack(getFile().getItemStack("items." + key + ".item"));
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(Util.color("&7Price: &a$" + price));
                lore.add(Util.color("&7&o(( Click to Buy! ))"));
                meta.setLore(lore);
                item.setItemMeta(meta);

                gui.i(count, item);
            }
            count++;
        }

        gui.onClick(e -> {

            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            Profile profile = Core.getInstance().getProfileManager().getProfile(player);
            int slot = e.getSlot();

            if (profile.getData().getPrisonMoney().getAmount() >= getFile().getInt("items." + slot + ".price")) {

                Profile seller = Core.getInstance().getProfileManager().getProfile(getFile().getString("items." + slot + ".seller"));


                ItemStack i = getFile().getItemStack("items." + slot + ".item");
                player.getInventory().addItem(i);

                seller.getData().getPrisonMoney().increaseAmount(getFile().getInt("items." + slot + ".price"));
                profile.getData().getPrisonMoney().decreaseAmount(getFile().getInt("items." + slot + ".price"));

                seller.getData().save();
                profile.getData().save();

                if (Bukkit.getPlayer(getFile().getString("items." + slot + ".seller")) != null) {

                    Player toSend = Bukkit.getPlayer(getFile().getString("items." + slot + ".seller"));
                    toSend.sendMessage(Color.prison("Market", "Someone purchased your black market listing for &d$" + getFile().getInt("items." + slot + ".price") + "!"));

                }

                player.sendMessage(Color.prison("Market", "You bought an item from the black market for &d$" + getFile().getInt("items." + slot + ".price")));

                Map<Integer, ItemStack> itemStackMap = new HashMap<>();
                Map<Integer, Integer> priceMap = new HashMap<>();
                Map<Integer, String> uuidMap = new HashMap<>();

                for (String key : getFile().getConfigurationSection("items").getKeys(false)) {

                    int toSet = Integer.parseInt(key);

                    if (toSet >= slot) {

                    if(toSet == 0) {
                        getFile().set("items." + key, null);
                        save();
                        continue;
                    }

                        itemStackMap.put(toSet, getFile().getItemStack("items." + key + ".item"));
                        priceMap.put(toSet, getFile().getInt("items." + key + ".price"));
                        uuidMap.put(toSet, getFile().getString("items." + key + ".seller"));


                        getFile().set("items." + key, null);

                        getFile().set("items." + (toSet - 1) + ".item", itemStackMap.get(toSet));
                        getFile().set("items." + (toSet - 1) + ".price", priceMap.get(toSet));
                        getFile().set("items." + (toSet - 1) + ".seller", uuidMap.get(toSet));

                        itemStackMap.remove(toSet);
                        priceMap.remove(toSet);
                        uuidMap.remove(toSet);

                        save();
                    }
                }
                player.getOpenInventory().close();
                showMarketGui(p);


            } else {
                player.getOpenInventory().close();
                player.sendMessage(Color.prison("Market", "You don't have enough money for that item!"));
            }


        });

        gui.show(p);

    }

}
