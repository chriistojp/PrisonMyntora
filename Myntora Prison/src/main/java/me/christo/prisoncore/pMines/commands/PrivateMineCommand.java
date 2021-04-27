package me.christo.prisoncore.pMines.commands;

import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.pMines.guis.MinesGui;
import me.christo.prisoncore.utils.Util;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DynamicCommand(
        name = "pmine"
)

public class PrivateMineCommand extends net.myntora.core.core.command.Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (args.length == 0) {
            MinesGui.showMembers(p);
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("invite")) {

                if (Bukkit.getPlayer(args[1]) != null) {
                    Player target = Bukkit.getPlayer(args[1]);
                    PMine.inviteHash.put(p, target);

                    p.sendMessage(Color.prison("Mines", "You invited " + target.getName() + " to your mine!"));
                    target.sendMessage(Color.prison("Mines", "You have recieved a mine invite from &d" + p.getName()));

                }

            }
            if(args[0].equalsIgnoreCase("leave")) {
                String toLeave = args[1];
                if(PMine.getFile().getStringList("mines." + Bukkit.getPlayer(toLeave).getUniqueId() + ".members").contains(p.getName())) {
                    List<String> members = PMine.getFile().getStringList("mines." + Bukkit.getPlayer(toLeave).getUniqueId() + ".members");
                    members.remove(p.getName());
                    PMine.getFile().set("mines." + Bukkit.getPlayer(toLeave).getUniqueId() + ".members", members);

                    List<String> membersOf = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".memberOf");
                    membersOf.remove(toLeave);
                    PMine.getFile().set("mines." + p.getUniqueId() + ".memberOf", membersOf);

                }
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (PMine.inviteHash.containsKey(Bukkit.getPlayer(args[1]))) {

                    Player inviter = Bukkit.getPlayer(args[1]);
                    if (PMine.inviteHash.get(inviter) == p) {

                        if (PMine.getFile().isSet("mines." + p.getUniqueId() + ".memberOf")) {
                            List<String> list = new ArrayList<>();
                            list.add(inviter.getName());
                            for (String s : PMine.getFile().getStringList("mines." + p.getUniqueId() + ".memberOf")) {
                                list.add(s);
                            }
                            PMine.getFile().set("mines." + p.getUniqueId() + ".memberOf", list);
                            PMine.save();
                        } else {
                            List<String> list = new ArrayList<>();
                            list.add(inviter.getName());
                            PMine.getFile().set("mines." + p.getUniqueId() + ".memberOf", list);
                            PMine.save();
                        }

                        if (PMine.getFile().isSet("mines." + inviter.getUniqueId() + ".members")) {
                            List<String> list = new ArrayList<>();
                            list.add(p.getName());
                            for (String s : PMine.getFile().getStringList("mines." + inviter.getUniqueId() + ".members")) {
                                list.add(s);
                            }
                            PMine.getFile().set("mines." + inviter.getUniqueId() + ".members", list);
                            PMine.save();

                            p.sendMessage(Color.prison("Mines", "You have joined " + inviter.getName() + "'s mine!"));
                            inviter.sendMessage(Color.prison("Mines", p.getName() + " has joined your mine!"));

                        } else {
                            List<String> list = new ArrayList<>();
                            list.add(p.getName());
                            PMine.getFile().set("mines." + inviter.getUniqueId() + ".members", list);
                            PMine.save();


                            p.sendMessage(Color.prison("Mines", "You have joined " + inviter.getName() + "'s mine!"));
                            inviter.sendMessage(Color.prison("Mines", p.getName() + " has joined your mine!"));

                        }

                        PMine.inviteHash.remove(inviter);

                    }

                }

            }
        }
    }
}
