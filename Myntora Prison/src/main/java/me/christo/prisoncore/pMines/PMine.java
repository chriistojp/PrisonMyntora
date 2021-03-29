package me.christo.prisoncore.pMines;


import me.christo.prisoncore.Prison;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PMine {

    private static File pmineFile;

    private static FileConfiguration pmine;

    public static FileConfiguration getFile() {
        return pmine;
    }

    public static HashMap<Player, Player> inviteHash = new HashMap<>();

    public static void save() {
        try {
            pmine.save(pmineFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

  public static void loadFile() {
        pmineFile = new File(Prison.getInstance().getDataFolder(), "pmine.yml");
        if (!pmineFile.exists()) {
            Prison.getInstance().saveResource("pmine.yml", false);
        }
        pmine = YamlConfiguration.loadConfiguration(pmineFile);

        try {
            pmine.save(pmineFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
