package me.christo.prisoncore.commands.admin;


import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.World;
import lombok.SneakyThrows;
import me.christo.prisoncore.Prison;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

@DynamicCommand(
        name = "generate"
)
public class GenerateMinesCommand extends Command {

    int toGenerate = Prison.getInstance().getConfig().getInt("generate");

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String... args) {

        if (args.length == 1) {

            Player p = (Player) sender;
            pasteSchematic(p.getLocation());
        } else {
            int toGenerate = Prison.getInstance().getConfig().getInt("generate");
            for (int i = 0; i < toGenerate; i++) {

                pasteSchematic((Player) sender);
                sender.sendMessage("generate");

            }
        }


}

    @SneakyThrows
    public void pasteSchematic(Player p) {


        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (profile.getData().getRank().isHigherOrEqualsTo(p, Rank.DEV, true)) {

            int lastGenerated = Prison.getInstance().getConfig().getInt("lastGenerated");
            Location loc = new Location(Bukkit.getWorld("prison_world"), lastGenerated * 100, 70, lastGenerated * 100);

            p.sendMessage("schematic pasted at " + loc);


            File file = new File(Prison.getInstance().getDataFolder() + File.separator + "clipboard.schematic");
            if (!file.exists()) {
                Prison.getInstance().saveResource("clipboard.schematic", false);
            }

            Vector vec = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            World w = new BukkitWorld(Bukkit.getWorld("prison_world"));

            FaweAPI.load(file).paste(w, vec);

            Prison.getInstance().getConfig().set("lastGenerated", lastGenerated + 1);
            Prison.getInstance().saveConfig();

        }

    }

    @SneakyThrows
    public void pasteSchematic(Location loc) {



        File file = new File(Prison.getInstance().getDataFolder() + File.separator + "clipboard.schematic");
        if (!file.exists()) {
            Prison.getInstance().saveResource("clipboard.schematic", false);
        }

        Vector vec = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        World w = new BukkitWorld(Bukkit.getWorld("prison_world"));

        FaweAPI.load(file).paste(w, vec);


    }
}
