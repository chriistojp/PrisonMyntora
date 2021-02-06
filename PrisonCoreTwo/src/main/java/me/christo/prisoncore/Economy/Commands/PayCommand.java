package me.christo.prisoncore.Economy.Commands;


import me.christo.prisoncore.Economy.Economy;
import me.christo.prisoncore.Main;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        System.out.println(1);
        Player player = (Player) sender;

        if (args.length == 2) {
                PrisonPlayer payFrom = new PrisonPlayer(player.getUniqueId());
                PrisonPlayer payTo = new PrisonPlayer(Bukkit.getPlayer(args[0]).getUniqueId());
                if (args[1].chars().allMatch(Character::isDigit)) {
                    long amount = Long.parseLong(args[1]);
                    if (payFrom.getTokens() < amount) {
                        payFrom.sendMessage(msg("insufficientBalance"));
                        return true;
                    }

                    payFrom.sendMessage(
                            msg("payedMoney").replace("[amount]", amount + "").replace("[player]", payTo.getName()));
                    payTo.sendMessage(msg("receivedMoney").replace("[amount]", amount + "").replace("[player]",
                            payFrom.getName()));
                    payFrom.takeTokens(amount);
                    payTo.addTokens(amount);

                }
            }

        return false;
    }

    public String msg(String path) {

        String prefix = Economy.getFile().getString("prefix");

        if (Economy.getFile().getBoolean("enabled")) {
            return Util.color(prefix + Economy.getFile().getString("messages." + path));
        }

        return Util.color(Economy.getFile().getString("messages." + path));

    }
}

