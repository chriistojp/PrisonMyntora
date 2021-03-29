package me.christo.prisoncore.commands;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.managers.Mines;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mongo.player.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@DynamicCommand(
        name = "startevent"
)
public class StartEventCommand extends Command {

    private int countdown = 3;

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (profile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("gold")) {
                    Mines.fillEventsMine(Material.GOLD_BLOCK);
                    p.sendMessage(Color.prison("Events", "Filled the events mine with gold blocks!"));
                    sendMessage("Gold Block");
                }
                if (args[0].equalsIgnoreCase("diamond")) {
                    Mines.fillEventsMine(Material.DIAMOND_BLOCK);
                    p.sendMessage(Color.prison("Events", "Filled the events mine with gold blocks!"));
                    sendMessage("Diamond Block");
                }
                if (args[0].equalsIgnoreCase("ores")) {
                    Mines.fillEventsMineOres();
                    p.sendMessage(Color.prison("Events", "Filled with an assortment of ores!"));
                    sendMessage("Ores");
                }
            }

        }


    }

    public void sendMessage(String event) {


        BukkitTask r = new BukkitRunnable() {
            @Override
            public void run() {

                if (countdown == 0) {
                    Bukkit.broadcastMessage(Color.prison("Events", "The &d" + event + " &7event &7has started!"));
                    countdown = 4;
                    this.cancel();
                } else {
                    Bukkit.broadcastMessage(Color.prison("Events", "The &d" + event + " &7event &7is starting in &d" + countdown + " &7seconds!"));
                }


                countdown--;

            }
        }.runTaskTimer(Prison.getInstance(), 20, 20);
    }

}
