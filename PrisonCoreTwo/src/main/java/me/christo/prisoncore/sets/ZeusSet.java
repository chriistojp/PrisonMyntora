package me.christo.prisoncore.sets;


import me.christo.prisoncore.managers.Sets;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ZeusSet implements Listener {

//    @EventHandler
//    public void onRightClick(PlayerInteractEvent e) {
//
//        Player p = e.getPlayer();
//        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//
//
//            if (e.getPlayer().getItemInHand().hasItemMeta()) {
//                if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
//                    if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(Sets.getString("zeus.boots.name"))) {
//                        if (e.getPlayer().getInventory().getBoots() == null) {
//                            p.sendMessage(Sets.getString("zeus.boots.equipped"));
//                          //  e.setCancelled(true);
//                            p.setAllowFlight(true);
//                            p.setFlying(true);
//                        }
//                    }
//                }
//            }
//
//        }
//
//    }



    @EventHandler
    public void onBootsEquip(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            if (p.getItemOnCursor().hasItemMeta()) {
                if (p.getItemOnCursor().getItemMeta().hasDisplayName()) {
                    if (p.getItemOnCursor().getItemMeta().getDisplayName().equals(Sets.getString("zeus.boots.name"))) {
                        if (p.getInventory().getBoots() == null) {
                            p.sendMessage(Sets.getString("zeus.boots.equipped"));
                            p.setAllowFlight(true);
                            p.setFlying(true);
                        }
                    }
                }
            }
        }
            if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                ItemStack i = e.getCurrentItem();
                if (p.getInventory().getBoots() != null) {
                    System.out.println(true);
                    if (i.getType().equals(Material.LEATHER_BOOTS)) {
                        if (i.hasItemMeta()) {
                            if (i.getItemMeta().hasDisplayName()) {
                                if (i.getItemMeta().getDisplayName().equals(Sets.getString("zeus.boots.name"))) {
                                    p.sendMessage(Sets.getString("zeus.boots.unequipped"));
                                    p.setAllowFlight(false);
                                    p.setFlying(false);
                                }
                            }
                        }
                    }
                }
            }
        }


    public static void giveZeusSet(Player p) {

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.fromBGR(255, 255, 255));
        chestplateMeta.setDisplayName(Sets.getString("zeus.chestplate.name"));
        chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        List<String> chestplateLore = new ArrayList<>();
        for (String chestLore : Sets.getFile().getStringList("zeus.chestplate.lore")) {
            chestplateLore.add(Util.color(chestLore));
        }
        chestplateMeta.setLore(chestplateLore);
        chestplate.setItemMeta(chestplateMeta);
        p.getInventory().addItem(chestplate);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.fromBGR(255, 255, 255));
        leggingsMeta.setDisplayName(Sets.getString("zeus.leggings.name"));
        leggingsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        List<String> leggingsLore = new ArrayList<>();
        for (String chestLore : Sets.getFile().getStringList("zeus.leggings.lore")) {
            leggingsLore.add(Util.color(chestLore));
        }
        leggingsMeta.setLore(leggingsLore);
        leggings.setItemMeta(leggingsMeta);
        p.getInventory().addItem(leggings);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.fromBGR(255, 255, 255));
        bootsMeta.setDisplayName(Sets.getString("zeus.boots.name"));
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
        List<String> bootsLore = new ArrayList<>();
        for (String chestLore : Sets.getFile().getStringList("zeus.boots.lore")) {
            bootsLore.add(Util.color(chestLore));
        }
        bootsMeta.setLore(bootsLore);
        boots.setItemMeta(bootsMeta);
        p.getInventory().addItem(boots);

    }

}
