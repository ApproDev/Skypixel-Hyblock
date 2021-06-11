package com.BadDevelopers.SkypixelHyblock.Items;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;

import net.minecraft.server.v1_16_R3.EnumItemSlot;

import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class CustomWeaponsEventManager implements Listener {
	
	final Main main;
	public CustomWeaponsEventManager(Main main) {
		this.main = main;	
	}
	
	public HashMap<UUID, Boolean> boneThrown = new HashMap<UUID, Boolean>();
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		EquipmentSlot hand = event.getHand();
		if(hand == null) return;
		if (!hand.equals(EquipmentSlot.OFF_HAND)) return;
		Player player = event.getPlayer();
		ItemStack is = player.getInventory().getItemInMainHand();
		if (is == null) return;
		if (is.getAmount() == 0) return;
		if (is.getType() == null) return;
		if (is.getType().equals(Material.AIR)) return;
		
		Item item = Item.valueOf(is);
		
		switch(item) {
		case SPEED_SWORD:
			if (!Main.stats.isReasonPresent(player, Item.SPEED_SWORD.name)) Main.stats.addStat(player, 20, Stat.Speed, 30000L, Item.SPEED_SWORD.name, false);
			break;
			
		default:
			break;
		}
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		
		if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR))) {return;}
		
		Player player = e.getPlayer();
		
		World playerWorld = player.getWorld();
		ItemStack is = player.getInventory().getItemInMainHand();
		Item skyItem = Item.valueOf(is);
		
		switch(skyItem) {
		
		case SLINGSHOT:
			
			if (is.getItemMeta().getDisplayName().equals("Loaded Slingshot")) {
				
				Vector playerDirection = player.getLocation().getDirection();
				Arrow arrow = player.launchProjectile(Arrow.class, playerDirection);
				arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
				
				ItemMeta im = is.getItemMeta();
				
				im.setDisplayName(ChatColor.RESET + "Slingshot");
				
				is.setItemMeta(im);
				
				Location arrowLocation = arrow.getLocation();
				Location pastArrowLocation = arrowLocation;
				pastArrowLocation.setY(pastArrowLocation.getY() + 1);
				
				ArmorStand armorStand = playerWorld.spawn(arrowLocation, ArmorStand.class);
				
				armorStand.setCanPickupItems(false);
				armorStand.setVisible(true);
				armorStand.setCollidable(false);	
				ItemStack pebble = new ItemStack(Material.ANDESITE);
				
				((CraftEntity) armorStand).getHandle().noclip = true;
				((CraftEntity) armorStand).getHandle().setSlot(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(pebble));
				
				new BukkitRunnable() {
					
					Location armourStandLocation = arrowLocation;

					@Override
					public void run() {
						
						Bukkit.broadcastMessage("iter");
						

						armourStandLocation.setY(armourStandLocation.getY() + 0);
						
						if (pastArrowLocation == arrowLocation) {
							
							arrow.remove();
							
							this.cancel();
							
						}
						
					}
					
				}.runTaskTimer(main, 0, 1);
				
			}
			
			else {
				
				ItemMeta im = is.getItemMeta();
				
				im.setDisplayName(ChatColor.RESET + "Loaded Slingshot");
				
				is.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				
				is.setItemMeta(im);
				
			}	
			break;
			
		case BOOMERANG:
			
			if(is.getType().equals(Material.GHAST_TEAR)) {return;}
			
			boneThrown.put(player.getUniqueId(), true);
			
			is.setType(Material.GHAST_TEAR);
			
			
			Location boneStartLocation = player.getEyeLocation();
			Integer lineDistance = 20;
			Integer singleTripTime = 20;
				
			//maths things
			
			boneStartLocation.setY(boneStartLocation.getY() - 1);
			
			Location boneEndLocation = player.getLocation().add(player.getLocation().getDirection().multiply(lineDistance));
			
			boneEndLocation.setY(boneEndLocation.getY() - 1);
			
			Vector boneTravelLength = new Vector();
			
			boneTravelLength.setX(boneEndLocation.getX() - boneStartLocation.getX());
			boneTravelLength.setY(boneEndLocation.getY() - boneStartLocation.getY());
			boneTravelLength.setZ(boneEndLocation.getZ() - boneStartLocation.getZ());
		
			Vector armorStandSpawn = new Vector(boneStartLocation.getX(), boneStartLocation.getY() - 2, boneStartLocation.getZ());

			ArmorStand armorStand = playerWorld.spawn(armorStandSpawn.toLocation(playerWorld), ArmorStand.class);
			
			armorStand.setCanPickupItems(false);
			armorStand.setVisible(false);
			armorStand.setCollidable(false);	
			ItemStack bone = new ItemStack(Material.BONE);
			bone.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			ItemMeta im = bone.getItemMeta();
			im.setDisplayName("Skelerang");
			bone.setItemMeta(im);
			
			((CraftEntity) armorStand).getHandle().noclip = true;
			((CraftEntity) armorStand).getHandle().setSlot(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(bone));
			
			new BukkitRunnable() {
				
				Integer count = 3;
				Integer requiredDamage = 0;
				
				Vector newBoneLocation = new Vector();
				Vector boneTravelDistance = new Vector();
				
				void resetBonemerang() {
					
					armorStand.remove();
					
					boneThrown.put(player.getUniqueId(), false);

					is.setType(Material.BONE);
							
					this.cancel();
					
				}
				
				@Override
				public void run() {
					
					count += 1;
					
					if (count > singleTripTime) {
						
						try {
						
							if ((armorStand.getLocation().distance(player.getLocation()) < 1) || (count > singleTripTime * 2)) {
								
								resetBonemerang();
								
							}
							
						}
						
						catch(IllegalArgumentException e) {
							
							resetBonemerang();
							
						}
						
					}
			
					if (count < singleTripTime) 
					{
						newBoneLocation.setX(boneStartLocation.getX() + ((boneTravelLength.getX() / singleTripTime) * count));
						newBoneLocation.setY(boneStartLocation.getY() + ((boneTravelLength.getY() / singleTripTime) * count));
						newBoneLocation.setZ(boneStartLocation.getZ() + ((boneTravelLength.getZ() / singleTripTime) * count));
							
						requiredDamage = 4;
						
					}
					else 
					{
						boneTravelDistance.setX(armorStand.getLocation().toVector().getX() - player.getLocation().toVector().getX());
						boneTravelDistance.setY(armorStand.getLocation().toVector().getY() - player.getLocation().toVector().getY());
						boneTravelDistance.setZ(armorStand.getLocation().toVector().getZ() - player.getLocation().toVector().getZ());
												
						newBoneLocation.setX(armorStand.getLocation().toVector().getX() - ((boneTravelDistance.getX() / singleTripTime) * (count - singleTripTime)));
						newBoneLocation.setY(armorStand.getLocation().toVector().getY() - ((boneTravelDistance.getY() / singleTripTime) * (count - singleTripTime)));
						newBoneLocation.setZ(armorStand.getLocation().toVector().getZ() - ((boneTravelDistance.getZ() / singleTripTime) * (count - singleTripTime)));
						
						requiredDamage = 8;
						
					}
					
					armorStand.setVelocity(newBoneLocation.subtract(armorStand.getLocation().toVector()));
					armorStand.setRotation(count * 80, 0);
					
					List<Entity> nearbyEntities = armorStand.getNearbyEntities(0.5, 0, 0.5);
					
					nearbyEntities.forEach(entity -> {
						
						//if (entity instanceof Player) {return;}
						
						if (entity instanceof LivingEntity) {
							
							((Damageable) entity).damage(requiredDamage, player);
							
							((LivingEntity) entity).setNoDamageTicks(((LivingEntity) entity).getMaximumNoDamageTicks() / 2 + 2);
							
							Vector vector = entity.getVelocity();
							
							vector.setX(0d);
							vector.setY(0d);
							vector.setZ(0d);
							
							entity.setVelocity(vector);
							
							
						}
						
					}); 
							
				}
			
			}.runTaskTimer(main, 0, 1);
			break;
		
		default:
			break;
		
		}
		
	}
	
}
