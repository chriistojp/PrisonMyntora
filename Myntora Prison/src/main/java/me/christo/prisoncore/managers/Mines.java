package me.christo.prisoncore.managers;


import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Mines {

    private static File minesFile;
    private static FileConfiguration mines;
    public static List<String> minesToReset;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return mines;

    }


    public static void loadFile() {
        minesFile = new File(Prison.getInstance().getDataFolder(), "mines.yml");
        if (!minesFile.exists()) {
            Prison.getInstance().saveResource("mines.yml", false);
        }
        mines = YamlConfiguration.loadConfiguration(minesFile);

        try {
            mines.save(minesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            mines.save(minesFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static void giveSelectorTool(Player p) {

        ItemStack i = new ItemStack(Material.STICK);
        ItemMeta meta = (ItemMeta) i.getItemMeta();
        meta.setDisplayName(Util.color("&a&lMINE SELECTOR TOOL"));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Util.color("&7- Left click for pos #1"));
        lore.add(Util.color("&7- Right click for pos #2"));
        lore.add("");
        lore.add(Util.color("&7/mine create (name) once finished."));
        meta.setLore(lore);
        i.setItemMeta(meta);

        p.getInventory().addItem(i);


    }

    public static boolean isSelector(Player p) {

        if (p.getItemInHand().hasItemMeta()) {
            if (p.getItemInHand().getItemMeta().hasDisplayName()) {
                if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Util.color("&a&lMINE SELECTOR TOOL"))) {
                    return true;
                }
            }
        }

        return false;

    }

    public static HashMap<Player, Location> positionOne = new HashMap<>();
    public static HashMap<Player, Location> positionTwo = new HashMap<>();

    public static String msg(String path) {
        return Util.color(Mines.getFile().getString("messages." + path));

    }

//    public static void fill(String mine) {
//
//        World w = Bukkit.getWorld(Objects.requireNonNull(Mines.getFile().getString("mines.world")));
//
//        ProtectedRegion r = Prison.getWorldGuard().getRegionManager(w)
//                .getRegion(mine + "-mine");
//
//
//        assert w != null;
//        AsyncWorld world = AsyncWorld.create(new WorldCreator(w.getName()));
//
//        TaskManager.IMP.taskWhenFree(new Runnable() {
//            @Override
//            public void run() {
//                for (int x = r.getMinimumPoint().getBlockX(); x < r.getMaximumPoint().getBlockX() + 1; x++) {
//                    for (int y = r.getMinimumPoint().getBlockY(); y < r.getMaximumPoint().getBlockY()
//                            + 1; y++) {
//                        for (int z = r.getMinimumPoint().getBlockZ(); z < r.getMaximumPoint().getBlockZ()
//                                + 1; z++) {
//
//                            for(String key : Mines.getFile().getConfigurationSection("mines." + mine + ".blocks").getKeys(false)) {
//                                System.out.println(key);
//                                System.out.println(Mines.getFile().getString("mines." + mine + ".blocks." + key + ".material"));
//                                int chance = ThreadLocalRandom.current().nextInt(0, 100);
//                                if(chance <= Mines.getFile().getInt("mines." + mine + ".blocks." + key + ".chance")) {
//                                    world.getBlockAt(new Location(w, x, y, z)).setType(Material.matchMaterial(Mines.getFile().getString("mines." + mine + ".blocks." + key + ".material")));
//                                }
//
//                            }
//                        }
//                    }
//                }
//                world.commit();
//                world.clear();
//
//            }
//        });
//
//    }

    //iron blocks
    //coal blocks
    // emerald blocks
    public static void fillEventsMineOres() {

        World w = Bukkit.getWorld("prison_spawn");

        ProtectedRegion r = Prison.getWorldGuard().getRegionManager(w)
                .getRegion("eventsmine");


        assert w != null;
        AsyncWorld world = AsyncWorld.create(new WorldCreator(w.getName()));

        TaskManager.IMP.taskWhenFree(new Runnable() {
            @Override
            public void run() {
                for (int x = r.getMinimumPoint().getBlockX(); x < r.getMaximumPoint().getBlockX() + 1; x++) {
                    for (int y = r.getMinimumPoint().getBlockY(); y < r.getMaximumPoint().getBlockY()
                            + 1; y++) {
                        for (int z = r.getMinimumPoint().getBlockZ(); z < r.getMaximumPoint().getBlockZ()
                                + 1; z++) {

                            int random = ThreadLocalRandom.current().nextInt(0, 11);

                            if (random <= 6) {
                                world.getBlockAt(new Location(w, x, y, z)).setType(Material.COAL_BLOCK);
                            } else if (random == 7 || random == 8 || random == 9) {
                                world.getBlockAt(new Location(w, x, y, z)).setType(Material.IRON_BLOCK);
                            } else if (random == 10) {
                                world.getBlockAt(new Location(w, x, y, z)).setType(Material.EMERALD_BLOCK);
                            }

                        }
                    }
                }
                world.commit();
                world.clear();

            }
        });
        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            TaskManager.IMP.taskWhenFree(new Runnable() {
                @Override
                public void run() {
                    for (int x = r.getMinimumPoint().getBlockX(); x < r.getMaximumPoint().getBlockX() + 1; x++) {
                        for (int y = r.getMinimumPoint().getBlockY(); y < r.getMaximumPoint().getBlockY()
                                + 1; y++) {
                            for (int z = r.getMinimumPoint().getBlockZ(); z < r.getMaximumPoint().getBlockZ()
                                    + 1; z++) {

                                world.getBlockAt(new Location(w, x, y, z)).setType(Material.AIR);

                            }
                        }
                    }
                    world.commit();
                    world.clear();

                }
            });

            Bukkit.broadcastMessage(Util.color("&d[Events] &7The Event has ended!"));
        }, 20 * 30);

    }


    public static void fillEventsMine(Material m) {

        World w = Bukkit.getWorld("prison_spawn");

        ProtectedRegion r = Prison.getWorldGuard().getRegionManager(w)
                .getRegion("eventsmine");


        assert w != null;
        AsyncWorld world = AsyncWorld.create(new WorldCreator(w.getName()));

        TaskManager.IMP.taskWhenFree(new Runnable() {
            @Override
            public void run() {
                for (int x = r.getMinimumPoint().getBlockX(); x < r.getMaximumPoint().getBlockX() + 1; x++) {
                    for (int y = r.getMinimumPoint().getBlockY(); y < r.getMaximumPoint().getBlockY()
                            + 1; y++) {
                        for (int z = r.getMinimumPoint().getBlockZ(); z < r.getMaximumPoint().getBlockZ()
                                + 1; z++) {

                            world.getBlockAt(new Location(w, x, y, z)).setType(m);

                        }
                    }
                }
                world.commit();
                world.clear();

            }
        });

        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {

            TaskManager.IMP.taskWhenFree(new Runnable() {
                @Override
                public void run() {
                    for (int x = r.getMinimumPoint().getBlockX(); x < r.getMaximumPoint().getBlockX() + 1; x++) {
                        for (int y = r.getMinimumPoint().getBlockY(); y < r.getMaximumPoint().getBlockY()
                                + 1; y++) {
                            for (int z = r.getMinimumPoint().getBlockZ(); z < r.getMaximumPoint().getBlockZ()
                                    + 1; z++) {

                                world.getBlockAt(new Location(w, x, y, z)).setType(Material.AIR);

                            }
                        }
                    }
                    world.commit();
                    world.clear();

                }
            });

            Bukkit.broadcastMessage(Util.color("&d[Events] &7The Event has ended!"));
        }, 20 * 30);


    }


    public static void fill(Player p, String mine) {

        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        String rank = profile.getData().getPrisonRank().getName();
        if (profile.getData().getPrisonMineNumber().getAmount() != 0) {

            profile.getData().getPrisonCellName().getCell();
            World w = Bukkit.getWorld("prison_world");
            ProtectedRegion r = Prison.getWorldGuard().getRegionManager(w)
                    .getRegion(mine);
            assert w != null;
            AsyncWorld world = AsyncWorld.create(new WorldCreator(w.getName()));
         //   Material[] m = profile.getData().getPrisonRank().getMineBlock();

            TaskManager.IMP.taskWhenFree(new Runnable() {
                @Override
                public void run() {
                    assert r != null;
                    for (int x = r.getMinimumPoint().getBlockX(); x < r.getMaximumPoint().getBlockX() + 1; x++) {
                        for (int y = r.getMinimumPoint().getBlockY(); y < r.getMaximumPoint().getBlockY()
                                + 1; y++) {
                            for (int z = r.getMinimumPoint().getBlockZ(); z < r.getMaximumPoint().getBlockZ()
                                    + 1; z++) {

//                                if (m.length == 1) {
//                                    world.getBlockAt(new Location(w, x, y, z)).setType(m[0]);
//                                }
//                                if (m.length == 2) {
//                                    world.getBlockAt(new Location(w, x, y, z)).setType(m[ThreadLocalRandom.current().nextInt(2)]);
//                                }
                            }

                        }
                    }
                    world.commit();
                    world.clear();

                }
            });


        }
    }

    public static boolean checkChance(int chance) {

        //60

        //0 - 100

        //
        int random = ThreadLocalRandom.current().nextInt(0, 100);

        // <= 30

        return random <= chance;

    }

}
