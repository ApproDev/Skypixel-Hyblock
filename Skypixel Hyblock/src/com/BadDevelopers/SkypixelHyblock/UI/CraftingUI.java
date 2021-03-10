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

public class CraftingUI extends InvHandler {
	
	public RecipeType type;

	public CraftingUI(HumanEntity player, Main main) {
		super(player, main);
	}

	@Override
	void invSpecificEvents(InventoryClickEvent e) {
		if (e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;
		
		Recipe recipe = Recipe.valueOf(e.getRawSlot());
		HumanEntity player = e.getWhoClicked();
		PlayerInventory pi = player.getInventory();
		HashMap<Item, Integer> stuff = new HashMap<Item, Integer>();
		for (int i = 0; i < recipe.recipe.length; i++) {
			Item item = recipe.recipe[i];
			ItemStack is = item.getItem(recipe.quantity[i], main);
			if (!pi.containsAtLeast(is, recipe.quantity[i])) {
				player.sendMessage(Main.prefix+ChatColor.YELLOW+"You dont have the materials for that!");
				return;
			}
			for (int n = 0; n < recipe.recipe.length; n++) {
				stuff.put(recipe.recipe[n], recipe.quantity[n]);
			}
			for (ItemStack itemS : pi.getContents()) {
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
		
		pi.addItem(recipe.out.getItem(recipe.outQ, main));
	}
	
	
	public void initItems() {
    	for (int slot = 0; slot < type.involved.length; slot++) {
    		Recipe recipe = type.involved[slot];
    		Item item = recipe.out;
    		ItemStack is = item.getItem(recipe.outQ, main);
    		ItemMeta im = is.getItemMeta();
    		ArrayList<String> lore = new ArrayList<String>();
    		for (int i = 0; i < recipe.quantity.length; i++) {
    			lore.add(ChatColor.GRAY+""+recipe.quantity[i]+" "+recipe.recipe[i]);
    		}
    		im.setLore(lore);
    		is.setItemMeta(im);
    		inv.setItem(slot, is);
    	}
	}
	
	@Override
	public void initGUI() {
		inv = Bukkit.createInventory(null, 54, "Crafting");
		}

}
