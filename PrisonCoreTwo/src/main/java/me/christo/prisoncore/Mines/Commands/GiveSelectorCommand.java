package me.christo.prisoncore.Mines.Commands;


import me.christo.prisoncore.Economy.Economy;
import me.christo.prisoncore.Mines.Mines;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSelectorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        if(p.isOp()) {
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("give")) {
                    if(Bukkit.getPlayer(args[1]) != null) {
                        Player toGive = Bukkit.getPlayer(args[1]);
                        Mines.giveSelectorTool(toGive);
                        p.sendMessage(msg("gaveSelector").replaceAll("%player%", toGive.getName()));
                    }
                }
            }



        }

        return false;
    }
    public String msg(String path) {



        return Util.color(Mines.getFile().getString("messages." + path));

    }

}
