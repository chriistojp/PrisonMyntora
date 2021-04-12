package me.christo.prisoncore.commands.admin;


import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "clear"
)
public class ChristoClearStuff extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("gang")) {

                profile.getData().getPrisonGangName().setCell(null);
                profile.getData().getIsGangOwner().setStatus(false);
                profile.getData().getIsGangAdmin().setStatus(false);
                profile.getData().getIsGangMember().setStatus(false);

                profile.getData().save();
                p.sendMessage(Color.main("Gangs", "Cleared!"));

            }
        }
        if(args[0].equalsIgnoreCase("mine")) {
            profile.getData().getPrisonMineNumber().setAmount(0);
            profile.getData().save();
        }

    }
}
