package me.christo.prisoncore.Utils;


import me.christo.prisoncore.PlayerDataConfig;
import org.bukkit.ChatColor;

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

}
