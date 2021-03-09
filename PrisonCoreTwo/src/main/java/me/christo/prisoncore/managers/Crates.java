package me.christo.prisoncore.managers;

import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Chest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Crates {

    public static File cratesFile;
    private static FileConfiguration crates;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return crates;

    }


    public static void loadFile() {
        cratesFile = new File(Prison.getInstance().getDataFolder(), "crates.yml");
        if (!cratesFile.exists()) {
            Prison.getInstance().saveResource("crates.yml", false);
        }
        crates = YamlConfiguration.loadConfiguration(cratesFile);

        try {
            crates.save(cratesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            crates.save(cratesFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }


    public static void spawnStand(Player p, Block b, String crate) {

        p.sendMessage(Util.color("&a&lCRATES &8| &7You are now opening a crate!"));


        String face = Util.getOppositeCardinal(p);

        Location loc = null;
        Location locTwo = null;


        //loc two is the one with the hologram


        if (face.equalsIgnoreCase("NORTH")) {
            loc = new Location(b.getWorld(), b.getX() + .05, b.getY() + .5, b.getZ() + 1);
            locTwo = new Location(b.getWorld(), b.getX() + .5, b.getY() - .5, b.getZ() + .25);
        }
        if (face.equalsIgnoreCase("SOUTH")) {
            loc = new Location(b.getWorld(), b.getX(), b.getY() + .5, b.getZ());
            locTwo = new Location(b.getWorld(), b.getX() + .5, b.getY() - .5, b.getZ() + .5);
        }



        if (face.equalsIgnoreCase("EAST")) {
            loc = new Location(b.getWorld(), b.getX(), b.getY() + .5, b.getZ());
            locTwo = new Location(b.getWorld(), b.getX() + .5, b.getY() - .5, b.getZ() + .5);
        }








        if (face.equalsIgnoreCase("WEST")) {
            loc = new Location(b.getWorld(), b.getX() + 1, b.getY() + .5, b.getZ() + 1);
            locTwo = new Location(b.getWorld(), b.getX() + .5, b.getY() - .5, b.getZ() + .5);
        }

        Location chest = b.getLocation();

        //Location loc = new Location(b.getWorld(), b.getX() + .5, b.getY() + .5, b.getZ() + .5);
        //Location locTwo = new Location(b.getWorld(), b.getX() + 1, b.getY() + .75, b.getZ() + .75);
        locTwo.setYaw((p.getLocation().getYaw() - 180) % 360);
        loc.setYaw((p.getLocation().getYaw() - 180) % 360);


        ArmorStand asTwo = (ArmorStand) b.getWorld().spawnEntity(locTwo, EntityType.ARMOR_STAND);
        asTwo.setGravity(false);
        asTwo.setVisible(false);


        ArmorStand as = (ArmorStand) b.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.getBodyPose().add(48, 0, 72);
        as.setVisible(false);
        as.setGravity(false);
        b.getWorld().getBlockAt(chest).setType(Material.CHEST);

        Block block = p.getWorld().getBlockAt(b.getLocation());
        org.bukkit.material.Chest chestData = new Chest(Material.CHEST);
        org.bukkit.block.Chest chest1 = (org.bukkit.block.Chest) block.getState();

        System.out.println(Util.getOppositeCardinal(p));
        chestData.setFacingDirection(BlockFace.valueOf(Util.getOppositeCardinal(p)));
        chest1.setData(chestData);
        chest1.update();


        int count = 0;
        for (String value : Crates.getFile().getConfigurationSection("crates." + crate).getKeys(false)) {
            count++;
            System.out.println(count);
            Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
                for (int c = 0; c < 37; c++) {

                    int lol = c > 34 ? (c % 5 * 20) + 120 : c > 30 ? (c % 5 * 10) + 60 : c * 2;

                    Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
                        if (Crates.getFile().getItemStack("crates." + crate + "." + value + ".itemStack") != null) {
                            ItemStack i = Crates.getFile().getItemStack("crates." + crate + "." + value + ".itemStack");
                            ItemMeta meta = i.getItemMeta();
                            if (!meta.hasDisplayName()) {
                                asTwo.setCustomName(i.getType().toString());
                            } else {
                                asTwo.setCustomName(Util.color(meta.getDisplayName()));
                            }
                            as.setItemInHand(i);
                            asTwo.setCustomNameVisible(true);
                        }
                    }, lol - 10);
                }
            }, 10L);
        }

        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            ItemStack i = null;

            for (String value : Crates.getFile().getConfigurationSection("crates." + crate).getKeys(false)) {
                int chance = ThreadLocalRandom.current().nextInt(0, 100);
                if (chance <= getFile().getInt("crates." + crate + "." + value + ".chance")) {
                    i = getFile().getItemStack("crates." + crate + "." + value + ".itemStack");
                    as.setItemInHand(i);
                }

            }
            ItemStack finalI = i;
            Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {


                Location spawnLoc = new Location(asTwo.getWorld(), asTwo.getLocation().getX(), asTwo.getLocation().getY() + 2, asTwo.getLocation().getZ());

                final Firework f = spawnLoc.getWorld().spawn(spawnLoc, Firework.class);
                FireworkMeta fm = f.getFireworkMeta();

                fm.addEffect(FireworkEffect.builder()

                        .flicker(true)
                        .trail(true)
                        .with(FireworkEffect.Type.STAR)
                        .with(FireworkEffect.Type.BALL)
                        .with(FireworkEffect.Type.BALL_LARGE)
                        .withColor(Color.PURPLE)
                        .withColor(Color.WHITE)
                        .build());

                fm.setPower(0);
                f.setFireworkMeta(fm);


                p.getInventory().addItem(finalI);

                as.remove();
                asTwo.remove();

                spawnLoc.getWorld().getBlockAt(chest).setType(Material.AIR);

                p.sendMessage(Util.color("&a&lCRATES &8| &7You won a reward from your crate!"));

            }, 20);
        }, 200L);

        //as.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
    }

}
