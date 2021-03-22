package me.christo.prisoncore.commands;


import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "blocksbroken",
        aliases = {"blocks", "checkblocks"}
)
public class CheckBlocksBrokenCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;

        if(args.length != 1) {
            p.sendMessage(Color.prison("Blocks", "Usage: /blocks <player>"));
        }
        if(args.length == 1) {
            if(Bukkit.getPlayer(args[0]) != null) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                Profile profile = Core.getInstance().getProfileManager().getProfile(targetPlayer);
                p.sendMessage(Color.prison("Blocks", targetPlayer.getName() + "'s Blocks Broken: " + profile.getData().getPrisonBlocksMined().getAmount()));
            }

        }


    }
}
