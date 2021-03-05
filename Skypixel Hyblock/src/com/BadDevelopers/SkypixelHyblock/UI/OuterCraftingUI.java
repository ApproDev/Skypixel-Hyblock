package com.BadDevelopers.SkypixelHyblock.UI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Items.Crafting.RecipeType;

// For selecting a recipe category to craft
public class OuterCraftingUI extends InvHandler {

	public OuterCraftingUI(HumanEntity player, Main main) {
		super(player, main);
	}

	@Override
	void invSpecificEvents(InventoryClickEvent e) {
		if (e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;
		
		CraftingUI ui = new CraftingUI(e.getWhoClicked(), main);
		
		ui.type = RecipeType.valueOf(e.getSlot());
		
		ui.initItems();
		
		Bukkit.getPluginManager().registerEvents(ui, main);
		
	}
	
	@Override // might need to be extended if we add too many recipe categories
	public void initGUI() {inv = Bukkit.createInventory(null, 9, "Crafting");}
	
	@Override
	public void initializeItems() {
    	for (int slot = 0; slot < inv.getSize(); slot++) if (!allowableSlots.contains(slot)) inv.setItem(slot, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    	// Sets the categories
    	for (int i = 0; i < RecipeType.values().length; i++) {
    		ItemStack is = RecipeType.valueOf(i).texture.getItem(1, main);
    		ItemMeta im = is.getItemMeta();
    		im.setDisplayName(ChatColor.RESET+RecipeType.valueOf(i).name);
    		is.setItemMeta(im);
    		inv.setItem(i, is);
    	}
	}

}
