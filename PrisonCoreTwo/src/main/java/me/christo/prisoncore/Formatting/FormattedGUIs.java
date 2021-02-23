package me.christo.prisoncore.Formatting;


import me.christo.prisoncore.Crates.Crates;
import me.christo.prisoncore.Utils.Gui;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import scala.Char;

import java.util.List;

public class FormattedGUIs implements CommandExecutor {

    public void makeFormat(Player p, FileConfiguration config, Gui gui, List<String> toFormat, String keyForItems) {

        int size = gui.getInventory().getSize();


        if(toFormat.size() == size / 9) {

            //for(int i = 0; i < 2 + 1; i++)
            for(int i = 0; i < (size / 9); i++) {
                System.out.println(i);
                String s = toFormat.get(i);
                for(int z = 0; z < 9; z++) {
                    String removeSpaces = s.replaceAll(" ", "");
                    System.out.println("nospaces "  + removeSpaces);
                    char individual = removeSpaces.charAt(z);
                    System.out.println("individual: " + individual);
                    //5 + 1 * 1 +
                    // 1 * 1 + 5
                    //((z + i) * i) + z
                    if(i > 0) {
                        gui.i((9 * i) + z, new ItemStack(Material.matchMaterial(config.getString(keyForItems + "." + individual + ".item"))));
                    } else {
                        gui.i(z, new ItemStack(Material.matchMaterial(config.getString(keyForItems + "." + individual + ".item"))));
                    }
                }

            }

        } else {
            System.out.println(Util.color("&5&lPRISON > &fINVALID SIZE!"));
        }



    }
    public static String getCharAt(String str, int index) {
        return str.substring(index, index);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {

        Player p = (Player) sender;
        List<String> format = Crates.getFile().getStringList("test.format");

        Gui test = new Gui("test", 27);

        makeFormat(p, Crates.getFile(), test, format, "test.items");

        test.show(p);


        return false;
    }
}

