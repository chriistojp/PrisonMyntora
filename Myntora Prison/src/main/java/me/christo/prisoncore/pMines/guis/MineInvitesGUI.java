package me.christo.prisoncore.pMines.guis;



import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MineInvitesGUI {

    private static FileConfiguration getConfig = PMine.getFile();
    public static Gui main() {


        Gui main = new Gui(getConfig.getString("MineInvitesGui.name"), getConfig.getInt("MineInvitesGui.size"));
        main.fill(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), "");

        main.onClick(e -> {

            e.setCancelled(true);

            if(e.getInventory().getItem(e.getSlot()).getType().equals(Material.SKULL_ITEM)) {

                String s = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                Profile profile = Core.getInstance().getProfileManager().getProfile(Bukkit.getOfflinePlayer(s));

                Location loc = new Location(Bukkit.getWorld("prison_world"), profile.getData().getPrisonMineNumber().getAmount() * 100, 85,profile.getData().getPrisonMineNumber().getAmount() * 100);

                e.getWhoClicked().teleport(loc);
            }

        });

        main.onOpen(e -> {

            int start = getConfig.getInt("MineInvitesGui.startsAt");

            for(String key : getConfig.getConfigurationSection("MineInvitesGui.Items").getKeys(false)) {

                ItemStack i = new ItemStack(Material.matchMaterial(getConfig.getString("MineInvitesGui.Items." + key + ".material")));
                ItemMeta meta = i.getItemMeta();
                meta.setDisplayName(Util.color(getConfig.getString("MineInvitesGui.Items." + key + ".name")));
                List<String> lore = new ArrayList<String>();
                for(String s : getConfig.getStringList("MineInvitesGui.Items." + key + ".lore")) {
                    lore.add(Util.color(s));
                }
                meta.setLore(lore);
                i.setItemMeta(meta);

                if(getConfig.getBoolean("MineInvitesGui.Items." + key + ".glow")) {
                    Util.addGlow(i);
                }

                main.i(Integer.parseInt(key), i);
            }

            //players:
            //  uuid:
            //    whitelistTo:


            for(String s : PMine.getFile().getStringList("mines." + e.getPlayer().getUniqueId() + ".whitelistedTo")) {
                ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta meta = (SkullMeta) i.getItemMeta();
                meta.setDisplayName(Util.color(getConfig.getString("MineInvitesGui.displayName").replace("[player]", s)));
                meta.setOwner(s);
                List<String> lore = new ArrayList<>();
                for(String lores : getConfig.getStringList("MineInvitesGui.displayLore")) {
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



