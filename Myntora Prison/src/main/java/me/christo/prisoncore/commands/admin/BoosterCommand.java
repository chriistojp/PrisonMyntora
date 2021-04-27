package me.christo.prisoncore.commands.admin;

import me.christo.prisoncore.boosts.boosts.Farming;
import me.christo.prisoncore.boosts.boosts.Looting;
import me.christo.prisoncore.boosts.boosts.Mega;
import me.christo.prisoncore.boosts.boosts.PVP;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import net.myntora.core.core.data.mysql.player.types.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "booster"

)

public class BoosterCommand extends Command {

    //booster give player type


    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        String[] types = {"pvp", "farming", "mega", "looting"};

        if(args.length == 0) {
            p.sendMessage(Color.prison("Usage", "/booster give (player) [type]"));
            p.sendMessage(Color.prison("Usage", "Types: PVP, Mega, Farming, Looting."));
        }

        if(args.length == 3) {
            if (profile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {
                if(args[0].equalsIgnoreCase("give")) {
                    if(Bukkit.getPlayer(args[1]) != null) {
                        for(String type : types) {
                            if(args[2].equalsIgnoreCase(type)) {
                                if(type.equalsIgnoreCase("farming")) {
                                    p.getInventory().addItem(Farming.getItemStack());
                                } else if (type.equalsIgnoreCase("mega")) {
                                    p.getInventory().addItem(Mega.getItemStack());
                                } else if (type.equalsIgnoreCase("pvp")) {
                                    p.getInventory().addItem(PVP.getItemStack());
                                } else if (type.equalsIgnoreCase("looting")) {
                                    p.getInventory().addItem(Looting.getItemStack());
                                }

                            }
                        }

                    } else {
                        p.sendMessage(Color.prison("Boosters", "Couldn't find that player."));
                    }
                }

            }
        }

    }
}
