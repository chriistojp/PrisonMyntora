package me.christo.prisoncore.commands.admin;


import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@DynamicCommand(
        name = "suicide"
)

public class SuicideCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;

        p.setHealth(0);


    }
}
