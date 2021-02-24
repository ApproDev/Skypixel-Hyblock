package com.BadDevelopers.SkypixelHyblock.Items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;

public interface ItemHolder {
	public enum Item {
		WORKBENCH(Material.CRAFTING_TABLE, "Workbench", Category.BLOCKS, true, new oreDic[0]),
		DIRT(Material.DIRT, "Dirt", Category.BLOCKS, true, new oreDic[0]),
		
		OAK_LOG(Material.OAK_LOG, "Oak Log", Category.BLOCKS, true, new oreDic[] {oreDic.LOG}),
		SPRUCE_LOG(Material.SPRUCE_LOG, "Spruce Log", Category.BLOCKS, true, new oreDic[] {oreDic.LOG}),
		DARK_LOG(Material.DARK_OAK_LOG, "Dark Log", Category.BLOCKS, true, new oreDic[] {oreDic.LOG}),
		SHIT_LOG(Material.BIRCH_LOG, "Birch Log", Category.BLOCKS, true, new oreDic[] {oreDic.LOG}),
		
		
		WOOD_SWORD(Material.WOODEN_SWORD, "Wooden Sword", Category.WEAPONS, true, new oreDic[0]),
		STONE_SWORD(Material.STONE_SWORD, "Stone Sword", Category.WEAPONS, true, new oreDic[0]),
		IRON_SWORD(Material.IRON_SWORD, "Iron Sword", Category.WEAPONS, true, new oreDic[0]),
		DIAMOND_SWORD(Material.DIAMOND_SWORD, "Diamond Sword", Category.WEAPONS, true, new oreDic[0]),
		
		SPEED_SWORD(Material.GOLDEN_SWORD, "Rogue Sword", Category.WEAPONS, false, new oreDic[0]),
		
		
		WOOD_PICK(Material.WOODEN_PICKAXE, "Wooden Pickaxe", Category.TOOLS, true, new oreDic[0]),
		STONE_PICK(Material.STONE_PICKAXE, "Stone Pickaxe", Category.TOOLS, true, new oreDic[0]),
		IRON_PICK(Material.IRON_PICKAXE, "Iron Pickaxe", Category.TOOLS, true, new oreDic[0]),
		DIAMOND_PICK(Material.DIAMOND_PICKAXE, "Diamond Pickaxe", Category.TOOLS, true, new oreDic[0]),
		WOOD_AXE(Material.WOODEN_AXE, "Wooden Axe", Category.TOOLS, true, new oreDic[0]),
		
		
		
		
		NULL(Material.AIR, "null", Category.NONE, true, new oreDic[0]);
		
		
		Material mat;
		String name;
		Category cat;
		oreDic[] oreDict;
		boolean isVanilla;
		Item(Material mat, String name, Category cat, boolean isVanilla, oreDic[] oreDict) {
			this.mat = mat;
			this.name = name;
			this.cat = cat;
			this.isVanilla = isVanilla;
			this.oreDict = oreDict;
		}
		
		public void giveItem(Player p, int amount) {
			if (this.equals(Item.NULL)) return;
			p.getInventory().addItem(getItem(amount));
		}
		
		public static Item getVanilla(Material mat) {
			for (Item item : values()) {
				if (!item.isVanilla) continue;
				
				if (item.mat.equals(mat)) return item;
			}
			return null;
		}
		
		
		public ItemStack getItem(int quantity) {
			ItemStack is = new ItemStack(mat, quantity);
			
			ItemMeta im = is.getItemMeta();
			
			im.setDisplayName(name);
			im.setUnbreakable(true);
			
			PersistentDataContainer tags = im.getPersistentDataContainer();
			
			tags.set(new NamespacedKey(getInstance(), "id"), PersistentDataType.INTEGER, ordinal());
			
			is.setItemMeta(im);
			
			return is;
		}
		
		public static Item valueOf(ItemStack is) {
			PersistentDataContainer pds = is.getItemMeta().getPersistentDataContainer();
			
			int id = pds.get(new NamespacedKey(getInstance(), "id"), PersistentDataType.INTEGER);
			
			return valueOf(id);
		}
		
		public static Item valueOf(int ordinal) {
			return values()[ordinal];
		}
	}
	
	public enum oreDic {
		LOG,
		STONE,
		COBBLESTONE
	}
	
	
	
	public enum Category {
		BLOCKS,
		TOOLS,
		WEAPONS,
		ARMOUR,
		MATERIAL,
		NONE
	}
	
	static Main getInstance() {
		return JavaPlugin.getPlugin(Main.class);
	}
}
