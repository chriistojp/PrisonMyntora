package me.christo.prisoncore.utils;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static DecimalFormat df = new DecimalFormat("#,##0.00");

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String replaceNumbers(Double num) {
        df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
        return df.format(num);
    }

    public static DecimalFormat df2 = new DecimalFormat("#,##0");

    public static String replaceNumbers(long num) {
        df2.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.getDefault()));
        return df2.format(num);
    }

    public static boolean checkChance(int chance) {


        int random = ThreadLocalRandom.current().nextInt(0, 100);
        if(random <= chance) {
            return true;
        }
        return false;

    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "NORTH";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NORTH";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "EAST";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "EAST";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "SOUTH";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SOUTH";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "WEST";
        }
        return "";
    }

    public static String formatNumber(long count) {
        if (count < 1000)
            return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        DecimalFormat format = new DecimalFormat("0.#");
        String value = format.format(count / Math.pow(1000, exp));
        return String.format("%s%c", value, "kMBTQ".charAt(exp - 1));
    }

    public static ItemStack addGlow(ItemStack i) {

        i.addUnsafeEnchantment(Enchantment.OXYGEN, 1);

        ItemMeta meta = i.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);
        return i;
    }

    public static String getOppositeCardinal(Player player) {
        try {
            double rotation = (player.getLocation().getYaw() - 180) % 360;
            System.out.println(rotation);
            if (rotation < 0) {
                rotation += 360.0;
            }
            if (0 <= rotation && rotation < 22.5) {
                return "SOUTH";
            } else if (22.5 <= rotation && rotation < 67.5) {
                return "SOUTH";
            } else if (67.5 <= rotation && rotation < 112.5) {
                return "WEST";
            } else if (112.5 <= rotation && rotation < 157.5) {
                return "WEST";
            } else if (157.5 <= rotation && rotation < 202.5) {
                return "NORTH";
            } else if (202.5 <= rotation && rotation < 247.5) {
                return "NORTH";
            } else if (247.5 <= rotation && rotation < 292.5) {
                return "EAST";
            }
        } catch (IllegalArgumentException e) {

        }
        return "NORTH";
    }

    public static Gui createRewardsGui(Player player, String configSection, String configSectionSubName, FileConfiguration file, File baseFile) {



        Gui gui = new Gui("&bDrop Editor", 54);
        gui.show(player);

        gui.onOpen(e -> {

            gui.i(5, 3, new ItemStack(Material.MINECART), "&c&lInfo", "",
                    "&7This is where you edit loot dropped from the envoy.", "",
                    "&7- &7Clicking the block to the right will toggle betwen adding and removing chance.",
                    "&7- Moving an item from slot to slot counts as putting a new item in.");
            gui.i(5, 5, new ItemStack(Material.EMERALD_BLOCK), "&aAdding Chance", "",
                    "&7Right clicking will &a+5&7 chance!");
            gui.fillRow(5, new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()), " ");

            System.out.println(file.getConfigurationSection(configSection + "." + configSectionSubName));
            for (String s : file.getConfigurationSection(configSection + "." + configSectionSubName).getKeys(false)) {
                if(s.equalsIgnoreCase("item")) {
                    continue;
                }
                System.out.println(s);
                gui.i(Integer.parseInt(s),
                        file.getItemStack(configSection + "." + configSectionSubName + "." + s + ".itemStack"));
            }
            gui.i(5, 5, new ItemStack(Material.EMERALD_BLOCK), "&aAdding Chance", "",
                    "&7Right clicking will &a+5&7 chance!");

            gui.i(5, 3, new ItemStack(Material.MINECART), "&c&lInfo", "",
                    "&7This is where you edit loot dropped from the envoy.", "",
                    "&7- &7Clicking the block to the right will toggle betwen adding and removing chance.",
                    "&7- Moving an item from slot to slot counts as putting a new item in.");
            gui.fillRow(5, new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()), " ");

        });
        gui.onClose(e -> {


            for (int i = 0; i < 45; i++) {


                if(e.getInventory().getItem(i) == null) {
                    System.out.println(i);
                    file.set(configSection + "." + configSectionSubName + "." + i, null);
                    continue;
                }
                if (!file.isSet(configSection + "." + configSectionSubName + "." + i + ".chance")) {
                    file.set(configSection + "." + configSectionSubName + "." + i + ".chance", 100);
                }
                file.set(configSection + "." + configSectionSubName + "." + i + ".itemStack", gui.getInventory().getItem(i));
                saveFile(file, baseFile);
            }


        });

        gui.onClick(e -> {
            if (e.getSlot() == 50) {
                e.setCancelled(true);
            }
            Player p = (Player) e.getWhoClicked();

            if (e.getClick().isShiftClick()) {
                return;
            }


            if (e.getSlot() == 50 && e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                gui.i(50, new ItemStack(Material.REDSTONE_BLOCK), "&cRemoving Chance", "",
                        "&7Right clicking will &c-5&7 chance!");
                return;
            }
            if (e.getSlot() == 50 && e.getCurrentItem().getType().equals(Material.REDSTONE_BLOCK)) {
                gui.i(50, new ItemStack(Material.EMERALD_BLOCK), "&aAdding Chance", "",
                        "&7Right clicking will &a+5&7 chance!");
                return;
            }


            if (e.getSlot() > 44) {
                e.setCancelled(true);
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().getType().toString().contains("PANE")) {
                        return; //my fav
                    }
                }
            }


            if (e.getClick().isRightClick()) {
                e.setCancelled(true);
                if (e.getInventory().getItem(e.getSlot()) != null) {
                    if (e.getInventory().getItem(50).getType().equals(Material.EMERALD_BLOCK)) {

                        if (file.getInt(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance") < 100) {
                            file.set(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance", file.getInt(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance") + 5);
                            saveFile(file, baseFile);
                            p.sendMessage(color("&c&lEnvoy > &7Chance: " + file.getInt(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance")));
                        }


                        gui.i(e.getSlot(), e.getCurrentItem());
                        return;
                    }
                    if (e.getInventory().getItem(50).getType().equals(Material.REDSTONE_BLOCK)) {
                        System.out.println(2);

                        if (file.getInt(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance") > 0) {
                            file.set(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance", file.getInt(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance") - 5);
                            saveFile(file, baseFile);
                            p.sendMessage(color("&c&lEnvoy > &7Chance: " + file.getInt(configSection + "." + configSectionSubName + "." + e.getSlot() + ".chance")));
                        }

                        gui.i(e.getSlot(), e.getCurrentItem());
                    }
                }
            }
        });

        return gui;
    }
    public static void saveFile(FileConfiguration fileConfig, File file) {
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

}

