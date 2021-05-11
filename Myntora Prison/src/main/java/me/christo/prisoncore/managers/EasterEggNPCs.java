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
        ServersNPC.createNPC(1006, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), -30, 98, -38, -178, 0), "Lunch Lady");
        ServersNPC.createNPC(1007, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), 7, 98, 141, 157, 0), "Charlie");
        ServersNPC.createNPC(1008, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), -12, 99, -136, -57, 0), "Noodles");
        ServersNPC.createNPC(1009, NPCType.PLAYER, new Location(Bukkit.getWorld("prison_spawn"), -18, 98, 27, -133, 0), "Zay");
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
            player.sendMessage(Color.prison("Crates", "These crates are pretty cool! Check 'em out on our store!"));
        }
        ZNPC christo = ZNPC.find(1002);

        if(event.getNpc().equals(christo)) {
            player.sendMessage(Color.prison("NPC", "Casino up ahead! I've added some pretty fun stuff!"));
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
        ZNPC food = ZNPC.find(1006);

        if(event.getNpc().equals(food)) {
            player.sendMessage(Color.prison("NPC", "Meatloaf, again..."));
        }
        ZNPC charlie = ZNPC.find(1007);

        if(event.getNpc().equals(charlie)) {
            player.sendMessage(Color.prison("NPC", "Be careful out there!"));
        }
        ZNPC noodles = ZNPC.find(1008);

        if(event.getNpc().equals(noodles)) {
            player.sendMessage(Color.prison("NPC", "They say the casino is pretty epic! Go check it out!"));
        }
        ZNPC zay = ZNPC.find(1009);

        if(event.getNpc().equals(zay)) {
            player.sendMessage(Color.prison("NPC", "Sorry I can't talk right now, I'm planning season 2!"));
        }
    }



}
