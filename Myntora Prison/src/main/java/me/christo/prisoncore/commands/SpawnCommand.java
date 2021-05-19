package me.christo.prisoncore.commands;


import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(

        name = "spawn"

)
public class SpawnCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;

        p.teleport(new Location(Bukkit.getWorld("prison_spawn"), 0, 100 ,0));
        p.sendMessage(Color.prison("Teleport", "Teleported you to Spawn!"));

    }
}
