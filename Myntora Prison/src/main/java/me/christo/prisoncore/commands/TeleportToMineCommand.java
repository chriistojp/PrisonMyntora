package me.christo.prisoncore.commands;


import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Prison;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "mine"
)
public class TeleportToMineCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int lastMine = Prison.getInstance().getConfig().getInt("lastMine");

        //checks to see if a mine has even been created

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("clear")) {
                profile.getData().getPrisonMineNumber().setAmount(0);
                Bukkit.broadcastMessage("t");

                return;
            }
        }

        if (lastMine == 0) {
            profile.getData().getPrisonMineNumber().setAmount(1);
            Prison.getInstance().getConfig().set("lastMine", 1);
            Prison.getInstance().saveConfig();
            teleport(p);
            assignRegions(p);
            return;
        }

        //checks to see if player has a mine. default value is 0, so if its 0, they don't.

        System.out.println(profile.getData().getPrisonMineNumber().getAmount());
        if (profile.getData().getPrisonMineNumber().getAmount() == 0) {
            profile.getData().getPrisonMineNumber().setAmount(lastMine + 1);
            Prison.getInstance().getConfig().set("lastMine", lastMine + 1);
            Prison.getInstance().saveConfig();
            assignRegions(p);
            teleport(p);
            return;
        }
        teleport(p);


    }

    public static void teleport(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        Location location = new Location(Bukkit.getWorld("prison_world"), (profile.getData().getPrisonMineNumber().getAmount() - 1) * 100 + 0.5, 70, (profile.getData().getPrisonMineNumber().getAmount() - 1) * 100 + 0.5, -180, 0);
        p.teleport(location);


    }

    public void assignRegions(Player p) {

        int lastClaimed = Prison.getInstance().getConfig().getInt("lastMine") - 1;

        BlockVector v = new BlockVector(lastClaimed * 100 + 57, 255,
                lastClaimed * 100 + 93);
        BlockVector x = new BlockVector(lastClaimed * 100 - 57, 0,
                lastClaimed * 100 - 21);

        BlockVector z = new BlockVector(lastClaimed * 100 - 15, 68,
                lastClaimed * 100 - 6);
        BlockVector y = new BlockVector(lastClaimed * 100 + 15, 39,
                lastClaimed * 100 - 36);

        ProtectedRegion pr = new ProtectedCuboidRegion(p.getName() + "schem", v, x);
        pr.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.DENY);
        pr.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
        pr.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
        pr.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
        pr.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
        pr.setFlag(DefaultFlag.EXIT, StateFlag.State.DENY);

        ProtectedRegion mine = new ProtectedCuboidRegion(p.getName() + "mine", z, y);
        mine.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
        mine.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
        mine.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
        mine.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
        mine.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
        mine.setFlag(DefaultFlag.EXIT, StateFlag.State.DENY);

        mine.setPriority(1);

        Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).addRegion(pr);
        Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).addRegion(mine);
    }
}
