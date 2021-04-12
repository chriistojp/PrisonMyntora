package me.christo.prisoncore.commands.admin;


import me.christo.prisoncore.Prison;
import net.myntora.core.core.command.Command;
import net.myntora.core.core.command.DynamicCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

@DynamicCommand(
        name = "armorstand"
)
public class ArmorStandTest extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {


        Player p = (Player) sender;

        Location locationOne = p.getLocation().clone().add(0, 0, 3);
        Location locationTwo = p.getLocation().clone().add(0, 0, 3).subtract(2, 0, 0);
        Location locationThree = p.getLocation().clone().add(2, 0, 3);

        ArmorStand asTwo = (ArmorStand) p.getWorld().spawnEntity(locationOne, EntityType.ARMOR_STAND);
        ArmorStand asThree = (ArmorStand) p.getWorld().spawnEntity(locationTwo, EntityType.ARMOR_STAND);
        ArmorStand asOne = (ArmorStand) p.getWorld().spawnEntity(locationThree, EntityType.ARMOR_STAND);

        asOne.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
        asTwo.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
        asThree.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));


        asOne.setVisible(false);
        asTwo.setVisible(false);
        asThree.setVisible(false);

//        count =
//        for (int i = 0; i < 100; i++) {
//            asOne.teleport(asOne.getLocation().setPitch(i);
//        }


        BukkitTask task = new BukkitRunnable() {


            boolean one = true;
            boolean two = false;
            boolean three = false;


            @Override
            public void run() {

                if(one) {
                    asOne.setHelmet(new ItemStack(Material.EMERALD_BLOCK));
                    asTwo.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                    asThree.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                    one = false;
                    two = true;
                } else if(two) {
                    asOne.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                    asTwo.setHelmet(new ItemStack(Material.EMERALD_BLOCK));
                    asThree.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                    two = false;
                    three = true;
                } else if(three) {
                    asOne.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                    asTwo.setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
                    asThree.setHelmet(new ItemStack(Material.EMERALD_BLOCK));
                    one = true;
                    three = false;
                }



            }
        }.runTaskTimer(Prison.getInstance(), 2, 2);


        if(args.length == 1) {
            task.cancel();
        }


//        Bukkit.getScheduler().runTaskTimer(Prison.getInstance(), () -> {
//
//        }, 30, 30);



        if(args.length == 3) {


            ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
            as.setHelmet(new ItemStack(Material.DIAMOND_BOOTS));
            as.setVisible(false);
            as.setRightArmPose(new EulerAngle(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));

            Bukkit.broadcastMessage(as.getRightArmPose().toString() + " ");

        }


    }
}
