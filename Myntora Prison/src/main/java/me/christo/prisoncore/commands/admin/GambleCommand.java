package me.christo.prisoncore.commands.admin;

import me.christo.prisoncore.gambling.highlow.HighLowGUI;
import me.christo.prisoncore.gambling.mines.MinesGUI;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "gamble"
)
public class GambleCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;

        if(args.length == 2) {
         //   profile.getData().getPrisonMoney().decreaseAmount(amount);
            if(args[0].equalsIgnoreCase("highlow")) {
                HighLowGUI.openHighLow(p, Integer.parseInt(args[1]));
            }
            if(args[0].equalsIgnoreCase("mines")) {
                MinesGUI.openGui(p, Integer.parseInt(args[1]));
            }
        }

    }
}
