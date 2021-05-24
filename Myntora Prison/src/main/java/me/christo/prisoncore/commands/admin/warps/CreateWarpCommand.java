package me.christo.prisoncore.commands.admin.warps;

import me.christo.prisoncore.managers.Warps;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;

@DynamicCommand(
        name = "setwarp",
        aliases = {"createwarp"}
)
public class CreateWarpCommand extends Command {


    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if(profile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {

            if(args.length == 1) {

                Location loc = p.getLocation();

                Warps.createWarp(p , args[0], loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch());




            }

        }


    }

}
