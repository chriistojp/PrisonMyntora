package me.christo.prisoncore.commands;


import lombok.SneakyThrows;
import me.christo.prisoncore.managers.Mines;
import me.christo.prisoncore.managers.NPCS;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.prison.PrisonRanks;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

@DynamicCommand(
        name = "rankup"
)
public class RankupCommand extends Command {
    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String... args) {
        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        String rank = profile.getData().getPrisonRank().getName();
        profile.getData().getPrisonRank().tryToRankup(p);

        Bukkit.broadcastMessage(rank);

        if (profile.getData().getPrisonMineNumber().getAmount() != 0) {

            if (profile.getData().getPrisonMineNumber().getAmount() != 0) {
                Mines.fill(p, p.getName() + "mine");
            } else {
                p.sendMessage(Color.prison("Mines", "You don't have a mine to reset!"));
            }


            NPCS.spawnGoalsNPC(p, p.getLocation());

        }

    }

    public boolean checkChance(int chance) {


        int random = ThreadLocalRandom.current().nextInt(0, 100);
        if(random <= chance) {
            return true;
        }
        return false;

    }

}
