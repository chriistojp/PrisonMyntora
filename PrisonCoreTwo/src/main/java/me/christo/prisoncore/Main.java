package me.christo.prisoncore;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.christo.prisoncore.CoreEvents.FirstJoinEvent;
import me.christo.prisoncore.Crates.Commands.CratesCommand;
import me.christo.prisoncore.Crates.Crates;
import me.christo.prisoncore.Economy.Commands.BalanceCommand;
import me.christo.prisoncore.Economy.Commands.EconomyCommand;
import me.christo.prisoncore.Economy.Commands.PayCommand;
import me.christo.prisoncore.Economy.Economy;
import me.christo.prisoncore.Gangs.Commands.GangCommand;
import me.christo.prisoncore.Gangs.Events.FriendlyFireEvent;
import me.christo.prisoncore.Gangs.Events.GangChatEvent;
import me.christo.prisoncore.Gangs.Gangs;
import me.christo.prisoncore.Goals.Commands.GoalsCommand;
import me.christo.prisoncore.Goals.Events.BlockGoalEvent;
import me.christo.prisoncore.Goals.Goals;
import me.christo.prisoncore.Mines.Commands.CreateMineCommand;
import me.christo.prisoncore.Mines.Commands.FillCommand;
import me.christo.prisoncore.Mines.Commands.GiveSelectorCommand;
import me.christo.prisoncore.Mines.Events.SelectEvent;
import me.christo.prisoncore.Mines.Mines;
import me.christo.prisoncore.Pickaxe.Commands.GivePickaxeCommand;
import me.christo.prisoncore.Pickaxe.Events.BlockCountEvent;
import me.christo.prisoncore.Pickaxe.StarterPickaxe;
import me.christo.prisoncore.Sets.Commands.GiveSetCommand;
import me.christo.prisoncore.Sets.IndivdualSets.ZeusSet;
import me.christo.prisoncore.Sets.Sets;
import me.christo.prisoncore.Utils.Util;
import me.christo.prisoncore.Zones.Commands.ZoneCommand;
import me.christo.prisoncore.Zones.Zones;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {


        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();



        instance = this;

        getServer().getPluginManager().registerEvents(new FirstJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new FriendlyFireEvent(), this);
        getServer().getPluginManager().registerEvents(new GangChatEvent(), this);
        getServer().getPluginManager().registerEvents(new SelectEvent(), this);
        getServer().getPluginManager().registerEvents(new BlockCountEvent(), this);
        getServer().getPluginManager().registerEvents(new CratesCommand(), this);
        getServer().getPluginManager().registerEvents(new ZeusSet(), this);

        getCommand("gang").setExecutor(new GangCommand());
        getCommand("eco").setExecutor(new EconomyCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("mineselector").setExecutor(new GiveSelectorCommand());
        getCommand("createmine").setExecutor(new CreateMineCommand());
        getCommand("fill").setExecutor(new FillCommand());
        getCommand("zone").setExecutor(new ZoneCommand());
        getCommand("crates").setExecutor(new CratesCommand());
        getCommand("pickaxe").setExecutor(new GivePickaxeCommand());
        getCommand("set").setExecutor(new GiveSetCommand());

        getCommand("goals").setExecutor(new GoalsCommand());
        //getServer().getPluginManager().registerEvents(new BlockGoalEvent(), this);

        Sets.loadFile();
        Gangs.loadFile();
        Economy.loadFile();
        Mines.loadFile();
        Zones.loadFile();
        Goals.loadFile();
        StarterPickaxe.loadFile();
        Crates.loadFile();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Util.players.put(p.getUniqueId(), new me.christo.prisoncore.PlayerDataConfig(this, p.getUniqueId()));
        }
        loadFiles();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }

    public void loadFiles() {
        for (File f : new File(getDataFolder() + File.separator + "players").listFiles()) {
            me.christo.prisoncore.PlayerDataConfig pdc = new me.christo.prisoncore.PlayerDataConfig(this, UUID.fromString(f.getName().replaceFirst(".yml", "")));
            Util.players.put(pdc.getId(), pdc);
        }
    }
    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        return (WorldGuardPlugin) plugin;
    }

}
