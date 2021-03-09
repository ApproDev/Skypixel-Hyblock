package com.BadDevelopers.SkypixelHyblock.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;


// difficult + cba
@Deprecated
public class RiverPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		if (!chunk.getBlock(7, 80, 7).getBiome().equals(Biome.RIVER)) return;
		short lowest = 255;
		short highest = 0;
		
		for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
			short level = (short) world.getHighestBlockYAt(chunk.getX()*16+x, chunk.getZ()*16+z);
			
			if (level > highest) highest = level;
			else if (level < lowest) lowest = level;
		}
		
		for (int x = 0; x < 16; x++) 
			for (int z = 0; z < 16; z++) 
				for (int y = lowest; y <= highest; y++) {
					Block b = chunk.getBlock(x, y, z);
					if (b.getType().isAir()) b.setType(Material.WATER);
		}
		
		
	}

}
