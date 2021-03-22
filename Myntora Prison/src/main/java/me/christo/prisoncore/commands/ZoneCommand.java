package me.christo.prisoncore.commands;


import me.christo.prisoncore.utils.Util;
import me.christo.prisoncore.managers.Zones;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class ZoneCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                p.sendMessage(Zones.msg("createdZone").replaceAll("%zone%", args[1]));
                Zones.getFile().createSection("zones." + args[1]);
                Zones.save();
            }
            if (args[0].equalsIgnoreCase("delete")) {
                p.sendMessage(Zones.msg("deletedZone"));
                Zones.getFile().set("zones." + args[1], null);
                Zones.save();
            }
            if (args[0].equalsIgnoreCase("addchest")) {
                if (p.getTargetBlock((HashSet<Byte>) null, 200).getType().equals(Material.CHEST)) {

                    int size;
                    if (Zones.getFile().getConfigurationSection("zones." + args[1] + ".chests") == null) {
                        size = 0;
                    } else {
                        size = Zones.getFile().getConfigurationSection("zones." + args[1] + ".chests").getKeys(false).size();
                    }


                    Zones.getFile().set("zones." + args[1] + ".chests." + size, p.getTargetBlock((HashSet<Byte>) null, 200).getLocation());
                    Zones.save();

                    p.sendMessage(Zones.msg("addedChest").replaceAll("%zone%", args[1]));
                    // Chest chest = (Chest) p.getTargetBlock(null, 200).getState();
                }
            }
            if (args[0].equalsIgnoreCase("fill")) {


                for (String key : Zones.getFile().getConfigurationSection("zones." + args[1] + ".chests").getKeys(false)) {
                    Location loc = (Location) Zones.getFile().get("zones." + args[1] + ".chests." + key);
                    System.out.println(loc);

                   World w = loc.getWorld();
                   if (w.getBlockAt(loc).getType().equals(Material.CHEST)) {
                        for (String reward : Zones.getFile().getConfigurationSection("zones." + args[1] + ".rewards").getKeys(false)) {
                            int chance = ThreadLocalRandom.current().nextInt(0, 100);
                            System.out.println("zones." + args[1] + ".rewards." + reward + ".chance");
                            System.out.println(chance + "<=" +  Zones.getFile().getInt("zones." + args[1] + ".rewards." + reward + ".chance"));
                            if (chance <= Zones.getFile().getInt("zones." + args[1] + ".rewards." + reward + ".chance")) {
                                System.out.println("true");
                                Chest chest = (Chest) w.getBlockAt(loc).getState();
                                chest.getBlockInventory().addItem(new ItemStack(Material.matchMaterial(Zones.getFile().getString("zones." + args[1] + ".rewards." + reward + ".item"))));
                            }
                        }
                    }
                }
            }
        }
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage(Util.color("&7-> &b&lZones List"));
                for(String name : Zones.getFile().getConfigurationSection("zones").getKeys(false)) {
                    p.sendMessage(Util.color(" &b- &7" + name));
                }
            }
        }


        return false;
    }

    //lol

}
