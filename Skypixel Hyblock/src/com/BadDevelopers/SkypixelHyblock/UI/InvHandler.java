package com.BadDevelopers.SkypixelHyblock.UI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.BadDevelopers.SkypixelHyblock.Main;

// Used for creating various GUIs
public abstract class InvHandler implements Listener {
	protected Inventory inv; // the inventory it creates
	protected final HumanEntity player; // the player that it opens the inventory to
	final ArrayList<Integer> allowableSlots = new ArrayList<Integer>(); // the slots which won't be auto cancelled
	Main main;

    public InvHandler(HumanEntity player, Main main) {
        this.player = player;
        this.main = main;
        initGUI();
        initializeItems();
        player.closeInventory(); // in case the player had a different inventory open
		player.openInventory(inv);
    }
    // Creates the inventory. This is designed to be overridden, to change inventory size & title
    public void initGUI() {inv = Bukkit.createInventory(null, 9, "Abstract");}
    
    // Sets all slots except for allowableSlots to a grey glass pane
    public void initializeItems() {
    	for (int slot = 0; slot < inv.getSize(); slot++) 
    		if (!allowableSlots.contains(slot)) 
    			inv.setItem(slot, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
    	if (e.getClickedInventory() == null) return;
        if (!e.getClickedInventory().equals(inv)) return;
        if (e.getViewers().size() == 0) {
        	e.getHandlers().unregister(this);
        	return;
        }
        
        invSpecificEvents(e); // Events specific to a particular inventory

        if (!allowableSlots.contains(e.getRawSlot())) e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
    }
    
    // Needs to be overriden inventory by inventory
    abstract void invSpecificEvents(InventoryClickEvent e);
}
