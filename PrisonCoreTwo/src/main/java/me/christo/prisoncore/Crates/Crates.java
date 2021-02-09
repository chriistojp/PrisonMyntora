package me.christo.prisoncore.Crates;

import me.christo.prisoncore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Chest;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Crates {

    private static File cratesFile;
    private static FileConfiguration crates;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return crates;

    }


    public static void loadFile() {
        cratesFile = new File(Main.getInstance().getDataFolder(), "crates.yml");
        if (!cratesFile.exists()) {
            Main.getInstance().saveResource("crates.yml", false);
        }
        crates = YamlConfiguration.loadConfiguration(cratesFile);

        try {
            crates.save(cratesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            crates.save(cratesFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }


    public static void spawnStand(Player p, Block b) {



        Location chest = b.getLocation();

        Location loc = new Location(b.getWorld(), b.getX() + 1, b.getY() + .5, b.getZ() + .75);
        loc.setYaw(90);

        ArmorStand as = (ArmorStand) b.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.getBodyPose().add(48, 0, 72);
        as.setVisible(true);
        as.setGravity(false);
        b.getWorld().getBlockAt(chest).setType(Material.CHEST);

        Block block = p.getWorld().getBlockAt(chest);


        int count = 1;
        for(String key : Crates.getFile().getConfigurationSection("crates").getKeys(false)) {
            for(String value : Crates.getFile().getConfigurationSection("crates." + key).getKeys(false)) {
                count++;
                System.out.println(count);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    System.out.println("ran");
                    as.setItemInHand(new ItemStack(Material.matchMaterial(Crates.getFile().getString("crates." + key + "." + value + ".material"))));
                    System.out.println(Material.matchMaterial(Crates.getFile().getString("crates." + key + "." + value + ".material")));

                }, 20 * count);
            }
        }

        //as.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
    }

}
