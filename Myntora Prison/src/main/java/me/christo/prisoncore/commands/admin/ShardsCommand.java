package me.christo.prisoncore.commands.admin;


import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "giveshards"
)

public class ShardsCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {


        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if(profile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {

            if(args.length == 0) {

                p.sendMessage(Color.prison("Usage", "/giveshards (player) [amount]"));
                return;
            }

            if(args.length == 2) {
                if(Bukkit.getPlayer(args[0]) != null) {
                    Profile toGive = Core.getInstance().getProfileManager().getProfile(Bukkit.getPlayer(args[0]));
                    if (args[1].chars().allMatch(Character::isDigit)) {

                        int amount = Integer.parseInt(args[1]);
                        toGive.getData().getPrisonShards().increaseAmount(amount);

                        p.sendMessage(Color.prison("Shards", "You gave " + amount + " shards to " + Bukkit.getPlayer(args[0]).getName()));
                        Bukkit.getPlayer(args[1]).sendMessage(Color.prison("Shards", "You were given " + amount + " shards!"));

                    }
            }


            }


        }


    }
}
