package com.BadDevelopers.SkypixelHyblock.Items;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;

// Custom event
public class DropItemEvent extends Event implements Cancellable {
	
	public Cancellable origin;
	
	public DropItemEvent(EntityDropItemEvent origin) {
		this.origin = origin;
	}
	
	public DropItemEvent(BlockDropItemEvent origin) {
		this.origin = origin;
	}
	
	
	@Override
	public boolean isCancelled() {
		return origin.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		origin.setCancelled(cancel);
		
	}

	private static HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
	
}