package com.BadDevelopers.SkypixelHyblock.Populators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.Plugin;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;

public class TreePopulator extends BlockPopulator {
	
	final Main main;
	public TreePopulator(Main main) {
		this.main = main;
	}
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		int x = random.nextInt(8);
		int z = random.nextInt(8);
		
		int trueX = chunk.getX()*16+x;
		int trueZ = chunk.getZ()*16+z;
		
		int y = world.getHighestBlockYAt(trueX, trueZ);
		
		Clipboard tree = getBiomeTree(chunk.getBlock(x, y, z).getBiome(), random);
		if (tree == null) return;
		try {
			generateClipboard(new Location(world, trueX, y, trueZ), tree, random);
		} catch (WorldEditException e) {
			e.printStackTrace();
		}
	}
	
	HashMap<Biome, Clipboard[]> trees = null;
	
	HashMap<Biome, Clipboard[]> getMap(){
		if (trees == null) {
			trees = new HashMap<Biome, Clipboard[]>();
			
			trees.put(Biome.FOREST, new Clipboard[] {getTree("Oak_1")});
			trees.put(Biome.JUNGLE, new Clipboard[] {getTree("Jungle_1")});
		}
		return trees;
	}
	
	Clipboard getBiomeTree(Biome biome, Random random) {
		HashMap<Biome, Clipboard[]> trees = getMap();
		
		if (!trees.containsKey(biome)) return null;
		
		Clipboard[] biomeTrees = trees.get(biome);
		
		return biomeTrees[random.nextInt(biomeTrees.length)];
	}
	
	Clipboard getTree(String filename) {
		Plugin p = (Plugin) main;
		String DataFolder = p.getDataFolder().getAbsolutePath();

		File file = new File(DataFolder+"/Schematics/"+filename+".schem");
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
			return reader.read();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	void generateClipboard(Location loc, Clipboard clip, Random random) throws WorldEditException {
		com.sk89q.worldedit.world.World w = BukkitAdapter.adapt(loc.getWorld());
		
		EditSession editSession = WorldEdit.getInstance().newEditSession(w);
		int degrees = random.nextInt(4)*90;
		ClipboardHolder ch = new ClipboardHolder(clip);
		ch.setTransform(new AffineTransform().rotateY(degrees));
		
		Operation operation = ch.createPaste(editSession)
			.to(BlockVector3.at(loc.getBlockX(), loc.getBlockY()-2, loc.getBlockZ()))
		    .ignoreAirBlocks(true)
		    
			.build();
		    Operations.complete(operation);
		
		
		editSession.close();
	}

}
