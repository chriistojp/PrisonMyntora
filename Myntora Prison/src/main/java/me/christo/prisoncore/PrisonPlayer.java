package me.christo.prisoncore;


import me.christo.prisoncore.managers.Gangs;
import me.christo.prisoncore.managers.Goals;
import me.christo.prisoncore.utils.PlayerDataConfig;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

public class PrisonPlayer {

    Player p;
    FileConfiguration config;
    PlayerDataConfig player;
    DecimalFormat df = new DecimalFormat("#.##");


    public PrisonPlayer(Player p) {
        this.p = p;
        player = Util.players.get(p.getUniqueId());
    }

    public PrisonPlayer(UUID p) {
        this.p = Bukkit.getOfflinePlayer(p).getPlayer();
        player = Util.players.get(p);
        config = Util.players.get(p).get();
    }

    public boolean hasGang() {
        if (config.getBoolean("gang")) {
            return true;
        }
        return false;
    }

    public void setGang(boolean b) {
        config.set("gang", b);
        player.save();
    }

    public void setGangName(String name) {
        config.set("gangName", name);
        player.save();
    }

    public String getName() {
        return p.getName();
    }

    public String getGang() {
        return config.getString("gangName");
    }

    public boolean isGangLeader() {

        return Gangs.getFile().getString("Gangs." + getGang() + ".leader").equalsIgnoreCase(p.getName());
    }

    public boolean isGangMember() {
        if (Gangs.getFile().getStringList("Gangs." + getGang() + ".members").contains(p.getName())) {
            return true;
        }
        return false;
    }

    public boolean isGangAdmin() {
        if (Gangs.getFile().getStringList("Gangs." + getGang() + ".admins").contains(p.getName())) {
            return true;
        }
        return false;
    }

    public void promoteMember(PrisonPlayer p) {

        List<String> members = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".members");
        members.remove(p.getName());

        Gangs.getFile().set("Gangs." + p.getGang() + ".members", null);
        Gangs.getFile().set("Gangs." + p.getGang() + ".members", members);

        List<String> admins = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".admins");
        admins.add(p.getName());

        Gangs.getFile().set("Gangs." + p.getGang() + ".admins", null);
        Gangs.getFile().set("Gangs." + p.getGang() + ".admins", admins);

        Gangs.save();


    }

    public void demoteMember(PrisonPlayer p) {


        List<String> members = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".members");
        members.add(p.getName());

        Gangs.getFile().set("Gangs." + p.getGang() + ".members", null);
        Gangs.getFile().set("Gangs." + p.getGang() + ".members", members);

        List<String> admins = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".admins");
        admins.remove(p.getName());

        Gangs.getFile().set("Gangs." + p.getGang() + ".admins", null);
        Gangs.getFile().set("Gangs." + p.getGang() + ".admins", admins);

        Gangs.save();


    }


    public void leaveGang() {

        PrisonPlayer p = new PrisonPlayer(player.getId());
        if (p.hasGang()) {

            if (p.isGangLeader()) {

                p.sendMessage(Util.color(Gangs.getFile().getString("Messages.leaderLeave")));
                return;
            }


            if (p.isGangMember()) {
                List<String> members = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".members");
                members.remove(p.getName());

                Gangs.getFile().set("Gangs." + p.getGang() + ".members", null);
                Gangs.getFile().set("Gangs." + p.getGang() + ".members", members);

                p.sendMessage(Util.color(Gangs.getFile().getString("Messages.leftGang")));


            }
            if (p.isGangAdmin()) {
                List<String> admins = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".admins");
                admins.remove(p.getName());

                Gangs.getFile().set("Gangs." + p.getGang() + ".admins", null);
                Gangs.getFile().set("Gangs." + p.getGang() + ".admins", admins);

                p.sendMessage(Util.color(Gangs.getFile().getString("Messages.leftGang")));

            }
            Gangs.save();

            config.set("gangName", "");
            config.set("gang", false);
            player.save();

            try {

                Gangs.messageAllPlayers(p.getGang(), config.getString("Messages.playerLeft").replaceAll("%player%", p.getName()));
            } catch (NullPointerException e) {

            }
        }
    }

    public void disbandGang() {
        PrisonPlayer p = new PrisonPlayer(player.getId());

        if (p.isGangLeader()) {

            List<String> members = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".members");

            for (String players : members) {

                PrisonPlayer player = new PrisonPlayer(Bukkit.getOfflinePlayer(players).getUniqueId());
                player.setGangName("");
                player.setGang(false);


            }
            List<String> admins = Gangs.getFile().getStringList("Gangs." + p.getGang() + ".admins");
            for (String players : admins) {
                PrisonPlayer player = new PrisonPlayer(Bukkit.getOfflinePlayer(players).getUniqueId());
                player.setGangName("");
                player.setGang(false);

            }

            p.setGangName("");
            p.setGang(false);

            Gangs.getFile().set(p.getGang(), null);

        }

    }

    public void enableFriendlyFire(boolean b) {
        PrisonPlayer p = new PrisonPlayer(player.getId());
        if (p.isGangLeader()) {
            if (b) {
                Gangs.getFile().set("Gangs." + p.getGang() + ".friendlyFire", true);
            } else {
                Gangs.getFile().set("Gangs." + p.getGang() + ".friendlyFire", false);
            }
        }

    }

    public boolean hasGangChat() {

        if (player.get().getBoolean("gangchat")) {
            return true;
        }
        return false;

    }

    public void setGangChat(boolean b) {


        player.get().set("gangchat", b);


    }

    public void sendMessage(String s) {

        p.sendMessage(s);

    }

    /**
     *
     *
     * Start of Economy Methods
     *
     *
     */

    public double getTokens() {

        System.out.println("tttt");
        if(config.isSet("balance")) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
        return Double.parseDouble(df.format(config.getDouble("balance")));

    }

    public void addTokens(double i) {
        player.get().set("balance", config.getDouble("balance") + i);
    }

    public void setTokens(double i) {
        player.get().set("balance", i);
    }
    public void takeTokens(double i) {
        set("balance", Double.valueOf(df.format(config.getDouble("balance") - i)));
    }

    public void resetTokens() {
        set("balance", 0);

    }

    public void set(String path, Object object) {
        player.get().set(path, object);
        player.save();
    }

   /*

   Goals Methods

    */

    public void addGoalProgress(String goal, int amount) {
        if(goal.equalsIgnoreCase("mining")) {
            set("goals.mining.blocks", config.getInt("goals.mining.blocks") + amount);
        }
    }

    public void startGoal(String goal) {

        if (!hasActiveGoal(goal)) {
            if(goal.equalsIgnoreCase("mining")) {
                    set("goals.mining.blocks", 0);
               }





            p.sendMessage(Util.color(Goals.getFile().getString("messages.startedGoal").replaceAll("%goal%", goal)));
           }

    }

    public boolean hasActiveGoal(String goal) {
        if(goal.equalsIgnoreCase("mining")) {
            if (config.isSet("goals.mining.blocks")) {
                return true;
            }
        }
        return false;
    }

    public int getGoalProgress(String goal) {

        if(goal.equalsIgnoreCase("mining")) {
            if(hasActiveGoal("mining")) {
                return config.getInt("goals.mining.blocks");
            }
        }

        return 0;
    }

    public void startGoalCooldown(String goal) {



    }

}
