package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Sets {

    public static File setsFile;
    private static FileConfiguration sets;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return sets;

    }

    public static String getString(String s) {

        return Util.color(getFile().getString(s));

    }


    public static void loadFile() {
        setsFile = new File(Prison.getInstance().getDataFolder(), "sets.yml");
        if (!setsFile.exists()) {
            Prison.getInstance().saveResource("sets.yml", false);
        }
        sets = YamlConfiguration.loadConfiguration(setsFile);

        try {
            sets.save(setsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            sets.save(setsFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
    
}
