package me.christo.prisoncore.commands;


import me.christo.prisoncore.managers.FarmCells;
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
        name = "farmcells"
)

public class FarmCellCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        String[] blocks = {"Farming"};

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {

                Profile prf = Core.getInstance().getProfileManager().getProfile(p);
                p.sendMessage(String.valueOf(prf.getData().getPrisonCellStatus().getStatus()));

                p.sendMessage(Util.color("&cFarm FarmCells.Help -"));
                p.sendMessage(Util.color("&7- /farmcells help"));
                p.sendMessage(Util.color("&7- /farmcells tool"));
                p.sendMessage(Util.color("&7- /farmcells create (name)"));
                p.sendMessage(Util.color("&7- /farmcellscells selector"));
            }
            if (args[0].equalsIgnoreCase("tool")) {

                ItemBuilder i = new ItemBuilder(Material.STICK)
                        .setName("&a&lFARM SELECTOR")
                        .addLoreLine("")
                        .addLoreLine("&7Right and left click to select positions!")
                        .addLoreLine("")
                        .addLoreLine("&7When done, do /farmcells create (name)");

                p.getInventory().addItem(i.toItemStack());
            }
            if (args[0].equalsIgnoreCase("setsign")) {


                int x = p.getTargetBlock((Set<Material>) null, 5).getX();
                int y = p.getTargetBlock((Set<Material>) null, 5).getY();
                int z = p.getTargetBlock((Set<Material>) null, 5).getZ();

                Sign sign = (Sign) p.getTargetBlock((Set<Material>) null, 5).getState();
                sign.setLine(0, Util.color("&7Cell: " + FarmCells.getCellName.get(p)));
                sign.setLine(1, Util.color("&cBlock: " + "Farming"));
                sign.setLine(2, Util.color("&7Rent: &a$100/wk"));
                sign.setLine(3, Util.color("&7Click to Claim!"));
                sign.update();

                p.sendMessage(Color.prison("FarmCells", "Cell created!"));

                FarmCells.getFile().set("cells." + "farms" + "." + FarmCells.getCellName.get(p) + ".signLocation.x", x);
                FarmCells.getFile().set("cells." + "farms" + "." + FarmCells.getCellName.get(p) + ".signLocation.y", y);
                FarmCells.getFile().set("cells." + "farms" + "." + FarmCells.getCellName.get(p) + ".signLocation.z", z);

                FarmCells.save();

            }
            if (args[0].equalsIgnoreCase("disband")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);
                if (!profile.getData().getPrisonCellName().getCell().equals("")) {
                    FarmCells.tryToDisband(p);
                } else {
                    p.sendMessage(Color.prison("FarmCells", "&7You don't currently have a cell!"));
                }
            }

            //SHOULD NOT BE IN PRODUCTION. IF FOUND IN PRODUCTION VERSION REMOVE IMMEDIATELY.
            if (args[0].equals("reset")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);

                profile.getData().getPrisonCellName().setCell("");
                profile.getData().getPrisonCellStatus().setStatus(false);

                profile.getData().save();

                p.sendMessage(Color.prison("FarmCells", "Reset it!"));
            }

            if (args[0].equalsIgnoreCase("accept")) {
                if (FarmCells.inviteHashmap.get(p) != null) {

                    Player originallySent = FarmCells.inviteHashmap.get(p);

                    p.sendMessage(Color.prison("FarmCells", "You have joined " + originallySent.getName() + "'s cell!"));
                    originallySent.sendMessage(Color.prison("FarmCells", p.getName() + " has joined your cell!"));

                    Bukkit.broadcastMessage("rg addmember " + originallySent.getName() + "-cell " + p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg addmember " + originallySent.getName() + "-cell " + p.getName() + " -w prison_world");

                    FarmCells.inviteHashmap.remove(p);

                } else {
                    p.sendMessage(Color.prison("FarmCells", ""));
                }
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("invite")) {
                Profile profile = Core.getInstance().getProfileManager().getProfile(p);
                if (profile.getData().getPrisonCellStatus().getStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {

                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        p.sendMessage(Color.prison("FarmCells", "You invited " + targetPlayer.getName() + " to your cell!"));
                        targetPlayer.sendMessage(Color.prison("FarmCells", "You have been invited to " + p.getName() + "'s cell!"));
                        FarmCells.inviteHashmap.put(targetPlayer, p);

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
                        p.sendMessage(Color.prison("Farm Cells", "You invited " + targetPlayer.getName() + " to your cell!"));
                        targetPlayer.sendMessage(Color.prison("Farm Cells", "You have been invited to " + p.getName() + "'s cell!"));
                        FarmCells.inviteHashmap.put(targetPlayer, p);

                    }

                } else {
                    p.sendMessage(Color.prison("Farm Cells", "You don't currently have a cell!"));
                }
            }
        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("create")) {
                FarmCells.enableSignStatus(p);
                if (FarmCells.getLocation(p, 1) != null && FarmCells.getLocation(p, 2) != null) {


                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".locationOne.x", FarmCells.getLocation(p, 1).getX());
                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".locationOne.y", FarmCells.getLocation(p, 1).getY());
                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".locationOne.z", FarmCells.getLocation(p, 1).getZ());

                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".locationTwo.x", FarmCells.getLocation(p, 2).getX());
                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".locationTwo.y", FarmCells.getLocation(p, 2).getY());
                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".locationTwo.z", FarmCells.getLocation(p, 2).getZ());

                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".name", args[1]);
                    FarmCells.getFile().set("cells." + "farms" + "." + args[1] + ".block", args[1]);
                    FarmCells.save();


                    FarmCells.getCellName.put(p, args[1]);

                    System.out.println(FarmCells.getCellName(p));


                    p.sendMessage(Color.prison("Farm Cells", "&7Please click the sign you'd like to assign to this cell!"));


                } else {
                    p.sendMessage(Util.color("&c&lFarmCells.> &7Please make your selection first!"));
                }
            }
        }

    }

}
