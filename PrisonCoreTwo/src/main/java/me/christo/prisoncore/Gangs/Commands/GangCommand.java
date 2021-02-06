package me.christo.prisoncore.Gangs.Commands;


import me.christo.prisoncore.Gangs.Gangs;
import me.christo.prisoncore.PlayerDataConfig;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GangCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        PrisonPlayer player = new PrisonPlayer(p.getUniqueId());
        FileConfiguration config = Gangs.getFile();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                for (String s : Gangs.getFile().getStringList("Messages.helpMessage")) {
                    p.sendMessage(Util.color(s));
                }
            }
            if (args[0].equalsIgnoreCase("leave")) {
                player.leaveGang();

            }
            if (args[0].equalsIgnoreCase("disband")) {

                Bukkit.broadcastMessage(Util.color(config.getString("Messages.disbandMessage").replaceAll("%gang%", player.getGang())));

                player.disbandGang();
            }


        }


        //create command
        //gang create name


        if (args.length == 2) {

            if(args[0].equalsIgnoreCase("chat")) {


                if(args[1].equalsIgnoreCase("enable")) {
                    p.sendMessage(Util.color(config.getString("Messages.toggledGangChat").replaceAll("%action%", "enabled")));
                    player.setGangChat(true);
                } else if(args[1].equalsIgnoreCase("disable")) {
                    p.sendMessage(Util.color(config.getString("Messages.toggledGangChat").replaceAll("%action%", "disabled")));
                    player.setGangChat(false);
                }
            }

            if (args[0].equalsIgnoreCase("friendlyfire")) {
                if (player.isGangLeader()) {
                    player.enableFriendlyFire(Boolean.parseBoolean(args[1]));
                    Gangs.messageAllPlayers(player.getGang(), config.getString("Messages.friendlyFireUpdate").replaceAll("%option%", args[1]));
                }
            }

            if (args[0].equalsIgnoreCase("kick")) {
                Gangs.kickFromGang(p, Objects.requireNonNull(Bukkit.getPlayer(args[1])), player.getGang());
            }

            if (args[0].equalsIgnoreCase("makeleader")) {
                if (player.isGangLeader()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);

                        if (target == p) {
                            p.sendMessage(Util.color(config.getString("Messages.alreadyTheLeader")));
                            return true;
                        }

                        Gangs.makeLeader(p, target);

                        Gangs.messageAllPlayers(player.getGang(), config.getString("Messages.playerPromotedToLeader").replaceAll("%player%", target.getName()));

                    }
                }
            }


            if (args[0].equalsIgnoreCase("promote")) {
                if (player.isGangLeader()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        PrisonPlayer toPromote = new PrisonPlayer(target.getUniqueId());
                        if (toPromote.isGangMember()) {
                            toPromote.promoteMember(toPromote);

                            Gangs.messageAllPlayers(player.getGang(), config.getString("Messages.playerPromoted").replaceAll("%player%", toPromote.getName()));
                        } else if (toPromote.isGangAdmin()) {
                            player.sendMessage(Util.color(config.getString("Messages.mustMakeLeaderToPromote")).replaceAll("%player%", toPromote.getName()));
                        }

                    }

                }
            }

            if (args[0].equalsIgnoreCase("demote")) {
                if (player.isGangLeader()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        PrisonPlayer toDemote = new PrisonPlayer(target.getUniqueId());
                        if (toDemote.isGangMember()) {
                            player.sendMessage(Util.color(config.getString("Messages.playerLowestRank").replaceAll("%player%", toDemote.getName())));
                            return true;
                        }
                        if (toDemote.isGangAdmin() && !toDemote.isGangLeader()) {
                            toDemote.demoteMember(toDemote);
                            Gangs.messageAllPlayers(player.getGang(), config.getString("Messages.playerDemoted").replaceAll("%player%", toDemote.getName()));
                        }
                    }
                }

            }


            if (args[0].equalsIgnoreCase("join")) {
                if (Gangs.inviteHash.containsKey(p)) {
                    if (args[1].equalsIgnoreCase(Gangs.inviteHash.get(p))) {
                        Gangs.addMember(Gangs.inviteHash.get(p), p);
                        player.setGang(true);
                        player.setGangName(Gangs.inviteHash.get(p));

                        p.sendMessage(Util.color(config.getString("Messages.joinedGang")).replaceAll("%gang%", Gangs.inviteHash.get(p)));
                        Gangs.inviteHash.remove(p);

                        Gangs.messageAllPlayers(player.getGang(), config.getString("Messages.playerJoined").replaceAll("%player%", player.getName()));

                    }
                }
            }

            if (args[0].equalsIgnoreCase("invite")) {
                if (player.hasGang()) {
                    if (player.isGangAdmin() || player.isGangLeader()) {

                        if (Bukkit.getPlayer(args[1]) != null) {
                            Player target = Bukkit.getPlayer(args[1]);
                            PrisonPlayer toInvite = new PrisonPlayer(target.getUniqueId());
                            if (!toInvite.hasGang()) {
                                p.sendMessage(Util.color(config.getString("Messages.invitedPlayer")).replaceAll("%player%", target.getName()));
                                target.sendMessage(Util.color(config.getString("Messages.invitation")).replaceAll("%gang%", player.getGang()).replaceAll("%player%", p.getName()));
                                Gangs.inviteHash.put(target, player.getGang());
                            } else {
                                p.sendMessage(Util.color(config.getString("Messages.playerInGang")));
                                return true;
                            }
                        }
                    } else {
                        p.sendMessage("t");
                    }
                } else {
                    p.sendMessage(Util.color(config.getString("Messages.notPartOfGang")));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (player.hasGang()) {
                    p.sendMessage(Util.color(config.getString("Messages.alreadyInAGang")));
                    return true;
                } else {
                    if (Gangs.checkIfExists(args[1])) {
                        p.sendMessage(Util.color(config.getString("Messages.nameTaken")));
                        return true;
                    }
                    //example
                    //   createdGang: "&c&lGangs > &c%player% &7has created a gang called %name%"
                    p.sendMessage(Util.color(config.getString("Messages.createdGang")).replaceAll("%name%", args[1]).replaceAll("%player%", p.getName()));
                    config.set("Gangs." + args[1] + ".leader", p.getName());
                    player.setGang(true);
                    player.setGangName(args[1]);
                    Gangs.save();


                }
            }

        }

        return false;
    }


}
