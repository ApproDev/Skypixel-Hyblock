package com.BadDevelopers.SkypixelHyblock.Items;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class PlayerKarma implements Listener {
	NamespacedKey key = new NamespacedKey(Main.main, "karma");
	
	@EventHandler
	public void onEntityDamge(EntityDamageByEntityEvent event) {
		
		if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;
		
		Player player = (Player) event.getDamager();
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		int karma = pdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
		
		Item item = Item.valueOf(player.getInventory().getItemInMainHand());
		
		if (item.equals(Item.ANGELS_BLESSING) || item.equals(Item.DEVILS_MERCY)) {
			event.setDamage(event.getDamage() + (karma / 5.0) * (karma / 1000));
		}
		
		if (!(event.getEntity() instanceof Mob)) return;
		
		Mob damaged = (Mob) event.getEntity();
		
		
		
		LivingEntity target = damaged.getTarget();
		
		if(target != null) {
			karma += target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
		} else {
			karma -= event.getFinalDamage();
		}
		
		pdc.set(key, PersistentDataType.INTEGER, karma);
	}
}