package me.christo.prisoncore.managers;


import me.christo.prisoncore.Prison;
import me.christo.prisoncore.utils.Util;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.myntora.core.core.Core;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Auctions {

    public static List<Player> auctionQueue = new ArrayList<>();
    public static HashMap<Player, ItemStack> auctionPlayerItem = new HashMap<>();
    public static HashMap<Player, Integer> startingAmountHash = new HashMap<>();
    public static HashMap<Player, Integer> incrementHashmap = new HashMap<>();
    public static HashMap<Player, Boolean> auctionFirstBidStatus = new HashMap<>();
    public static HashMap<Player, Integer> currentBid = new HashMap<>();

    //puts the auctioneer with the player who last bid so we can remove money
    public static HashMap<Player, Player> currentBidder = new HashMap<>();

    public static void startAuction(Player p, ItemStack auctionItem, int startingAmount, int increment) {



        p.sendMessage(Color.prison("Auctions", "You have placed an auction! &d#" + (Auctions.auctionQueue.size() + 1) + " &7in the queue! "));
        p.getInventory().getItemInHand().setType(Material.AIR);

        if(!auctionQueue.contains(p)) {
            auctionQueue.add(p);
        }
        auctionPlayerItem.put(p, auctionItem);
        startingAmountHash.put(p, startingAmount);
        incrementHashmap.put(p, increment);
        auctionFirstBidStatus.put(p, true);

//        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Util
//                .color("test")
//                + "\",\"extra\":[{\"text\":\""
//                + Util.color("test")
//                + "\",\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"" + auctionPlayerItem.get(p)
//                + "\"}}]}");
//
//        PacketPlayOutChat packet = new PacketPlayOutChat(comp, (byte) 0);
//        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);



        if (auctionQueue.indexOf(p) == 0) {

            for(Player player : Bukkit.getOnlinePlayers()) {
                sendItemTooltipMessage(player, Color.prison("Auctions", "An Auction has started for &d(Hover Here)!"), auctionItem);
            }
            Bukkit.broadcastMessage(Util.color("&7Starting Bid: &d$" + startingAmount + " &7Bid Increment: &d$" + increment));
            Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {


                if (auctionFirstBidStatus.get(p) == null) {
                   Bukkit.broadcastMessage("ITS NULLL");
                }
                if (auctionFirstBidStatus.get(p)) {

                    Bukkit.broadcastMessage(Color.prison("Auctions", "Auction ended! No Bids were placed!"));
                    if (p.getInventory().firstEmpty() == -1) {
                        p.sendMessage(Color.prison("Auctions", "Your inventory was full so your item was dropped on the ground."));
                        p.getLocation().getWorld().dropItem(p.getLocation(), auctionItem);
                    } else {
                        p.sendMessage(Color.prison("Auctions", "Your item was returned to your inventory!"));
                        p.getInventory().addItem(auctionItem);
                    }

//                    Bukkit.broadcastMessage("OLD PLAYER " +  auctionQueue.get(0).getName());
//                    Bukkit.broadcastMessage("-- Queue CHECK BEFORE --");
//                    checkQueue();
//                    Bukkit.broadcastMessage("-- ----- --");
                    auctionQueue.remove(0);
                    auctionPlayerItem.remove(p);
                    startingAmountHash.remove(p);
                    incrementHashmap.remove(p);
                    auctionFirstBidStatus.clear();
                    currentBid.remove(p);
                    currentBidder.clear();

//                    Bukkit.broadcastMessage(auctionQueue.get(0).toString());

//                    if(auctionQueue.size() >= 0) {
//                    Bukkit.broadcastMessage("-- Queue CHECK AFTER --");
//                    checkQueue();
//                    Bukkit.broadcastMessage("-- ----- --");
                    if(!auctionQueue.isEmpty()) {

                        Player newPlayer = auctionQueue.get(0);
//                    Bukkit.broadcastMessage("NEW PLAYER " + auctionQueue.get(0).getName());
//                    Bukkit.broadcastMessage(newPlayer.getName() + " " + auctionPlayerItem.get(newPlayer).toString());
//                    Bukkit.broadcastMessage(newPlayer.getName() + " " + startingAmountHash.get(newPlayer).toString());
//                    Bukkit.broadcastMessage(newPlayer.getName() + " " + incrementHashmap.get(newPlayer).toString());

                        auctionFirstBidStatus.put(newPlayer, true);

//                    Bukkit.broadcastMessage("here");

                        Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
                            Bukkit.broadcastMessage(auctionQueue.get(0).getName());
                            Auctions.startAuction(auctionQueue.get(0), auctionPlayerItem.get(newPlayer), startingAmountHash.get(newPlayer), incrementHashmap.get(newPlayer));
                        }, 20 * 5);
                    }

                    return;
                }

                Player winner = currentBidder.get(p);

                Bukkit.broadcastMessage(Color.prison("Auctions", "The Auction has ended! &d" + winner.getName() + " won!"));
                Profile profile = Core.getInstance().getProfileManager().getProfile(winner);

                profile.getData().getPrisonMoney().decreaseAmount(currentBid.get(p));
                winner.sendMessage(Color.prison("Auctions", "You won the auction! &c-$" + currentBid.get(p)));

                if (winner.getInventory().firstEmpty() == -1) {
                    winner.getLocation().getWorld().dropItem(winner.getLocation(), auctionPlayerItem.get(p));
                    winner.sendMessage(Color.prison("Auctions", "Your inventory was full so your item was dropped at your feet."));
                } else {
                    winner.getInventory().addItem(auctionPlayerItem.get(p));
                    winner.sendMessage(Color.prison("Auctions", "Your item has been placed in your inventory!"));
                }


                auctionQueue.remove(0);
                auctionPlayerItem.remove(p);
                startingAmountHash.remove(p);
                incrementHashmap.remove(p);
                auctionFirstBidStatus.clear();
                currentBid.remove(p);
                currentBidder.clear();



                if(!auctionQueue.isEmpty()) {

                    Player newPlayer = auctionQueue.get(0);

//                    Bukkit.broadcastMessage("NEW PLAYER " + auctionQueue.get(0).getName());
//                    Bukkit.broadcastMessage(newPlayer.getName() + " " + auctionPlayerItem.get(newPlayer).toString());
//                    Bukkit.broadcastMessage(newPlayer.getName() + " " + startingAmountHash.get(newPlayer).toString());
//                    Bukkit.broadcastMessage(newPlayer.getName() + " " + incrementHashmap.get(newPlayer).toString());

                    auctionFirstBidStatus.put(newPlayer, true);


                    Bukkit.getScheduler().runTaskLater(Prison.getInstance(), () -> {
                        Bukkit.broadcastMessage("dsee blow");
                        Bukkit.broadcastMessage(auctionQueue.get(0).getName());
                        Auctions.startAuction(auctionQueue.get(0), auctionPlayerItem.get(newPlayer), startingAmountHash.get(newPlayer), incrementHashmap.get(newPlayer));
                    }, 20 * 5);
                }


            }, 20 * 10);

        }

    }

    public static void flush() {

        auctionQueue.clear();
        auctionPlayerItem.clear();
        startingAmountHash.clear();
        incrementHashmap.clear();
        auctionFirstBidStatus.clear();
        currentBid.clear();
        currentBidder.clear();

    }

    public static void checkQueue() {
        for(Player p : auctionQueue) {
            Bukkit.broadcastMessage(p.getName());
        }
    }

    public static Player getCurrentAuction() {

        return auctionQueue.get(0);

    }

    public static boolean hasOngoingAuction() {

        if (auctionQueue.size() == 0) {
            return false;
        }
        return true;

    }

    public static String convertItemStackToJson(ItemStack itemStack) {
        // First we convert the item stack into an NMS itemstack
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        net.minecraft.server.v1_8_R3.NBTTagCompound compound = new NBTTagCompound();
        compound = nmsItemStack.save(compound);

        return compound.toString();
    }
    public static void sendItemTooltipMessage(Player player, String message, ItemStack item) {
        String itemJson = convertItemStackToJson(item);

        // Prepare a BaseComponent array with the itemJson as a text component
        BaseComponent[] hoverEventComponents = new BaseComponent[]{
                new TextComponent(itemJson) // The only element of the hover events basecomponents is the item json
        };

        // Create the hover event
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents);

        /* And now we create the text component (this is the actual text that the player sees)
         * and set it's hover event to the item event */
        TextComponent component = new TextComponent(message);
        component.setHoverEvent(event);

        // Finally, send the message to the player
        player.spigot().sendMessage(component);
    }

}
