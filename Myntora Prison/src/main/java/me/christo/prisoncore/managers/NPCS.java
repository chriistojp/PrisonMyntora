package me.christo.prisoncore.managers;


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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class NPCS {

    @Deprecated
    public static void spawnMinesNPC(Player p) throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {

        int lastClaimed = Prison.getInstance().getConfig().getInt("lastMine");

        for(int i = lastClaimed; i > 0; i--) {
        }

    }

    public static void spawnGoalsNPC(Player p, Location loc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {



    }

    public static void spawnMinesNPC(Location loc) {
//
//        Bukkit.broadcastMessage(loc.getX() + " " + loc.getY() + " " + loc.getZ());
//
//        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "chriisto");
//        npc.getTrait(SkinTrait.class).setSkinName("chriisto");
//        npc.setName(Util.color("&e&lMine Management"));
//        npc.getTrait(CommandTrait.class)
//                .addCommand(new CommandTrait.NPCCommandBuilder("pmine", CommandTrait.Hand.BOTH).cooldown(4).player(true));
//
//        CitizensAPI.getNPCRegistry().getByUniqueId(npc.getUniqueId())
//                .spawn(new Location(Bukkit.getWorld("prison_world"), loc.getX(), 71, loc.getZ(), 90, 0));
//
//        Bukkit.broadcastMessage("he got set!");

    }

}
