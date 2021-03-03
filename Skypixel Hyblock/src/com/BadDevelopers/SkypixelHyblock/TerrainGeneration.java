package com.BadDevelopers.SkypixelHyblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftLightable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import com.BadDevelopers.SkypixelHyblock.Populators.CaveLightsPopulator;
import com.BadDevelopers.SkypixelHyblock.Populators.GrassPopulator;
import com.BadDevelopers.SkypixelHyblock.Populators.OrePopulator;
import com.BadDevelopers.SkypixelHyblock.Populators.TreePopulator;

import net.minecraft.server.v1_16_R3.BlockPosition;

public class TerrainGeneration extends ChunkGenerator implements Listener {
	
	@Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		ChunkData chunk = createChunkData(world);
		
		SimplexOctaveGenerator normalGen = new SimplexOctaveGenerator(world, 8);
		normalGen.setScale(0.03);
		
		SimplexOctaveGenerator bottomGen = new SimplexOctaveGenerator(world, 8);
		bottomGen.setScale(1/256);
		
		SimplexOctaveGenerator caveGen = new SimplexOctaveGenerator(world, 8);
		caveGen.setScale(1/16.0);
		
		SimplexOctaveGenerator gen1 = new SimplexOctaveGenerator(world,8);
		SimplexOctaveGenerator gen2 = new SimplexOctaveGenerator(world,8);
		gen1.setScale(1/64.0);
		gen2.setScale(1/128.0);
		
		
		for (int x=0; x<16; x++) 
		    for (int z=0; z<16; z++) {
		 
		        int realX = x + chunkX * 16; //used so that the noise function gives us
		        int realZ = z + chunkZ * 16; //different values each chunk
		        double frequency = 0.0005; // the reciprocal of the distance between points
		        double amplitude = 0.2; // The distance between largest min and max values
		        int multitude = 4; //how much we multiply the value between -1 and 1. It will determine how "steep" the hills will be.
		        int sea_level = 80;
		 
		        double maxHeight = Math.max(gen1.noise(realX, realZ, frequency, amplitude) * multitude + sea_level, gen2.noise(realX, realZ, frequency, amplitude) * multitude + sea_level);
		        double normalHeight = normalGen.noise(realX, realZ, frequency, amplitude) * multitude + sea_level + 5;
		        
		        if (maxHeight < normalHeight) maxHeight = normalHeight;
		        
		        if (maxHeight < 0) maxHeight = 0;
		        
		        for (int y=1;y<maxHeight;y++) {/*
		        	if (y < 40) {
		        		if (normalGen.noise(realX, y, realZ, frequency, amplitude) < 0.2) chunk.setBlock(x, y, z, Material.STONE);} //set the current block to stone
		        	else if (normalGen.noise(realX, y, realZ, frequency, amplitude) < 0.4) chunk.setBlock(x, y, z, Material.STONE);*/
		        	if (y<5) {
		        		if (caveGen.noise(realX, y, realZ, frequency, amplitude) < 0.4) {
		        			if (random.nextInt(10) < 8) chunk.setBlock(x, y, z, Material.BLACKSTONE);
		        			else chunk.setBlock(x, y, z, Material.OBSIDIAN);
		        		} else chunk.setBlock(x, y, z, Material.LAVA);
		        	}
		        	
		        	if (caveGen.noise(realX, y, realZ, frequency, amplitude) < (y-20) / maxHeight) {chunk.setBlock(x, y, z, Material.STONE);}
		        
		        }
		        
		        Material grass = getGrass(world, random, x, (int) maxHeight, z, biome);
		        
		        
		        
		        if (caveGen.noise(realX, maxHeight+1, realZ, frequency, amplitude) < 0.499D) chunk.setBlock(x, (int) maxHeight+1, z, grass);
		        if (caveGen.noise(realX, maxHeight, realZ, frequency, amplitude) < 0.499D)chunk.setBlock(x, (int) maxHeight, z, Material.DIRT);
		        if (caveGen.noise(realX, maxHeight-1, realZ, frequency, amplitude) < 0.499D)chunk.setBlock(x, (int) maxHeight-1, z, Material.DIRT);
		        
		        
		        chunk.setBlock(x, (int) 0, z, Material.BEDROCK);
		    }
		/*
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, 8);
		generator.setScale(0.03D);
		
		
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++) {
				int y = getHeight(world, generator, x + chunkX*16, z + chunkZ*16, 0.5, 0.5);
				chunk.setBlock(x, y, z, Material.GRASS_BLOCK);
				
				chunk.setBlock(x, 0, z, Material.BEDROCK);
				
				do {
					y--;
					chunk.setBlock(x, y, z, Material.STONE);
				} while (y > 1);
			}*/
		
