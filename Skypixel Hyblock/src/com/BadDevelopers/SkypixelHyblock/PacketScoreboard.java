package com.BadDevelopers.SkypixelHyblock;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.ScoreboardObjective;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_16_R3.PlayerConnection;

@Deprecated
public class PacketScoreboard implements Runnable {
	
	Player player;
	ScoreboardObjective obj;
	String prev;
	Main main;
	
	public PacketScoreboard(Player player, ScoreboardObjective obj, Main main) {
		this.player = player;
		this.obj = obj;
		this.main = main;
	}
	
	@Override
	public void run() {
		PlayerConnection pc = ((CraftPlayer) player).getHandle().playerConnection;
		String out = "Purse: "+main.currency.getPurse(player);
		pc.sendPacket(createObjectivePacket(1, prev));
		pc.sendPacket(createObjectivePacket(0, out));
		prev = out;
		
	}
	
	private PacketPlayOutScoreboardObjective createObjectivePacket(int mode, String displayName) {
		PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective(obj, 1);
		
		setField(packet, "a", player.getName());
		// Mode
		// 0 : Create
		// 1 : Delete
		// 2 : Update
		setField(packet, "d", mode);

		if (mode == 0 || mode == 2) {
			setField(packet, "a", displayName);
			setField(packet, "c", IScoreboardCriteria.DUMMY);
		}

		return packet;
	}
	
	private static void setField(Object edit, String fieldName, Object value) {
		try {
			Field field = edit.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(edit, value);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
