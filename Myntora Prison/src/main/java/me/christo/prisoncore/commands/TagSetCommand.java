package me.christo.prisoncore.commands;


import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "tag"
)

public class TagSetCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        if(args.length == 1) {
            ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
            as.setGravity(false);
            as.setCustomName(Util.color(args[0]));
            as.setCustomNameVisible(true);
            as.setVisible(false);
            as.setSmall(true);
            p.setPassenger(as);
        }


    }
}
