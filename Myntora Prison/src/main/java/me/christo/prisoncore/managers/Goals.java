package me.christo.prisoncore.managers;

import me.christo.prisoncore.Prison;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Goals {

    //messages:
    //  miningCompleted: "u have completed mining goal"
    //  updatedRewards: "u have updated %goal%"
    //  startedGoal: "u have started goal %goal%"

    private static File goalsFile;
    private static FileConfiguration goals;

    public static List<String> getConfig(List<String> path) {

        path = getFile().getStringList(path.toString());
        return path;

    }

    public static FileConfiguration getFile() {

        return goals;

    }


    public static void loadFile() {
        goalsFile = new File(Prison.getInstance().getDataFolder(), "goals.yml");
        if (!goalsFile.exists()) {
            Prison.getInstance().saveResource("goals.yml", false);
        }
        goals = YamlConfiguration.loadConfiguration(goalsFile);

        try {
            goals.save(goalsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        try {
            goals.save(goalsFile);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }




    public static void giveGoalRewards(String goal, Player p) {

        if(goal.equalsIgnoreCase("mining")) {
            for(String key : getFile().getConfigurationSection("goals.mining.rewards").getKeys(false)) {

                ItemStack i = getFile().getItemStack("goals.mining.rewards." + key);
                p.getInventory().addItem(i);
            }
        }

    }


}
