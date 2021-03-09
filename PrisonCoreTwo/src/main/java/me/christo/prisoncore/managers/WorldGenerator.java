package me.christo.prisoncore.managers;


import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class WorldGenerator extends ChunkGenerator {

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int chunkx, int chunkz,
                                          ChunkGenerator.BiomeGrid biomes) {
        return new byte[world.getMaxHeight() / 16][];
    }

}
