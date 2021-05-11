package me.christo.prisoncore.commands.admin;

//import com.sun.org.apache.bcel.internal.generic.InstructionFactory;
import me.christo.prisoncore.managers.Infirmary;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "infirmary"
)
public class InfirmaryCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Infirmary.openGui(p);
    }
}
