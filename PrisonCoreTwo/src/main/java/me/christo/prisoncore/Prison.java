package me.christo.prisoncore;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import me.christo.prisoncore.commands.*;
import me.christo.prisoncore.commands.admin.GenerateMinesCommand;
import me.christo.prisoncore.commands.admin.PlayerInfoCommand;
import me.christo.prisoncore.commands.admin.SetPlayerRankCommand;
import me.christo.prisoncore.events.*;
import me.christo.prisoncore.managers.*;
import me.christo.prisoncore.guis.FormattedGUIs;
import me.christo.prisoncore.scoreboard.ScoreboardManager;
import me.christo.prisoncore.sets.ZeusSet;
import me.christo.prisoncore.utils.PlayerDataConfig;
import me.christo.prisoncore.utils.Util;
import net.myntora.core.core.command.CommandLib;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public final class Prison extends JavaPlugin {

    public static Prison instance;

    @Getter
    private ScoreboardManager scoreboardManager;

    @Getter
    public Manager manager;

    @Override
    public void onEnable() {


        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();



        instance = this;

        loadManagers();
        loadCommands();

        getServer().getPluginManager().registerEvents(new FirstJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new FriendlyFireEvent(), this);
        getServer().getPluginManager().registerEvents(new GangChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new CratesCommand(), this);
        getServer().getPluginManager().registerEvents(new ZeusSet(), this);
        getServer().getPluginManager().registerEvents(new CellEvents(), this);

        getCommand("gang").setExecutor(new GangCommand());
        getCommand("zone").setExecutor(new ZoneCommand());
        getCommand("crates").setExecutor(new CratesCommand());
        getCommand("pickaxe").setExecutor(new GivePickaxeCommand());
        getCommand("set").setExecutor(new GiveSetCommand());
        getCommand("format").setExecutor(new FormattedGUIs());

        getCommand("goals").setExecutor(new GoalsCommand());
        //getServer().getPluginManager().registerEvents(new BlockGoalEvent(), this);

        Sets.loadFile();
        Gangs.loadFile();
        Mines.loadFile();
        Zones.loadFile();
        Goals.loadFile();
        Crates.loadFile();
        Cells.loadFile();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Util.players.put(p.getUniqueId(), new PlayerDataConfig(this, p.getUniqueId()));
        }
        loadFiles();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static Prison getInstance() {
        return instance;
    }

    private WorldGenerator makeWorld() {
        WorldGenerator gen = new WorldGenerator();
        WorldCreator wc = new WorldCreator("prison_world");
        wc.generateStructures(false);
        wc.generator(gen);
        Bukkit.getServer().createWorld(wc);
        return gen;
    }

    public void loadFiles() {
        for (File f : new File(getDataFolder() + File.separator + "players").listFiles()) {
            PlayerDataConfig pdc = new PlayerDataConfig(this, UUID.fromString(f.getName().replaceFirst(".yml", "")));
            Util.players.put(pdc.getId(), pdc);
        }
    }
    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        return (WorldGuardPlugin) plugin;
    }
    private void loadManagers() {
        scoreboardManager = new ScoreboardManager(this);
    }
    private void loadCommands() {
        new CommandLib().setupBukkit(this)
                .register(new CheckBlocksBrokenCommand())
                .register(new GenerateMinesCommand())
                .register(new TeleportToMineCommand())
                .register(new RankupCommand())
                .register(new PlayerInfoCommand())
                .register(new SetPlayerRankCommand())
                .register(new CellCommand())
                .register(new GuiTestCommand())
                .register(new AuctionCommand());
    }

}
