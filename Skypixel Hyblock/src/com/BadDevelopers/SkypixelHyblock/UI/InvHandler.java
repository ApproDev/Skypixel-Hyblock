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

public abstract class InvHandler implements Listener {
	protected Inventory inv;
	protected final HumanEntity player;
	final ArrayList<Integer> allowableSlots = new ArrayList<Integer>();
	Main main;

    public InvHandler(HumanEntity player, Main main) {
        this.player = player;
        this.main = main;
        initGUI();
        initializeItems();
        player.closeInventory();
		player.openInventory(inv);
    }
    public void initGUI() {inv = Bukkit.createInventory(null, 9, "Abstract");}
    public void initializeItems() {
    	for (int slot = 0; slot < inv.getSize(); slot++) if (!allowableSlots.contains(slot)) inv.setItem(slot, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getClickedInventory().equals(inv)) return;

        if (!allowableSlots.contains(e.getRawSlot())) e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
    }
}
