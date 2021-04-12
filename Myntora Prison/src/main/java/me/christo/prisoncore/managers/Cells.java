package me.christo.prisoncore.managers;


import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.minecraft.server.v1_8_R3.BlockCactus;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Getter
public class Cells {

    public static HashMap<Player, String> blockName = new HashMap<>();


    static HashMap<Player, Location> locationOne = new HashMap<>();
    static HashMap<Player, Location> locationTwo = new HashMap<>();
    static HashMap<Player, Boolean> getSignSelection = new HashMap<>();
    public static HashMap<Player, String> getCellName = new HashMap<>();
    public static HashMap<Player, Boolean> disbandStatus = new HashMap<>();
    public static HashMap<Player, Player> inviteHashmap = new HashMap<>();


    //puts the auctioneer with the player who last bid so we can remove money

    public static void tryToDisband(Player p) {

        disbandStatus.put(p, true);
        p.sendMessage(Color.prison("Cell", "Are you sure you want to disband? Type 'yes' or 'cancel'!"));



    }

    public static void setLocation(Player p, Location loc, int locNumber) {

        if(locNumber == 1) {
            locationOne.put(p, loc);
        }
        if(locNumber == 2) {
            locationTwo.put(p, loc);
        }

    }

    public String getCell(Player p) {

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        return profile.getData().getPrisonCellName().getCell();

    }


    public static void flush(Player p) {
        blockName.remove(p);
        getSignSelection.remove(p);
        locationOne.remove(p);
        locationTwo.remove(p);
        getSignSelection.remove(p);
        getCellName.remove(p);
    }

    public static Location getLocation(Player p, int locationNumber) {

        if(locationNumber == 1) {
            return locationOne.get(p);
        }
        if(locationNumber == 2) {
            return locationTwo.get(p);
        }
        return null;
    }

    public static void enableSignStatus(Player p) {

        getSignSelection.put(p, true);

    }

    public static boolean signStatus(Player p) {

        if(getSignSelection.get(p) == null) {
            return false;
        }

       return getSignSelection.get(p);
    }

    public static String getCellName(Player p) {
        return getCellName.get(p);
    }
    public static String setCellName(Player p, String cellName) {
        return getCellName.put(p, cellName);
    }
    private static File cellsFile;
    private static FileConfiguration cells;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return cells;

    }


    public static void loadFile() {
        cellsFile = new File(Prison.getInstance().getDataFolder(), "cells.yml");
        if (!cellsFile.exists()) {
            Prison.getInstance().saveResource("cells.yml", false);
        }
        cells = YamlConfiguration.loadConfiguration(cellsFile);

        try {
            cells.save(cellsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        try {
            cells.save(cellsFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
