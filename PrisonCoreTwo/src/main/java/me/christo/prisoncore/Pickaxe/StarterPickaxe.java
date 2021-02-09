package me.christo.prisoncore.Pickaxe;

import me.christo.prisoncore.Main;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarterPickaxe {

    private static File pickaxeFile;
    private static FileConfiguration pickaxe;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return pickaxe;

    }


    public static void loadFile() {
        pickaxeFile = new File(Main.getInstance().getDataFolder(), "pickaxe.yml");
        if (!pickaxeFile.exists()) {
            Main.getInstance().saveResource("pickaxe.yml", false);
        }
        pickaxe = YamlConfiguration.loadConfiguration(pickaxeFile);

        try {
            pickaxe.save(pickaxeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //pickaxe:
    //  item: DIAMOND_PICKAXE
    //  displayName: "&b&lSTARTER PICKAXE"
    //  lore:
    //  - ""
    // - "&7Made by christo!"
    //messages:
    //  upgradedPickaxe: "&b&lPICKAXE &8| &&Your pickaxe has been upgraded!"

    public static void givePickaxe(Player p) {

        ItemStack i = new ItemStack(Material.matchMaterial(getFile().getString("pickaxe.item")));
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(Util.color(getFile().getString("pickaxe.displayName")));
        List<String> lore = new ArrayList<>();
        for(String s : getFile().getStringList("pickaxe.lore")) {
            lore.add(Util.color(s).replaceAll("%blocks%", "0"));
        }
        i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
        i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        meta.setLore(lore);
        i.setItemMeta(meta);

    }

    public static boolean holdingPickaxe(Player p) {

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(p.getItemInHand());
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        if(itemC.get("blocks") != null) {
            return true;
        }
        return false;
    }

    public static int getBlocks(ItemStack i) {

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        return itemC.getInt("blocks");
    }

    
    public static void tryToUpgrade(Player p) {

        int[] multiples = {500, 1500, 3000, 5000, 6000, 6500, 7000, 8000, 9000, 10000};

        ItemStack i = p.getItemInHand();

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        int blocks = itemC.getInt("blocks");

        if(Arrays.asList(multiples).contains(blocks)) {
            if (blocks == 500) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
            }
            if (blocks == 1500) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
            }
            if (blocks == 3000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
            }
            if (blocks == 5000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 4);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
            }
            if (blocks == 6000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
            }
            if (blocks == 6500) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            }
            if (blocks == 7000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            }
            if (blocks == 8000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 7);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            }
            if (blocks == 9000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 8);
                i.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            }
            if (blocks == 10000) {
                i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 9);
                i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 4);
                ItemMeta meta = i.getItemMeta();
                meta.spigot().setUnbreakable(true);
            }

            p.sendMessage(getFile().getString("messages.upgradedPickaxe"));
        }


    }



}
