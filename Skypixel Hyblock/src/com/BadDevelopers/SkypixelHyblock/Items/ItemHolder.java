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
		WORKBENCH(Material.CRAFTING_TABLE, "Workbench", Category.BLOCKS, true),
		DIRT(Material.DIRT, "Dirt", Category.BLOCKS, true),
		OAK_LOG(Material.OAK_LOG, "Oak Log", Category.BLOCKS, true),
		SPRUCE_LOG(Material.SPRUCE_LOG, "Spruce Log", Category.BLOCKS, true),
		DARK_LOG(Material.DARK_OAK_LOG, "Dark Log", Category.BLOCKS, true),
		SHIT_LOG(Material.BIRCH_LOG, "Birch Log", Category.BLOCKS, true),
		
		
		WOOD_SWORD(Material.WOODEN_SWORD, "Wooden Sword", Category.WEAPONS, true),
		STONE_SWORD(Material.STONE_SWORD, "Stone Sword", Category.WEAPONS, true),
		IRON_SWORD(Material.IRON_SWORD, "Iron Sword", Category.WEAPONS, true),
		DIAMOND_SWORD(Material.DIAMOND_SWORD, "Diamond Sword", Category.WEAPONS, true),
		
		SPEED_SWORD(Material.IRON_SWORD, "Rogue Sword", Category.WEAPONS, false),
		
		
		WOOD_PICK(Material.WOODEN_PICKAXE, "Wooden Pickaxe", Category.TOOLS, true),
		STONE_PICK(Material.STONE_PICKAXE, "Stone Pickaxe", Category.TOOLS, true),
		IRON_PICK(Material.IRON_PICKAXE, "Iron Pickaxe", Category.TOOLS, true),
		DIAMOND_PICK(Material.DIAMOND_PICKAXE, "Diamond Pickaxe", Category.TOOLS, true),
		WOOD_AXE(Material.WOODEN_AXE, "Wooden Axe", Category.TOOLS, true),
		
		
		
		
		NULL(Material.AIR, "null", Category.NONE, true);
		
		
		Material mat;
		String name;
		Category cat;
		boolean isVanilla;
		Item(Material mat, String name, Category cat, boolean isVanilla) {
			this.mat = mat;
			this.name = name;
			this.cat = cat;
			this.isVanilla = isVanilla;
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
	
	
	
	
	
	public enum Category {
		BLOCKS,
		TOOLS,
		WEAPONS,
		MATERIAL,
		NONE
	}
	
	static Main getInstance() {
		return JavaPlugin.getPlugin(Main.class);
	}
}
