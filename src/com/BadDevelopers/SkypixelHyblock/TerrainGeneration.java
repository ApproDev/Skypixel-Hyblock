package com.BadDevelopers.SkypixelHyblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
	/*
	public static Integer interpolate(int lowX, int lowY, int highX, int highY) {
		return lowX + ();
	} // y = y1 + ((x - x1) / (x2 - x1)) * (y2 - y1)
	
	
	public BiomeGrid fixBiomeBorders(Random random, World world, BiomeGrid biomes) {
		
		for (int x = 0; x < 16; x ++)
			for (int z = 0; z < 16; z ++) {
				/*
				Biome[][] surrounding = new Biome[2][2];
				
				
				ArrayList<Biome> surrounding = new ArrayList<Biome>();
				
				surrounding.add(biomes.getBiome(x-1, 80, z));
				surrounding.add(biomes.getBiome(x+1, 80, z));
				surrounding.add(biomes.getBiome(x, 80, z-1));
				surrounding.add(biomes.getBiome(x, 80, z+1));
				/*
				surrounding[0][0] = biomes.getBiome(x-1, 80, z);
				surrounding[0][1] = biomes.getBiome(x+8, 80, z);
				surrounding[1][0] = biomes.getBiome(x, 80, z-1);
				surrounding[1][1] = biomes.getBiome(x, 80, z+8);
				
				
				int changeCount = 0;
				Biome prev = Biome.THE_VOID;
				
				for (Biome[] bs : surrounding)
					for (Biome biome : bs) {
						if (!biome.equals(prev)) changeCount += 1;
						prev = biome;
					}
				
				if (changeCount < 1) return biomes;
				
						Biome newBiome;
						/*
						boolean dX = iX > 3;
						boolean dZ = iZ > 3;
						
						boolean useX = iX+((dX ? 1 : 0) * 8) > iZ+((dZ ? 1 : 0) * 8);
						
						if (useX && dX) newBiome = surrounding[0][0];
						
						else if (useX) newBiome = surrounding[0][1];
						
						else if (dZ) newBiome = surrounding[1][0];
						
						else newBiome = surrounding[1][1];
						
						
						//newBiome = surrounding[random.nextBoolean() ? 1 : 0][random.nextBoolean() ? 1 : 0];
						//newBiome = surrounding[random.nextInt(1)][random.nextInt(1)];
						newBiome = surrounding.get(random.nextInt(surrounding.size()));
						//Bukkit.broadcastMessage(Integer.toString(iX+x)+" "+Integer.toString(iZ+z));
						if (!biomes.getBiome(x, 80, z).equals(newBiome)) {
							//Bukkit.broadcastMessage("not equal");
							for (int y = 0; y < 256; y++) biomes.setBiome(x, y, z, newBiome);
						}
							
					
					
			}
		return biomes;
	}
		
		/*
		for (int x = 0; x < 16; x++)
			for (int z = 0; z < 16; z++) {
				Integer biome = interpolate(
						getBiomeInt(world.getBiome(x-1, 80 ,z)),
						getBiomeInt(world.getBiome(x, 80 ,z-1)),
						getBiomeInt(world.getBiome(x+1, 80 ,z)),
						getBiomeInt(world.getBiome(x, 80 ,z+1)));
				
				
				Biome before = biomes.getBiome(x, 80, z);
				Integer biome = ((Long) Math.round(
						
						(
								(getBiomeInt(biomes.getBiome(x-1, 80 ,z)) + 
								getBiomeInt(biomes.getBiome(x+1, 80 ,z))) / 2.0 +
								(getBiomeInt(biomes.getBiome(x, 80 ,z-1)) +
								getBiomeInt(biomes.getBiome(x, 80 ,z+1)) / 2.0)
								
								) / 2.0)
						
						).intValue();
				
				
				Biome newBiome = reverseGetBiomeInt(biome);
				if (!before.equals(newBiome)) for (int y = 0; y < 256; y++) biomes.setBiome(x, y, z, newBiome);
			}*/
	
	@Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
		ChunkData chunk = createChunkData(world);
		
		//biomes = fixBiomeBorders(random, world, biomes);
		
		//biomes = setBiomeData(chunkX, chunkZ, biomes);
		
		//setBiomeData(chunk, random, biome);
		
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
		        
		        Material grass = getGrass(world, random, x, (int) maxHeight, z, biomes);
		        
		        
		        
		        if (caveGen.noise(realX, maxHeight+1, realZ, frequency, amplitude) < 0.4999D) chunk.setBlock(x, (int) maxHeight+1, z, grass);
		        if (caveGen.noise(realX, maxHeight, realZ, frequency, amplitude) < 0.4999D)chunk.setBlock(x, (int) maxHeight, z, Material.DIRT);
		        if (caveGen.noise(realX, maxHeight-1, realZ, frequency, amplitude) < 0.4999D)chunk.setBlock(x, (int) maxHeight-1, z, Material.DIRT);
		        
		        
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
	/*
	static Biome reverseGetBiomeInt(Integer val) {
		switch(val) {
		case 1:
			return Biome.DESERT;
		case 2:
			return Biome.DESERT_HILLS;
		case 3:
			return Biome.SAVANNA;
		case 4:
			return Biome.SAVANNA_PLATEAU;
		case 5:
			return Biome.SHATTERED_SAVANNA_PLATEAU;
		case 6:
			return Biome.SHATTERED_SAVANNA;
		case 7:
			return Biome.JUNGLE;
		case 8:
			return Biome.JUNGLE_HILLS;
		case 9:
			return Biome.JUNGLE_EDGE;
		case 10:
			return Biome.BAMBOO_JUNGLE;
		case 11:
			return Biome.BAMBOO_JUNGLE_HILLS;
		case 12:
			return Biome.BIRCH_FOREST;
		case 13:
			return Biome.BIRCH_FOREST_HILLS;
		case 14:
			return Biome.SWAMP;
		case 15:
			return Biome.PLAINS;
		case 16:
			return Biome.MUSHROOM_FIELDS;
		case 17:
			return Biome.MUSHROOM_FIELD_SHORE;
		case 18:
			return Biome.DARK_FOREST;
		case 19:
			return Biome.DARK_FOREST_HILLS;
		case 20:
			return Biome.FOREST;
		case 21:
			return Biome.GIANT_TREE_TAIGA;
		case 22:
			return Biome.GIANT_TREE_TAIGA_HILLS;
		case 23:
			return Biome.SNOWY_TAIGA_MOUNTAINS;
		case 24:
			return Biome.GIANT_SPRUCE_TAIGA;
		case 25:
			return Biome.GIANT_SPRUCE_TAIGA_HILLS;
		case 26:
			return Biome.MOUNTAINS;
		case 27:
			return Biome.MOUNTAIN_EDGE;
		case 28:
			return Biome.SNOWY_TUNDRA;
		case 29:
			return Biome.SNOWY_TAIGA;
		case 30:
			return Biome.SNOWY_TAIGA_HILLS;
		case 31:
			return Biome.SNOWY_MOUNTAINS;
		case 32:
			return Biome.ICE_SPIKES;
		default:
			//Bukkit.broadcastMessage("Error: unknown biome value "+'"'+val+'"');
			return Biome.THE_VOID;
		}
	}*/
	/*
	static Integer getBiomeInt(Biome biome) {
		switch(biome) {
		case DESERT:
			return 1;
		case DESERT_HILLS:
			return 2;
		case SAVANNA:
			return 3;
		case SAVANNA_PLATEAU:
			return 4;
		case SHATTERED_SAVANNA_PLATEAU:
			return 5;
		case SHATTERED_SAVANNA:
			return 6;
		case JUNGLE:
			return 7;
		case JUNGLE_HILLS:
			return 8;
		case JUNGLE_EDGE:
			return 9;
		case BAMBOO_JUNGLE:
			return 10;
		case BAMBOO_JUNGLE_HILLS:
			return 11;
		case BIRCH_FOREST:
			return 12;
		case BIRCH_FOREST_HILLS:
			return 13;
		case SWAMP:
			return 14;
		case PLAINS:
			return 15;
		case MUSHROOM_FIELDS:
			return 16;
		case MUSHROOM_FIELD_SHORE:
			return 17;
		case DARK_FOREST:
			return 18;
		case DARK_FOREST_HILLS:
			return 19;
		case FOREST:
			return 20;
		case GIANT_TREE_TAIGA:
			return 21;
		case GIANT_TREE_TAIGA_HILLS:
			return 22;
		case SNOWY_TAIGA_MOUNTAINS:
			return 23;
		case GIANT_SPRUCE_TAIGA:
			return 24;
		case GIANT_SPRUCE_TAIGA_HILLS:
			return 25;
		case MOUNTAINS:
			return 26;
		case MOUNTAIN_EDGE:
			return 27;
		case SNOWY_TUNDRA:
			return 28;
		case SNOWY_TAIGA:
			return 29;
		case SNOWY_TAIGA_HILLS:
			return 30;
		case SNOWY_MOUNTAINS:
			return 31;
		case ICE_SPIKES:
			return 32;
		default:
			return 15;
		}
	}*/
	
	Material getGrass(World world, Random random, int x, int y, int z, BiomeGrid biome) {
		/*Material mat =*/return Material.GRASS_BLOCK;
		
		//if (biome == null || biome.getBiome(x, y, z) == null) return Material.WHITE_CONCRETE;
		/*
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
		case SWAMP:
			if (random.nextBoolean()) mat = Material.WATER;
			break;
		case GIANT_TREE_TAIGA:
			mat = Material.PODZOL;
			break;
		case GIANT_TREE_TAIGA_HILLS:
			mat = Material.PODZOL;
			break;
		case THE_VOID:
			mat = Material.BLACK_CONCRETE;
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
			
			//else biome.setBiome(x, y, z, Biome.PLAINS);
			break;
        }
		
		return mat;*/
	}
	/*
	public Long noise(Random random, int x, int amplitude) {
		return Math.abs(Math.round(Math.sin(x/32.0)*amplitude));
	}
	
	public Long noise(Random random, int x, int y, int amplitude) {
		return Math.round((noise(random, x, amplitude) + noise(random, y, amplitude)) / 2.0);
	}
	*/
	
	/*int getValueSingle(int val) {
		double valu = val / 256.0;
		return (int) Math.round(Math.abs(Math.sin(valu) + Math.cos(valu))*18*0.5)+1;
		
		
		return (int) Math.round(
				
				(
				
					(val % 2 == 1 ? 1 : 0) +
					(val % 3 == 1 ? 1 : 0) +
					(val % 5 == 1 ? 1 : 0) +
					(val % 7 == 1 ? 1 : 0) +
					(val % 11 == 1 ? 1 : 0) +
					(val % 13 == 1 ? 1 : 0) +
					(val % 17 == 1 ? 1 : 0) +
					(val % 19 == 1 ? 1 : 0) +
					(val % 23 == 1 ? 1 : 0) +
					(val % 29 == 1 ? 1 : 0) +
					(val % 31 == 1 ? 1 : 0) +
					(val % 37 == 1 ? 1 : 0) +
					(val % 41 == 1 ? 1 : 0) +
					(val % 43 == 1 ? 1 : 0) +
					(val % 47 == 1 ? 1 : 0) +
					(val % 53 == 1 ? 1 : 0) +
					(val % 59 == 1 ? 1 : 0) +
					(val % 61 == 1 ? 1 : 0) +
					(val % 67 == 1 ? 1 : 0) +
					(val % 71 == 1 ? 1 : 0) +
					(val % 73 == 1 ? 1 : 0) +
					(val % 79 == 1 ? 1 : 0) +
					(val % 83 == 1 ? 1 : 0) +
					(val % 89 == 1 ? 1 : 0) +
					(val % 97 == 1 ? 1 : 0)
				
				) / 25.0*18+1);
	}*/
	
	/*int getValue(int X, int Z) {
		return (int) Math.round((getValueSingle(X) + getValueSingle(Z)) / 2.0);
	}
	
	BiomeGrid setBiomeData(int chunkX, int chunkZ, BiomeGrid bg) {
		//SimplexOctaveGenerator gen = new SimplexOctaveGenerator(random, 8);
		for (int x = 0; x < 16; x++)
		for (int z = 0; z < 16; z++) {
			//int value = getValue(chunkX*16+x, chunkZ*16+z);
			
			int value = Generator.get(chunkX*16+x, chunkZ*16+z);
			
			Biome biome = null;
			
			//Integer ran = (int) (Math.copySign(gen.noise(x/128.0, z/128.0, 0.5d, 0.5d), 1)*30)+1;
			
			//Long ran = noise(random, x, z, 31) + 1;
			
			//Bukkit.broadcastMessage("Biome id:"+ran);
			
			switch(value) {
			case 1:
				biome = Biome.DESERT;
				break;
			case 2:
				biome = Biome.DESERT_HILLS;
				break;
			case 3:
				biome = Biome.SAVANNA;
				break;
			case 4:
				biome = Biome.SAVANNA_PLATEAU;
				break;
			case 5:
				biome = Biome.SHATTERED_SAVANNA_PLATEAU;
				break;
			case 6:
				biome = Biome.SHATTERED_SAVANNA;
				break;
			case 7:
				biome = Biome.JUNGLE;
				break;
			case 8:
				biome = Biome.JUNGLE_HILLS;
				break;
			case 9:
				biome = Biome.JUNGLE_EDGE;
				break;
			case 10:
				biome = Biome.BAMBOO_JUNGLE;
				break;
			case 11:
				biome = Biome.BAMBOO_JUNGLE_HILLS;
				break;
			case 12:
				biome = Biome.BIRCH_FOREST;
				break;
			case 13:
				biome = Biome.BIRCH_FOREST_HILLS;
				break;
			case 14:
				biome = Biome.SWAMP;
				break;
			case 15:
				biome = Biome.PLAINS;
				break;
			case 16:
				biome = Biome.DARK_FOREST;
				break;
			case 17:
				biome = Biome.DARK_FOREST_HILLS;
				break;
			case 18:
				biome = Biome.FOREST;
				break;
			default:
				//Bukkit.broadcastMessage(value+"");
				biome = Biome.THE_VOID;
				break;
			}
			
			for (int y = 0; y < 255; y++) bg.setBiome(x, y, z, biome);
		}
		return bg;
			
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
					
					if ((CraftBlockData) (((CraftBlock) block).getBlockData()) instanceof CraftLightable) {
						//Bukkit.broadcastMessage("lighting");
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
/*
interface Generator {
	
	
	
	public static int get(int x, int z) {
		double d = avg(get2(x), get2(z)) * 17 + 1;
		
		return  (int) Math.round(d);
	}
	
	private static double avg(double ... numbers) {
		int num = 0;
		
		for (double i : numbers) {
			num += i;
		}
		
		return (int) Math.round(num / ((double) numbers.length));
	}
	
	@SuppressWarnings("unused")
	private static double get1(double valu) {
		return Math.atan(valu);
	}
	
	private static double get2(int valu) {
		return getSound(valu, Main.terrainSoundHolder);
	}
	
	@SuppressWarnings("unused")
	private static double get3(int val) {
		return getSound(val, Main.terrainSoundHolder2);
	}
	
	private static double getSound(int val, SoundHolder sh) {
		val = Math.abs(val);
		
		double d = sh.get(val % sh.length()) / sh.maxAmplitude;
		
		//System.out.println(""+d);
		
		return d;
	}
	
	@SuppressWarnings("unused")
	private static double get4(int val) {
		return (int) Math.round(Math.abs(Math.sin(val/100.0)*18));
	}
}*/