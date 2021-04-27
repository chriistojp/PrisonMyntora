package me.christo.prisoncore.zones;

import me.christo.prisoncore.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public enum Items {

    //zone 1
    STEAK(1, 50, 16, Material.COOKED_BEEF),
    VOUCHER_1(1, 25, 1, Material.PAPER, "&a&lMONEY &7($50)", "", "&7Click this to receive $50."),
    IRON_HELMET(1, 40, 1, Material.IRON_HELMET),
    IRON_CHESTPLATE(1, 30, 1, Material.IRON_CHESTPLATE),
    IRON_LEGGINGS(1, 30, 1, Material.IRON_BOOTS);


    private int zone;
    private int chance;
    private int amount;
    private Material material;
    private String name;
    private String[] lore;

    public void addItems() {
        for(String key : Zones.getFile().getConfigurationSection("zones").getKeys(false)) {

            World world = Bukkit.getWorld(Zones.getFile().getString("zones." + key + ".world"));
            int x = Zones.getFile().getInt("zones." + key + ".x");
            int y = Zones.getFile().getInt("zones." + key + ".y");
            int z = Zones.getFile().getInt("zones." + key + ".z");
            int zone = Zones.getFile().getInt("zones." + key + ".zone");

            Chest chest = null;
            Location location = new Location(world, x, y, z);
            if (location.getWorld().getBlockAt(location).getType() != null) {
                if (location.getWorld().getBlockAt(location).getType() == Material.CHEST) {

                    chest = (Chest) location.getWorld().getBlockAt(location);

                }
            }
            for (Items i : Items.values()) {
                boolean add = checkChance(i);
                if (add) {
                    ItemStack item = getItemStack(i);
                    //1 3
                    if(zone >= i.zone) {
                        chest.getBlockInventory().addItem(item);
                    }

                }


            }
        }

    }

    public static int getZone(Items i) {
        return i.zone;
    }
    public static boolean checkChance(Items i) {
        return Util.checkChance(i.chance);
    }
    public static ItemStack getItemStack(Items i) {

        ItemStack item = new ItemStack(i.material);
        item.setAmount(i.amount);
        if(i.name != null) {
            ItemMeta meta = (ItemMeta) item.getItemMeta();
            meta.setDisplayName(Util.color(i.name));
            List<String> lore = new ArrayList<>();
            for(String s : i.lore) {
                lore.add(Util.color(s));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    private Items(int zone, int chance, int amount, Material material, String name, String... lore) {
        this.zone = zone;
        this.chance = chance;
        this.amount = amount;
        this.material = material;
        this.name = name;
        this.lore = lore;
    }
    private Items(int zone, int chance, int amount, Material material) {
        this.zone = zone;
        this.chance = chance;
        this.amount = amount;
        this.material = material;
    }

}