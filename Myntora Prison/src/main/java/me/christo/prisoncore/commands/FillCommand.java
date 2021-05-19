package me.christo.prisoncore.commands;

import me.christo.prisoncore.managers.Mines;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.util.Color;
import net.myntora.core.core.util.pagination.buttons.Button;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "fillmine"
)
public class FillCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        p.sendMessage("yes");
        MineCommand.teleport(p);

        Mines.fill(p, p.getName() + "mine");
        Button.playSuccess(p);

        p.sendMessage(Color.prison("Fill", "Success!"));

    }
}
