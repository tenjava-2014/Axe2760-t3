package com.tenjava.entries.Axe2760.t3;

import net.minecraft.server.v1_7_R3.EntityTypes;
import net.minecraft.server.v1_7_R3.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class RandomHorde extends JavaPlugin {
    public void onEnable(){
        try{
            Method a = EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
            a.setAccessible(true);

            a.invoke(null, SlimeMan.class, "SlimeMan", 999);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)){
            sender.sendMessage("This command is players only!");
            return true;
        }
        Player p = (Player) sender;
        World world = ((CraftWorld)p.getWorld()).getHandle();
        SlimeMan slimeMan = new SlimeMan(world);
        slimeMan.setPosition(p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
        world.addEntity(slimeMan);
        p.sendMessage("spawned slimeman");
        return true;
    }
}
