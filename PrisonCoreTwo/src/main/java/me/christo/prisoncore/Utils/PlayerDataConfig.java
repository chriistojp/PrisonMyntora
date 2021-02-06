package me.christo.prisoncore;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

public class PlayerDataConfig {

    private final File playerData;
    private FileConfiguration config;
    private UUID uuid;
    private Main plugin;
    private File playerDataFolder;

    public PlayerDataConfig(Main plugin, UUID uuid) {
        this.uuid = uuid;
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder() + File.separator + "players");
        this.playerData = new File(this.playerDataFolder.getAbsoluteFile() + File.separator + uuid.toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(playerData);
        if (!this.playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        if(!playerData.exists()) {
            set("balance", 0);
        }
    }

    public FileConfiguration get() {
        return this.config;
    }

    public void save() {
        try {
            this.config.save(this.playerData);
        } catch (IOException e) {
            this.plugin.getServer().getConsoleSender().sendMessage("Error saving " + uuid.toString() + ".yml");
            e.printStackTrace();
        }
    }

    public void set(String path, Object object) {
        get().set(path, object);
        save();
    }
    public UUID getId() {
        return uuid;
    }





}
