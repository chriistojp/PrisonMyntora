package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.PrisonPlayer;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Gangs {

    private static File gangsFile;
    private static FileConfiguration gangs;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return gangs;

    }


    public static void loadFile() {
        gangsFile = new File(Prison.getInstance().getDataFolder(), "gangs.yml");
        if (!gangsFile.exists()) {
            Prison.getInstance().saveResource("gangs.yml", false);
        }
        gangs = YamlConfiguration.loadConfiguration(gangsFile);

        try {
            gangs.save(gangsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfExists(String gang) {

        if(gangs.getConfigurationSection("Gangs") == null) {
            return false;
        }

        for(String key : gangs.getConfigurationSection("Gangs").getKeys(false)) {
            if(gang.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    public static void save() {
        try {
            gangs.save(gangsFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public static void addMember(String gang, Player p) {

        List<String> members = gangs.getStringList("Gangs." + gang + ".members");
        members.add(p.getName());
        gangs.set("Gangs." + gang + ".members", members);

        save();

    }

    public static void kickFromGang(Player sender, Player target, String gang) {

        PrisonPlayer player = new PrisonPlayer(sender.getUniqueId());
        PrisonPlayer kicked = new PrisonPlayer(target.getUniqueId());

        if(player.isGangMember()) {
            player.sendMessage("you dont have perms to do that");
            return;
        }

        if(player.getGang().equals(kicked.getGang())) {
            if (kicked.isGangMember()) {
                List<String> members = gangs.getStringList("Gangs." + gang + ".members");
                members.remove(target.getName());
                gangs.set("Gangs." + gang + ".members", members);

                kicked.sendMessage(Util.color(gangs.getString("Messages.kicked")));
            }
            if (kicked.isGangAdmin()) {
                if(player.isGangLeader()) {
                    List<String> members = gangs.getStringList("Gangs." + gang + ".admins");
                    members.remove(target.getName());
                    gangs.set("Gangs." + gang + ".admins", members);
                    kicked.sendMessage(Util.color(gangs.getString("Messages.kicked")));
                } else {
                    player.sendMessage("you must be the owner to kick admins");
                    return;
                }
            }
            save();

            kicked.setGang(false);
            kicked.setGangName("");

            Gangs.messageAllPlayers(player.getGang(), getFile().getString("Messages.kickedPlayer").replaceAll("%player%", kicked.getName()));
        } else {
            sender.sendMessage("you guys arent in the same gang");
        }
    }

    public static void messageAllPlayers(String gang, String message) {


        for(String s : gangs.getStringList("Gangs." + gang + ".members")) {

            Player p = Bukkit.getPlayer(s);
            assert p != null;
            p.sendMessage(Util.color(message));

        }
        for(String s : gangs.getStringList("Gangs." + gang + ".admins")) {

            Player p = Bukkit.getPlayer(s);
            assert p != null;
            p.sendMessage(Util.color(message));

        }

        Player p = Bukkit.getPlayer(Objects.requireNonNull(gangs.getString("Gangs." + gang + ".leader")));
        assert p != null;
        p.sendMessage(Util.color(message));




    }

    public static void makeLeader(Player sender, Player target) {



        PrisonPlayer oldLeader = new PrisonPlayer(sender.getUniqueId());
        PrisonPlayer newLeader = new PrisonPlayer(target.getUniqueId());

        gangs.set("Gangs." + oldLeader.getGang() + ".leader", newLeader.getName());

        List<String> admins = gangs.getStringList("Gangs." + oldLeader.getGang() + ".admins");
        admins.add(oldLeader.getName());
        gangs.set("Gangs." + oldLeader.getGang() + ".admins", admins);

        //removing old leader
        List<String> members = gangs.getStringList("Gangs." + oldLeader.getGang() + ".members");
        if(members.contains(newLeader.getName())) {
            members.remove(newLeader.getName());
            gangs.set("Gangs." + oldLeader.getGang() + ".members", members);
        }
        List<String> oldAdmin = gangs.getStringList("Gangs." + oldLeader.getGang() + ".admins");
        if(oldAdmin.contains(newLeader.getName())) {
            oldAdmin.remove(newLeader.getName());
            gangs.set("Gangs." + oldLeader.getGang() + ".admins", oldAdmin);
        }
        
        save();
    }





    public static HashMap<Player, String> inviteHash = new HashMap<>();

}
