package me.christo.prisoncore.commands;


import me.christo.prisoncore.managers.StarterPickaxe;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.player.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePickaxeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        Profile profile = Core.getInstance().getProfileManager().getProfile((Player) commandSender);

        if(args.length == 1) {
            if(Bukkit.getPlayer(args[0]) != null) {
                if(profile.getData().getRank().isHigherOrEqualsTo((Player) commandSender, Rank.ADMIN, true)) {

                    StarterPickaxe.givePickaxe((Player) commandSender);
                }
            } else {
                commandSender.sendMessage(Color.prison("Pickaxe", "Couldn't find that player."));
            }
        }

        return false;
    }

    //lol

}
