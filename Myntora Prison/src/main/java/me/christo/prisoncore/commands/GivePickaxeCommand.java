package me.christo.prisoncore.commands;


import me.christo.prisoncore.pickaxe.StarterPickaxe;
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
        name = "givepickaxe"
)
public class GivePickaxeCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

            Player p = (Player) sender;
            Profile profile = Core.getInstance().getProfileManager().getProfile((Player) sender);

            if(args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (profile.getData().getRank().isHigherOrEqualsTo((Player) sender, Rank.ADMIN, true)) {
                        p.getInventory().addItem(StarterPickaxe.getPlayersPickaxe(p));

                    }
                } else {
                    sender.sendMessage(Color.prison("Pickaxe", "Couldn't find that player."));
                }
            }
    }
}

