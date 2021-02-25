package com.BadDevelopers.SkypixelHyblock.Items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;

public interface ItemHolder {
	public enum Item {
		WORKBENCH(Material.CRAFTING_TABLE, "Workbench", Category.BLOCKS, true),
		DIRT(Material.DIRT, "Dirt", Category.BLOCKS, true),
		
		OAK_LOG(Material.OAK_LOG, Category.BLOCKS, new oreDic[] {oreDic.LOG}),
		SPRUCE_LOG(Material.SPRUCE_LOG, Category.BLOCKS, new oreDic[] {oreDic.LOG}),
		DARK_LOG(Material.DARK_OAK_LOG, "Dark Log", Category.BLOCKS, new oreDic[] {oreDic.LOG}),
		SHIT_LOG(Material.BIRCH_LOG, Category.BLOCKS, new oreDic[] {oreDic.LOG}),
		
		
		WOOD_SWORD(Material.WOODEN_SWORD, Category.WEAPONS),
		STONE_SWORD(Material.STONE_SWORD, Category.WEAPONS),
		IRON_SWORD(Material.IRON_SWORD, Category.WEAPONS),
		DIAMOND_SWORD(Material.DIAMOND_SWORD, Category.WEAPONS),
		
		SPEED_SWORD(Material.GOLDEN_SWORD, "Rogue Sword", Category.WEAPONS),
		
		
		WOOD_PICK(Material.WOODEN_PICKAXE, Category.TOOLS),
		STONE_PICK(Material.STONE_PICKAXE, Category.TOOLS),
		IRON_PICK(Material.IRON_PICKAXE, Category.TOOLS),
		DIAMOND_PICK(Material.DIAMOND_PICKAXE, Category.TOOLS),
		WOOD_AXE(Material.WOODEN_AXE, Category.TOOLS),
		
		
		
		IRON_HELM(Material.IRON_HELMET, Category.ARMOUR),
		IRON_CHEST(Material.IRON_CHESTPLATE, Category.ARMOUR),
		IRON_LEGS(Material.IRON_LEGGINGS, Category.ARMOUR),
		IRON_BOOTS(Material.IRON_BOOTS, Category.ARMOUR),
		
		LAPIS_HELM(Material.LEATHER_HELMET, Color.fromRGB(0, 0, 255), "Lapis Helmet", Category.ARMOUR, false, 25),
		LAPIS_CHEST(Material.LEATHER_CHESTPLATE, Color.fromRGB(0, 0, 255), "Lapis Chestplate", Category.ARMOUR, false, 40),
		LAPIS_LEGS(Material.LEATHER_LEGGINGS, Color.fromRGB(0, 0, 255), "Lapis Leggings", Category.ARMOUR, false, 35),
		LAPIS_BOOTS(Material.LEATHER_BOOTS, Color.fromRGB(0, 0, 255), "Lapis Boots", Category.ARMOUR, false, 20),
		
		
		
		
		NULL(Material.AIR, "null", Category.NONE, true);
		
		
		
		Material mat;
		String name;
		Category cat;
		oreDic[] oreDict;
		boolean isVanilla;
		Color colour = null;
		Integer armour = 0;
		Item(Material mat, Color colour, String name, Category cat, boolean isVanilla, Integer armour) {
			this(mat, name, cat, isVanilla);
			this.colour = colour;
			this.armour = armour;
		}
		
		Item(Material mat, String name, Category cat, boolean isVanilla, oreDic[] oreDict) {
			this.mat = mat;
			this.name = name;
			this.cat = cat;
			this.isVanilla = isVanilla;
			this.oreDict = oreDict;
		}
		
		Item(Material mat, String name, Category cat, boolean isVanilla) {
			this( mat, name, cat, isVanilla, new oreDic[0] );
		}
		Item(Material mat, String name, Category cat) {
			this( mat, name, cat, false, new oreDic[0] );
		}
		
		Item(Material mat, String name) {
			this( mat, name, Category.NONE, false, new oreDic[0] );
		}
		Item(Material mat, Category cat) {
			this( mat, mat.name(), cat, true, new oreDic[0] );
		}
		
		Item(Material mat, String name, Category cat, oreDic[] oreDict) {
			this(mat, name, cat, true);
		}

		Item(Material mat, Category cat, oreDic[] oreDict) {
			this(mat, mat.name(), cat, oreDict);
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
			
			if (im instanceof LeatherArmorMeta && colour != null) ((LeatherArmorMeta) im).setColor(colour);;
			
			im.setDisplayName(name);
			im.setUnbreakable(true);
			
			PersistentDataContainer tags = im.getPersistentDataContainer();
			
			tags.set(new NamespacedKey(getInstance(), "id"), PersistentDataType.INTEGER, ordinal());
			
			is.setItemMeta(im);
			
			return is;
		}
		
		public static boolean isSkyItem(ItemStack is) {
			if (is == null) return false;
			ItemMeta im = is.getItemMeta();
			if (im == null) return false;
			PersistentDataContainer pds = im.getPersistentDataContainer();
			if (pds == null) return false;
			if (pds.has(new NamespacedKey(getInstance(), "id"), PersistentDataType.INTEGER)) return true;
			else return false;
		}
		
		public static Item valueOf(ItemStack is) {
			PersistentDataContainer pds = is.getItemMeta().getPersistentDataContainer();
			
			if (!isSkyItem(is)) return Item.NULL;
			
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
