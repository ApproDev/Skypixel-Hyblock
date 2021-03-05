package com.BadDevelopers.SkypixelHyblock.Items;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;
import com.BadDevelopers.SkypixelHyblock.Enchantments.Glow;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

// All items & various functions concerning said items
public interface ItemHolder {
	public enum Item {
		

		OAK_PLANKS(Material.OAK_PLANKS, Category.BLOCKS),
		
		STONE(Material.STONE, Category.BLOCKS),
		

		//vanilla blocks
		WORKBENCH(Material.CRAFTING_TABLE, "Workbench", Category.BLOCKS, true),
		DIRT(Material.DIRT, "Dirt", Category.BLOCKS, true),		

    
		OAK_LOG(Material.OAK_LOG, Category.BLOCKS),
		SPRUCE_LOG(Material.SPRUCE_LOG, Category.BLOCKS),
		DARK_LOG(Material.DARK_OAK_LOG, "Dark Log", Category.BLOCKS),
		SHIT_LOG(Material.BIRCH_LOG, Category.BLOCKS),
		
		//vanilla weapons
		WOOD_SWORD(Material.WOODEN_SWORD, Category.WEAPONS),
		STONE_SWORD(Material.STONE_SWORD, Category.WEAPONS),
		IRON_SWORD(Material.IRON_SWORD, Category.WEAPONS),
		DIAMOND_SWORD(Material.DIAMOND_SWORD, Category.WEAPONS),
		
		//hyblock weapons
		SPEED_SWORD(Material.GOLDEN_SWORD, "Rogue Sword", Category.WEAPONS),
		
		//vanilla tools
		WOOD_PICK(Material.WOODEN_PICKAXE, Category.TOOLS),
		STONE_PICK(Material.STONE_PICKAXE, Category.TOOLS),
		IRON_PICK(Material.IRON_PICKAXE, Category.TOOLS),
		DIAMOND_PICK(Material.DIAMOND_PICKAXE, Category.TOOLS),
		WOOD_AXE(Material.WOODEN_AXE, Category.TOOLS),
		
		
		//vanilla armour sets
		IRON_HELM(Material.IRON_HELMET, Category.ARMOUR),
		IRON_CHEST(Material.IRON_CHESTPLATE, Category.ARMOUR),
		IRON_LEGS(Material.IRON_LEGGINGS, Category.ARMOUR),
		IRON_BOOTS(Material.IRON_BOOTS, Category.ARMOUR),
		
		//hyblock armour sets
		LAPIS_HELM(Material.LEATHER_HELMET, Color.fromRGB(0, 0, 255), ChatColor.AQUA+"Lapis Helmet", Category.ARMOUR, false, 25),
		LAPIS_CHEST(Material.LEATHER_CHESTPLATE, Color.fromRGB(0, 0, 255), ChatColor.AQUA+"Lapis Chestplate", Category.ARMOUR, false, 40),
		LAPIS_LEGS(Material.LEATHER_LEGGINGS, Color.fromRGB(0, 0, 255), ChatColor.AQUA+"Lapis Leggings", Category.ARMOUR, false, 35),
		LAPIS_BOOTS(Material.LEATHER_BOOTS, Color.fromRGB(0, 0, 255), ChatColor.AQUA+"Lapis Boots", Category.ARMOUR, false, 20),
		
		REDSTONE_HELM(Material.LEATHER_HELMET, Color.fromRGB(175, 0, 0), ChatColor.DARK_RED+"Redstone Helmet", Category.ARMOUR, false, 25),
		REDSTONE_CHEST(Material.LEATHER_CHESTPLATE, Color.fromRGB(175, 0, 0), ChatColor.DARK_RED+"Redstone Chestplate", Category.ARMOUR, false, 25),
		REDSTONE_LEGS(Material.LEATHER_LEGGINGS, Color.fromRGB(175, 0, 0), ChatColor.DARK_RED+"Redstone Leggings", Category.ARMOUR, false, 25),
		REDSTONE_BOOTS(Material.LEATHER_BOOTS, Color.fromRGB(175, 0, 0), ChatColor.DARK_RED+"Redstone Boots", Category.ARMOUR, false, 25),
		
		//materials
		ENCHANTED_DIAMOND(Material.DIAMOND, "Enchanted Diamond", Category.MATERIAL, false),
		STICK(Material.STICK, Category.MATERIAL),

