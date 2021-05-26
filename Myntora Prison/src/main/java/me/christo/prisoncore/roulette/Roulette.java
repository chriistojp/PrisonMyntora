package me.christo.prisoncore.roulette;


import jdk.nashorn.internal.objects.annotations.Getter;
import me.christo.prisoncore.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Roulette {

    static Map<Location, Material> locations = new HashMap<>();

    public static void loadLocations() {


        Location location;

        location = new Location(Bukkit.getWorld("prison_spawn"), -7, 100, -225);
        locations.add(location);

        for(double i = location.getX(); i <= 7; i++) {

            location = new Location(Bukkit.getWorld("prison_spawn"), i, 100, -225);

            locations.add(location);
        }


    }

    public static void start() {

        loadLocations();

        int count = 0;
        while(10 < count) {
            for(Location loc : locations.keySet()) {
                loc.add(1, 0, 0);
                if(loc.add(1, 0, 0).getX() > loc.getX() - locations.size()) {
                    Location newLocation = loc.subtract(locations.size(), 0, 0);
                }
            }
        }


//        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
//
//
//
//
//            int count = 1;
//            for(Location loc : locations) {
//
//                Material material = loc.getBlock().getType();
//                Material toMove;
//                if(loc.getX() == -7) {
//                    toMove = new Location(Bukkit.getWorld("prison_spawn"), 7, 100, -225).getBlock().getType();
//                    Bukkit.broadcastMessage("ran.");
//                } else {
//                   toMove = locations.get(count).getBlock().getType();
//                }
//
//
//
//                Bukkit.broadcastMessage(material.toString() + " ( " + loc.getX() + " )" + " -> " + toMove.toString() + " (" + locations.get(count).getX() + " )");
//                loc.getBlock().setType(toMove);
//
//                count++;
//            }
//
//
//        }, 40);

    }

}