		return chunk;
	}
	
	Material getGrass(World world, Random random, int x, int y, int z, BiomeGrid biome) {
		Material mat = Material.GRASS_BLOCK;
		
		switch(biome.getBiome(x, y, z)) {
		case DESERT:
			mat = Material.SAND;
			break;
		case DESERT_HILLS:
			mat = Material.SAND;
			break;
		case GRAVELLY_MOUNTAINS:
			mat = Material.GRAVEL;
			break;
		case ICE_SPIKES:
			mat = Material.PACKED_ICE;
			break;
		case MOUNTAINS:
			mat = Material.STONE;
			break;
		case MOUNTAIN_EDGE:
			mat = Material.STONE;
			break;
		case MUSHROOM_FIELDS:
			mat = Material.MYCELIUM;
			break;
		case MUSHROOM_FIELD_SHORE:
			mat = Material.MYCELIUM;
			break;
		case SNOWY_MOUNTAINS:
			mat = Material.SNOW_BLOCK;
			break;
		case SNOWY_TAIGA:
			mat = Material.SNOW_BLOCK;
			break;
		case SNOWY_TAIGA_HILLS:
			mat = Material.SNOW_BLOCK;
			break;
		case SNOWY_TAIGA_MOUNTAINS:
			mat = Material.SNOW_BLOCK;
			break;
		case SNOWY_TUNDRA:
			mat = Material.SNOW_BLOCK;
			break;
		case SUNFLOWER_PLAINS:
			break;
		case SWAMP:
			if (random.nextBoolean()) mat = Material.WATER;
			break;
		case GIANT_TREE_TAIGA:
			mat = Material.PODZOL;
			break;
		case GIANT_TREE_TAIGA_HILLS:
			mat = Material.PODZOL;
			break;
		default:
			Biome b = biome.getBiome(x, y, z);
			if (
				   b.equals(Biome.BAMBOO_JUNGLE)
				|| b.equals(Biome.BAMBOO_JUNGLE_HILLS)
				|| b.equals(Biome.BIRCH_FOREST)
				|| b.equals(Biome.BIRCH_FOREST_HILLS)
				|| b.equals(Biome.DARK_FOREST)
				|| b.equals(Biome.DARK_FOREST_HILLS)
				|| b.equals(Biome.FOREST)
				|| b.equals(Biome.GIANT_SPRUCE_TAIGA)
				|| b.equals(Biome.GIANT_SPRUCE_TAIGA_HILLS)
				|| b.equals(Biome.JUNGLE)
				|| b.equals(Biome.JUNGLE_EDGE)
				|| b.equals(Biome.JUNGLE_HILLS)
				|| b.equals(Biome.SAVANNA)
				|| b.equals(Biome.SAVANNA_PLATEAU)
				|| b.equals(Biome.SHATTERED_SAVANNA)
				|| b.equals(Biome.SHATTERED_SAVANNA_PLATEAU)
			);
			
			else biome.setBiome(x, y, z, Biome.PLAINS);
			break;
        }
		
		return mat;
	}
	
	/*
	private int getHeight(World world, SimplexOctaveGenerator generator, int x, int y, double freq, double ampl) {
		
		double result = generator.noise(x, y, freq, ampl, true)*4+64;
		
		
		return NoiseGenerator.floor(result);
	}*/
	
	final Main main;
	
	public TerrainGeneration(Main main) {
		this.main = main;
	}
	
	@Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
		ArrayList<BlockPopulator> pops = new ArrayList<BlockPopulator>();
		
		pops.add(new GrassPopulator());
		pops.add(new CaveLightsPopulator());
		pops.add(new OrePopulator());
		pops.add(new TreePopulator(main));
		
        return pops;
    }
	
	@Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int x = random.nextInt(200) - 100;
        int z = random.nextInt(200) - 100;
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
	
	/*@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), new Runnable() {

			@Override
			public void run() {
				World world = event.getWorld();
				
				for (Chunk chunk : world.getLoadedChunks()) {

					reloadLighting(world, chunk.getX(), chunk.getZ());
					
					//onChunkLoad(new ChunkLoadEvent(chunk, false));
				}
				
			}
			
		});
	}*/
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		//onWorldLoad(new WorldLoadEvent(event.getWorld()));
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), new Runnable() {

			@Override
			public void run() {
				Chunk chunk = event.getChunk();
				
				reloadLighting(event.getWorld(), chunk.getX(), chunk.getZ());
							
				
			}
			
		});
			
		
	}
	
	/*public static void recalculateLighting(World world, int blockX, int blockY, int blockZ) {
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		final LightEngineThreaded lightEngine = worldServer.getChunkProvider().getLightEngine();

		lightEngine.queueUpdate();
		
		BlockPosition bp = new BlockPosition(blockZ, blockY, blockZ);
		lightEngine.a(bp);
		
		lightEngine.queueUpdate();

	}*/
	

	public void reloadLighting(World world, int chunkX, int chunkZ) {
		
		//for (int i = 0; i < 2; i++)
		for (Chunk chunk : world.getLoadedChunks()) {
			int cx = chunk.getX();
			int cz = chunk.getZ();
			if (cx != chunkX) continue;
			if (cz != chunkZ) continue;
			
			for (int x = 0; x < 16; x++) for (int z = 0; z < 16;z++)
				for (int y = 0; y < 256; y++) {
					net.minecraft.server.v1_16_R3.Chunk c = ((CraftChunk) chunk).getHandle();
					boolean loaded = c.loaded;
					//c.getBukkitChunk().unload();
					//((CraftWorld) world).getHandle().notifyAll();
					c.getBukkitChunk().load();
					
					Block block = chunk.getBlock(x, y, z);
					
					if (block instanceof CraftLightable) {
						Bukkit.broadcastMessage("lighting");
						CraftLightable light = ((CraftLightable) ((CraftBlockData) block.getState().getBlockData()));
						light.setLit(true);
						
					}
					((CraftWorld) block.getWorld()).getHandle().a(x, y, z, 12);
					((CraftWorld) world).getHandle().update(new BlockPosition(x, y, z), ((CraftBlock) chunk.getBlock(x, y, z)).getNMS().getBlock());
					
					//chunk.getBlock(x, y, z).getState().update(true, false);
					//c.e().a();
					//c.e().a(new BlockPosition(x, y, z));
					//recalculateLighting(chunk.getWorld(), x, y, z);
				
					//c.getBukkitChunk().setForceLoaded(false);
					
					c.setLoaded(loaded);
				}
		}
	}
	
	
}
