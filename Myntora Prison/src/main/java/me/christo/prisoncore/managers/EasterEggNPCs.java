package me.christo.prisoncore.managers;


import ak.znetwork.znpcservers.ServersNPC;
import ak.znetwork.znpcservers.events.NPCInteractEvent;
import ak.znetwork.znpcservers.npc.ZNPC;
import ak.znetwork.znpcservers.npc.enums.NPCType;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EasterEggNPCs implements Listener {

    public static void spawnEasterEggNPCs() {

        ServersNPC.createNPC(1001, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), -7, 100, 5, -130, 0), "Bob");
        ServersNPC.createNPC(1002, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), 6, 98, -90, 115, 0), "Christo");
        ServersNPC.createNPC(1003, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), 33, 98, -49.5, 89, 0), "Nurse");
        ServersNPC.createNPC(1004, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), 129, 92, 29, 145, 0), "Farmer");
        ServersNPC.createNPC(1005, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), -20, 98, -78, -32, 0), "Cactus");
    }

    @EventHandler
    public void onNpcInteract(NPCInteractEvent event) {

        //player who interacted with the npc
        Player player = (Player) event.getPlayer();

        //1001 is the id of the npc
        ZNPC bob = ZNPC.find(1001);
        //find the npc your looking for

        //if the npc they interacted with is bob
        if(event.getNpc().equals(bob)) {
            //Color.prison is how we format everything.
            //the module is the thing in [] and the body is the rest of the message
            player.sendMessage(Color.prison("Crates", "You found bob!"));
        }
        ZNPC christo = ZNPC.find(1002);

        if(event.getNpc().equals(christo)) {
            player.sendMessage(Color.prison("NPC", "Casino up ahead!"));
        }
        ZNPC nurse = ZNPC.find(1003);

        if(event.getNpc().equals(nurse)) {
            Infirmary.openGui(player);
        }
        ZNPC farmer = ZNPC.find(1004);

        if(event.getNpc().equals(farmer)) {
            player.sendMessage(Color.prison("NPC", "I've got all your farming needs"));
        }
        ZNPC cactus = ZNPC.find(1005);

        if(event.getNpc().equals(cactus)) {
            player.sendMessage(Color.prison("NPC", "Can you believe Zay coded this?!"));
        }
    }



}
