package com.BadDevelopers.SkypixelHyblock.Enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class Telekinesis extends Enchantment {
	
	public static String name = "Telekinesis";

	public Telekinesis(NamespacedKey key) {
		super(key);
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		return (EnchantmentTarget.TOOL.includes(item) || EnchantmentTarget.WEAPON.includes(item));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean conflictsWith(Enchantment other) {
		if (other.getName().equals(getName())) return true;
		
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

}
