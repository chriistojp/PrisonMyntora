package me.christo.prisoncore.pickaxe.enchants;


import me.christo.prisoncore.pickaxe.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.entity.Player;

public class Fortune {

    public static int getUpgradeCost(int currentLevel) {
        int originalCost = 1000;
        return currentLevel * originalCost;
    }

    public static void addFortune(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int cost = getUpgradeCost(profile.getData().getPrisonFortuneLevel().getAmount());

        if(profile.getData().getPrisonFortuneLevel().getAmount() == 10) {
            p.sendMessage(Color.prison("Enchants", "You've Reached Max Level!"));
            return;
        }
        if(profile.getData().getPrisonMoney().getAmount() >= cost) {
            profile.getData().getPrisonFortuneLevel().setAmount(profile.getData().getPrisonFortuneLevel().getAmount() + 1);


            p.setItemInHand(StarterPickaxe.getPlayersPickaxe(p));
            p.getOpenInventory().close();
            EnchantsGUI.showEnchantsGui(p);

        } else {
            p.sendMessage(Color.prison("Enchantments", "You can't afford that!"));
        }

        //8154128841

    }

}
