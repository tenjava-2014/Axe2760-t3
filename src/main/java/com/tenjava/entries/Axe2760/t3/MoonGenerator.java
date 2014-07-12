package com.tenjava.entries.Axe2760.t3;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

public class MoonGenerator extends ChunkGenerator{
    private long seed;

    public MoonGenerator(){
        seed = new Random().nextLong();
    }

    public MoonGenerator(long seed){
        this.seed = seed;
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        byte[][] result = new byte[world.getMaxHeight() /16][];
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(seed, 3);
        for (int x = 0; x < 16; x++){
            for (int z = 0; z < 16; z++){
                setBlock(result, x,0,z,Material.BEDROCK);
                int topY = (int)Math.floor(generator.noise(chunkX * 16 + x, chunkZ * 16 + z, 10, 10));
                for (int y = 1; y <= topY; y++){
                    setBlock(result,x,y,z,Material.STONE);
                }
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
