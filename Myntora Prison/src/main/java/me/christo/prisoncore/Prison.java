package me.christo.prisoncore;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import lombok.SneakyThrows;
import me.christo.prisoncore.commands.*;
import me.christo.prisoncore.commands.admin.*;
import me.christo.prisoncore.events.*;
import me.christo.prisoncore.managers.*;
import me.christo.prisoncore.guis.FormattedGUIs;
import me.christo.prisoncore.pMines.PMine;
import me.christo.prisoncore.pMines.commands.PrivateMineCommand;
import me.christo.prisoncore.pickaxe.events.DropPickaxeEvent;
import me.christo.prisoncore.pickaxe.events.PlayerBlockBreakEvent;
import me.christo.prisoncore.pickaxe.events.SwitchToPickaxeEvent;
import me.christo.prisoncore.pickaxe.events.ToolRightClickEvent;
import me.christo.prisoncore.scoreboard.ScoreboardManager;
import me.christo.prisoncore.sets.ZeusSet;
import me.christo.prisoncore.shop.commands.ShopCommand;
import me.christo.prisoncore.zones.commands.ZonesCommand;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.CommandLib;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Prison extends JavaPlugin {

    public static Prison instance;

    @Getter
    private ScoreboardManager scoreboardManager;

    @Getter
    public Manager manager;



    @SneakyThrows
    @Override
    public void onEnable() {

        // 1 shard digger = 25 shards/min
        // 1 token fetcher = 2500 tokens/min //50

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();



        instance = this;

        System.out.println("STARTING NPC GENERATION");
        NPCS.spawnNPCS();
        loadManagers();
        loadCommands();
        EasterEggNPCs.spawnEasterEggNPCs();


//        Bukkit.getScheduler().runTaskTimer(this, () -> {
//            for(Player p : Bukkit.getOnlinePlayers()) {
//
//                Profile profile = Core.getInstance().getProfileManager().getProfile(p);
//                int tokensToAdd = profile.getData().getPrisonPetTokenFinderLevel().getAmount() * 2500;
//                int shardsToAdd = profile.getData().getPrisonPetShardFinderLevel().getAmount() * 25;
//
//
//                profile.getData().getPrisonShards().increaseAmount(shardsToAdd);
//                profile.getData().getPrisonMoney().increaseAmount(tokensToAdd);
//
//
//
//
//            }
//        }, 20 * 60, 20 * 60);

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new CratesCommand(), this);
        getServer().getPluginManager().registerEvents(new ZeusSet(), this);
        getServer().getPluginManager().registerEvents(new CellEvents(), this);
        getServer().getPluginManager().registerEvents(new MineSelectorEvent(), this);
        getServer().getPluginManager().registerEvents(new FishCatchEvent(), this);
        getServer().getPluginManager().registerEvents(new FarmMineSelectorEvent(), this);
        getServer().getPluginManager().registerEvents(new GangChatEvent(), this);

        getServer().getPluginManager().registerEvents(new ToolRightClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DropPickaxeEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakEvent(), this);
        getServer().getPluginManager().registerEvents(new SwitchToPickaxeEvent(), this);
        getServer().getPluginManager().registerEvents(new BoosterRightClickEvent(), this);
        getServer().getPluginManager().registerEvents(new QuickToolEvent(), this);

        getServer().getPluginManager().registerEvents(new PetsClickEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(), this);

        getServer().getPluginManager().registerEvents(new EasterEggNPCs(), this);
        getServer().getPluginManager().registerEvents(new NPCS(), this);

        getCommand("zone").setExecutor(new ZoneCommand());
        getCommand("crates").setExecutor(new CratesCommand());
        getCommand("set").setExecutor(new GiveSetCommand());
        getCommand("format").setExecutor(new FormattedGUIs());


        //getServer().getPluginManager().registerEvents(new BlockGoalEvent(), this);

        Sets.loadFile();
        Mines.loadFile();
        Zones.loadFile();
        Goals.loadFile();
        Crates.loadFile();
        Cells.loadFile();
        PMine.loadFile();
        FarmCells.loadFile();
        Gangs.loadFile();
        BlackMarket.loadFile();
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
                .register(new MineCommand())
                .register(new RankupCommand())
                .register(new PlayerInfoCommand())
                .register(new SetPlayerRankCommand())
                .register(new CellCommand())
                .register(new GuiTestCommand())
                .register(new AuctionCommand())
                .register(new ShopCommand())
                .register(new SellCommand())
                .register(new ResetMineCommand())
                .register(new PrivateMineCommand())
                .register(new StartEventCommand())
                .register(new FarmCellCommand())
                .register(new GangCommand())
                .register(new ChristoClearStuff())
                .register(new BlackMarketCommand())
                .register(new BoosterCommand())
                .register(new GivePickaxeCommand())
                .register(new TagSetCommand())
                .register(new GivePetCommand())
                .register(new ShardsBalanceCommand())
                .register(new OpenCasinoCommand())
                .register(new ArmorStandTest())
                .register(new PayCommand())
                .register(new ShardsCommand())
                .register(new ZonesCommand())
                .register(new QuickToolCommand())
                .register(new GambleCommand())
                .register(new InfirmaryCommand())
                .register(new SuicideCommand());
    }

}
