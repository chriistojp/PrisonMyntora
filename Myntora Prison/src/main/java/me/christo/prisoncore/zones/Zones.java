package me.christo.prisoncore.zones;

import javafx.util.Pair;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Zones {

    HashMap<Integer, Pair<ItemStack, Integer>> zonesHash = new HashMap<>();


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
        zonesFile = new File(Prison.getInstance().getDataFolder(), "zones.yml");
        if (!zonesFile.exists()) {
            Prison.getInstance().saveResource("zones.yml", false);
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


}