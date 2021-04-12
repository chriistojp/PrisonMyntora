package me.christo.prisoncore.events;


import me.christo.prisoncore.boosts.boosts.Farming;
import me.christo.prisoncore.boosts.boosts.Looting;
import me.christo.prisoncore.boosts.boosts.Mega;
import me.christo.prisoncore.boosts.boosts.PVP;
import me.christo.prisoncore.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import sun.java2d.pipe.NullPipe;

public class BoosterRightClickEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            try {
                Player p = e.getPlayer();
                if (p.getItemInHand().hasItemMeta()) {
                    if (p.getItemInHand().getItemMeta().hasDisplayName()) {
                        if (p.getItemInHand().getItemMeta().getDisplayName().equals(Util.color("&c&lPVP" + " BOOSTER"))) {
                            PVP.setActive();
                            p.setItemInHand(new ItemStack(Material.AIR));
                        }
                        if (p.getItemInHand().getItemMeta().getDisplayName().equals(Util.color("&b&lLOOTING" + " BOOSTER"))) {
                            Looting.setActive();
                            p.setItemInHand(new ItemStack(Material.AIR));
                        }
                        if (p.getItemInHand().getItemMeta().getDisplayName().equals(Util.color("&a&lFARMING" + " BOOSTER"))) {
                            Farming.setActive();
                            p.setItemInHand(new ItemStack(Material.AIR));
                        }
                        if (p.getItemInHand().getItemMeta().getDisplayName().equals(Util.color("&4&lM&c&lE&e&lG&a&lA&7 &9&lB&1&lO&5&lO&4&lS&c&lT&e&lE&a&lR"))) {

                            for (int i = 0; i < 4; i++) {
                                final Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
                                FireworkMeta fm = f.getFireworkMeta();

                                fm.addEffect(FireworkEffect.builder()

                                        .flicker(true)
                                        .trail(true)
                                        .with(FireworkEffect.Type.STAR)
                                        .with(FireworkEffect.Type.BALL)
                                        .with(FireworkEffect.Type.BALL_LARGE)
                                        .withColor(org.bukkit.Color.PURPLE)
                                        .withColor(Color.WHITE)
                                        .build());

                                fm.setPower(0);
                                f.setFireworkMeta(fm);
                            }

                            Mega.setActive();
                            p.setItemInHand(new ItemStack(Material.AIR));
                        }
                    }
                }
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }

        }

    }

}
