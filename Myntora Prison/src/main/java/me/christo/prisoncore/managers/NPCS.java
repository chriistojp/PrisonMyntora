package me.christo.prisoncore.managers;


import ak.znetwork.znpcservers.ServersNPC;
import ak.znetwork.znpcservers.events.NPCInteractEvent;
import ak.znetwork.znpcservers.npc.ZNPC;
import ak.znetwork.znpcservers.npc.enums.NPCType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CommandTrait;
import net.citizensnpcs.trait.SkinTrait;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class NPCS implements Listener {


    public static void spawnNPCS() {

        int lastGenerated = Prison.getInstance().getConfig().getInt("lastGenerated");

        for(int i = lastGenerated; lastGenerated >= 0; lastGenerated--) {

            System.out.println("current number " + lastGenerated);

            Location loc = new Location(Bukkit.getWorld("prison_world"), (lastGenerated * 100) + 5, 71, (lastGenerated) * 100, 90, 0);
            ServersNPC.createNPC(lastGenerated, NPCType.PLAYER, loc, "Bob");

            System.out.println(i + " ");
            System.out.println(loc + " ");

        }
    }
    @EventHandler
    public void onNpcInteract(NPCInteractEvent event) {
        Player p = (Player) event.getPlayer();


        if(event.getNpc().getEntityID() <= 1000) {
            p.performCommand("pmine");

        }

    }
}
