package me.christo.prisoncore.Mines.Commands;


import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Main;
import me.christo.prisoncore.Mines.Mines;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateMineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        Player p = (Player) sender;

        if (!Mines.positionOne.containsKey(p)) {
            p.sendMessage(Mines.msg("invalidSelection").replaceAll("%selection%", "1"));
            return true;
        }
        if (!Mines.positionTwo.containsKey(p)) {
            p.sendMessage(Mines.msg("invalidSelection").replaceAll("%selection%", "2"));
            return true;
        }

        if (Mines.positionOne.containsKey(p) && Mines.positionTwo.containsKey(p)) {
            if (args.length == 1) {

                Location locOne = Mines.positionOne.get(p);
                Location locTwo = Mines.positionTwo.get(p);

                Mines.getFile().set("mines." + args[0] + ".locationOne", locOne);
                Mines.getFile().set("mines." + args[0] + ".locationTwo", locTwo);
                Mines.save();

                p.sendMessage(Mines.msg("createdMine").replaceAll("%mine%", args[0]));

                BlockVector pointOne = new BlockVector(locOne.getX(), locOne.getY(), locOne.getZ());
                BlockVector pointTwo = new BlockVector(locTwo.getX(), locTwo.getY(), locTwo.getZ());

                ProtectedRegion mine = new ProtectedCuboidRegion(args[0] + "-mine", pointOne, pointTwo);
                mine.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
                mine.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
                mine.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
                mine.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
                mine.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);

                Main.getWorldGuard().getRegionManager(locOne.getWorld()).addRegion(mine);

                Mines.positionOne.remove(p);
                Mines.positionTwo.remove(p);
            }
        }


        return false;
    }

    //lol

}
