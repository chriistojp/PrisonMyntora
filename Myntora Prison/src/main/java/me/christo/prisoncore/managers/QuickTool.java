package me.christo.prisoncore.managers;

import me.christo.prisoncore.commands.admin.PlayerInfoCommand;
import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.utils.Gui;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.stats.StatsCommand;
import net.myntora.core.core.util.Color;
import net.myntora.core.core.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuickTool {

    public static void giveQuickTool(Player p) {

        ItemBuilder builder = new ItemBuilder(Material.COMPASS)
                .setName("&d&lQuick Tool")
                .setLore("")
                .setLore("&7This compass will allow quick selection")
                .setLore("&7of the most commonly used features throughout")
                .setLore("&7the prison.");

        p.getInventory().addItem(builder.toItemStack());

    }

    public static void openQuickTool(Player p) {

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        Gui gui = new Gui("Quick Tool", 54);
        gui.fill(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15));
        gui.i(19, StarterPickaxe.getPlayersPickaxe(p));
        gui.i(20, Material.DRAGON_EGG, "&d&lPet Menu", "", "&7Click to open the pet menu.");
        gui.i(24, Material.MAP, "&e&lGang Info", "", "&7Click to display your gang info.");
        gui.i(25, Material.BOOK_AND_QUILL, "&f&lStats", "", "&7Click to display your stats.");
        gui.i(39, Material.DIAMOND_PICKAXE, "&6&lTeleport to Mine", "", "&7Click to teleport to your mine.");
        gui.i(40, Material.NETHER_STAR, "&5&lSpawn Warp", "", "&7Click to teleport to spawn.");
        gui.i(41, Material.IRON_FENCE, "&a&lCell Warp", "", "&7Click to teleport to your cell.");

        gui.onClick(e -> {
           e.setCancelled(true);
           if(e.getSlot() == 20) {
               Pets.openGui(p);
           }
           if(e.getSlot() == 24) {
               if(profile.getData().getPrisonGangName().getCell() != null) {
                   p.getOpenInventory().close();
                   p.performCommand("gang info");
               } else {
                   p.getOpenInventory().close();
                   p.sendMessage(Color.prison("Quick Tool", "You are not currently part of a gang."));
               }
           }
           if(e.getSlot() == 25) {
               PlayerInfoCommand.infoGui(p, p);
           }
           if(e.getSlot() == 39) {
               p.getOpenInventory().close();
               p.performCommand("mine");
           }
           if(e.getSlot() == 40) {
               p.getOpenInventory().close();
               p.performCommand("spawn");
           }
           if(e.getSlot() == 41) {
               p.getOpenInventory().close();
               p.performCommand("cell");
           }

        });
        gui.show(p);

    }
}
