package me.christo.prisoncore.pickaxe.events;


import me.christo.prisoncore.managers.StarterPickaxe;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SwitchToPickaxeEvent implements Listener {

    @EventHandler
    public void onHold(PlayerItemHeldEvent e) {
        if (e.getPlayer().getInventory().getItem(e.getNewSlot()) != null && e.getPlayer().getInventory().getItem(e.getNewSlot()).equals(StarterPickaxe.findTool(e.getPlayer()))) {

            Player p = e.getPlayer();
            Profile profile = Core.getInstance().getProfileManager().getProfile(p);

            int hasteLevel = profile.getData().getPrisonHasteLevel().getAmount();
            int speedLevel = profile.getData().getPrisonSpeedLevel().getAmount();
            int jumpLevel = profile.getData().getPrisonJumpLevel().getAmount();

            if(hasteLevel >= 1) {
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, hasteLevel - 1));
            }
            if(speedLevel >= 1) {
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, speedLevel - 1));
            }
            if(jumpLevel >= 1) {
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, jumpLevel - 1));
            }
        } else {
            e.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
            e.getPlayer().removePotionEffect(PotionEffectType.SPEED);
            e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
        }

    }

}
