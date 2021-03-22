package me.christo.prisoncore.managers;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.seocraft.npcs.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class NPCS {

    public static void spawnGoalsNPC(Player p, Location loc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {


        NpcAPI.createNPC("chriisto", loc, true, p, "KingCactus1");


    }

}
