package me.christo.prisoncore.pMines.guis;


import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MinesGui {

    public static void showMembers(Player p) {

        Gui gui = new Gui("Your Members", 54);

        //        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        //        SkullMeta meta = (SkullMeta) i.getItemMeta();

        gui.fillRow(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6), " ");
        gui.i(53, Material.DOUBLE_PLANT, "&e&lClick to Toggle", "", "&7Clicking this will allow you to view mines that", "&7you are part of.");

        if (PMine.getFile().isSet("mines." + p.getUniqueId())) {
            if (PMine.getFile().isSet("mines." + p.getUniqueId() + ".members")) {

                int count = 0;
                for (String members : PMine.getFile().getStringList("mines." + p.getUniqueId() + ".members")) {

                    ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta meta = (SkullMeta) i.getItemMeta();
                    meta.setOwner(members);
                    meta.setDisplayName(Util.color("&d&l" + members));
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(Util.color("&7This player is a part of your mine."));
                    lore.add(Util.color(""));
                    lore.add(Util.color("&4* &cMiddle Click here to remove this player from your mine."));
                    meta.setLore(lore);
                    i.setItemMeta(meta);

                    gui.i(count, i);


                    count++;
                }

            }
        }

        gui.onClick(e -> {
            e.setCancelled(true);
            if(e.getSlot() == 53) {
//                if(e.getClick() == ClickType.MIDDLE) {
//
//                    List<String> members = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".members");
//                    members.remove(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
//
//                    PMine.getFile().set("mines." + p.getUniqueId() + ".members", members);
//
//                    p.getOpenInventory().close();
//                    showMembers(p);
//
//                    List<String> addedTo = PMine.getFile().getStringList("mines." + Bukkit.getOfflinePlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())).getUniqueId() + ".memberOf");
//                    addedTo.remove(p.getName());
//
//                    if(Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) != null) {
//
//                        Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())).sendMessage(Color.prison("Mines", "You have been removed from &d" + p.getName() + "'s &7mine!"));
//
//                    }
//
//                    PMine.save();
//
//                }
                p.getOpenInventory().close();
                showAddedTo(p);
            }
        });

        gui.show(p);

    }

    public static void showAddedTo(Player p) {

        Gui gui = new Gui("Your Mines", 54);

        //        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        //        SkullMeta meta = (SkullMeta) i.getItemMeta();

        gui.fillRow(5, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 6), " ");
        gui.i(53, Material.DOUBLE_PLANT, "&e&lClick to Toggle", "", "&7Clicking this will allow you to view members that are", "&7currently added to your mine.");

        if (PMine.getFile().isSet("mines." + p.getUniqueId())) {
            if (PMine.getFile().isSet("mines." + p.getUniqueId() + ".memberOf")) {

                int count = 0;
                for (String members : PMine.getFile().getStringList("mines." + p.getUniqueId() + ".memberOf")) {

                    ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta meta = (SkullMeta) i.getItemMeta();
                    meta.setOwner(members);
                    meta.setDisplayName(Util.color("&d&l" + members));
                    List<String> lore = new ArrayList<>();
                    lore.add("");

                    Profile profile = Core.getInstance().getProfileManager().getProfile(p);
//                    Profile toTeleport = Core.getInstance().getProfileManager().getProfile(Bukkit.getOfflinePlayer(members));
//
//                    if(toTeleport.getData().getPrisonRank().getLevel() >= profile.getData().getPrisonRank().getLevel()) {

                        lore.add(Util.color("&7Status: &a&lUNLOCKED"));
                        lore.add(Util.color(""));
                        lore.add(Util.color("&7&oClick to Teleport!"));
//                    } else {
//                        lore.add(Util.color("&7Status: &c&lLOCKED"));
//                        lore.add(Util.color(""));
//                        lore.add(Util.color("&7&oYou rank must be equal or higher than this"));
//                        lore.add(Util.color("&7&operson's rank to unlock their mine"));
//                    }
                    meta.setLore(lore);
                    i.setItemMeta(meta);

                    gui.i(count, i);


                    count++;
                }

            }
        }

        gui.onClick(e -> {
            e.setCancelled(true);
            if(e.getSlot() == 53) {
                p.getOpenInventory().close();
                showMembers(p);
            }
        });

        gui.show(p);

    }

}
