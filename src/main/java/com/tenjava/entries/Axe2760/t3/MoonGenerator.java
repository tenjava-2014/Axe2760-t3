package com.tenjava.entries.Axe2760.t3;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class MoonGenerator extends ChunkGenerator{
    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        byte[][] result = new byte[world.getMaxHeight() /16][];
        for (x = 0; x < 16; x++){
            for (z = 0; z < 16; z++){
                setBlock(result, x,0,z,Material.BEDROCK);
            }
        }
        return result;
    }

    private void setBlock(byte[][] result, int x, int y, int z, Material material) {

        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte)material.getId();
    }
}
