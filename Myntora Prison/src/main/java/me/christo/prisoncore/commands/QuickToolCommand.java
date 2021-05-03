package me.christo.prisoncore.commands;

import me.christo.prisoncore.managers.QuickTool;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.util.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

@DynamicCommand(
        name = "quicktool",
        aliases = {"qt"}
)
public class QuickToolCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;

        boolean found = false;
        for(ItemStack i : p.getInventory().getContents()) {
            if(i == null) {
                continue;
            }
            if(i.getType() == Material.COMPASS) {
                if(i.hasItemMeta()) {
                    if(i.getItemMeta().hasDisplayName()) {
                        if(i.getItemMeta().getDisplayName().equals(Util.color("&d&Quick Tool"))) {
                            p.sendMessage(Color.prison("Quick Tool", "You already have a quick tool!"));
                            found = true;
                        }
                    }
                }
            }
        }
        if(!found) {
            p.sendMessage(Color.prison("Quick Tool", "Success!"));
            QuickTool.giveQuickTool(p);
        }

    }
}
