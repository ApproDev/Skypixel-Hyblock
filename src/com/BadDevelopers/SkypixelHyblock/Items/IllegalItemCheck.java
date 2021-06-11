package com.BadDevelopers.SkypixelHyblock.Items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class IllegalItemCheck implements Runnable {

	@Override
	public void run() {
		
		//inventory check
		for (Player player : Bukkit.getOnlinePlayers()) {
												
			for (ItemStack is : player.getInventory().getContents()) {
				
				Item item = Item.valueOf(is);
								
				if ((item.equals(Item.SKELERANG))
					&& (is.getType().equals(Material.GHAST_TEAR))
					&& (Main.customweaponseventmanager.boneThrown.getOrDefault(player.getUniqueId(), false) == false)) {
					
					is.setType(Material.BONE);
					
				}
				
			}
			
		}
		
	}

}
