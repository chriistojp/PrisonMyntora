package me.christo.prisoncore.pickaxe.enchants;


import me.christo.prisoncore.managers.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.entity.Player;

public class Haste {

    public static int getUpgradeCost(int currentLevel) {
        int originalCost = 1000;
        return currentLevel * originalCost;
    }

    public static void addHaste(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int cost = getUpgradeCost(profile.getData().getPrisonHasteLevel().getAmount());

        if(profile.getData().getPrisonHasteLevel().getAmount() == 3) {
            p.sendMessage(Color.prison("Enchants", "You've Reached Max Level!"));
            return;
        }
        if(profile.getData().getPrisonMoney().getAmount() >= cost) {
            profile.getData().getPrisonHasteLevel().setAmount(profile.getData().getPrisonHasteLevel().getAmount() + 1);
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
