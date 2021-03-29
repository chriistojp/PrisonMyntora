package me.christo.prisoncore.commands;


import me.christo.prisoncore.managers.Gangs;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DynamicCommand(
        name = "gang",
        aliases = {"gangs"}
)
public class GangCommand extends net.myntora.core.core.command.Command {
    @Override
    public void execute(CommandSender sender, String... args) {


        Player p = (Player) sender;
        Profile player = Core.getInstance().getProfileManager().getProfile(p);
        FileConfiguration config = Gangs.getFile();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("info")) {

                String gangName = player.getData().getPrisonGangName().getCell();
                p.sendMessage(Util.color("&7&m-------&r &d[Gang Info] &7&m-------"));
                p.sendMessage(Util.color("&7- Name: &d" + gangName));

                String members = String.join(", ", Gangs.getFile().getStringList("Gangs." + gangName + ".members"));
                p.sendMessage(Util.color("&7- Members: &d" + members));

                int totalBlocks = 0;
                for (String blocks : Gangs.getFile().getStringList("Gangs." + gangName + ".members")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(blocks);
                    Profile profile = Core.getInstance().getProfileManager().getProfile(offlinePlayer);
                    totalBlocks = totalBlocks + profile.getData().getPrisonBlocksMined().getAmount();
                }
                p.sendMessage(Util.color("&7- Blocks Mined: &d" + totalBlocks + ""));

                int totalMoney = 0;
                for (String blocks : Gangs.getFile().getStringList("Gangs." + gangName + ".members")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(blocks);
                    Profile profile = Core.getInstance().getProfileManager().getProfile(offlinePlayer);
                    totalMoney = totalMoney + profile.getData().getPrisonMoney().getAmount();
                }
                p.sendMessage(Util.color("&7- Total Balance: &d$" + totalMoney + ""));

            }
            if (args[0].equalsIgnoreCase("leave")) {
                if (!player.getData().getPrisonGangName().getCell().equals("none")) {

                    List<String> members = Gangs.getFile().getStringList("Gangs." + player.getData().getPrisonGangName().getCell() + ".members");
                    members.remove(p.getName());
                    Gangs.getFile().set("Gangs." + player.getData().getPrisonGangName().getCell() + ".members", members);
                    Gangs.save();

                    player.getData().getPrisonGangName().setCell("none");
                    player.getData().getIsGangMember().setStatus(false);
                    player.getData().getIsGangAdmin().setStatus(false);

                    player.getData().save();


                    p.sendMessage(Color.prison("Gangs", "You have left your gang!"));
                } else {
                    p.sendMessage(Color.prison("Gangs", "You are not in a gang!"));
                }
            }
            if (args[0].equalsIgnoreCase("disband")) {

                if (player.getData().getIsGangOwner().getStatus()) {

                    Bukkit.broadcastMessage(Color.prison("Gangs", "The gang &d" + player.getData().getPrisonGangName().getCell() + "&7 has been disbanded!"));
                    Gangs.getFile().set("Gangs." + player.getData().getPrisonGangName().getCell(), null);

                    player.getData().getPrisonGangName().setCell("none");
                    player.getData().getIsGangOwner().setStatus(false);


                    //loop through members, make profile, set gangs.
                    //  player.disbandGang();
                } else {
                    p.sendMessage(Color.prison("Gangs", "You are not the gang owner!"));
                }
            }


        }


        //create command
        //gang create name


        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("chat")) {


                if (args[1].equalsIgnoreCase("enable")) {
                    p.sendMessage(Color.prison("Gangs", "Gang chat enabled!"));
                    Gangs.gangChat.add(p);
                    //     player.setGangChat(true);
                } else if (args[1].equalsIgnoreCase("disable")) {
                    if (Gangs.gangChat.contains(p)) {
                        p.sendMessage(Color.prison("Gangs", "Gang chat disabled!"));
                        //    player.setGangChat(false);
                    } else {
                        p.sendMessage(Color.prison("Gangs", "You don't have gang chat enabled!"));
                    }
                }
            }

            if (args[0].equalsIgnoreCase("friendlyfire")) {
                if (player.getData().getIsGangOwner().getStatus()) {
                    //   player.enableFriendlyFire(Boolean.parseBoolean(args[1]));
                    Gangs.messageAllPlayers(player.getData().getPrisonGangName().getCell(), config.getString("Messages.friendlyFireUpdate").replaceAll("%option%", args[1]));
                }
            }

            if (args[0].equalsIgnoreCase("kick")) {
                Gangs.kickFromGang(p, Objects.requireNonNull(Bukkit.getPlayer(args[1])), player.getData().getPrisonGangName().getCell());
            }

            if (args[0].equalsIgnoreCase("makeleader")) {
                if (player.getData().getIsGangOwner().getStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);

                        if (target == p) {
                            p.sendMessage(Color.prison("Gangs", "That player is already the leader!"));
                            return;
                        }

                        Gangs.makeLeader(p, target);

                        Gangs.messageAllPlayers(player.getData().getPrisonGangName().getCell(), Util.color(target.getName() + " has been promoted to &dGang Leader!"));

                    }
                }
            }


            if (args[0].equalsIgnoreCase("promote")) {
                if (player.getData().getIsGangOwner().getStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        Profile toPromote = Core.getInstance().getProfileManager().getProfile(target);
                        if (toPromote.getData().getIsGangMember().getStatus()) {
                            toPromote.getData().getIsGangMember().setStatus(false);
                            toPromote.getData().getIsGangAdmin().setStatus(true);

                            Gangs.messageAllPlayers(player.getData().getPrisonGangName().getCell(), Util.color("&d" + target.getName() + " &7has been promoted to &dGang Mentor!"));
                        } else if (toPromote.getData().getIsGangAdmin().getStatus()) {
                            p.sendMessage(Color.prison("Gangs", "&d" + target.getName() + " &&is already a gang admin. Run /gang makeleader " + target.getName() + " to make them the leader."));
                            p.sendMessage(Util.color(config.getString("Messages.mustMakeLeaderToPromote")).replaceAll("%player%", target.getName()));
                        }

                    }

                }
            }

            if (args[0].equalsIgnoreCase("demote")) {
                if (player.getData().getIsGangOwner().getStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        Profile toDemote = Core.getInstance().getProfileManager().getProfile(target);
                        if (toDemote.getData().getIsGangMember().getStatus()) {
                            p.sendMessage(Color.prison("Gangs", "That player is already the lowest rank!"));
                            return;
                        }
                        if (toDemote.getData().getIsGangAdmin().getStatus() && !toDemote.getData().getIsGangOwner().getStatus()) {

                            toDemote.getData().getIsGangAdmin().setStatus(false);
                            toDemote.getData().getIsGangMember().setStatus(true);

                            Gangs.messageAllPlayers(player.getData().getPrisonGangName().getCell(), Util.color("&d" + target.getName() + " &7has been demoted to &dGang Member!"));
                        }
                    }
                }

            }


            if (args[0].equalsIgnoreCase("join")) {
                if (Gangs.inviteHash.containsKey(p)) {
                    if (args[1].equalsIgnoreCase(Gangs.inviteHash.get(p))) {
                        if (!player.getData().getPrisonGangName().getCell().equalsIgnoreCase("none")) {
                            Gangs.addMember(Gangs.inviteHash.get(p), p);
                            player.getData().getIsGangMember().setStatus(true);
                            player.getData().getPrisonGangName().setCell(Gangs.inviteHash.get(p));
                            player.getData().save();

                            p.sendMessage(Color.prison("Gangs", "&7You joined the gang!"));
                            Gangs.inviteHash.remove(p);

                            Gangs.messageAllPlayers(player.getData().getPrisonGangName().getCell(), "&d" + p.getName() + "&7 has joined the gang!");
                        }

                    }
                }
            }

            if (args[0].equalsIgnoreCase("invite")) {
                if (!player.getData().getPrisonGangName().getCell().equals("none")) {
                    if (player.getData().getIsGangAdmin().getStatus() || player.getData().getIsGangOwner().getStatus()) {

                        if (Bukkit.getPlayer(args[1]) != null) {
                            Player target = Bukkit.getPlayer(args[1]);
                            Profile toInvite = Core.getInstance().getProfileManager().getProfile(target);
                            if (toInvite.getData().getPrisonGangName().getCell().equals("none")) {
                                p.sendMessage(Color.prison("Gangs", "You invited &d" + target.getName() + "&7 to join your gang!"));
                                target.sendMessage(Color.prison("Gangs", "You have been invited to join &d" + player.getData().getPrisonGangName().getCell() + "&7 by &d" + p.getName() + "!"));
                                Gangs.inviteHash.put(target, player.getData().getPrisonGangName().getCell());
                            } else {
                                p.sendMessage(Color.prison("Gangs", "That player is already in a gang!"));
                                return;
                            }
                        }
                    } else {
                        p.sendMessage(Color.prison("Gangs", "You are lacking the permission to do that!"));
                    }
                } else {
                    p.sendMessage(Color.prison("Gangs", "That player is already in a gang!"));
                    return;
                }
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (player.getData().getPrisonGangName().getCell() != null) {
                        p.sendMessage(Color.prison("Gangs", "You are already in a gang"));
                        return;
                } else {
                    if (Gangs.checkIfExists(args[1])) {
                        p.sendMessage(Color.prison("Gangs", "That gang name is already taken!"));
                        return;
                    }
                    //example
                    //   createdGang: "&d&lGangs > &d%player% &7has created a gang called %name%"
                    p.sendMessage(Color.prison("Gangs", "You created a gang named &d" + args[1] + "!"));
                    // p.sendMessage(Util.color(config.getString("Messages.createdGang")).replaceAll("%name%", args[1]).replaceAll("%player%", p.getName()));
                    List<String> members = new ArrayList<>();
                    members.add(p.getName());
                    config.set("Gangs." + args[1] + ".members", members);
                    player.getData().getIsGangOwner().setStatus(true);
                    player.getData().getPrisonGangName().setCell(args[1]);
                    player.getData().save();
                    Gangs.save();


                }
            }

        }

    }


}