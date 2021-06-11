package com.BadDevelopers.SkypixelHyblock.Enchantments;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.BadDevelopers.SkypixelHyblock.Main;

@Deprecated
public class EnchantListener implements Listener {
	
	Main main;
	
	public EnchantListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEntityDie(EntityDeathEvent event) {
		Entity dead = event.getEntity();
		
		EntityDamageEvent cause = dead.getLastDamageCause();
		if (cause == null) return;
		if (!cause.getCause().equals(DamageCause.ENTITY_ATTACK)) return;
		EntityDamageByEntityEvent prev = (EntityDamageByEntityEvent) cause;
		if (!(prev.getDamager() instanceof Player)) return;
		
		Player p = (Player) prev;
		
		ItemStack mainhand = p.getInventory().getItemInMainHand();
		
		ItemMeta im = mainhand.getItemMeta();
		
		if (im == null) return;
		
		List<String> lore = im.getLore();
		
		if (lore == null) return;
		
		
		for (String line : lore) {
			String[] split = line.split(" ");
			switch (split[0]) {
			case "telekinesis":
				if (new Random().nextInt(3) + 1 <= Integer.parseInt(split[1])) {
					PlayerInventory inv = p.getInventory();
					for (ItemStack is : event.getDrops())
						inv.addItem(is);
					event.getDrops().clear();
				}
				break;
			default:
				break;
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		
		ItemStack mainhand = p.getInventory().getItemInMainHand();
		
		ItemMeta im = mainhand.getItemMeta();
		Bukkit.broadcastMessage("1");
		if (im == null) return;
		Bukkit.broadcastMessage("2");
		List<String> lore = im.getLore();
		
		if (lore == null) return;
		
		Bukkit.broadcastMessage("3");
		for (String line : lore) {
			String[] split = line.split(" ");
			Bukkit.broadcastMessage(split[0]+" "+split[1]);
			if (split[0].strip().equals("telekinesis".strip())) {
				Bukkit.broadcastMessage("4");
				if (new Random().nextInt(3) + 1 <= Integer.parseInt(split[1])) {
					event.setDropItems(false);
					PlayerInventory inv = p.getInventory();
					for (ItemStack is : event.getBlock().getDrops(inv.getItemInMainHand(), p))
						inv.addItem(is);
				}
				break;
			}
		}
	}
}
