package me.christo.prisoncore.events;


import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.managers.FarmCells;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FarmCellEvents implements Listener {



    //disband chat event
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        if(FarmCells.disbandStatus.containsKey(e.getPlayer())) {
            if (e.getMessage().equals("yes")) {
                e.setCancelled(true);

                Profile profile = Core.getInstance().getProfileManager().getProfile(e.getPlayer());
                Bukkit.broadcastMessage(profile.getData().getPrisonFarmName().getCell());
                String cellName = profile.getData().getPrisonFarmName().getCell();
                
                profile.getData().getPrisonFarmCellStatus().setStatus(false);
                profile.getData().getPrisonFarmName().setCell("");
                e.getPlayer().sendMessage(Color.prison("FarmCells", "You have disbanded your cell!"));
                FarmCells.disbandStatus.remove(e.getPlayer());



                int signX = FarmCells.getFile().getInt("cells." + cellName + ".signLocation.x");
                int signY = FarmCells.getFile().getInt("cells." + cellName + ".signLocation.y");
                int signZ = FarmCells.getFile().getInt("cells." + cellName + ".signLocation.z");
                System.out.println("SIGN X " + signX);
                Sign sign = (Sign) Bukkit.getWorld("prison_world").getBlockAt(signX, signY, signZ).getState();

                sign.setLine(0, Util.color("&7Cell: " + FarmCells.getFile().getString("cells." + cellName + ".name")));
                sign.setLine(1, Util.color("&cBlock: " + FarmCells.getFile().getString("cells." + cellName + ".block")));
                sign.setLine(2, Util.color("&7Rent: &a$100/wk"));
                sign.setLine(3, Util.color("&7Click to Claim!"));
                sign.update();

                Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).removeRegion(e.getPlayer().getName() + "-cell");



            } else if(e.getMessage().equals("cancel")) {
                FarmCells.disbandStatus.remove(e.getPlayer());
                e.getPlayer().sendMessage(Color.prison("FarmCells", "Disbanding cell cancelled!"));
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
                        //e.setCancelled(true);
                    }
                }
            }

        }

    }


    //obvious, trying to claim
    @EventHandler
    public void onTryToClaimEvent(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sign.getLine(3).equals(Util.color("&7Click to Claim!"))) {
                    if (profile.getData().getPrisonFarmCellStatus().getStatus()) {
                        p.sendMessage(Color.prison("FarmCells", "You already have a cell"));
                    } else {

                        String cellName = ChatColor.stripColor(sign.getLine(0)).replaceAll("Cell:", "").replace(" ", "");



                        

                        System.out.println("cells." + "farms" + "." + cellName + ".locationOne.x");
                        int x = FarmCells.getFile().getInt("cells." + "farms" + "." + cellName + ".locationOne.x");
                        System.out.println(x);
                        int y = FarmCells.getFile().getInt("cells." + "farms" + "." + cellName + ".locationOne.y");
                        int z = FarmCells.getFile().getInt("cells." + "farms" + "." + cellName + ".locationOne.z");

                        int x2 = FarmCells.getFile().getInt("cells." + "farms" + "." + cellName + ".locationTwo.x");
                        int y2 = FarmCells.getFile().getInt("cells." + "farms" + "." + cellName + ".locationTwo.y");
                        int z2 = FarmCells.getFile().getInt("cells." + "farms" + "." + cellName + ".locationTwo.z");

                        BlockVector pos1 = new BlockVector(x, y, z);
                        BlockVector pos2 = new BlockVector(x2, y2, z2);
                        ProtectedRegion region = new ProtectedCuboidRegion(e.getPlayer().getName() + "-cell", pos1, pos2);
                        region.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.DENY);
                        region.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
                        region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
                        region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
                        region.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
                        region.setFlag(DefaultFlag.ENTRY, StateFlag.State.DENY);
                        Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).addRegion(region);
                        //             Prison.getWorldGuard().getRegionManager(Bukkit.getWorld("prison_world")).getRegion(e.getPlayer().getName() + "-cell").getMembers().addPlayer(p.getName());

                        sign.setLine(1, Util.color("&aClaimed!"));
                        sign.setLine(3, Util.color("Owner: &5" + e.getPlayer().getName()));
                        sign.update();

                        p.sendMessage(Color.prison("FarmCells", "You have claimed " + cellName + "!"));

                        profile.getData().getPrisonFarmName().setCell("farms" + "." + cellName);

                        profile.getData().getPrisonFarmCellStatus().setStatus(true);


                    }
                }
            }
        }


    }



    //selector stuff


}
