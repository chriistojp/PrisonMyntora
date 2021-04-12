package me.christo.prisoncore.commands;


import me.christo.prisoncore.shop.ShopManager;
import me.christo.prisoncore.shop.util.Items;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@DynamicCommand(
        name = "sell"
)
public class SellCommand extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;
        Profile profile = Core.getInstance().getProfileManager().getProfile(p);

        if (p.getItemInHand() != null) {
            ItemStack i = p.getItemInHand();
            if (Items.isSellableItem(i)) {
                Items item = Items.translateFromMaterial(i.getType());
                assert item != null;
                double cost = Items.getSellPrice(item) * i.getAmount();
                profile.getData().getPrisonMoney().increaseAmount((int) cost);

                p.sendMessage(Color.main("Sale", "Your sale earned you &d$" + cost + "!"));
                p.setItemInHand(null);


            } else {
                p.sendMessage(Color.prison("Sell", "You cannot sell this item!"));
            }


        } else {
            p.sendMessage(Color.prison("Sell", "You cannot sell air!"));
        }

    }
}
