package me.christo.prisoncore.pMines.commands;

import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.pMines.guis.MainGUI;
import me.christo.prisoncore.utils.Util;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

@DynamicCommand(
        name = "pmine"
)

public class PrivateMineCommand extends net.myntora.core.core.command.Command {
    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);


        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("leave")) {

//                if (Util.players.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId()) == null) {
//                    p.sendMessage(Util.color(PMine.getFile().getString("Messages.leaveFail")));
//                    return true;
//                }
                // gets the player and the mine they are leaving
                List<String> whitelistedTo = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelistedTo");
                // gets the player that owns the mine they are leaving
                List<String> whitelisted = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelisted");

                if (!whitelistedTo.contains(args[1])) {
                    p.sendMessage(Util.color(PMine.getFile().getString("Messages.leaveFail")));
                    return;
                }

                whitelistedTo.remove(Bukkit.getOfflinePlayer(args[1]).getName());
                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelistedTo", whitelistedTo);
                PMine.save();

                whitelisted.remove(p.getName());
                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelisted", whitelisted);
                PMine.save();

                p.teleport(new Location(Bukkit.getWorld("prison_world"), profile.getData().getPrisonMineNumber().getAmount() * 100, 85,
                        profile.getData().getPrisonMineNumber().getAmount() * 100));
                p.sendMessage(Util.color(PMine.getFile().getString("Messages.leftMine").replace("[player]",
                        Bukkit.getOfflinePlayer(args[1]).getName())));

                if (Bukkit.getPlayer(args[1]) != null) {
                    Bukkit.getPlayer(args[1]).sendMessage(Util.color(
                            PMine.getFile().getString("Messages.leftMineOwner").replace("[player]", p.getName())));
                }

            }
            if (args[0].equalsIgnoreCase("remove")) {

//                //              whitelistedTo.remove(Bukkit.getOfflinePlayer(args[1]).getName());
//                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelistedTo", whitelistedTo);
//                PMine.save();
                List<String> whitelisted = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelisted");
                if (!whitelisted.contains(args[1])) {
                    p.sendMessage(Util.color(PMine.getFile().getString("Messages.removedFail")));
                    return ;
                }
                List<String> whitelistedTo = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelistedTo");

                whitelistedTo.remove(Bukkit.getOfflinePlayer(args[1]).getName());
                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelistedTo", whitelistedTo);
                PMine.save();

                whitelisted.remove(p.getName());
                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelisted", whitelisted);
                PMine.save();

                p.sendMessage(Util.color(PMine.getFile().getString("Messages.removedPlayerOwner").replace("[player]",
                        Bukkit.getOfflinePlayer(args[1]).getName())));

                if (Bukkit.getPlayer(args[1]) != null) {
                    Profile target = Core.getInstance().getProfileManager().getProfile(Bukkit.getPlayer(args[1]));
                    Bukkit.getPlayer(args[1]).teleport(new Location(Bukkit.getWorld("prison_world"), target.getData().getPrisonMineNumber().getAmount() * 100, 85,
                            target.getData().getPrisonMineNumber().getAmount() * 100));
                    Bukkit.getPlayer(args[1]).sendMessage(Util.color(
                            PMine.getFile().getString("Messages.removedPlayer").replace("[player]", p.getName())));
                }

            }
            if (args[0].equalsIgnoreCase("accept")) {

                if (!PMine.inviteHash.containsKey(p)) {
                    p.sendMessage(Util.color(PMine.getFile().getString("Messages.noInvites")));
                    return ;
                }


                List<String> whitelisted = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelisted");
                List<String> whitelistedTo = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelistedTo");

                whitelistedTo.add(Bukkit.getOfflinePlayer(args[1]).getName());
                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelistedTo", whitelistedTo);
                PMine.save();

                whitelisted.add(p.getName());
                PMine.getFile().set("mines." + p.getUniqueId() + ".whitelisted", whitelisted);
                PMine.save();

                p.sendMessage(Util.color(PMine.getFile().getString("Messages.joinedMessage").replace("[player]",
                        PMine.inviteHash.get(p).getName())));
                p.teleport(new Location(Bukkit.getWorld("prison_world"), profile.getData().getPrisonMineNumber().getAmount() * 100, 85,
                        profile.getData().getPrisonMineNumber().getAmount() * 100));

                PMine.inviteHash.remove(p);

            }
            if (args[0].equalsIgnoreCase("invite")) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    Player target = Bukkit.getPlayer(args[1]);
                    List<String> whitelisted = PMine.getFile().getStringList("mines." + p.getUniqueId() + ".whitelisted");

                    if (whitelisted.contains(target.getName())) {
                        p.sendMessage(Util.color(PMine.getFile().getString("Messages.alreadyMember").replace("[player]",
                                target.getName())));
                        return ;
                    }

                    p.sendMessage(Util.color(PMine.getFile().getString("Messages.invitedMessage")).replace("[player]",
                            target.getName()));
                    PMine.inviteHash.put(target, p);

                    IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Util.color(Util
                            .color(PMine.getFile().getString("Messages.inviteMessage").replace("[player]", p.getName()))
                            + "\",\"extra\":[{\"text\":\""
                            + Util.color(PMine.getFile().getString("Messages.clickMessage"))
                            + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + "/pmine accept "
                            + p.getName() + "\"}}]}"));

                    PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 0);
                    ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);
                }
            }
            return ;
        }
        if (args.length > 0) {
            for (String msg : PMine.getFile().getStringList("help")) {
                sender.sendMessage(Util.color(msg));
            }
            return;
        }
        MainGUI.main().show(p);
        return ;
    }

}
