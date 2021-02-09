package me.christo.prisoncore.Crates.Commands;

import me.christo.prisoncore.Crates.Crates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CratesCommand implements CommandExecutor, Listener {

    @EventHandler
    public void onCratePlace(BlockPlaceEvent e) {

        Crates.spawnStand(e.getPlayer(), e.getBlock());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;



        return false;
    }
}
