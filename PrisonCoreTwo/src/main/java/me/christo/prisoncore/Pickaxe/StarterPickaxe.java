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
import java.util.Collections;
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
        pickaxeFile = new File(Main.getInstance().getDataFolder(), "starterpick.yml");
        if (!pickaxeFile.exists()) {
            Main.getInstance().saveResource("starterpickaxe.yml", false);
        }
        pickaxe = YamlConfiguration.loadConfiguration(pickaxeFile);

        try {
            pickaxe.save(pickaxeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void givePickaxe(Player p) {

        ItemStack i = new ItemStack(Material.matchMaterial(getFile().getString("pickaxe.item")));
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(Util.color(getFile().getString("pickaxe.displayName")));
        List<String> lore = new ArrayList<>();
        for (String s : getFile().getStringList("pickaxe.lore")) {
            lore.add(Util.color(s).replaceAll("%blocks%", "0"));
        }
        meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setLore(lore);
        i.setItemMeta(meta);

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        itemC.setInt("blocks", 0);
        ItemStack newItem = CraftItemStack.asBukkitCopy(itemNMS);
        p.getInventory().addItem(newItem);

    }

    public static boolean holdingPickaxe(Player p) {

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(p.getItemInHand());
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        if (itemC.get("blocks") != null) {
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

        ItemStack i = p.getItemInHand();

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        int blocks = itemC.getInt("blocks");
        itemC.setInt("blocks", itemC.getInt("blocks") + 1);


        ItemStack newItem = CraftItemStack.asBukkitCopy(itemNMS);

        ItemMeta meta = i.getItemMeta();
        System.out.println(blocks);

        if (blocks == 106) {
            meta.addEnchant(Enchantment.DIG_SPEED, 2, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 1500) {
            meta.addEnchant(Enchantment.DIG_SPEED, 3, true);
            meta.addEnchant(Enchantment.DURABILITY, 2, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 3000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 3, true);
            meta.addEnchant(Enchantment.DURABILITY, 2, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 5000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 4, true);
            meta.addEnchant(Enchantment.DURABILITY, 2, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 6000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
            meta.addEnchant(Enchantment.DURABILITY, 2, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 2, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 6500) {
            meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 7000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 6, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 8000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 7, true);
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 9000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 8, true);
            meta.addEnchant(Enchantment.DURABILITY, 4, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        if (blocks == 10000) {
            meta.addEnchant(Enchantment.DIG_SPEED, 9, true);
            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 4, true);
            meta.spigot().setUnbreakable(true);
            p.sendMessage(Util.color(getFile().getString("messages.upgradedPickaxe")));
        }
        i.setItemMeta(meta);
        p.getInventory().setItem(p.getInventory().getHeldItemSlot(), newItem);

    }


}
