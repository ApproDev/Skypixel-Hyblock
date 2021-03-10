package com.BadDevelopers.SkypixelHyblock.Populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.PerlinNoiseGenerator;

public class CaveLightsPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		PerlinNoiseGenerator gen = new PerlinNoiseGenerator(world);
		
		for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++) {
				int maxHeight = world.getHighestBlockAt(chunk.getX() * 16 + x, 
						chunk.getZ() * 16 + z).getY()+1;
				int realX = x + chunk.getX()*16;
				int realZ = z + chunk.getZ()*16;
				for (int y = 1; y < maxHeight; y++) {
					if (chunk.getBlock(x, y, z).getType().equals(Material.SHROOMLIGHT)) return;
					if (!chunk.getBlock(x, y, z).getType().equals(Material.STONE)) continue;
				
					if (gen.noise(realX, y, realZ) > 0.4) chunk.getBlock(x, y, z).setType(Material.SHROOMLIGHT, false);
				}
				
		}
		
		//Main.gen.onWorldLoad(new WorldLoadEvent(world));
	}
	

}
