package me.christo.prisoncore.Economy.Commands;


import me.christo.prisoncore.Economy.Economy;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            PrisonPlayer p = new PrisonPlayer(player.getUniqueId());
            System.out.println(p.getTokens());
            player.sendMessage(msg("moneyBalance").replace("[amount]", Util.replaceNumbers(p.getTokens())));
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


