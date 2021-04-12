package me.christo.prisoncore.commands;


import me.christo.prisoncore.managers.Casino;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "opencasino"
)
public class OpenCasinoCommand extends Command {


    @Override
    public void execute(CommandSender sender, String... args) {

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("lw")) {
                Casino.openLavaWater((Player) sender);
            }
        }

    }
}
