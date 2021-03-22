package me.christo.prisoncore.shop.commands;


import me.christo.prisoncore.shop.gui.ShopGUI;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "shop",
        aliases = {"shop"}
)
public class ShopCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {
        Player p = (Player) sender;
        ShopGUI.getMainShopGui().show(p);
    }
}
