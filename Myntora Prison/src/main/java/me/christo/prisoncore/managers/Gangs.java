package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Gangs {

    private static File gangsFile;
    private static FileConfiguration gangs;

    public static List<Player> gangChat = new ArrayList<>();

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

        Profile player = Core.getInstance().getProfileManager().getProfile(sender);
        Profile kicked = Core.getInstance().getProfileManager().getProfile(target);

        kicked.getData().getPrisonGangName().setCell(null);
        kicked.getData().save();

        if(player.getData().getIsGangMember().getStatus()) {
            sender.sendMessage(Color.prison("Gangs", "You don't have permission to do that!"));
            return;
        }

        if(player.getData().getPrisonGangName().getCell().equals(kicked.getData().getPrisonGangName().getCell())) {
            if (kicked.getData().getIsGangMember().getStatus()) {
                List<String> members = gangs.getStringList("Gangs." + gang + ".members");
                members.remove(target.getName());
                gangs.set("Gangs." + gang + ".members", members);

                target.sendMessage(Color.main("Gangs", "You have been kicked from your gang!"));
            }
            if (kicked.getData().getIsGangAdmin().getStatus()) {
                if(player.getData().getIsGangOwner().getStatus()) {
                    List<String> members = gangs.getStringList("Gangs." + gang + ".members");
                    members.remove(target.getName());
                    gangs.set("Gangs." + gang + ".members", members);
                    target.sendMessage(Color.main("Gangs", "You have been kicked from your gang!"));
                } else {
                    sender.sendMessage(Color.prison("Gangs", "You must be the gang owner to kick admins!"));
                    return;
                }
            }
            save();

            kicked.getData().getPrisonGangName().setCell("none");
            kicked.getData().getIsGangAdmin().setStatus(false);
            kicked.getData().getIsGangMember().setStatus(false);

            kicked.getData().save();



            Gangs.messageAllPlayers(gang, Util.color("&d" + target.getName() + "&7 has been kicked from the gang!"));
        } else {
            sender.sendMessage(Color.prison("Gangs", "That player is not in your gang!"));
        }
    }

    public static void messageAllPlayers(String gang, String message) {
        for(String s : gangs.getStringList("Gangs." + gang + ".members")) {
            Player p = Bukkit.getPlayer(s);
            assert p != null;
            p.sendMessage(Util.color("&d[Gang Chat] " + message));

        }
//        Player p = Bukkit.getPlayer(Objects.requireNonNull(gangs.getString("Gangs." + gang + ".leader")));
//        assert p != null;
//        p.sendMessage(Util.color(message));
    }
    public static void gangChatMessage(Player originalPlayer, String gang, String message) {
        for(String s : gangs.getStringList("Gangs." + gang + ".members")) {
            Player p = Bukkit.getPlayer(s);
            assert p != null;
            p.sendMessage(Util.color("&d[Gang Chat] " + originalPlayer.getName() + ": &7" + message));

        }
//        Player p = Bukkit.getPlayer(Objects.requireNonNull(gangs.getString("Gangs." + gang + ".leader")));
//        assert p != null;
//        p.sendMessage(Util.color(message));
    }

    public static void makeLeader(Player sender, Player target) {


        Profile oldLeader = Core.getInstance().getProfileManager().getProfile(sender);
        Profile newLeader = Core.getInstance().getProfileManager().getProfile(target);



        oldLeader.getData().getIsGangOwner().setStatus(false);
        oldLeader.getData().getIsGangAdmin().setStatus(true);

        newLeader.getData().getIsGangAdmin().setStatus(false);
        newLeader.getData().getIsGangOwner().setStatus(true);

        sender.sendMessage(Color.prison("Gangs", "You have given ownership to &d" + target.getName() + "!"));
        target.sendMessage(Color.prison("Gangs", "You are now the leader of your gang!"));

//        gangs.set("Gangs." + oldLeader.getGang() + ".leader", newLeader.getName());



//        List<String> admins = gangs.getStringList("Gangs." + oldLeader.getGang() + ".admins");
//        admins.add(oldLeader.getName());
//        gangs.set("Gangs." + oldLeader.getGang() + ".admins", admins);
//
//        //removing old leader
//        List<String> members = gangs.getStringList("Gangs." + oldLeader.getGang() + ".members");
//        if(members.contains(newLeader.getName())) {
//            members.remove(newLeader.getName());
//            gangs.set("Gangs." + oldLeader.getGang() + ".members", members);
//        }
//        List<String> oldAdmin = gangs.getStringList("Gangs." + oldLeader.getGang() + ".admins");
//        if(oldAdmin.contains(newLeader.getName())) {
//            oldAdmin.remove(newLeader.getName());
//            gangs.set("Gangs." + oldLeader.getGang() + ".admins", oldAdmin);
//        }

        save();
    }





    public static HashMap<Player, String> inviteHash = new HashMap<>();

}