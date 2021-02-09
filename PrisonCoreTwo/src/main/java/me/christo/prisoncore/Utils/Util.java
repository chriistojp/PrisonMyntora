package me.christo.prisoncore.Utils;


import me.christo.prisoncore.PlayerDataConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Util {

    public static DecimalFormat df = new DecimalFormat("#,##0.00");
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static Map<UUID, PlayerDataConfig> players = new HashMap<>();

    public static String replaceNumbers(Double num) {
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
        return df.format(num);
    }

    public static DecimalFormat df2 = new DecimalFormat("#,##0");

    public static String replaceNumbers(long num) {
        df2.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
        return df2.format(num);
    }
    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "NORTH";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NORTH";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "EAST";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "EAST";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "SOUTH";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SOUTH";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "WEST";
        }
        return "";
    }
    public static String getOppositeCardinal(Player player) {
        try {
            double rotation = (player.getLocation().getYaw() - 180) % 360;
            if (rotation < 0) {
                rotation += 360.0;
            }
            if (0 <= rotation && rotation < 22.5) {
                return "SOUTH";
            } else if (22.5 <= rotation && rotation < 67.5) {
                return "SOUTH";
            } else if (67.5 <= rotation && rotation < 112.5) {
                return "WEST";
            } else if (112.5 <= rotation && rotation < 157.5) {
                return "WEST";
            } else if (157.5 <= rotation && rotation < 202.5) {
                return "NORTH";
            } else if (202.5 <= rotation && rotation < 247.5) {
                return "NORTH";
            } else if (247.5 <= rotation && rotation < 292.5) {
                return "EAST";
            }
        } catch (IllegalArgumentException e) {

        }
        return "NORTH";
    }
}
