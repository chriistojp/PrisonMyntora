package me.christo.prisoncore.commands;


import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "shards"
)
public class ShardsBalanceCommand extends Command {


    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int shards = profile.getData().getPrisonShards().getAmount();

        profile.getData().getPrisonShards().increaseAmount(1000000);
        profile.getData().save();

        p.sendMessage(Color.prison("Shards", "Current Balance: " + shards));



    }
}
