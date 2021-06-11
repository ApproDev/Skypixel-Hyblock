package com.BadDevelopers.SkypixelHyblock.UI;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.BadDevelopers.SkypixelHyblock.CustomRaces.Race;
import com.BadDevelopers.SkypixelHyblock.Main;

public class RaceUI extends InvHandler {

	public RaceUI(HumanEntity player, Main main) {
		super(player, main);
	}
	
	@Override
	public void initGUI() {
		inv = Bukkit.createInventory(null, 18, "Choose a race...");
	}
	
	public void initializeItems() {
		
    	for (int slot = 0; slot < inv.getSize(); slot++)
    		inv.setItem(slot, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
    	
    	for (int i = 0; i < Race.values().length; i++) {
    		Race race = Race.valueOf(i);
    		ItemStack is = new ItemStack(race.representative);
    		ItemMeta im = is.getItemMeta();
    		im.setDisplayName(ChatColor.RESET+""+ChatColor.DARK_AQUA+race.format());
    		is.setItemMeta(im);
    		inv.setItem(i, is);
    	}
    }
	
	@Override
	void invSpecificEvents(InventoryClickEvent e) {
		Material mat = inv.getItem(e.getRawSlot()).getType();
		if (mat.equals(Material.GRAY_STAINED_GLASS_PANE)) return;
		if (mat.equals(Material.RED_TERRACOTTA)) {
			Race race = Race.valueOf(e.getRawSlot());
			race.setRace((Player) player, main);
			
			race.applyStats((Player) player, main);
			player.closeInventory();
			
		}
		
		else {
			ItemStack is = new ItemStack(Material.RED_TERRACOTTA);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(ChatColor.WHITE+"Are you sure?");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.DARK_RED+"This cannot be changed later.");
			im.setLore(lore);
			is.setItemMeta(im);
			
			inv.setItem(e.getRawSlot(), is);
		}
	}

}
