package me.christo.prisoncore.commands;


import lombok.SneakyThrows;
import me.christo.prisoncore.managers.NPCS;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.prison.PrisonRanks;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "rankup"
)
public class RankupCommand extends Command {
    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String... args) {
        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        profile.getData().getPrisonRank().tryToRankup(p);

        NPCS.spawnGoalsNPC(p, p.getLocation());


    }
}
