package me.christo.prisoncore.shop.util;


import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public enum Items {

    //WOOD
    OAK_LOG(5, "wood", Material.LOG),
    SPRUCE_LOG(10, "wood", new ItemStack(Material.LOG, 1, (byte) 1)),
    BIRCH_LOG(15, "wood", new ItemStack(Material.LOG, 1, (byte) 2)),
    ACACIA_LOG(20, "wood", new ItemStack(Material.getMaterial(162), 1, (byte) 0)),
    JUNGLE_LOG(25, "wood", new ItemStack(Material.LOG, 1, (byte) 3)),
    DARK_OAK_LOG(30, "wood", new ItemStack(Material.getMaterial(162), 1, (byte) 1)),


    //MINING

    COBBLESTONE(0.05, "mining", Material.COBBLESTONE),
    STONE(.50, "mining", Material.STONE),
    COAL(1, "mining", Material.COAL),
    COAL_ORE(.75, "mining", Material.COAL_ORE),
    COAL_BLOCK(10, "mining", Material.COAL_BLOCK),
    LAPIS(2, "mining", new ItemStack(Material.INK_SACK, 1, (byte) 4)),
    LAPIS_ORE(1, "mining", Material.LAPIS_ORE),
    LAPIS_BLOCK(21, "mining", Material.LAPIS_BLOCK),
    IRON_INGOT(5, "mining", Material.IRON_INGOT),
    IRON_ORE(4, "mining", Material.IRON_ORE),
    IRON_BLOCk(46, "mining", Material.IRON_BLOCK),
    GOLD_INGOT(8, "mining", Material.GOLD_INGOT),
    GOLD_ORE(7, "mining", Material.GOLD_ORE),
    GOLD_BLOCK(73, "mining", Material.GOLD_BLOCK),
    DIAMOND(10, "mining", Material.DIAMOND),
    DIAMOND_ORE(9, "mining", Material.DIAMOND_ORE),
    DIAMOND_BLOCK(100, "mining", Material.DIAMOND_BLOCK),
    EMERALD(12, "mining", Material.EMERALD),
    EMERALD_ORE(11, "mining", Material.EMERALD_ORE),
    EMERALD_BLOCK(125, "mining", Material.EMERALD_BLOCK),

    //fishing

    RAW_FISH(25, "fishing", Material.RAW_FISH),
    RAW_SALMON(50, "fishing", new ItemStack(Material.RAW_FISH, 1, (byte) 1)),
    CLOWNFISH(75, "fishing", new ItemStack(Material.RAW_FISH, 1, (byte) 2)),
    PUFFERFISH(100, "fishing", new ItemStack(Material.RAW_FISH, 1, (byte) 3)),

    //FARMING

    KUSH(50, "farming", new ItemStack(Material.INK_SACK, 1, (byte) 2)),
    SPEED(150, "farming", Material.SUGAR);



    private double buyPrice;
    private double sellPrice;
    private String category;
    private Material material;
    private ItemStack itemStack;


    private Items(double sellPrice, String category, ItemStack itemStack) {

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.category = category;
        this.itemStack = itemStack;

    }

    private Items(double sellPrice, String category, Material material) {

        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.category = category;
        this.material = material;

    }

    public static boolean isMaterial(Items item) {

        if(item.material != null) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isSellableItem(ItemStack i) {

        for(Items items : Items.values()) {
            if(isMaterial(items)) {
                if(i.getType().equals(Items.getMaterial(items))) {
                    return true;

                }
            } else {
                if(i.getType().equals(Items.getItemStack(items).getType())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static double getSellPrice(Items prices) {
        return prices.sellPrice;
    }
    public static String getCategory(Items prices) {
        return prices.category;
    }
    public static ItemStack getItemStack(Items itemStack) {
        return itemStack.itemStack;
    }
    public static Material getMaterial(Items prices) {
        return prices.material;
    }
    public static Items translateFromMaterial(Material m) {

        for(Items i : Items.values()) {
            if(isMaterial(i)) {
                if (Items.getMaterial(i).equals(m)) {
                    return i;
                }
            }
        }
        return null;
    }

}
