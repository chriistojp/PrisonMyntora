package me.christo.prisoncore.commands.admin;


import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.utils.Gui;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

@DynamicCommand(
        name = "playerinfo"

)
public class PlayerInfoCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        if(args.length == 1) {

            Player p = (Player) sender;

            if(Bukkit.getPlayer(args[0]) == null) {
                p.sendMessage(Color.prison("Info", args[0] + " was not found online!"));
                return;
            }

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            p.sendMessage(Color.prison("Info", "Opening..."));
            infoGui(p, player);

        }

    }

    public Gui infoGui(Player sender, OfflinePlayer p) {

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        Gui info = new Gui(p.getName() + "'s Info:", 36);

        ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) i.getItemMeta();
        meta.setOwner(p.getName());
        meta.setDisplayName(Util.color("&d" + p.getName() + "'s Info"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&7This gui is useful for quickly"));
        lore.add(Util.color("&7glancing at player info!"));
        meta.setLore(lore);
        i.setItemMeta(meta);

        info.i(10, i);

        info.i(12, Material.PRISMARINE_SHARD, "&3&lSHARDS » &7" + profile.getData().getPrisonShards().getAmount());
        info.i(14, Material.TRIPWIRE_HOOK, "&c&lRANK » &7" + profile.getData().getPrisonRank().getName());
        info.i(16, StarterPickaxe.getPlayersPickaxe(p));
        info.i(21, Material.GOLD_INGOT, "&e&lBALANCE » &7" + profile.getData().getPrisonMoney().getAmount());

        for(int c = 0; c < 36; c++) {
            if(info.getInventory().getItem(c) == null) {
                info.i(c, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15));
            }
        }

        info.onClick(e -> {
            e.setCancelled(true);
        });

        info.show(sender);
        return info;

    }

}
