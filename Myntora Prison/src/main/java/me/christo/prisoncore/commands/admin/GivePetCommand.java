package me.christo.prisoncore.commands.admin;


import lombok.SneakyThrows;
import me.christo.prisoncore.managers.NPCS;
import me.christo.prisoncore.managers.Pets;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.data.mysql.player.types.Rank;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "givepet"
)
public class GivePetCommand extends Command {

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);


        if (profile.getData().getRank().isHigherOrEqualsTo(p, Rank.ADMIN, true)) {

            if(args.length == 0) {
                p.sendMessage(Color.prison("Usage", "/givepet (player)"));
            }

            if (args.length == 1) {

                if (Bukkit.getPlayer(args[0]) != null) {

                    Pets.giveItemStack(Bukkit.getPlayer(args[0]));
                    p.sendMessage(Color.prison("Pets", "You gave a pet to &d" + Bukkit.getPlayer(args[0]).getName()));

                    NPCS.spawnMinesNPC(p);

                }
            }


        }
    }
}
