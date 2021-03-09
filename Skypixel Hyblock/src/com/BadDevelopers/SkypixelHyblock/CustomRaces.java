package com.BadDevelopers.SkypixelHyblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.BadDevelopers.SkypixelHyblock.Stats.Stat;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;
import com.BadDevelopers.SkypixelHyblock.UI.RaceUI;

public class CustomRaces implements Runnable, Listener {
	
	final Main main;
	
	public CustomRaces(@Nonnull Main main) {
		this.main = main;
	}
	public static World getHell() {
		for (World world : Bukkit.getWorlds()) 
			if (world.getEnvironment().equals(Environment.NETHER)) return world;
		return null;
	}
	
	DamageCause[] trueDamageCauses = new DamageCause[] {DamageCause.DRAGON_BREATH,
			DamageCause.FIRE, DamageCause.LIGHTNING, DamageCause.MAGIC};
	
	@EventHandler
	public void onPlayerDamageEvent(EntityDamageEvent event) {
		if (!event.getEntityType().equals(EntityType.PLAYER)) return;
		
		Player player = (Player) event.getEntity();
		
		Race race = Race.getRace(player, main);
		
		if (race == null) return;
		if (!race.equals(Race.DEMIGOD)) return;
		
		for (DamageCause cause : trueDamageCauses)
			if (cause.equals(event.getCause())) {
				event.setDamage(event.getDamage() * 0.8);
			}
		
		
	}
	
	@EventHandler
	public void onBowFire(EntityShootBowEvent event) {
		if (!event.getEntityType().equals(EntityType.PLAYER)) return;
		
		if (!(event.getProjectile() instanceof Arrow)) return;
		
		Player player = (Player) event.getEntity();
		
		if (!player.isSneaking()) return;
		
		Race race = Race.getRace(player, main);
		
		if (race == null) return;
		
		if (!race.equals(Race.SKELETON)) return;
		
		homingArrows.add(new HomingArrow(player, (Arrow) event.getProjectile()));
	}
	
	ArrayList<HomingArrow> homingArrows = new ArrayList<HomingArrow>();
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		Race race = Race.getRace(player, main);
		
		if (race == null) {
			Bukkit.getPluginManager().registerEvents(new RaceUI(player, main), main);
			return;
		}
		
		if (race.spawnInHell) Bukkit.getScheduler().runTaskLater(main, new Runnable() {

			@Override
			public void run() {
				player.teleport(getHell().getSpawnLocation());
				
			}}, 1);
		
		Item item = race.spawnItem;
		
		if (item != null) player.getInventory().addItem(item.getItem(1, main));
		
