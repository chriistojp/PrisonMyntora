package me.christo.prisoncore.zones.commands;

import me.christo.prisoncore.zones.Zones;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

@DynamicCommand(
        name = "zone",
        aliases = {"zones"}
)
public class ZonesCommand extends Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (profile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {
            if (args.length == 0) {
                p.sendMessage(Color.prison("Usage", "/zone (zone)"));
                p.sendMessage(Color.prison("Usage", "Zones: 1, 2, 3, 4, 5, 6"));
                p.sendMessage(Color.prison("Usage", "Look at a chest for this command to work properly."));
            }
            if (args.length == 1) {

                if (args[0].chars().allMatch(Character::isDigit)) {

                    Block block = (Block) p.getTargetBlock((Set<Material>) null, 5);
                    if (block.getType() == Material.CHEST) {

                        int size;
                        if (Zones.getFile().isSet("zones")) {

                            size = Zones.getFile().getConfigurationSection("zones." + args[0]).getKeys(false).size();

                            Zones.getFile().set("zones." + args[0] + "." + size + ".world", block.getWorld());

                            Zones.getFile().set("zones." + size + ".x", block.getX());
                            Zones.getFile().set("zones." + size + ".y", block.getY());
                            Zones.getFile().set("zones." + size + ".z", block.getZ());
                            Zones.getFile().set("zones." + size + ".zone", args[0]);

                            Zones.save();

                        } else {
                            size = 0;

                            Zones.getFile().set("zones." + args[0] + "." + size + ".world", block.getWorld());

                            Zones.getFile().set("zones." + size + ".x", block.getX());
                            Zones.getFile().set("zones." + size + ".y", block.getY());
                            Zones.getFile().set("zones." + size + ".z", block.getZ());
                            Zones.getFile().set("zones." + size + ".zone", args[0]);

                            Zones.save();

                        }

                    }

                }

                p.sendMessage(Color.prison("Zones", "You added a chest to zone &d" + args[0] + "."));

            }

        }
    }
}