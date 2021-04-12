package me.christo.prisoncore.commands;


import me.christo.prisoncore.managers.Auctions;
import net.myntora.core.core.Core;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import net.myntora.core.core.data.Profile;
import net.myntora.core.core.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@DynamicCommand(
        name = "auction",
        aliases = {"auc"}
)
public class AuctionCommand extends Command {


    @Override
    public void execute(CommandSender sender, String... args) {

        Player p = (Player) sender;

        if (args.length == 0) {

            p.sendMessage(Color.prison("Auctions", "Help menu for auctions:"));
            p.sendMessage(Color.prison("Help", "/auction start <startingBid> <bidIncrements> - Start an Auction"));
            p.sendMessage(Color.prison("Help", "/auction bid - Places a bid on the current item"));
            p.sendMessage(Color.prison("Help", "/auction info - Sends info about the current auction"));
            p.sendMessage(Color.prison("Help", "/auction cancel - Cancels your pending auction"));
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("bid")) {

                if (Auctions.hasOngoingAuction()) {
                    Profile profile = Core.getInstance().getProfileManager().getProfile(p);

                    Player currentAuctioneer = Auctions.getCurrentAuction();
                    if (currentAuctioneer == null) {
                        p.sendMessage(Color.prison("Auctions", "There is no current auction!"));
                        return;
                    }
                    if(p == currentAuctioneer) {
                        p.sendMessage(Color.prison("Auctions", "You cannot bid on your own auction!"));
                        return;
                    }

                    //if a bid has not been made
                    if (Auctions.auctionFirstBidStatus.get(currentAuctioneer)) {
                        if (profile.getData().getPrisonMoney().getAmount() >= Auctions.startingAmountHash.get(currentAuctioneer)) {

                            Auctions.auctionFirstBidStatus.remove(currentAuctioneer);
                            Auctions.auctionFirstBidStatus.put(currentAuctioneer, false);
                            Auctions.currentBid.put(currentAuctioneer, Auctions.startingAmountHash.get(currentAuctioneer));
                            Auctions.currentBidder.put(currentAuctioneer, p);

                            Bukkit.broadcastMessage(Auctions.currentBidder.get(currentAuctioneer).getName());

                            Bukkit.broadcastMessage(Color.prison("Auctions", p.getName() + " has made the starting bid of &d$" + Auctions.startingAmountHash.get(currentAuctioneer) + "!"));


                        } else {
                            p.sendMessage(Color.prison("Auctions", "You cannot afford the starting bid!"));
                        }
                        //if a bid has been made
                    } else {

                        if (profile.getData().getPrisonMoney().getAmount() >= Auctions.currentBid.get(currentAuctioneer) + Auctions.incrementHashmap.get(currentAuctioneer)) {

                            int bid = Auctions.currentBid.get(currentAuctioneer) + Auctions.incrementHashmap.get(currentAuctioneer);
                            Auctions.currentBid.remove(currentAuctioneer);
                            Auctions.currentBid.put(currentAuctioneer, bid);
                            Auctions.currentBidder.put(currentAuctioneer, p);


                            Bukkit.broadcastMessage(Color.prison("Auctions", p.getName() + " has bid &d$" + bid + "&7 on " + currentAuctioneer.getName() + "'s auction!"));


                        } else {
                            p.sendMessage(Color.prison("Auctions", "You do not have the money to afford that!"));
                        }

                    }

                } else {
                    p.sendMessage(Color.prison("Auctions", "There are no ongoing auctions!"));
                }
            }
            if (args[0].equalsIgnoreCase("clear")) {
                p.sendMessage(Color.prison("Auctions", "All auctions cleared"));
                Auctions.flush();

            }
            if (args[0].equalsIgnoreCase("cancel")) {
                if (Auctions.auctionQueue.contains(p)) {
                    if (!Auctions.auctionQueue.get(0).equals(p)) {

                        Auctions.auctionQueue.remove(p);

                        Auctions.startingAmountHash.remove(p);
                        Auctions.incrementHashmap.remove(p);
                        // Auctions.auctionFirstBidStatus.clear();


                        if (p.getInventory().firstEmpty() == -1) {
                            p.sendMessage(Color.prison("Auctions", "Your inventory was full so your item was dropped on the ground."));
                            p.getLocation().getWorld().dropItem(p.getLocation(), Auctions.auctionPlayerItem.get(p));
                        } else {
                            p.sendMessage(Color.prison("Auctions", "Your item was returned to your inventory!"));
                            p.getInventory().addItem(Auctions.auctionPlayerItem.get(p));
                        }

                        Auctions.auctionPlayerItem.remove(p);

                    } else {
                        p.sendMessage(Color.prison("Auctions", "You cannot cancel an ongoing auction! Sorry!"));
                    }
                } else {
                    p.sendMessage(Color.prison("Auctions", "You aren't in the auction queue!"));
                }
            }
            if (args[0].equalsIgnoreCase("queue")) {

                Auctions.checkQueue();

            }

        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("start")) {
                if (args[1].chars().allMatch(Character::isDigit)) {
                    if (args[2].chars().allMatch(Character::isDigit)) {
                        if (Integer.parseInt(args[1]) >= 100) {
                            if (p.getItemInHand().getType() != Material.AIR) {
                                if (!Auctions.auctionQueue.contains(p)) {

                                    Auctions.startAuction(p, p.getInventory().getItemInHand(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

                                    p.setItemInHand(null);

                                } else {
                                    p.sendMessage(Color.prison("Auctions", "You have a pending auction!"));
                                }
                            } else {
                                p.sendMessage(Color.prison("Auctions", "You cannot auction air!"));
                            }
                        } else {
                            p.sendMessage(Color.prison("Auctions", "Starting bid must me more than $100!"));
                        }

                    } else {
                        p.sendMessage(Color.prison("Auctions", "Please enter a valid number!"));
                    }
                } else {
                    p.sendMessage(Color.prison("Auctions", "Please enter a valid number!"));
                }
            }
        }

    }
}
