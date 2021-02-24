package com.BadDevelopers.SkypixelHyblock.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class CustomWeaponsEventManager implements Listener {
	
	final Main main;
	public CustomWeaponsEventManager(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if (!event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		Player player = event.getPlayer();
		ItemStack is = player.getInventory().getItemInMainHand();
		if (is == null) return;
		if (is.getAmount() == 0) return;
		if (is.getType() == null) return;
		if (is.getType().equals(Material.AIR)) return;
		
		Item item = Item.valueOf(is);
		
		switch(item) {
		case SPEED_SWORD:
			Main.stats.addStat(player, 20, Stat.Speed, 30000);
			break;
		
		default:
			break;
		}
	}
}
