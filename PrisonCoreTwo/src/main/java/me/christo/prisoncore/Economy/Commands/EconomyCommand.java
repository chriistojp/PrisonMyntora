package me.christo.prisoncore.Economy.Commands;


import me.christo.prisoncore.Economy.Economy;
import me.christo.prisoncore.Main;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EconomyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (args[2].chars().allMatch(Character::isDigit)) {
                            new PrisonPlayer(Bukkit.getPlayer(args[1])).addTokens(Long.valueOf(args[2]));
                        }
                    }
                }
            }
            return true;
        }
        Player player = (Player) sender;
        PrisonPlayer tokenSender = new PrisonPlayer(player);
        // Admin Commands
        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("reset")) {
                PrisonPlayer p = new PrisonPlayer(Bukkit.getPlayer(args[1]).getUniqueId());
                p.resetTokens();
                player.sendMessage(msg("moneyReset").replace("[player]", p.getName()));
            }

        }
        if (args.length == 3) {
            PrisonPlayer p = new PrisonPlayer(Bukkit.getPlayer(args[1]).getUniqueId());
            if (player.hasPermission("prison.admin")) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args[2].chars().allMatch(Character::isDigit)) {
                        p.setTokens(Long.parseLong(args[2]));
                        player.sendMessage(
                                msg("setPlayerMoney").replace("[player]", p.getName()).replace("[amount]", args[2]));
                    }
                }

                if (args[0].equalsIgnoreCase("give")) {
                    if (args[2].chars().allMatch(Character::isDigit)) {
                        player.sendMessage(
                                msg("givePlayerMoney").replace("[player]", p.getName()).replace("[amount]", args[2]));
                        p.addTokens(Long.parseLong(args[2]));
                    }
                }
                if (args[0].equalsIgnoreCase("take")) {
                    if (args[2].chars().allMatch(Character::isDigit)) {
                        if (Long.parseLong(args[2]) > p.getTokens()) {
                            player.sendMessage(msg("insufficientMoney"));
                            return true;
                        }
                        player.sendMessage(
                                msg("takePlayerMoney").replace("[player]", p.getName()).replace("[amount]", args[2]));
                        p.takeTokens(Long.parseLong(args[2]));
                    }
                }
            }
        }

        // Player Commands
        if (args.length == 0) {
            PrisonPlayer p = new PrisonPlayer(player);
            for(String s : Economy.getFile().getStringList("messages.help")) {
                p.sendMessage(Util.color(s));
            }
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                for (String s : Main.getInstance().getConfig().getStringList("messages.help")) {
                    player.sendMessage(Util.color(s));
                }
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                PrisonPlayer p = new PrisonPlayer(Bukkit.getPlayer(args[0]).getUniqueId());
                player.sendMessage(
                        msg("balanceOthers").replace("[player]", p.getName()).replace("[amount]", p.getTokens() + ""));
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("pay")) {
                PrisonPlayer payFrom = new PrisonPlayer(player);
                PrisonPlayer payTo = new PrisonPlayer(Bukkit.getPlayer(args[1]).getUniqueId());
                if (args[2].chars().allMatch(Character::isDigit)) {
                    long amount = Long.parseLong(args[2]);
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


