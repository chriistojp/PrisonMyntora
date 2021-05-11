package me.christo.prisoncore.managers;


import ak.znetwork.znpcservers.ServersNPC;
import ak.znetwork.znpcservers.npc.enums.NPCType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class EasterEggNPCs {

    public void spawnEasterEggNPCs() {

        ServersNPC.createNPC(1001, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), 1, 1, 1, 90, 0), "Bob");

    }



}
