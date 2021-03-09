package me.christo.prisoncore.commands;

import me.christo.prisoncore.guis.GoalsGUI;
import me.christo.prisoncore.guis.InputRewardsGUI;
import me.christo.prisoncore.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GoalsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Player p = (Player) commandSender;

        //goal editrewards {goal}

        String[] goals = {"mining", "pvp", "pve", "fishing", "looting"};

        if(args.length == 0) {
            GoalsGUI.gui().show(p);
        }
        if(args.length == 2) {
            if(p.isOp()) {
                if (args[0].equalsIgnoreCase("editrewards")) {
                    if(Arrays.asList(goals).contains(args[1].toLowerCase())) {
                        InputRewardsGUI.main(args[1]).show(p);
                    } else {
                        p.sendMessage(Util.color("&cInvalid Goal!"));
                    }
                }
            }
        }

        return false;
    }
}
