package me.christo.prisoncore.commands;


import me.christo.prisoncore.roulette.Roulette;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;

@DynamicCommand(
        name = "roulette"
)
public class StartRouletteCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {
        Roulette.start();
    }
}
