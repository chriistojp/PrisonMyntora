package me.christo.prisoncore.commands;


import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "pay"
)

public class PayCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        if (sender instanceof Player) {

            if (args.length == 0) {
                sender.sendMessage(Color.prison("Usage", "/pay (player) [amount)"));
            }

            if (args.length == 2) {
                if (args[0].chars().allMatch(Character::isDigit)) {
                    if (Bukkit.getPlayer(args[1]) != null) {

                        Profile profile = Core.getInstance().getProfileManager().getProfile((Player) sender);
                        Profile toPay = Core.getInstance().getProfileManager().getProfile(Bukkit.getPlayer(args[1]));

                        int amount = Integer.parseInt(args[0]);

                        if (profile.getData().getPrisonMoney().getAmount() >= amount) {

                            profile.getData().getPrisonMoney().decreaseAmount(amount);
                            toPay.getData().getPrisonMoney().increaseAmount(amount);

                            profile.getData().save();
                            toPay.getData().save();

                            sender.sendMessage(Color.prison("Money", "You paid &d" + amount + "&7 to " + Bukkit.getPlayer(args[1]).getName()));
                            Bukkit.getPlayer(args[1]).sendMessage(Color.prison("Money", "You recieved &d" + amount + "&7 from " + sender.getName()));


                        } else {
                            sender.sendMessage(Color.prison("Money", "You cannot afford that!"));
                        }
                    }
                }
            }
        }


    }
}
