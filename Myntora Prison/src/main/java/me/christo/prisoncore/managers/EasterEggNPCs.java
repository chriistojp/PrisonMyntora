package me.christo.prisoncore.managers;


import ak.znetwork.znpcservers.ServersNPC;
import ak.znetwork.znpcservers.npc.enums.NPCType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class EasterEggNPCs {

    public static void spawnEasterEggNPCs() {

        ServersNPC.createNPC(1001, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), -7, 100, 5, -130, 0), "Bob");

    }



}
