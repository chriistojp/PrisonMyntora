package me.christo.prisoncore.pMines.guis;


import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainGUI {

    private static FileConfiguration getConfig = PMine.getFile();
    public static Gui main() {


        Gui main = new Gui(getConfig.getString("MainGui.name"), getConfig.getInt("MainGui.size"));
        main.fill(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), "");

        main.onClick(e -> {

            e.setCancelled(true);

            if(getConfig.getBoolean("MainGui.Items." + e.getSlot() + ".opensMyMineGui")) {
                MyMineGUI.main().show((Player) e.getWhoClicked());
            }
            if(getConfig.getBoolean("MainGui.Items." + e.getSlot() + ".opensInviteGui")) {
                MineInvitesGUI.main().show((Player) e.getWhoClicked());
            }

        });


        main.onOpen(e -> {

            for(String key : getConfig.getConfigurationSection("MainGui.Items").getKeys(false)) {

                ItemStack i = new ItemStack(Material.matchMaterial(getConfig.getString("MainGui.Items." + key + ".material")));
                ItemMeta meta = i.getItemMeta();
                meta.setDisplayName(Util.color(getConfig.getString("MainGui.Items." + key + ".name")));
                List<String> lore = new ArrayList<String>();
                for(String s : getConfig.getStringList("MainGui.Items." + key + ".lore")) {
                    lore.add(Util.color(s));
                }
                meta.setLore(lore);
                i.setItemMeta(meta);

                if(getConfig.getBoolean("MainGui.Items." + key + ".glow")) {
                    Util.addGlow(i);
                }

                main.i(Integer.parseInt(key), i);
            }

        });

        return main;


    }

}
