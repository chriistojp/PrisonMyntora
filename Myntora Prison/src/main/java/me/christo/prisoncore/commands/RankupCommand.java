package me.christo.prisoncore.commands;


import lombok.SneakyThrows;
import me.christo.prisoncore.managers.Mines;
import me.christo.prisoncore.managers.NPCS;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.prison.PrisonRanks;
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

        switch (rank) {
            case "D1":
                Mines.fill(Material.COBBLESTONE, p.getName() + "mine");
            case "D2":
                Mines.fill(Material.STONE, p.getName() + "mine");
            case "D3":
                if(checkChance(25)) {
                    Mines.fill(Material.COAL, p.getName() + "mine");
                } else {
                    Mines.fill(Material.STONE, p.getName() + "mine");
                }
            case "D4":
                if(checkChance(50)) {
                    Mines.fill(Material.COAL, p.getName() + "mine");
                } else {
                    Mines.fill(Material.STONE, p.getName() + "mine");
                }
            case "D5":
                Mines.fill(Material.COAL, p.getName() + "mine");
            case "C1":
                if(checkChance(25)) {
                    Mines.fill(Material.LAPIS_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.COAL_ORE, p.getName() + "mine");
                }
            case "C2":
                if(checkChance(25)) {
                    Mines.fill(Material.LAPIS_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.COAL_ORE, p.getName() + "mine");
                }
            case "C3":
                    Mines.fill(Material.LAPIS_ORE, p.getName() + "mine");
            case "C4":
                if(checkChance(25)) {
                    Mines.fill(Material.IRON_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.LAPIS_ORE, p.getName() + "mine");
                }
            case "C5":
                if(checkChance(50)) {
                    Mines.fill(Material.IRON_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.LAPIS_ORE, p.getName() + "mine");
                }
            case "B1":
                Mines.fill(Material.IRON_ORE, p.getName() + "mine");
            case "B2":
                if(checkChance(25)) {
                    Mines.fill(Material.GOLD_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.IRON_ORE, p.getName() + "mine");
                }
            case "B3":
                if(checkChance(50)) {
                    Mines.fill(Material.GOLD_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.IRON_ORE, p.getName() + "mine");
                }
            case "B4":
                    Mines.fill(Material.GOLD_ORE, p.getName() + "mine");
            case "B5":
                if(checkChance(25)) {
                    Mines.fill(Material.DIAMOND_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.GOLD_ORE, p.getName() + "mine");
                }
            case "A1":
                if(checkChance(50)) {
                    Mines.fill(Material.DIAMOND_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.GOLD_ORE, p.getName() + "mine");
                }
            case "A2":
                Mines.fill(Material.DIAMOND_ORE, p.getName() + "mine");
            case "A3":
                if(checkChance(25)) {
                    Mines.fill(Material.EMERALD_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.DIAMOND_ORE, p.getName() + "mine");
                }
            case "A4":
                if(checkChance(50)) {
                    Mines.fill(Material.EMERALD_ORE, p.getName() + "mine");
                } else {
                    Mines.fill(Material.DIAMOND_ORE, p.getName() + "mine");
                }
            case "A5":
                Mines.fill(Material.EMERALD_ORE, p.getName() + "mine");
            }


        NPCS.spawnGoalsNPC(p, p.getLocation());


    }

    public boolean checkChance(int chance) {

        //60

        //0 - 100

        //
        int random = ThreadLocalRandom.current().nextInt(0, 100);
        if(random <= chance) {
            return true;
        }
        return false;

    }

}
