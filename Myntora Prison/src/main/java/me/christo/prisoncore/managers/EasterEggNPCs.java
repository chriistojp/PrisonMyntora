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


    }



}
