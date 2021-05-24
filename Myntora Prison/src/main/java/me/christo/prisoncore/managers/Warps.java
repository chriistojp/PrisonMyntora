package me.christo.prisoncore.managers;

import me.christo.prisoncore.Prison;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Warps {


    private static File warpsFile;
    private static FileConfiguration warps;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return warps;

    }


    public static void loadFile() {
        warpsFile = new File(Prison.getInstance().getDataFolder(), "warps.yml");
        if (!warpsFile.exists()) {
            Prison.getInstance().saveResource("warps.yml", false);
        }
        warps = YamlConfiguration.loadConfiguration(warpsFile);

        try {
            warps.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            warps.save(warpsFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static boolean exists(String warp) {


        for(String key : getFile().getConfigurationSection("warps").getKeys(false)) {
            if (warp.equalsIgnoreCase(key)) {

                return true;
            }
        }

        return false;

    }

    public static void sendListMessage(Player p) {

        List<String> warps = new ArrayList<>();

        warps.addAll(getFile().getConfigurationSection("warps").getKeys(false));

        String message = String.join(", ", warps);

        p.sendMessage(Color.prison("Warps", "Available Warps:"));
        p.sendMessage(Color.prison("Warps", message));


    }


    public static void teleportPlayer(Player p, String warp) {

        if(exists(warp)) {


            World world = Bukkit.getWorld(getFile().getString("warps." + warp + ".world"));
            int x = getFile().getInt("warps." + warp + ".x");
            int y = getFile().getInt("warps." + warp + ".y");
            int z = getFile().getInt("warps." + warp + ".z");

            float yaw = getFile().getInt("warps." + warp + ".yaw");
            float pitch = getFile().getInt("warps." + warp + ".pitch");

            Location location = new Location(world, x, y, z, pitch, yaw);

            p.teleport(location);
            p.sendMessage(Color.prison("Warps", "You warped to &d" + warp + "&7!"));

        } else {
            p.sendMessage(Color.prison("Warps", "Couldn't find a warp with that name. Do /warps for a list of warps."));
        }


    }

    public static void deleteWarp(Player p, String warp) {

        boolean exists = false;

        for(String key : getFile().getConfigurationSection("warps").getKeys(false)) {
            if (warp.equalsIgnoreCase(key)) {
                exists = true;
                break;
            }
        }

        if(exists) {
            getFile().set("warps." + warp, null);

            save();

            loadFile();

            p.sendMessage(Color.prison("Warps", "Warp successfully deleted!"));

        } else {
            p.sendMessage(Color.prison("Warps", "Couldn't find a warp with that name!"));
        }

    }

    public static void createWarp(Player p, String warp, String world, int x, int y, int z, float pitch, float yaw) {

        boolean exists = false;

        if(getFile().getConfigurationSection("warps").getKeys(false) != null) {

            for (String key : getFile().getConfigurationSection("warps").getKeys(false)) {
                if (warp.equalsIgnoreCase(key)) {
                    exists = true;
                    break;
                }
            }
        }

        if(!exists) {

            getFile().set("warps." + warp + ".world", world);

            getFile().set("warps." + warp + ".x", x);
            getFile().set("warps." + warp + ".y", y);
            getFile().set("warps." + warp + ".z", z);
            getFile().set("warps." + warp + ".yaw", yaw);
            getFile().set("warps." + warp + ".pitch", pitch);

            save();

            loadFile();

            p.sendMessage(Color.prison("Warps", "Warp successfully created!"));

        } else {
            p.sendMessage(Color.prison("Warps", "A warp with that name already exists!"));
        }

    }
    
    
}
