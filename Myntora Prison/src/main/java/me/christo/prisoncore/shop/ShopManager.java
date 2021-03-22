package me.christo.prisoncore.shop;


import me.christo.prisoncore.Manager;
import me.christo.prisoncore.Prison;
import me.christo.prisoncore.shop.commands.ShopCommand;
import me.christo.prisoncore.shop.gui.ShopGUI;
import me.christo.prisoncore.shop.util.Items;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.CommandLib;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import net.myntora.core.core.util.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ShopManager extends Manager {

    public ShopManager(Prison plugin) {
        super(plugin);
        new CommandLib().setupBukkit(plugin)
                .register(new ShopCommand());
        Color.log("Enabled ShopManager!");
    }

    public static void openShop(Player player, String pageName, String category) {
        Profile profile = Core.getInstance().getProfileManager().getProfile(player);
        Gui gui = new Gui(Color.translate("&5&l" + pageName), 54);

        gui.i(53, Material.DOUBLE_PLANT, Color.translate("&7Balance: &6$" + profile.getData().getSurvivalBalance().getAmount()));
        gui.i(52, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 4), Color.translate("&6&lBACK"), "", Color.translate("&7Click to go back to the &6&nMain&r &7page."));

        gui.onOpen(e -> {
            int count = 0;
            for (Items i : Items.values()) {

                if (Items.getCategory(i).equals(category)) {
//                    if(Items.isMaterial(i)) {
//                        gui.i(count, Items.getMaterial(i), Color.translate("&5&l" + Items.getMaterial(i).toString().toUpperCase().replaceAll("_", " ")), "",
//                                "&7Sell Price: &a$" + Items.getSellPrice(i), "", "&7&o(( MMB to Sell All ))");
//                        count++;
//                    } else {
//                        gui.i(count, Items.getItemStack(i), Color.translate("&5&l" + Items.getItemStack(i).getType().toString().toUpperCase().replaceAll("_", " ")), "",
//                                "&7Sell Price: &a$" + Items.getSellPrice(i), "", "&7&o(( MMB to Sell All ))");
//                    }
                    if (Items.getMaterial(i) != null) {
                        gui.i(count, Items.getMaterial(i), Color.translate("&5&l" + Items.getMaterial(i).toString().toUpperCase().replaceAll("_", " ")), "",
                                "&7Sell Price: &a$" + Items.getSellPrice(i), "", "&7&o(( MMB to Sell All ))");
                        count++;
                    } else {
                        gui.i(count, Items.getItemStack(i), Color.translate("&5&l" + Items.getItemStack(i).getType().toString().toUpperCase().replaceAll("_", " ")), "",
                                "&7Sell Price: &a$" + Items.getSellPrice(i), "", "&7&o(( MMB to Sell All ))");
                        count++;
                    }
                }
            }

        });
        gui.onClick(e -> {
            e.setCancelled(true);
            if(e.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            if(e.getCurrentItem().getType() == null) {
                return;
            }
            if (e.getSlot() == 53) {
                return;
            }
            if (e.getCurrentItem() == null) {
                return;
            }
            if (e.getSlot() == 52) {
                ShopGUI.getMainShopGui().show((Player) e.getWhoClicked());
                return;
            }
            Items i = Items.translateFromMaterial(e.getCurrentItem().getType());
            boolean b = false;

            if (e.getClick().equals(ClickType.LEFT) || e.getClick().equals(ClickType.RIGHT)) {
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item == null) {
                        continue;
                    }
                    if (item.getType().equals(e.getCurrentItem().getType())) {
                        if(item.getData().equals(e.getCurrentItem().getData())) {
                            double sellPrice = Items.getSellPrice(i);
                            player.sendMessage(Color.main("Shop", "+ &a$" + sellPrice));
                            item.setAmount(item.getAmount() - 1);
                            profile.getData().getSurvivalBalance().increaseAmount((int) sellPrice);
                            b = true;
                        }
                    }
                }
                if (!b) {
                    player.sendMessage(Color.main("Shop", "You don't have any of that item!"));
                }
            }
            if (e.getClick().equals(ClickType.MIDDLE)) {
                int total = 0;
                double sellPrice = 0;
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item == null) {
                        continue;
                    }
                    if (item.getType().equals(e.getCurrentItem().getType())) {
                        if(item.getData().equals(e.getCurrentItem().getData())) {
                            total = total + item.getAmount();
                            sellPrice = Items.getSellPrice(i);
                            player.getInventory().removeItem(item);
                            b = true;
                        }
                    }
                }
                if (!b) {
                    player.sendMessage(Color.main("Shop", "You don't have any of that item!"));
                    return;
                }
                player.sendMessage(Color.main("Shop", "+ &a$" + sellPrice * total));
                profile.getData().getSurvivalBalance().increaseAmount((int) sellPrice * total);
            }
        });
        gui.show(player);
    }


}
