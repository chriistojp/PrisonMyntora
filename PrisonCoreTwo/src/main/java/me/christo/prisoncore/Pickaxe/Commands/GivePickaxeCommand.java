package me.christo.prisoncore.Pickaxe.Commands;


import me.christo.prisoncore.Pickaxe.StarterPickaxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePickaxeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {


        StarterPickaxe.givePickaxe((Player) commandSender);

        return false;
    }

    //lol

}
