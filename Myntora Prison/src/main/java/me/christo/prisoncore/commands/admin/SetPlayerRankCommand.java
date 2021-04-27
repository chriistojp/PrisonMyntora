package me.christo.prisoncore.commands.admin;


import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import net.myntora.core.core.data.mysql.player.types.PrisonRanks;
import net.myntora.core.core.util.Color;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "setprisonrank"
)
public class SetPlayerRankCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile playerProfile = Core.getInstance().getProfileManager().getProfile(p);
        if (playerProfile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {
            if(args.length < 2) {
                sender.sendMessage(Color.prison("Rank", "Usage: /setprisonrank <player> <rank>"));
                sender.sendMessage(Color.prison("Rank", "Rank List: " + StringUtils.join(PrisonRanks.values(), ", ")));
            }
            if (args.length == 2) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(Bukkit.getPlayer(args[0]));
                try {
                    profile.getData().setPrisonRank(PrisonRanks.valueOf(args[1].toUpperCase()));

                } catch (Exception e) {
                    p.sendMessage(Color.prison("Rank", "&7Invalid Rank: &d" + args[1].toUpperCase()));
                    return;
                }

                sender.sendMessage(Color.prison("Rank", "You set &d" + Bukkit.getPlayer(args[0]).getName() + "'s &7rank to &d" + args[1].toUpperCase()));

            }

        }
    }
}
