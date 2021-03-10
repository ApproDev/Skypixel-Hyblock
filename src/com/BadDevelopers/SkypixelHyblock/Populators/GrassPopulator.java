package com.BadDevelopers.SkypixelHyblock.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class GrassPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
			if (random.nextInt(10) > 7) {
				Block block = chunk.getBlock(x, world.getHighestBlockAt(chunk.getX() * 16 + x, 
						chunk.getZ() * 16 + z).getY()+1, z);
				
				if (!block.getType().equals(Material.GRASS) 
						&& !block.getType().equals(Material.DIRT))
					continue;
				
				block.setType(Material.GRASS, false);
			}
				
		}

	}

}
