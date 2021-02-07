package me.christo.prisoncore.Crates;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Crates {


    public static void spawnStand(Player b) {
        ArmorStand as = (ArmorStand) b.getWorld().spawnEntity(b.getLocation(), EntityType.ARMOR_STAND);

        as.setVisible(false);
        as.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
    }

}
