package me.christo.prisoncore.Mines.Commands;


import me.christo.prisoncore.Mines.Mines;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FillCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {
            Mines.fill(args[0]);
        }

        return false;
    }

    //lol

}
