package me.christo.prisoncore.Events.Commands;


import me.christo.prisoncore.Mines.Mines;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player p = (Player) commandSender;

        if(p.isOp()) {
            if(args.length == 2) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (args[1].equalsIgnoreCase("diamondrush")) {
                        for (String key : Mines.getFile().getConfigurationSection("mines").getKeys(false)) {
                            Mines.fill(Material.DIAMOND_BLOCK, key);
                        }
                    }
                    if (args[1].equalsIgnoreCase("goldrush")) {
                        for (String key : Mines.getFile().getConfigurationSection("mines").getKeys(false)) {
                            Mines.fill(Material.GOLD_BLOCK, key);
                        }
                    }
                }
            }
        }

        return false;
    }

    //lol

}
