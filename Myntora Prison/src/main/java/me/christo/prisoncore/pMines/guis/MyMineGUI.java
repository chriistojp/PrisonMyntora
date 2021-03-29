package me.christo.prisoncore.pMines.guis;


import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class MyMineGUI {

    private static FileConfiguration getConfig = PMine.getFile();
    public static Gui main() {


        Gui main = new Gui(getConfig.getString("MyMineGui.name"), getConfig.getInt("MyMineGui.size"));
        main.fill(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), "");

        main.onClick(e -> {
            e.setCancelled(true);
        });
        main.onOpen(e -> {

            int start = getConfig.getInt("MyMineGui.startsAt");

            for(String key : getConfig.getConfigurationSection("MyMineGui.Items").getKeys(false)) {

                ItemStack i = new ItemStack(Material.matchMaterial(getConfig.getString("MyMineGui.Items." + key + ".material")));
                ItemMeta meta = i.getItemMeta();
                meta.setDisplayName(Util.color(getConfig.getString("MyMineGui.Items." + key + ".name")));
                List<String> lore = new ArrayList<String>();
                for(String s : getConfig.getStringList("MyMineGui.Items." + key + ".lore")) {
                    lore.add(Util.color(s));
                }
                meta.setLore(lore);
                i.setItemMeta(meta);


                main.i(Integer.parseInt(key), i);
            }

            for(String s : PMine.getFile().getStringList("mines." + e.getPlayer().getUniqueId() + ".whitelisted")) {

                ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta meta = (SkullMeta) i.getItemMeta();
                meta.setDisplayName(Util.color(getConfig.getString("MyMineGui.displayName").replace("[player]", s)));
                meta.setOwner(s);
                List<String> lore = new ArrayList<>();
                for(String lores : getConfig.getStringList("MyMineGui.displayLore")) {
                    lore.add(Util.color(lores));
                }
                meta.setLore(lore);
                i.setItemMeta(meta);
                main.i(start, i);
                start++;
            }

        });

        return main;


    }

}
