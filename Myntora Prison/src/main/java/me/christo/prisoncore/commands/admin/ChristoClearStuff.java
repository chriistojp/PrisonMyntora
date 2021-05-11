package me.christo.prisoncore.commands.admin;


import me.christo.prisoncore.Prison;
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
                profile.getData().getPrisonGangOwner().setStatus(false);
                profile.getData().getPrisonGangAdmin().setStatus(false);
                profile.getData().getPrisonGangMember().setStatus(false);


                p.sendMessage(Color.main("Gangs", "Cleared!"));

            }
        }
        if(args[0].equalsIgnoreCase("mine")) {
            profile.getData().getPrisonMineNumber().setAmount(0);

            Prison.getInstance().getConfig().set("lastMine", 1);
            Prison.getInstance().getConfig().set("lastGenerated", 0);
            Prison.getInstance().saveConfig();
        }
        if(args[0].equalsIgnoreCase("cell")) {
            profile.getData().getPrisonCellStatus().setStatus(false);
            profile.getData().getPrisonCellName().setCell("");
        }

    }
}
