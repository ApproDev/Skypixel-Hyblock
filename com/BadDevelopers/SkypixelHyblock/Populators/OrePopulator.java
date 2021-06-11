package com.BadDevelopers.SkypixelHyblock.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class OrePopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++) {
				int realX = chunk.getX()*16+x;
				int realZ = chunk.getZ()*16+x;
				int maxHeight = world.getHighestBlockYAt(realX, realZ);
				
				for (int y = 1; y < maxHeight-5; y++) {
					if (chunk.getBlock(x, y, z).getType() != Material.STONE) continue;
					double noise = random.nextDouble()*2;
					
					if (y < 5) {
						if (noise < 0.05) chunk.getBlock(x, y, z).setType(Material.ANCIENT_DEBRIS, false);
						continue;
					}
					
					Material mat = Material.STONE;
					
					if (noise < 0.2) {
						
						if (noise < 0.05) mat = Material.EMERALD_ORE;
						
						else if (noise < 0.06 && y < 40) mat = Material.DIAMOND_ORE;
						
						else if (noise < 0.18 && noise > 0.1) mat = Material.GOLD_ORE;
					}
					
					else if (noise < 0.3) {
						if (noise < 0.25) mat = Material.IRON_ORE;
						
						else mat = Material.COAL_ORE;
					}
					else if (noise < 0.35) mat = Material.LAPIS_ORE;
					
					chunk.getBlock(x, y, z).setType(mat, false);
				}
			}
				
	}

}
