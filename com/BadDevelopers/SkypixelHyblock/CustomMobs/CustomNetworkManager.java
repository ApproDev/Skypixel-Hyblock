package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.net.SocketAddress;

import javax.annotation.Nullable;
import javax.crypto.Cipher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_16_R3.EnumProtocolDirection;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.Packet;


public class CustomNetworkManager extends NetworkManager {

	public CustomNetworkManager(EnumProtocolDirection enumprotocoldirection) {
		super(enumprotocoldirection);
		this.socketAddress = null;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext channelhandlercontext) {}
	@Override
	public void channelInactive(ChannelHandlerContext channelhandlercontext) {}
	@Override
	public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {}
	@Override
	protected void channelRead0(ChannelHandlerContext channelhandlercontext, Packet<?> packet) {}
	@Override
	public void sendPacket(Packet<?> packet) {}
	@Override
	public void sendPacket(Packet<?> packet, @Nullable GenericFutureListener<? extends Future<? super Void>> genericfuturelistener) {}
	@Override
	public void a() {}
	@Override
	protected void b() {}
	@Override
	public void close(IChatBaseComponent ichatbasecomponent) {}
	@Override
	public boolean isLocal() {return true;}
	@Override
	public void a(Cipher cipher, Cipher cipher1) {}
	@Override
	public boolean isConnected() {return true;}
	
	public boolean connected = true;
	
	@Override
	public boolean i() {return false;}
	@Override
	public void stopReading() {}
	@Override
	public void setCompressionLevel(int i) {}
	@Override
	public void handleDisconnection() {connected = false;}
	@Override
	public SocketAddress getRawAddress() {
		return this.socketAddress;
	}
}