		//talismans 
		SHINY_YELLOW_ROCK(Material.GOLD_NUGGET, "Shiny yellow Rock", Category.TALISMAN, "ring_of_love", 1, true, new Stat[] {}, new Integer[] {}),
		DAY_CRYSTAL(Material.NETHER_STAR, "Day Crystal", Category.TALISMAN, "day_crystal", 3, true, new Stat[] {}, new Integer[] {}),
		NIGHT_CRYSTAL(Material.NETHER_STAR, "Night Crystal", Category.TALISMAN, "night_crystal", 3, true, new Stat[] {}, new Integer[] {}),
		MELODYS_HAIR(Material.STRING, "Melody's Hair", Category.TALISMAN, "melody", 4, true, new Stat[] {}, new Integer[] {}),
		PERSONAL_COMPACTOR_4000(Material.DROPPER, "Personal Compactor 4000", Category.TALISMAN, "compactor", 2, true, new Stat[] {}, new Integer[] {}),
		PERSONAL_COMPACTOR_5000(Material.DROPPER, "Personal Compactor 5000", Category.TALISMAN, "compactor", 3, true, new Stat[] {}, new Integer[] {}),
		PERSONAL_COMPACTOR_6000(Material.DROPPER, "Personal Compactor 6000", Category.TALISMAN, "compactor", 4, true, new Stat[] {}, new Integer[] {}),
		PERSONAL_COMPACTOR_7000(Material.DROPPER, "Personal Compactor 7000", Category.TALISMAN, "compactor", 5, true, new Stat[] {}, new Integer[] {}),
		
		
		//talismans (heads)
		//FORMAT: <TALISMAN_NAME>("<skin url>", "<Talisman Name>", Category.TALISMAN, "<family>", 1, false, new Stat[] {}, new Integer[] {}),
		POTATO_TALISMAN("http://textures.minecraft.net/texture/dbfb6d4a54c17e2748437acc7098fbb1a3a12a407f51b3e49542332714846fd8", "Potato Talisman", Category.TALISMAN, "potato", 1, false, new Stat[] {Stat.Speed}, new Integer[] {1}),
		SCAVENGER_TALISMAN("http://textures.minecraft.net/texture/211ab3a1132c9d1ef835ea81d972ed9b5cd8ddff0a07c55a749bcfcf8df5", "Scavenger Talisman", Category.TALISMAN, "scavenger", 1, false, new Stat[] {}, new Integer[] {}),
		MINE_AFFINITY_TALISMAN("http://textures.minecraft.net/texture/d9563fdc4acab6db324b21bc43b238fe465e530a6327e7eef11d2d0c4ea", "Mine Affinity Talisman", Category.TALISMAN, "mine", 1, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/		VILLAGE_AFFINITY_TALISMAN("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Village Affinity Talisman", Category.TALISMAN, "village", 1, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/		VACCINE_TALISMAN("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Vaccine Talisman", Category.TALISMAN, "vaccine", 1, false, new Stat[] {}, new Integer[] {}),
		SKELETON_TALISMAN("http://textures.minecraft.net/texture/301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2", "Skeleton Talisman", Category.TALISMAN, "skeleton", 1, false, new Stat[] {}, new Integer[] {}),
		FARMING_TALISMAN("http://textures.minecraft.net/texture/9af328c87b068509aca9834eface197705fe5d4f0871731b7b21cd99b9fddc", "Farming Talisman", Category.TALISMAN, "farming", 1, false, new Stat[] {}, new Integer[] {}),
		KING_TALISMAN("http://textures.minecraft.net/texture/3e10b69a973869b961db7909342c6565a88bcd10f7058a07bcd740956e89637", "King Talisman", Category.TALISMAN, "king", 1, false, new Stat[] {}, new Integer[] {}),
		FIRE_TALISMAN("http://textures.minecraft.net/texture/70a44d51ecc79f694cfd60228c88428848ca618e36a659a416e9246d841aec8", "Fire Talisman", Category.TALISMAN, "fire", 1, false, new Stat[] {}, new Integer[] {}),
		NIGHT_VISION_CHARM("http://textures.minecraft.net/texture/ec33fa1487ec8376d8c8f4bd91077dadecc2ad1bce3b7d48fb88218fbfc6fe19", "Night Vision Talisman", Category.TALISMAN, "night_vision", 1, false, new Stat[] {}, new Integer[] {}),
		TALISMAN_OF_COINS("http://textures.minecraft.net/texture/452dca68c8f8af533fb737faeeacbe717b968767fc18824dc2d37ac789fc77", "Talisman Of Coins", Category.TALISMAN, "talisman_coins", 1, false, new Stat[] {}, new Integer[] {}),
		ZOMBIE_TALISMAN("http://textures.minecraft.net/texture/56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11", "Zombie Talisman", Category.TALISMAN, "zombie", 1, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/		ZOMBIE_RING("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Zombie Ring", Category.TALISMAN, "zombie", 2, false, new Stat[] {}, new Integer[] {}),
		ZOMBIE_ARTIFACT("http://textures.minecraft.net/texture/c3fb4e5db97f479c66a42bbd8a7d781daf201a8ddaf77afcf4aef87779aa8b4", "Zombie Artifact", Category.TALISMAN, "zombie", 3, false, new Stat[] {}, new Integer[] {}),
		MEDIOCRE_RING_OF_LOVE("http://textures.minecraft.net/texture/46d339070a0b87529ffcc7b8960ef52d70b6f20094fe31f62afad252c663371", "Mediocre Ring Of Love", Category.TALISMAN, "ring_of_love", 2, false, new Stat[] {}, new Integer[] {}),
		MODEST_RING_OF_LOVE("http://textures.minecraft.net/texture/46d339070a0b87529ffcc7b8960ef52d70b6f20094fe31f62afad252c663371", "Modest Ring Of Love", Category.TALISMAN, "ring_of_love", 3, false, new Stat[] {}, new Integer[] {}),
		EXQUISITE_RING_OF_LOVE("http://textures.minecraft.net/texture/46d339070a0b87529ffcc7b8960ef52d70b6f20094fe31f62afad252c663371", "Exquisite Ring Of Love", Category.TALISMAN, "ring_of_love", 4, false, new Stat[] {}, new Integer[] {}),
		LEGENDARY_RING_OF_LOVE("http://textures.minecraft.net/texture/46d339070a0b87529ffcc7b8960ef52d70b6f20094fe31f62afad252c663371", "LEGENDARY Ring Of Love", Category.TALISMAN, "ring_of_love", 5, false, new Stat[] {}, new Integer[] {}),
		CAMPFIRE_INITIATE_BADGE("http://textures.minecraft.net/texture/af41cc2250d2f5cfcf4384aa0cf3e23c19767549a2a8abd7532bd52c5a1de", "Campfire Initiate Badge", Category.TALISMAN, "campfire", 1, false, new Stat[] {}, new Integer[] {}),
		CAMPFIRE_ADEPT_BADGE("http://textures.minecraft.net/texture/af41cc2250d2f5cfcf4384aa0cf3e23c19767549a2a8abd7532bd52c5a1de", "Campfire Adept Badge", Category.TALISMAN, "campfire", 2, false, new Stat[] {}, new Integer[] {}),
		CAMPFIRE_CULTIST_BADGE("http://textures.minecraft.net/texture/a3cfd94e925eab4330a768afcae6c128b0a28e23149eee41c9c6df894c24f3de", "Campfire Cultist Badge", Category.TALISMAN, "campfire", 3, false, new Stat[] {}, new Integer[] {}),
		CAMPFIRE_SCION_BADGE("http://textures.minecraft.net/texture/a3cfd94e925eab4330a768afcae6c128b0a28e23149eee41c9c6df894c24f3de", "Campfire Scion Badge", Category.TALISMAN, "campfire", 4, false, new Stat[] {}, new Integer[] {}),
		CAMPFIRE_GOD_BADGE("http://textures.minecraft.net/texture/4080bbefca87dc0f36536b6508425cfc4b95ba6e8f5e6a46ff9e9cb488a9ed", "Campfire God Badge", Category.TALISMAN, "campfire", 5, false, new Stat[] {}, new Integer[] {}),
		WOOD_AFFINITY_TALISMAN("http://textures.minecraft.net/texture/16e32486ea2ef286f2ed68ea11aa506353b51c3b134ba0dfa3a2950e2947a2", "Wood Affinity Talisman", Category.TALISMAN, "wood", 2, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/		SEA_CREATURE_TALISMAN("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Sea Creature Talisman", Category.TALISMAN, "sea_creature", 1, false, new Stat[] {}, new Integer[] {}),
		EMERALD_RING("http://textures.minecraft.net/texture/f36b821c1afdd5a5d14e3b3bd0a32263c8df5df5db6e1e88bf65e97b27a8530", "Emerald Ring", Category.TALISMAN, "emerald", 2, false, new Stat[] {}, new Integer[] {}),
		SPEED_TALISMAN("http://textures.minecraft.net/texture/8624bacb5f1986e6477abce4ae7dca1820a5260b6233b55ba1d9ba936c84b", "Speed Talisman", Category.TALISMAN, "speed", 1, false, new Stat[] {Stat.Speed}, new Integer[] {1}),
/*TODO get texture*/		SPEED_RING("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Speed Ring", Category.TALISMAN, "speed", 2, false, new Stat[] {Stat.Speed}, new Integer[] {3}),
/*TODO get texture*/		SPEED_ARTIFACT("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Speed Artifact", Category.TALISMAN, "speed", 3, false, new Stat[] {Stat.Speed}, new Integer[] {5}),
		CAT_TALISMAN("http://textures.minecraft.net/texture/e4b45cbaa19fe3d68c856cd3846c03b5f59de81a480eec921ab4fa3cd81317", "Cat Talisman", Category.TALISMAN, "cat", 2, false, new Stat[] {Stat.Speed}, new Integer[] {1}),
/*TODO get texture*/		LYNX_TALISMAN("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Lynx Talisman", Category.TALISMAN, "cat", 3, false, new Stat[] {Stat.Speed}, new Integer[] {2}),
		CHEETAH_TALISMAN("http://textures.minecraft.net/texture/1553f8856dd46de7e05d46f5fc2fb58eafba6829b11b160a1545622e89caaa33", "Cheetah Talisman", Category.TALISMAN, "cat", 4, false, new Stat[] {Stat.Speed}, new Integer[] {3}),
		SCARFS_STUDIES("http://textures.minecraft.net/texture/6de4ab129e137f9f4cbf7060318ee1748dc39da9b5d129a8da0e614e2337693", "Scarf's Studies", Category.TALISMAN, "scarf", 3, false, new Stat[] {}, new Integer[] {}),
		SCARFS_THESIS("http://textures.minecraft.net/texture/8ce4c87eb4dde27459e3e7f85921e7e57b11199260caa5ce63f139ee3d188c", "Scarf's Thesis", Category.TALISMAN, "scarf", 4, false, new Stat[] {}, new Integer[] {}),
		SCARFS_GRIMOIRE("http://textures.minecraft.net/texture/bafb195cc75f31b619a077b7853653254ac18f220dc32d1412982ff437b4d57a", "Scarf's Grimoire", Category.TALISMAN, "scarf", 5, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/        TREASURE_TALISMAN("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Treasure Talisman", Category.TALISMAN, "treasure", 3, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/		TREASURE_RING("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Treasure Ring", Category.TALISMAN, "treasure", 4, false, new Stat[] {}, new Integer[] {}),
/*TODO get texture*/		TREASURE_ARTIFACT("http://textures.minecraft.net/texture/e6b543464930e8d38676db6be9920dd82a54859c3497d9fe31ef92a884c84", "Treasure Artifact", Category.TALISMAN, "treasure", 5, false, new Stat[] {}, new Integer[] {}),
		
		
		
		//special talismans
		JOSH_TALISMAN("http://textures.minecraft.net/texture/46f06001bf95367c6a9e0717e4d2d41eb5a4c34d49f619ee12ebb88bc18bf837", "Josh Talisman", Category.TALISMAN, "josh", 7, false, new Stat[] {Stat.Health}, new Integer[] {20}),
		SEAN_TALISMAN("http://textures.minecraft.net/texture/5d3813d24a633062df28c5fb38ae8de808fe8da0ebc51e03b4df741da386dafb", "Sean Talisman", Category.TALISMAN, "sean", 7, false, new Stat[] {Stat.Defence}, new Integer[] {50}),
		
		NULL(Material.AIR, "null", Category.NONE, true);		
		
		
		Material mat;
		String name;
		Category cat;
		boolean isVanilla;
		Color colour = null;
		Integer armour = 0;
		boolean looksEnchanted = false;
		String talismanFamily;
		Integer rarity;
		Stat[] stats = new Stat [0];
		Integer[] statValues = new Integer [0];
		String skinURL;
		
		//talismans
		Item(Material mat, String name, Category cat, String talismanFamily, Integer rarity, boolean looksEnchanted, Stat[] stats, Integer[] statValues){
			this.mat = mat;
			this.name = name;
			this.cat = cat;
			this.talismanFamily = talismanFamily;
			this.rarity = rarity;
			this.looksEnchanted = looksEnchanted;
			this.stats = stats;
			this.statValues = statValues;
		}
		//talismans (heads)
		Item(String skinURL, String name, Category cat, String talismanFamily, Integer rarity, boolean looksEnchanted, Stat[] stats, Integer[] statValues){
			this.mat = Material.PLAYER_HEAD;
			this.skinURL = skinURL;
			this.name = name;
			this.cat = cat;
			this.talismanFamily = talismanFamily;
			this.rarity = rarity;
			this.looksEnchanted = looksEnchanted;
			this.stats = stats;
			this.statValues = statValues;
		}
		
		// Leather armour
		Item(Material mat, Color colour, String name, Category cat, boolean isVanilla, Integer armour) {
			this(mat, name, cat, isVanilla);
			this.colour = colour;
			this.armour = armour;
		}
		
		Item(Material mat, String name, Category cat, boolean isVanilla) {
			this.mat = mat;
			this.name = name;
			this.cat = cat;
			this.isVanilla = isVanilla;
		}
		
		Item(Material mat, String name, Category cat) {
			this( mat, name, cat, false);
		}
		
		Item(Material mat, String name) {
			this( mat, name, Category.NONE, false);
		}
		Item(Material mat, Category cat) {
			this( mat, mat.getKey().getKey(), cat, true);
		}

		public void giveItem(Player p, int amount, Main main) {
			if (this.equals(Item.NULL)) return;
			p.getInventory().addItem(getItem(amount, main));
		}
		
		public static Item getVanilla(Material mat) {
			for (Item item : values()) {
				if (!item.isVanilla) continue;
				
				if (item.mat.equals(mat)) return item;
			}
			return null;
		}
		
		
		public ItemStack getItem(int quantity, Main main) {
			ItemStack is = new ItemStack(mat, quantity);
			
			
			ItemMeta im = is.getItemMeta();
			if (im instanceof SkullMeta) {
				
				im = getSkull(skinURL, (SkullMeta) im);
				
			}
		
			if (im instanceof LeatherArmorMeta && colour != null) ((LeatherArmorMeta) im).setColor(colour);
			
			if (looksEnchanted) im.addEnchant(new Glow(new NamespacedKey(main, Glow.name)), 1, true);
			im.setDisplayName(ChatColor.RESET+name+ChatColor.RESET);
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
			if (is == null) return Item.NULL;
			if (is.getItemMeta() == null) return Item.NULL;
			if (is.getItemMeta().getPersistentDataContainer() == null) return Item.NULL;
			PersistentDataContainer pds = is.getItemMeta().getPersistentDataContainer();
			
			if (!isSkyItem(is)) return Item.NULL;
			
			int id = pds.get(new NamespacedKey(getInstance(), "id"), PersistentDataType.INTEGER);
			
			return valueOf(id);
		}
		
		public static Item valueOf(int ordinal) {
			return values()[ordinal];
		}
		
		public static SkullMeta getSkull(String url, SkullMeta itemmeta) {

			        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
			        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
			        Field profileField = null;

			        try {
			            profileField = itemmeta.getClass().getDeclaredField("profile");
			        } catch (NoSuchFieldException | SecurityException e) {
			            e.printStackTrace();
			        }

			        profileField.setAccessible(true);

			        try {
			            profileField.set(itemmeta, profile);
			        } catch (IllegalArgumentException | IllegalAccessException e) {
			            e.printStackTrace();
			        }

			        return itemmeta;
			} 
		
	}
	
	
	public enum Category {
		BLOCKS,
		TOOLS,
		WEAPONS,
		ARMOUR,
		MATERIAL,
		TALISMAN,
		NONE
	}
	
	static Main getInstance() {
		return JavaPlugin.getPlugin(Main.class);
	}
}

