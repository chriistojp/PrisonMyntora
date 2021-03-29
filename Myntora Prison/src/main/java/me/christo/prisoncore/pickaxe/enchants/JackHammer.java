package me.christo.prisoncore.pickaxe.enchants;


import me.christo.prisoncore.managers.StarterPickaxe;
import me.christo.prisoncore.pickaxe.guis.EnchantsGUI;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.entity.Player;

public class JackHammer {

    public static int getUpgradeCost(int currentLevel) {
        int originalCost = 1000;
        return currentLevel * originalCost;
    }

    public static void addJackHammer(Player p) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);
        int cost = getUpgradeCost(profile.getData().getPrisonJackHammerLevel().getAmount());

        if(profile.getData().getPrisonJackHammerLevel().getAmount() == 10) {
            p.sendMessage(Color.prison("Enchants", "You've Reached Max Level!"));
            return;
        }
        if(profile.getData().getPrisonMoney().getAmount() >= cost) {
            profile.getData().getPrisonJackHammerLevel().setAmount(profile.getData().getPrisonJackHammerLevel().getAmount() + 1);
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
