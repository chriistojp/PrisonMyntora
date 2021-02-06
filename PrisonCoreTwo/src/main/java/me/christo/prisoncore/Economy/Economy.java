package me.christo.prisoncore.Economy;


import me.christo.prisoncore.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Economy {

    private static File economyFile;
    private static FileConfiguration economy;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return economy;

    }


    public static void loadFile() {
        economyFile = new File(Main.getInstance().getDataFolder(), "economy.yml");
        if (!economyFile.exists()) {
            Main.getInstance().saveResource("economy.yml", false);
        }
        economy = YamlConfiguration.loadConfiguration(economyFile);

        try {
            economy.save(economyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
