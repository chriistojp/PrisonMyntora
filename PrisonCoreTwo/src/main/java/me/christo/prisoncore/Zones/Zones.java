package me.christo.prisoncore.Zones;


import me.christo.prisoncore.Main;
import me.christo.prisoncore.Mines.Mines;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Zones {

    private static File zonesFile;
    private static FileConfiguration zones;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return zones;

    }


    public static void loadFile() {
        zonesFile = new File(Main.getInstance().getDataFolder(), "zones.yml");
        if (!zonesFile.exists()) {
            Main.getInstance().saveResource("zones.yml", false);
        }
        zones = YamlConfiguration.loadConfiguration(zonesFile);

        try {
            zones.save(zonesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            zones.save(zonesFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static boolean exists(String zone) {

        for(String key : getFile().getConfigurationSection("zones").getKeys(false)) {
            if(key.equalsIgnoreCase(zone)) {
                return true;
            }
        }
        return false;

    }

    public static String msg(String path) {
        return Util.color(Zones.getFile().getString("messages." + path));

    }

}
