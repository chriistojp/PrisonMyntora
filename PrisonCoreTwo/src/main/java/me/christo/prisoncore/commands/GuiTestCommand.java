package me.christo.prisoncore.commands;


import me.christo.prisoncore.utils.Gui;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@DynamicCommand(
        name = "opengui"
)

public class GuiTestCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        openGui((Player) sender);

//    public Gui i(int slot, ItemStack item, String name, clickEvent e, String... lore) {
//
//        onClick(event -> {
//            if(event.getSlot() == 11) {
//
//            }
//        });
//        return this;
//    }

    }

    public static void openGui(Player p) {

        Gui test = new Gui("test", 9);
        test.i(1, new ItemStack(Material.DIRT));
        test.show(p);

    }
}
