package me.christo.prisoncore.commands;


import com.sk89q.worldguard.bukkit.RegionContainer;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.managers.Cells;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import net.myntora.core.core.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

@DynamicCommand(
        name = "cell"
)

public class CellCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        String[] blocks = {"A", "B", "C", "D"};

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {

                Profile prf = Core.getInstance().getProfileManager().getProfile(p);
                p.sendMessage(String.valueOf(prf.getData().getPrisonCellStatus().getStatus()));

                p.sendMessage(Util.color("&cCells Help -"));
                p.sendMessage(Util.color("&7- /cells help"));
                p.sendMessage(Util.color("&7- /cells tool"));
                p.sendMessage(Util.color("&7- /cells create (block) (name)"));
                p.sendMessage(Util.color("&7- /cells selector"));
            }
            if (args[0].equalsIgnoreCase("tool")) {

                ItemBuilder i = new ItemBuilder(Material.STICK)
                        .setName("&a&lCell Selector")
                        .addLoreLine("")
                        .addLoreLine("&7Right and left click to select positions!")
                        .addLoreLine("")
                        .addLoreLine("&7When done, do /cell create (block) (name)");

                p.getInventory().addItem(i.toItemStack());
            }
            if (args[0].equalsIgnoreCase("setsign")) {


                int x =  p.getTargetBlock((Set<Material>) null, 5).getX();
                int y =  p.getTargetBlock((Set<Material>) null, 5).getY();
                int z = p.getTargetBlock((Set<Material>) null, 5).getZ();

                Sign sign = (Sign) p.getTargetBlock((Set<Material>) null, 5).getState();
                sign.setLine(0, Util.color("&7Cell: " + Cells.getCellName.get(p)));
                sign.setLine(1, Util.color("&cBlock: " + Cells.blockName.get(p)));
                sign.setLine(2, Util.color("&7Rent: &a$100/wk"));
                sign.setLine(3, Util.color("&7Click to Claim!"));
                sign.update();

                p.sendMessage(Color.prison("Cells", "Cell created!"));

                Cells.getFile().set("cells." + Cells.blockName.get(p) + "." + Cells.getCellName.get(p) + ".signLocation.x", x);
                Cells.getFile().set("cells." + Cells.blockName.get(p) + "." + Cells.getCellName.get(p) + ".signLocation.y", y);
                Cells.getFile().set("cells." + Cells.blockName.get(p) + "." + Cells.getCellName.get(p) + ".signLocation.z", z);

                Cells.save();

            }
            if(args[0].equalsIgnoreCase("disband")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);
                if(!profile.getData().getPrisonCellName().getCell().equals("")) {
                    Cells.tryToDisband(p);
                } else {
                    p.sendMessage(Color.prison("Cells", "&7You don't currently have a cell!"));
                }
            }

            //SHOULD NOT BE IN PRODUCTION. IF FOUND IN PRODUCTION VERSION REMOVE IMMEDIATELY.
            if(args[0].equals("reset")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);

                profile.getData().getPrisonCellName().setCell("");
                profile.getData().getPrisonCellStatus().setStatus(false);

                profile.getData().save();

                p.sendMessage(Color.prison("Cells", "Reset it!"));
            }

            if(args[0].equalsIgnoreCase("accept")) {
                if(Cells.inviteHashmap.get(p) != null) {

                    Player originallySent = Cells.inviteHashmap.get(p);

                    p.sendMessage(Color.prison("Cells", "You have joined " + originallySent.getName() + "'s cell!"));
                    originallySent.sendMessage(Color.prison("Cells", p.getName() + " has joined your cell!"));

                    Bukkit.broadcastMessage("rg addmember " + originallySent.getName() + "-cell " + p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addmember " + originallySent.getName() + "-cell " + p.getName() + " -w prison_world");

                    Cells.inviteHashmap.remove(p);

                } else {
                    p.sendMessage(Color.prison("Cells", ""));
                }
            }
        }

        if(args.length == 2) {
            if (args[0].equalsIgnoreCase("invite")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);
                if (profile.getData().getPrisonCellStatus().getStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {

                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        p.sendMessage(Color.prison("Cells", "You invited " + targetPlayer.getName() + " to your cell!"));
                        targetPlayer.sendMessage(Color.prison("Cells", "You have been invited to " + p.getName() + "'s cell!"));
                        Cells.inviteHashmap.put(targetPlayer, p);

                    }

                } else {
                    p.sendMessage(Color.prison("Cell", "You don't currently have a cell!"));
                }
            }
            if (args[0].equalsIgnoreCase("remove")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);
                if (profile.getData().getPrisonCellStatus().getStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {

                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        p.sendMessage(Color.prison("Cells", "You invited " + targetPlayer.getName() + " to your cell!"));
                        targetPlayer.sendMessage(Color.prison("Cells", "You have been invited to " + p.getName() + "'s cell!"));
                        Cells.inviteHashmap.put(targetPlayer, p);

                    }

                } else {
                    p.sendMessage(Color.prison("Cell", "You don't currently have a cell!"));
                }
            }
        }

        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("create")) {
                Cells.enableSignStatus(p);
                for (String s : blocks) {
                    if (args[1].equalsIgnoreCase(s)) {
                        if (Cells.getLocation(p, 1) != null && Cells.getLocation(p, 2) != null) {

                            String cellName = args[2];

                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".locationOne.x", Cells.getLocation(p, 1).getX());
                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".locationOne.y", Cells.getLocation(p, 1).getY());
                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".locationOne.z", Cells.getLocation(p, 1).getZ());

                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".locationTwo.x", Cells.getLocation(p, 2).getX());
                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".locationTwo.y", Cells.getLocation(p, 2).getY());
                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".locationTwo.z", Cells.getLocation(p, 2).getZ());

                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".name", args[2]);
                            Cells.getFile().set("cells." + args[1] + "." + args[2] + ".block", args[1]);
                            Cells.save();


                            Cells.getCellName.put(p, cellName);
                            Cells.blockName.put(p, args[1]);

                            System.out.println(Cells.getCellName(p));


                            p.sendMessage(Util.color("&c&lCells > &7Please click the sign you'd like to assign to this cell!"));


                        } else {
                            p.sendMessage(Util.color("&c&lCells > &7Please make your selection first!"));
                        }
                    }
                }
            }

        }

    }
}
