package me.christo.prisoncore.commands;

import me.christo.prisoncore.managers.Crates;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CratesCommand implements CommandExecutor, Listener {


    @EventHandler
    public void onCratePlace(BlockPlaceEvent e) {

        ItemStack i = e.getItemInHand();

        net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                : new net.minecraft.server.v1_8_R3.NBTTagCompound();

        if(itemC.getBoolean("crate")) {
            Crates.spawnStand(e.getPlayer(), e.getBlock(), itemC.getString("type"));
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        FileConfiguration config = Crates.getFile();

        //crates give (player) (crate) (amount)
        if (p.isOp()) {
            if (args.length == 4) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Crates.getFile().isSet("crates." + args[2])) {

                            ItemStack i = new ItemStack(Material.CHEST);
                            ItemMeta meta = i.getItemMeta();
                            meta.setDisplayName(Util.color(config.getString("crates." + args[2] + ".item.name")));
                            ArrayList<String> lore = new ArrayList<>();
                            for (String s : config.getStringList("crates." + args[2] + ".item.lore")) {
                                lore.add(Util.color(s));
                            }
                            meta.setLore(lore);
                            i.setItemMeta(meta);

                            net.minecraft.server.v1_8_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(i);
                            net.minecraft.server.v1_8_R3.NBTTagCompound itemC = (itemNMS.hasTag()) ? itemNMS.getTag()
                                    : new net.minecraft.server.v1_8_R3.NBTTagCompound();

                            itemC.setBoolean("crate", true);
                            itemC.setString("type", args[2]);
                            ItemStack newItem = CraftItemStack.asBukkitCopy(itemNMS);

                            Bukkit.getPlayer(args[1]).getInventory().addItem(newItem);

                        }
                    }
                }
            }
            //crates create (crate)
            if (args.length == 2) {
                if(args[0].equalsIgnoreCase("edit")) {
                    if (Crates.getFile().isSet("crates." + args[1])) {
                        Util.createRewardsGui(p, "crates", args[1], Crates.getFile(), Crates.cratesFile);
                    }
                }
                if (args[0].equalsIgnoreCase("create")) {
                    if (!Crates.getFile().isSet("crates." + args[1])) {

                        createCrate(p, args[1]);
                        p.sendMessage(Util.color(config.getString("messages.createdCrate")).replaceAll("%crate%", args[1]));

                    } else {
                        p.sendMessage(Util.color(config.getString("messages.alreadyExists")));
                        return true;
                    }
                }
            }
        }



        return false;
    }

    public void createCrate(Player p, String crateName) {

        FileConfiguration config = Crates.getFile();

        if (Crates.getFile().get("crates." + crateName) == null) {

//            Crates.getFile().createSection("crates." + crateName);
//            Crates.getFile().createSection("crates." + crateName + ".item.name");
//            Crates.getFile().createSection("crates." + crateName + ".item.lore");
//            Crates.save();

            List<String> lore = new ArrayList<>();
            lore.add("&r");
            lore.add("&7Place this crate on the ground to open!");
            lore.add("&r");
            lore.add("&7More crates can be obtained at our");
            lore.add("&7store @ &a&nchristoiskingz@gmail.com");

            System.out.println(config);

            config.set("crates." + crateName + ".item.name", "&a&l" + crateName + " Crate");
            config.set("crates." + crateName + ".item.lore", lore);

            Crates.save();

        } else {
            p.sendMessage(Util.color(config.getString("messages.alreadyExists")));
        }

    }
}