		//if (race.spawnInHell) player.teleport(getHell().getSpawnLocation());
	}
	
	
	HashMap<UUID, Long> lastRightClick = new HashMap<UUID, Long>();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Race race = Race.getRace(event.getPlayer(), main);
		
		if (race != null) race.applyStats(event.getPlayer(), main);
	}
	
	@EventHandler // Active racial abilities
	public void onswapHandItems(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		
		if (!player.isSneaking()) return;
		
		Race race = Race.getRace(player, main);
		
		if (race == null) return;
		
		Long last = lastRightClick.getOrDefault(player.getUniqueId(), 0L);
		Long current = System.currentTimeMillis();
		if (current - last < 10000) {
			player.sendMessage(Main.prefix+ChatColor.DARK_RED+"This ability is on cooldown!");
			return;
		}
		Location loc;
		Location eye;
		Entity e;
		Sound sound;
		switch(race) {
		case DRAGON:
			eye = player.getEyeLocation();
			loc = rayTrace(eye, eye.getDirection(), 10, player);
			AreaEffectCloud ec = (AreaEffectCloud) loc.getWorld().spawnEntity(loc, EntityType.AREA_EFFECT_CLOUD);
			ec.setBasePotionData(new PotionData(PotionType.INSTANT_DAMAGE));
			ec.setParticle(Particle.PORTAL);
			sound = Sound.ENTITY_ENDER_DRAGON_GROWL;
			break;
		case ENDERMAN:
			eye = player.getEyeLocation();
			loc = rayTrace(eye, eye.getDirection(), 40, player);
			//if (!loc.getWorld().getBlockAt(loc).getType().isAir()) return;
			player.teleport(loc.setDirection(eye.getDirection()));
			sound = Sound.ENTITY_ENDERMAN_TELEPORT;
			break;
		case SQUID:
			eye = player.getEyeLocation();
			e = rayTraceEntity(eye, eye.getDirection(), 5, player);
			if (e == null) return;
			((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*10, 0));
			loc = e.getLocation();
			sound = Sound.ENTITY_SQUID_SQUIRT;
			break;
		case BLAZE:
			eye = player.getEyeLocation();
			
			Vector pv = player.getVelocity();
			Vector pe = player.getEyeLocation().getDirection();
			
			pv.add(pe);
			
			pv.multiply(1.5);
			
			e = eye.getWorld().spawnEntity(eye, EntityType.FIREBALL);
			
			e.setVelocity(pv);
			
			loc = e.getLocation();
			sound = Sound.ENTITY_BLAZE_SHOOT;
			break;
		case WITHER:
			eye = player.getEyeLocation();
			e = rayTraceEntity(eye, eye.getDirection(), 5, player);
			if (e == null) return;
			((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20*5, 3));
			loc = e.getLocation();
			sound = Sound.ENTITY_WITHER_SHOOT;
			break;
		case GUARDIAN:
			eye = player.getEyeLocation();
			e = rayTraceEntity(eye, eye.getDirection(), 5, player);
			if (e == null) return;
			((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20*10, 3));
			loc = e.getLocation();
			sound = Sound.ENTITY_ELDER_GUARDIAN_CURSE;
			break;
		default:
			return;
		}
		Bukkit.getOnlinePlayers().forEach(
				p -> {p.playSound(loc, sound, 1, 1);});
		lastRightClick.put(player.getUniqueId(), current);
	}
	
	public static Entity rayTraceEntity(Location start, Vector direction, double distance, Player exclude) {
		World world = start.getWorld();
		
		Predicate<Entity> predicate = new Predicate<Entity>() {

			@Override
			public boolean test(Entity t) {
				return (t instanceof Player) && !(t.getUniqueId().equals(exclude.getUniqueId()));
			}};
		
		RayTraceResult result = ((CraftWorld) world)
				.rayTraceEntities(start, direction, distance, predicate);
		if (result == null) return null;
		return result.getHitEntity();
	}
	
	public static Location rayTrace(Location start, Vector direction, double distance, Player exclude) {
		World world = start.getWorld();
		
		Predicate<Entity> predicate = new Predicate<Entity>() {

			@Override
			public boolean test(Entity t) {
				return !t.getUniqueId().equals(exclude.getUniqueId());
			}};
		
		RayTraceResult result = ((CraftWorld) world)
				.rayTrace(start, direction, distance, 
						FluidCollisionMode.ALWAYS, true, 0.1d, predicate);
		
		return result.getHitPosition().toLocation(world).setDirection(direction);
	}
	
	public enum Race {
		ELF(Material.STICK, -10d, 50d, 0.5, 0d, null, false, false, true, true), 
		DRAGON(Material.DRAGON_BREATH, 0d, 0d, 0d, 0d, null, false, false, true, false), 
		ENDERMAN(Material.ENDER_PEARL, 5d, 0d, 0.2, 0d, null, false, false, false, true), 
		SKELETON(Material.BONE, -20d, 10d, 0d, -30d, Item.BOW, false, false, true, true), 
		GOLEM(Material.IRON_BLOCK, 10d, -50d, 0d, 100d, null, false, false, true, false), 
		CHICKEN(Material.FEATHER, 0d, 0d, -0.2d, -20d, null, false, false, true, true), 
		SQUID(Material.INK_SAC, 0d, -30d, 0d, 0d, null, true, false, true, true), 
		CAT(Material.STRING, 0d, 30d, 0d, -10d, null, false, false, true, true), 
		BLAZE(Material.BLAZE_ROD, 150d, 0d, 0d, 0d, null, false, true, false, false), 
		DROWNED(Material.TRIDENT, 0d, -20d, 0d, 0d, Item.TRIDENT, false, false, true, true), 
		WITHER(Material.WITHER_SKELETON_SKULL, 60d, 0d, 0d, 60d, Item.STONE_SWORD, false, true, true, true), 
		VEX(Material.SUGAR, 0d, 0d, 0d, -30d, null, false, false, true, true),
		HOGLIN(Material.PORKCHOP, 50d, 0d, 0d, 150d, null, false, true, true, true), 
		GUARDIAN(Material.PRISMARINE, 0d, -20d, 0d, 0d, null, true, false, true, true),
		DEMIGOD(Material.BARRIER, 40d, 20d, 0d, 0d, null, false, false, true, true);
		
		public Material representative;
		public Double defenceModifier;
		public Double speedModifier;
		public Double IQStatModifier;
		public Double healthModifier;
		public Item spawnItem;
		public boolean fastInWater;
		public boolean spawnInHell;
		public boolean canTouchWater;
		public boolean canSwim;
		
		
		Race(Material representative,
		Double defenceModifier,
		Double speedModifier,
		Double IQStatModifier,
		Double healthModifier,
		Item spawnItem,
		boolean fastInWater,
		boolean spawnInHell,
		boolean canTouchWater,
		boolean canSwim) {
			this.representative = representative;
			this.defenceModifier = defenceModifier;
			this.speedModifier = speedModifier;
			this.IQStatModifier = IQStatModifier;
			this.healthModifier = healthModifier;
			this.spawnItem = spawnItem;
			this.fastInWater = fastInWater;
			this.spawnInHell = spawnInHell; 
			this.canTouchWater = canTouchWater;
			this.canSwim = canSwim;
		
		}
		
		public void applyStats(Player player, Main main) {
			Main.stats.addStat(player, (int) Math.round(defenceModifier), Stat.Defence, 20000L, this.toString()+"_Defence", true);
			Main.stats.addStat(player, (int) Math.round(speedModifier), Stat.Speed, 20000L, this.toString()+"_Speed", true);
			// IQ is a multiplier, so it is in Stats.java
			Main.stats.addStat(player, (int) Math.round(healthModifier), Stat.Health, 20000L, this.toString()+"_Health", true);
			
			if (fastInWater) player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 21*20, 0));
		
			if (this.equals(Race.VEX)) {
				player.setFlySpeed(0.025f);
				player.setAllowFlight(true);
			}
			if (this.equals(Race.BLAZE)) player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 21*20, 0));
			
		}
		
		public String format() { // Getting a user friendly version of the name
			String s = toString();
			
			s = s.replace('_', ' ');
			
			s = s.strip().toLowerCase();
			
			String s2 = Character.toString(s.charAt(0));
			
			s = s.replaceFirst(s2, s2.toUpperCase());
			
			return s;
		}
		
		@Nullable
		public static Race getRace(Player player, Main main) {
			String s = player.getPersistentDataContainer()
					.get(new NamespacedKey(main, "race"), PersistentDataType.STRING);
			if (s == null) return null;
			else return valueOf(s);
		}
		
		public static Race valueOf(int ordinal) {
			return values()[ordinal];
		}
		
		public void setRace(Player player, Main main) {
			player.getPersistentDataContainer().set(
					new NamespacedKey(main, "race"), 
					PersistentDataType.STRING, 
					this.toString());
			
			player.getInventory().clear();
			player.damage(Integer.MAX_VALUE);
		}
	}

	@Override
	public void run() {
		Bukkit.getOnlinePlayers().forEach(player -> {
			Race race = Race.getRace(player, main);
			
			if (race != null) {
			
				if (player.getLocation()
					//.add(new Location(player.getLocation().getWorld(), 0, 1, 0))
					.getBlock().getType().equals(Material.WATER)) {
						if (!race.canTouchWater) player.damage(1);
						if (!race.canSwim) player.setVelocity(player.getVelocity().add(new Vector(0, -0.1, 0)));
				}
			}
			
			if (race.equals(Race.CHICKEN) && player.getFallDistance() > 2 && !player.isSneaking())
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0));
			else if (race.equals(Race.CAT)) player.setFallDistance(-20f);
		});
		
		for (int i = 0; i < homingArrows.size(); i++) {
			HomingArrow homingArrow = homingArrows.get(i);
			
			if (homingArrow.hasHit()) {
				homingArrows.remove(i);
				i--;
				continue;
			}
			
			homingArrow.home();
		}
		
	}
	
	/*
	 	DWARF, // Easier to level blacksmithing & stoneworking, higher attack, 1/2 a block less reach
		ELF, // Slightly faster, IQ stat easier to level
		DRAGON, // You can breathe dragon's breath
		ENDERMAN, // Shift right click to teleport, cant touch water
		SKELETON, // Bow aimbot if you're sneaking
		IRON_GOLEM, // Tanky health, hella slow
		CHICKEN, // Slow falling. Shift to fall normally, low health
		SQUID, //  Move fast in water, slow out of water. Right click on someone to blind them
		CAT, // No fall damage, fast, low health
		BLAZE, // Fire res, high defence, spawn in hell, shoot fireballs on shift+rightclick, water damage
		DROWNED, // Slow, spawn with a trident
		WITHER_SKELETON, // Right click to give people wither, spawn with a stone sword
		VEX, // Low health, noclip, slow flight
		HOGLIN, // VERY high health, spawn in nether
		GUARDIAN, // Slow on land, fast in water, right click on someone to give them mining fatigue
		Demigod; - You're resistant to True Damage
	 */
}

class HomingArrow {
	final Player shooter;
	final Arrow arrow;
	
	public HomingArrow(Player shooter, Arrow arrow) {
		this.shooter = shooter;
		this.arrow = arrow;
	}
	
	boolean hasHit() {
		return arrow.isDead();
	}
	
	public void home() {
		Location startLoc = arrow.getLocation();
		Location closest = null;
		for (Entity entity : arrow.getNearbyEntities(40, 40, 40)) {
			if (!entity.equals(shooter)
					&& entity instanceof LivingEntity
					&& (closest == null 
						|| entity.getLocation().distance(startLoc) < closest.distance(startLoc))) closest = entity.getLocation();
		}
		
		if (closest != null) arrow.setVelocity(getDirection(startLoc, closest));
	}
	
	Vector getDirection(Location startLoc, Location endLoc) {
		
		return endLoc.toVector().subtract(startLoc.toVector());
	}
}