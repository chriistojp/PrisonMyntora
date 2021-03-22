package me.christo.prisoncore.commands;


import me.christo.prisoncore.sets.ZeusSet;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        ZeusSet.giveZeusSet((Player) commandSender);

        return false;
    }



}
