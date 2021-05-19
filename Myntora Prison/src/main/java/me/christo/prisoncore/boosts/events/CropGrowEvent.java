package me.christo.prisoncore.boosts.events;


import me.christo.prisoncore.boosts.boosts.Farming;
import me.christo.prisoncore.boosts.boosts.Mega;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

public class CropGrowEvent implements Listener {

    @EventHandler
    public void onGrow(BlockGrowEvent e) {

        if(Farming.isActive() || Mega.isActive()) {
            e.getBlock().setData((byte) (e.getBlock().getData() + 1));
        }

    }


}
