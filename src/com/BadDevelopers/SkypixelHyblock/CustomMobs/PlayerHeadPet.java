package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.EnumInteractionResult;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.EnumMoveType;
import net.minecraft.server.v1_16_R3.HeightMap.Type;
import net.minecraft.server.v1_16_R3.Vec3D;

public abstract class PlayerHeadPet extends EntityArmorStand {
	
	String skin;
	
	private BlockPosition target = null;
	
	public PlayerHeadPet(Location loc, String skin) {
		super(((CraftWorld) loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
		this.skin = skin;
		
		spawnEntity(loc);
	}
	
	void spawnEntity(Location loc) {
		World world = loc.getWorld();
		
		((CraftWorld) world).addEntity(this, SpawnReason.CUSTOM);
		
		ItemStack is = new ItemStack(Material.PLAYER_HEAD);
		
		is.setItemMeta( Item.getSkull(skin, (SkullMeta) is.getItemMeta()) );
		
		//this.activeItem = CraftItemStack.asNMSCopy(is);
		
		this.setInvisible(true);
		
		this.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(is));
		
		this.setNoGravity(true);
		
		this.noclip = true;
	}
	
	@Override
	public EnumInteractionResult a(EntityHuman eh, Vec3D vector, EnumHand hand) {
		return EnumInteractionResult.FAIL;
	}
	
	@Override
	public void tick() {
		super.tick();
	    
	    if (world.getType(new BlockPosition(locX(), locY()+1, locZ())).isAir() 
	    	/*|| Float.parseFloat(new DecimalFormat("#.#").format(locY() - 0.375)) != this.y()*/) {
	    	this.move(EnumMoveType.SELF, new Vec3D(0, -0.1, 0));
	    }
	    
	    //runPathfinding();
	}
	
	public void runPathfinding() {
		if (world.getType(new BlockPosition(locX(), locY(), locZ())).isAir()) return;
		
		if (target == null) {
			Random random = new Random();
			int x = (int) (random.nextInt(8)-7 + locX());
			int z = (int) (random.nextInt(8)-7 + locZ());
			
			target = world.getHighestBlockYAt(Type.WORLD_SURFACE,
					new BlockPosition(x, 255, z));
		}
		//boolean temp = true;
		//if (locX() > target.getX()) temp = false;
		//int dX = Math.sqrt(Math.abs(locX()));
		
		double dirX = Math.copySign( 0.1, Math.signum(target.getX() - locX()) );
		double dirY = Math.copySign( 0.1, Math.signum(target.getY() - (locY()+1)) );
		double dirZ = Math.copySign( 0.1, Math.signum(target.getZ() - (locZ())) );
		
		Vec3D movement = new Vec3D(dirX, dirY, dirZ);
		
		move(EnumMoveType.SELF, movement);
	}
	
}
