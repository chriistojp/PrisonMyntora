package me.christo.prisoncore.managers;

import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.player.ProfileData;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StarterPickaxe {


    public static void update(Player p) {

        ProfileData profile = Core.getInstance().getProfileManager().getProfile(p).getData();

        ItemStack item = p.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color(" &6&lStatistics:"));
        lore.add(Util.color("  &6&l&m»&r &fBlocks Broken: &7" + profile.getPrisonBlocksMined().getAmount()));
        lore.add("");
        lore.add(Util.color(" &c&lEnchantments:"));
        lore.add(Util.color("  &c&l&m»&r &7Efficiency &f") + profile.getPrisonEfficiencyLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Fortune &f") + profile.getPrisonFortuneLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Speed &f") + profile.getPrisonSpeedLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Haste &f") + profile.getPrisonHasteLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Jump Boost &f") + profile.getPrisonJumpLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Shard Finder &f") + profile.getPrisonShardFinderLevel().getAmount());
        meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    public static void givePickaxe(Player p) {

        ProfileData profile = Core.getInstance().getProfileManager().getProfile(p).getData();

        ItemStack i = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(Util.color("&b&l" + p.getName() + "'s Omnitool &8(&7Right-Click&8)"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color(" &6&lStatistics:"));
        lore.add(Util.color("  &6&l&m»&r &fBlocks Broken: &7" + profile.getPrisonBlocksMined().getAmount()));
        lore.add("");
        lore.add(Util.color(" &c&lEnchantments:"));
        lore.add(Util.color("  &c&l&m»&r &7Efficiency &f") + profile.getPrisonEfficiencyLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Fortune &f") + profile.getPrisonFortuneLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Speed &f") + profile.getPrisonSpeedLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Haste &f") + profile.getPrisonHasteLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Jump Boost &f") + profile.getPrisonJumpLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Shard Finder &f") + profile.getPrisonShardFinderLevel().getAmount());
        meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        itemC.setInt("blocks", 0);
        ItemStack newItem = CraftItemStack.asBukkitCopy(itemNMS);
        p.getInventory().addItem(newItem);

    }

    public static ItemStack getPlayersPickaxe(Player p) {

        ProfileData profile = Core.getInstance().getProfileManager().getProfile(p).getData();

        ItemStack i = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(Util.color("&b&l" + p.getName() + "'s Omnitool &8(&7Right-Click&8)"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color(" &6&lStatistics:"));
        lore.add(Util.color("  &6&l&m»&r &fBlocks Broken: &7" + profile.getPrisonBlocksMined().getAmount()));
        lore.add("");
        lore.add(Util.color(" &c&lEnchantments:"));
        lore.add(Util.color("  &c&l&m»&r &7Efficiency &f") + profile.getPrisonEfficiencyLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Fortune &f") + profile.getPrisonFortuneLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Speed &f") + profile.getPrisonSpeedLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Haste &f") + profile.getPrisonHasteLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Jump Boost &f") + profile.getPrisonJumpLevel().getAmount());
        lore.add(Util.color("  &c&l&m»&r &7Shard Finder &f") + profile.getPrisonShardFinderLevel().getAmount());
        meta.addEnchant(Enchantment.DIG_SPEED, 1, true);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);


        return i;

    }

    public static boolean holdingPickaxe(Player p) {

        if(p.getItemInHand().getType() == null) {
            return true;
        }
        if(p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {

            net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(p.getItemInHand());
            net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                    : new net.minecraft.server.v1_8_R3.NBTTagCompound();

            if (itemC.get("blocks") != null) {
                return true;
            }
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

        i.setItemMeta(meta);
        p.getInventory().setItem(p.getInventory().getHeldItemSlot(), newItem);

    }


}
