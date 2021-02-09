package me.christo.prisoncore.Mines;


import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.christo.prisoncore.Main;
import me.christo.prisoncore.Utils.Util;
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

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return mines;

    }


    public static void loadFile() {
        minesFile = new File(Main.getInstance().getDataFolder(), "mines.yml");
        if (!minesFile.exists()) {
            Main.getInstance().saveResource("mines.yml", false);
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

        if(p.getItemInHand().hasItemMeta()) {
            if(p.getItemInHand().getItemMeta().hasDisplayName()) {
                if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Util.color("&a&lMINE SELECTOR TOOL"))) {
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

    public static void fill(String mine) {

        World w = Bukkit.getWorld(Objects.requireNonNull(Mines.getFile().getString("mines.world")));

        ProtectedRegion r = Main.getWorldGuard().getRegionManager(w)
                .getRegion(mine + "-mine");


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

                            for(String key : Mines.getFile().getConfigurationSection("mines." + mine + ".blocks").getKeys(false)) {
                                System.out.println(key);
                                System.out.println(Mines.getFile().getString("mines." + mine + ".blocks." + key + ".material"));
                                int chance = ThreadLocalRandom.current().nextInt(0, 100);
                                if(chance <= Mines.getFile().getInt("mines." + mine + ".blocks." + key + ".chance")) {
                                    world.getBlockAt(new Location(w, x, y, z)).setType(Material.matchMaterial(Mines.getFile().getString("mines." + mine + ".blocks." + key + ".material")));
                                }

                            }
                        }
                    }
                }
                world.commit();
                world.clear();

            }
        });

    }
    public static void fill(Material m, String mine) {

        World w = Bukkit.getWorld(Objects.requireNonNull(Mines.getFile().getString("mines.world")));

        ProtectedRegion r = Main.getWorldGuard().getRegionManager(w)
                .getRegion(mine + "-mine");


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

    }

}
