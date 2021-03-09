package me.christo.prisoncore.commands;


import me.christo.prisoncore.Prison;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "mine"
)
public class TeleportToMineCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int lastMine = Prison.getInstance().getConfig().getInt("lastMine");

        //checks to see if a mine has even been created

        if(lastMine == 0) {
            profile.getData().getPrisonMineNumber().setAmount(1);
            Prison.getInstance().getConfig().set("lastMine", 1);
            Prison.getInstance().saveConfig();
            teleport(p);
            return;
        }

        //checks to see if player has a mine. default value is 0, so if its 0, they don't.

        System.out.println(profile.getData().getPrisonMineNumber().getAmount());
        if(profile.getData().getPrisonMineNumber().getAmount() == 0) {
            profile.getData().getPrisonMineNumber().setAmount(lastMine + 1);
            Prison.getInstance().getConfig().set("lastMine", lastMine + 1);
            Prison.getInstance().saveConfig();
            teleport(p);
            return;
        }
        teleport(p);


    }
    public static void teleport(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        Location location = new Location(Bukkit.getWorld("prison_world"), (profile.getData().getPrisonMineNumber().getAmount() - 1) * 100, 70, (profile.getData().getPrisonMineNumber().getAmount() - 1) * 100);
        p.teleport(location);


    }
}
