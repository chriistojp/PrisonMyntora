package me.christo.prisoncore.events;


import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.cooldown.Cooldowns;
import me.christo.cooldown.api.API;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.managers.Cells;
import me.christo.prisoncore.utils.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import net.myntora.core.core.util.UtilEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CellEvents implements Listener {



    //disband chat event
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if(Cells.disbandStatus.containsKey(e.getPlayer())) {
            if (e.getMessage().equals("yes")) {
                e.setCancelled(true);

                Profile profile = Core.getInstance().getProfileManager().getProfile(e.getPlayer());
                Bukkit.broadcastMessage(profile.getData().getPrisonCellName().getCell());
                String cellName = profile.getData().getPrisonCellName().getCell();

                profile.getData().getPrisonCellStatus().setStatus(false);
                profile.getData().getPrisonCellName().setCell("");
                e.getPlayer().sendMessage(Color.prison("Cells", "You have disbanded your cell!"));
                Cells.disbandStatus.remove(e.getPlayer());



                int signX = Cells.getFile().getInt("cells." + cellName + ".signLocation.x");
                int signY = Cells.getFile().getInt("cells." + cellName + ".signLocation.y");
                int signZ = Cells.getFile().getInt("cells." + cellName + ".signLocation.z");
                System.out.println("SIGN X " + signX);
                Sign sign = (Sign) Bukkit.getWorld("prison_world").getBlockAt(signX, signY, signZ).getState();

                sign.setLine(0, Util.color("&7Cell: " + Cells.getFile().getString("cells." + cellName + ".name")));
                sign.setLine(1, Util.color("&cBlock: " + Cells.getFile().getString("cells." + cellName + ".block")));
                sign.setLine(2, Util.color("&7Rent: &a$100/wk"));
                sign.setLine(3, Util.color("&7Click to Claim!"));
                sign.update();

                Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).removeRegion(e.getPlayer().getName() + "-cell");



            } else if(e.getMessage().equals("cancel")) {
                Cells.disbandStatus.remove(e.getPlayer());
                e.getPlayer().sendMessage(Color.prison("Cells", "Disbanding cell cancelled!"));
            }
        }

    }

    //Cancels block from being broken when selecting with left click
    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        if (e.getPlayer().getItemInHand() != null) {
            if (e.getPlayer().getItemInHand().hasItemMeta()) {
                if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Util.color("&a&lCell Selector"))) {
                    e.setCancelled(true);
                    }
                }
            }

        }

    }




    //obvious, trying to claim
    @EventHandler
    public void onTryToClaimEvent(PlayerInteractEvent e) throws StorageException {

        Player p = e.getPlayer();
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(3).equals(Util.color("&7Click to Claim!"))) {
                    if (profile.getData().getPrisonCellStatus().getStatus()) {
                        p.sendMessage(Color.prison("Cells", "You already have a cell"));
                    } else {

                        String cellName = ChatColor.stripColor(sign.getLine(0)).replaceAll("Cell:", "").replace(" ", "");
                        String blockName = ChatColor.stripColor(sign.getLine(1)).replaceAll("Block:", "").replace(" ", "");

                        if (blockName.equalsIgnoreCase("d")) {
                            if (profile.getData().getPrisonMoney().getAmount() >= 5000) {
                                createCell(p, sign, "5k");
                            }
                        } else if (blockName.equalsIgnoreCase("c")) {
                            if (profile.getData().getPrisonMoney().getAmount() >= 10000) {
                                createCell(p, sign, "10k");
                            }
                        } else if (blockName.equalsIgnoreCase("b")) {
                            if (profile.getData().getPrisonMoney().getAmount() >= 25000) {
                                createCell(p, sign, "25k");
                            }
                        } else if (blockName.equalsIgnoreCase("a")) {
                            if (profile.getData().getPrisonMoney().getAmount() >= 30000) {
                                createCell(p, sign, "30k");
                            }
                        }
                    }
                }
            }
        }


    }

    public void createCell(Player p, Sign sign, String rent) throws StorageException {

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (sign.getLine(3).equals(Util.color("&7Click to Claim!"))) {
            if (profile.getData().getPrisonCellStatus().getStatus()) {
                p.sendMessage(Color.prison("Cells", "You already have a cell"));
            } else {

                String cellName = ChatColor.stripColor(sign.getLine(0)).replaceAll("Cell:", "").replace(" ", "").toLowerCase();
                String blockName = ChatColor.stripColor(sign.getLine(1)).replaceAll("Block:", "").replace(" ", "");

                int x = Cells.getFile().getInt("cells." + blockName + "." + cellName + ".locationOne.x");
                int y = Cells.getFile().getInt("cells." + blockName + "." + cellName + ".locationOne.y");
                int z = Cells.getFile().getInt("cells." + blockName + "." + cellName + ".locationOne.z");

                int x2 = Cells.getFile().getInt("cells." + blockName + "." + cellName + ".locationTwo.x");
                int y2 = Cells.getFile().getInt("cells." + blockName + "." + cellName + ".locationTwo.y");
                int z2 = Cells.getFile().getInt("cells." + blockName + "." + cellName + ".locationTwo.z");

                BlockVector pos1 = new BlockVector(x, y, z);
                BlockVector pos2 = new BlockVector(x2, y2, z2);
                ProtectedRegion region = new ProtectedCuboidRegion(p.getName() + "-cell", pos1, pos2);
                region.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.DENY);
                region.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
                region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
                region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
                region.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
                region.setFlag(DefaultFlag.ENTRY, StateFlag.State.DENY);
                Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).addRegion(region);
                Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).saveChanges();

                Bukkit.broadcastMessage(cellName + " " + blockName);
                Bukkit.broadcastMessage(x + " " + y + " " + z);
                Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).getRegion(p.getName() + "-cell").getMembers().addPlayer(p.getName());

                sign.setLine(1, Util.color("&d&lClaimed!"));
          //      sign.setLine();
                sign.setLine(3, Util.color("&dOwner: &7" + p.getName()));
                sign.update();

                p.sendMessage(Color.prison("Cells", "You have claimed " + cellName + "!"));

                profile.getData().getPrisonCellName().setCell(blockName + "." + cellName);

                profile.getData().getPrisonCellStatus().setStatus(true);


                API api = new API(p.getUniqueId());
                api.createCooldown("cell", 604800);
            }
        }
    }



    //selector stuff

    @EventHandler
    public void onSignClick(PlayerInteractEvent e) {


        if(e.getClickedBlock() == null) {
            return;
        }

        if(e.getClickedBlock().getType() == Material.WALL_SIGN) {

            Sign sign = (Sign) e.getClickedBlock().getState();
            if(ChatColor.stripColor(sign.getLine(3)).contains(e.getPlayer().getName())) {

                API api = new API(e.getPlayer().getUniqueId());

                long seconds = api.getTimeLeft("cell");
                SimpleDateFormat formatter = new SimpleDateFormat("dd:HH:mm:ss", Locale.US);

                Date date = new Date(seconds);
                long days = seconds / 86400;
                long hours = (seconds / 3600) - (24 * days);



                String result = formatter.format(date);



                e.getPlayer().sendMessage(Color.prison("Cells", "Your cell has &d" + days + "d " + hours + "h &7before it expires."));


            }

        }

    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            if (e.getPlayer().getItemInHand() != null) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Util.color("&a&lCell Selector"))) {

                            Cells.setLocation(e.getPlayer(), e.getClickedBlock().getLocation(), 1);
                            e.getPlayer().sendMessage(Util.color("&c&lCells > &7Location #1 Selected!"));
                        }
                    }
                }
            }

        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if (e.getPlayer().getItemInHand() != null) {
                if (e.getPlayer().getItemInHand().hasItemMeta()) {
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Util.color("&a&lCell Selector"))) {
                            Cells.setLocation(e.getPlayer(), e.getClickedBlock().getLocation(), 2);
                            e.getPlayer().sendMessage(Util.color("&c&lCells > &7Location #2 Selected!"));

                        }
                    }
                }
            }

        }

    }

}
