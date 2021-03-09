package me.christo.prisoncore.scoreboard;

import me.christo.prisoncore.Manager;
import me.christo.prisoncore.Prison;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.player.Rank;
import net.myntora.core.core.scoreboard.MainScoreboard;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class ScoreboardManager extends Manager implements Listener {


    public ScoreboardManager(Prison plugin) {
        super(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        net.myntora.core.core.scoreboard.ScoreboardManager scoreboardManager = new net.myntora.core.core.scoreboard.ScoreboardManager(Core.getInstance()) {

            @Override
            public void setup(MainScoreboard scoreboard) {

                for (Rank rank : Rank.values()) {
                    if (!rank.getName().isEmpty()) {
                        scoreboard.getHandle().registerNewTeam(rank.name()).setPrefix(rank.getPrefix());
                    } else {
                        scoreboard.getHandle().registerNewTeam(rank.name()).setPrefix("");
                    }
                }
                scoreboard.register(PrisonScoreboardLine.START_EMPTY_SPACER)
                        .register(PrisonScoreboardLine.PLAYER_TITLE)
                        .register(PrisonScoreboardLine.RANK)
                        .register(PrisonScoreboardLine.BALANCE)
                        .register(PrisonScoreboardLine.SHARDS)
                        .register(PrisonScoreboardLine.PLAYER_EMPTY_SPACE)
                        .register(PrisonScoreboardLine.GANG)
                        .register(PrisonScoreboardLine.GANG_NAME)
                        .register(PrisonScoreboardLine.FINAL_SPACER)
                        .register(PrisonScoreboardLine.WEBSITE_VALUE)
                        .recalculate();

                scoreboard.get(PrisonScoreboardLine.PLAYER_TITLE).write(color("&d&lPlayer:"));
                scoreboard.get(PrisonScoreboardLine.RANK).write(color("&7Rank:"));
                scoreboard.get(PrisonScoreboardLine.BALANCE).write(color("&7Balance:"));
                scoreboard.get(PrisonScoreboardLine.SHARDS).write(color("&7Shards:"));
                scoreboard.get(PrisonScoreboardLine.BALANCE).write(ChatColor.GREEN + "" + ChatColor.BOLD + "Gems");
                scoreboard.get(PrisonScoreboardLine.SHARDS).write(ChatColor.RED + "" + ChatColor.BOLD + "Website");
                scoreboard.get(PrisonScoreboardLine.WEBSITE_VALUE).write(color("&dmyntora.net"));
            }

            @Override
            public void draw(MainScoreboard scoreboard) {
                scoreboard.setSidebarName(color("&d&lPRISON"));
                Profile profilemain = Core.getInstance().getProfileManager().getProfile(scoreboard.getOwner());
                if (profilemain.getData().getRank().isLowerOrEqualsTo(Rank.DEFAULT)) {
                    scoreboard.get(PrisonScoreboardLine.RANK).write(color("  &7Rank: &dPlayer"));
                } else {
                    scoreboard.get(PrisonScoreboardLine.RANK).write(color("  &7Rank: &d") + Core.getInstance().getProfileManager().getProfile(scoreboard.getOwner()).getData().getRank().getName());
                }
                scoreboard.get(PrisonScoreboardLine.BALANCE).write(color("  &7Balance: &d") + Core.getInstance().getProfileManager().getProfile(scoreboard.getOwner()).getData().getPrisonMoney().getAmount());
                scoreboard.get(PrisonScoreboardLine.SHARDS).write(color("  &7Shards: &d") + Core.getInstance().getProfileManager().getProfile(scoreboard.getOwner()).getData().getPrisonShards().getAmount());
                scoreboard.get(PrisonScoreboardLine.GANG).write(color("&d&lGang:"));
                scoreboard.get(PrisonScoreboardLine.GANG_NAME).write(color("  &7Name: &dThisIsStatic"));
                for (Rank rank : Rank.values()) {
                    if (!rank.getName().isEmpty()) {
                        scoreboard.getHandle().getTeam(rank.name()).setPrefix(rank.getPrefix());
                    } else {
                        scoreboard.getHandle().getTeam(rank.name()).setPrefix("");
                    }
                }
            }

            @Override
            public void handlePlayerJoin(String playerName) {
                Player player = Bukkit.getPlayerExact(playerName);

                Profile profile = Core.getInstance().getProfileManager().getProfile(player);


                for (MainScoreboard scoreboard : getScoreboards().values()) {

                    scoreboard.getHandle().getTeam(profile.getData().getRank().name()).setPrefix(playerName);
                }

                if (get(player) != null) {

                    for (Player player1 : Bukkit.getOnlinePlayers()) {

                        Profile profile2 = Core.getInstance().getProfileManager().getProfile(player1);

                        get(player).getHandle().getTeam(profile2.getData().getRank().name()).setPrefix(player1.getName());
                    }
                }
            }

            @Override
            public void handlePlayerQuit(String playerName) {
                Player player = Bukkit.getPlayerExact(playerName);
                Profile profile = Core.getInstance().getProfileManager().getProfile(playerName);
                for (MainScoreboard scoreboard : getScoreboards().values()) {
                    scoreboard.getHandle().getTeam(profile.getData().getRank().name()).removeEntry(playerName);
                }
            }
        };
        Color.log("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }
    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
