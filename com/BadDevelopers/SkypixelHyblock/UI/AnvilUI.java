 package com.BadDevelopers.SkypixelHyblock.UI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.BadDevelopers.SkypixelHyblock.Main;

public class AnvilUI extends InvHandler {

	public AnvilUI(HumanEntity player, Main main) {
		super(player, main);
	}
	
	@Override
	public void initGUI() {
		inv = Bukkit.createInventory(null, 45, "Anvil");
		
		allowableSlots.add(13);
		allowableSlots.add(29);
		allowableSlots.add(33);
		
		inv.setItem(13, new ItemStack(Material.RED_STAINED_GLASS_PANE));
	}

	@Override
	void invSpecificEvents(InventoryClickEvent e) {
		if (inv.getItem(29) == null || inv.getItem(33) == null) return;
		
		
		
	}

}
