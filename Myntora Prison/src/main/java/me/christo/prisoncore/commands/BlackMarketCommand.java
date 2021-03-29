package me.christo.prisoncore.commands;


import me.christo.prisoncore.managers.BlackMarket;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.util.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@DynamicCommand(
        name = "blackmarket",
        aliases = {"bm"}
)
public class BlackMarketCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        //&f- /blackmarket sell (amount)

        Player p = (Player) sender;

        if(args.length == 0) {
            BlackMarket.showMarketGui((Player) sender);
        }

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("sell")) {
                if (args[1].chars().allMatch(Character::isDigit)) {
                    if(p.getItemInHand() != null || (p.getItemInHand()).getType() != Material.AIR) {
                        BlackMarket.addItem(p.getName(), p.getItemInHand(), Integer.parseInt(args[1]));
                        p.setItemInHand(new ItemStack(Material.AIR));
                        p.sendMessage(Color.prison("Market", "Your item has been listed on the black market!"));

                    } else {
                        p.sendMessage(Color.prison("Market", "You cannot auction air."));
                    }
                }
            }
        }

           // BlackMarket.showMarketGui((Player) sender);


    }
}
