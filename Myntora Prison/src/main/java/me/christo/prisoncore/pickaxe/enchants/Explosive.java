package me.christo.prisoncore.pickaxe.enchants;


import me.christo.prisoncore.managers.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.entity.Player;

public class Explosive {

    //Honestly just start at $500 for all of them for level one and then up the cost by like $1000 or somethin and go to level 10


    public static int getUpgradeCost(int currentLevel) {
        int originalCost = 10000;
        return currentLevel * originalCost;
    }

    public static void addExplosive(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int cost = getUpgradeCost(profile.getData().getPrisonExplosiveLevel().getAmount());

        if(profile.getData().getPrisonExplosiveLevel().getAmount() == 10) {
            p.sendMessage(Color.prison("Enchants", "You've Reached Max Level!"));
            return;
        }
        if(profile.getData().getPrisonMoney().getAmount() >= cost) {
            profile.getData().getPrisonExplosiveLevel().setAmount(profile.getData().getPrisonExplosiveLevel().getAmount() + 1);
            profile.getData().save();

            p.setItemInHand(StarterPickaxe.getPlayersPickaxe(p));
            p.getOpenInventory().close();
            EnchantsGUI.showEnchantsGui(p);

        } else {
            p.sendMessage(Color.prison("Enchantments", "You can't afford that!"));
        }

        //8154128841

    }
}
