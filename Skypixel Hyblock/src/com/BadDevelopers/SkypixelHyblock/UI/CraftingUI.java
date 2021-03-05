package com.BadDevelopers.SkypixelHyblock.UI;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Items.Crafting.Recipe;
import com.BadDevelopers.SkypixelHyblock.Items.Crafting.RecipeType;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

// Shows a UI for a particular category eg Woodworking
public class CraftingUI extends InvHandler {
	
	public RecipeType type;

	public CraftingUI(HumanEntity player, Main main) {
		super(player, main);
	}

	@Override
	void invSpecificEvents(InventoryClickEvent e) {
		// If the clicked item is the background item, return as it won't be a required slot.
		// e.getCurrentItem() will never be null as no slots in this UI can be empty
		if (e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;
		
		Recipe recipe = Recipe.valueOf(e.getRawSlot()); // The recipes are entered by Recipe#values so they should be in order of the slot number
		HumanEntity player = e.getWhoClicked();
		PlayerInventory pi = player.getInventory();
		HashMap<Item, Integer> stuff = new HashMap<Item, Integer>();
		for (int i = 0; i < recipe.recipe.length; i++) { // Iterates over the items in the recipe, to check that the player has all of the required materials
			Item item = recipe.recipe[i];
			ItemStack is = item.getItem(recipe.quantity[i], main);
			if (!pi.containsAtLeast(is, recipe.quantity[i])) {
				player.sendMessage(Main.prefix+ChatColor.YELLOW+"You dont have the materials for that!");
				return;
			} // puts all of the items in a map
			for (int n = 0; n < recipe.recipe.length; n++) {
				stuff.put(recipe.recipe[n], recipe.quantity[n]);
			}
			for (ItemStack itemS : pi.getContents()) { // removes the items from the player
				Item Sitem = Item.valueOf(itemS);
				if (!stuff.containsKey(Sitem)) continue;
				int Q = stuff.get(Sitem);
				
				if (itemS.getAmount() >= Q) {
					itemS.setAmount(itemS.getAmount() - Q);
					stuff.remove(Sitem);
				}
				
				else {
					stuff.put(Sitem, itemS.getAmount() - Q);
					itemS.setAmount(0);
				}
			}
			
		}
		// Adds the output item to the player's inventory
		pi.addItem(recipe.out.getItem(recipe.outQ, main));
	}
	
	// Adds the items to the UI
	public void initItems() {
    	for (int slot = 0; slot < type.involved.length; slot++) {
    		Recipe recipe = type.involved[slot];
    		Item item = recipe.out;
    		ItemStack is = item.getItem(recipe.outQ, main);
    		ItemMeta im = is.getItemMeta();
    		ArrayList<String> lore = new ArrayList<String>(); // creates a custom lore, which is the items required to make it
    		for (int i = 0; i < recipe.quantity.length; i++) {
    			lore.add(ChatColor.GRAY+""+recipe.quantity[i]+" "+recipe.recipe[i]);
    		}
    		im.setLore(lore);
    		is.setItemMeta(im);
    		inv.setItem(slot, is);
    	}
	}
	
	@Override // creates the inventory with the correct size
	public void initGUI() {
		inv = Bukkit.createInventory(null, 54, "Crafting");
		}

}
