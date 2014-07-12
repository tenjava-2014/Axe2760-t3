package com.tenjava.entries.Axe2760.t3;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class MoonWorld extends JavaPlugin{
    public ChunkGenerator getDefaultWorldGenerator(String name, String id){
        return new MoonGenerator();
    }
}
