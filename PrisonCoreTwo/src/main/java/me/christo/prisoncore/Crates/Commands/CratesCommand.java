package me.christo.prisoncore.Crates.Commands;

import me.christo.prisoncore.Crates.Crates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CratesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;

        Crates.spawnStand(p);

        return false;
    }
}
