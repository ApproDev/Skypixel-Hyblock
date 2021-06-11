package com.BadDevelopers.SkypixelHyblock.Items;

import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class Crafting {
	
	public static enum RecipeType {
		WOODWORKING("Woodworking", Item.OAK_PLANKS, new Recipe[] {
				Recipe.OAK_LOG_TO_PLANKS,
				Recipe.OAK_PLANKS_TO_STICKS,
				Recipe.STICKS_AND_PLANKS_TO_PICKAXE}),
		
		BLACKSMITHING("Blacksmithing", Item.ANVIL, new Recipe[] {
				Recipe.SCRAP_AND_GOLD_TO_NETHERITE_INGOT,
				Recipe.NETHERITE_INGOT_TO_NETHERITE_HELM,
				Recipe.NETHERITE_INGOT_TO_NETHERITE_CHEST,
				Recipe.NETHERITE_INGOT_TO_NETHERITE_LEGS,
				Recipe.NETHERITE_INGOT_TO_NETHERITE_BOOTS,
				Recipe.DIAMOND_TO_DIAMOND_HELM,
				Recipe.DIAMOND_TO_DIAMOND_CHEST,
				Recipe.DIAMOND_TO_DIAMOND_LEGS,
				Recipe.DIAMOND_TO_DIAMOND_BOOTS}),
		
		STONEWORKING("Stoneworking", Item.STONE, new Recipe[] {});
		
		public String name;
		public Recipe[] involved;
		public Item texture;
		
		RecipeType(String name, Item texture, Recipe[] involved) {
			this.name = name;
			this.involved = involved;
			this.texture = texture;
		}
		
		public static RecipeType valueOf(int ordinal) {
			return values()[ordinal];
		}
	}
	
	
	public static enum Recipe {
		
		SCRAP_AND_GOLD_TO_NETHERITE_INGOT(Item.NETHERITE_INGOT, 1, new Item[] {Item.NETHERITE_SCRAP, Item.GOLD_INGOT},  new Integer[] {4, 4}),
		
		NETHERITE_INGOT_TO_NETHERITE_HELM(Item.NETHERITE_HELM, 1 , new Item[] {Item.NETHERITE_INGOT}, new Integer[] {5}),
		NETHERITE_INGOT_TO_NETHERITE_CHEST(Item.NETHERITE_CHEST, 1 , new Item[] {Item.NETHERITE_INGOT}, new Integer[] {8}),
		NETHERITE_INGOT_TO_NETHERITE_LEGS(Item.NETHERITE_LEGS, 1 , new Item[] {Item.NETHERITE_INGOT}, new Integer[] {7}),
		NETHERITE_INGOT_TO_NETHERITE_BOOTS(Item.NETHERITE_BOOTS, 1 , new Item[] {Item.NETHERITE_INGOT}, new Integer[] {4}),
		
		DIAMOND_TO_DIAMOND_HELM(Item.DIAMOND_HELM, 1 , new Item[] {Item.DIAMOND}, new Integer[] {5}),
		DIAMOND_TO_DIAMOND_CHEST(Item.DIAMOND_CHEST, 1 , new Item[] {Item.DIAMOND}, new Integer[] {8}),
		DIAMOND_TO_DIAMOND_LEGS(Item.DIAMOND_LEGS, 1 , new Item[] {Item.DIAMOND}, new Integer[] {7}),
		DIAMOND_TO_DIAMOND_BOOTS(Item.DIAMOND_BOOTS, 1 , new Item[] {Item.DIAMOND}, new Integer[] {4}),
		
		
		
		OAK_LOG_TO_PLANKS(Item.OAK_PLANKS, 4, new Item[] {Item.OAK_LOG}, new Integer[] {1}),
		OAK_PLANKS_TO_STICKS(Item.STICK, 4, new Item[] {Item.OAK_PLANKS}, new Integer[] {2}),
		STICKS_AND_PLANKS_TO_PICKAXE(Item.WOOD_PICK, 1, new Item[] {Item.STICK, Item.OAK_PLANKS}, new Integer[] {2, 3}),
		
		NULL(Item.NULL, 0, new Item[] {Item.OAK_LOG}, new Integer[] {1});
		
		public Item[] recipe;
		public Integer[] quantity;
		public Item out;
		public int outQ;
		
		Recipe(Item out, int outQuantity, Item[] requirement, Integer[] requirementQ) {
			recipe = requirement;
			quantity = requirementQ;
			this.out = out;
			this.outQ = outQuantity;
		}
		
		public static Recipe valueOf(int ordinal) {
			return values()[ordinal];
		}
		
		
		/*@SuppressWarnings("unused")
		public static Crafting getRecipe(Item[][] table) {
			for (Recipe rec : values()) {
				Item[][] recipe = rec.recipe;
				Item[][] tempR = null;
				ArrayList<ArrayList<Integer>> qFArray = null;
				for (int x = 0; x < (table.length - recipe.length) + 1; x++) {
					Item[] recRow = recipe[x];
					Item[] tableRow = table[x];
					for (int y = 0; y < (tableRow.length - recRow.length) + 1; y++) {
						qFArray = new ArrayList<ArrayList<Integer>>();
						
						tempR = new Item[3][3];
						for (int i = 0; i < 3; i++) {
							tempR[i] = new Item[3];
							for (int n = 0; n < 3; n++) {
								tempR[i][n] = Item.NULL;
							}
						}
						
						for (int i = 0; i < recipe.length; i++) {
							System.arraycopy(recipe[i + x], 0, tempR[i + x], y, recipe[i + x].length);
						}
						
						loop :for (int itemX = 0; itemX < 3; itemX++) {
							String s = "";
							for (int itemY = 0; itemY < 3; itemY++) {
								s.concat(" . "+tempR[itemX][itemY].toString());
								if (!tempR[itemX][itemY].equals(table[itemX][itemY])) {
									qFArray = new ArrayList<ArrayList<Integer>>();
									tempR = new Item[3][3];
									Bukkit.broadcastMessage("RESET");
									break loop;
								}
							Bukkit.broadcastMessage(s);
							}
						}
						//while (qFArray.size() < 3) {
						//	qFArray.add(new ArrayList<Integer>());
						//}
						//for (int i = 0; i < qFArray.size(); i++)
						//	for (int n = 0; n < qFArray.get(i).size(); n++) {
						//		ArrayList al = qFArray
						//}
						
					}
					return new Crafting(rec, tempR, toIntegerArray(qFArray));
				}
			}
			return new Crafting(Recipe.NULL, new Item[0][0], new Integer[0][0]);
		}
		
		static Integer[][] toIntegerArray(ArrayList<ArrayList<Integer>> list) {
			ArrayList<Integer[]> list1 = new ArrayList<Integer[]>();
			int innerSize = 0;
			for (ArrayList<Integer> element : list) {
				Integer[] array = element.toArray(new Integer[element.size()]);
				if (array.length > innerSize) innerSize = array.length;
				list1.add(array);
			}
			
			return list1.toArray(new Integer[list1.size()][innerSize]);
		}
		
		static Item[][] toItemArray(ArrayList<ArrayList<Item>> list) {
			ArrayList<Item[]> list1 = new ArrayList<Item[]>();
			int innerSize = 0;
			for (ArrayList<Item> element : list) {
				Item[] array = element.toArray(new Item[element.size()]);
				if (array.length > innerSize) innerSize = array.length;
				list1.add(array);
			}
			
			return list1.toArray(new Item[list1.size()][innerSize]);
		}*/
	}
	
}
