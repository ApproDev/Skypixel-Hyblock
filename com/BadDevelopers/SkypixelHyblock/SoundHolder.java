package com.BadDevelopers.SkypixelHyblock;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundHolder {
	final byte[] data;
	final int bitsPerSample;
	final long maxAmplitude;
	final int bytesPerSample;
	
	public SoundHolder(File file) throws UnsupportedAudioFileException, IOException {
		this(new DataInputStream(new FileInputStream(file)));
	}
	
	public SoundHolder(DataInputStream stream) throws IOException {
		
		stream.skipBytes(34);
		
		bitsPerSample = stream.readByte();
		stream.skip(9);
		
		//System.out.println(bytesPerSample);
	
		this.data = stream.readAllBytes();
		this.bytesPerSample = bitsPerSample / 8;
		
		long hAmp = 0;
		
		for (int i = 0; i < length(); i++) {
			long a = Math.abs(get(i));
			
			if (a > hAmp) hAmp = a;
		}
		
		maxAmplitude = hAmp;
	}
	
	public long get(int pos) {
		byte[] involved = new byte[bytesPerSample];
		
		System.arraycopy(data, pos*bytesPerSample, involved, 0, bytesPerSample);
		
		long l = bytesToLong(involved);
		
		//System.out.println(l);
		
		return l;
	}
	
	public int length() {
		return data.length / bytesPerSample;
	}
	
	
	public static long bytesToLong(final byte[] b) {
	    long result = 0;
	    for (int i = 0; i < b.length; i++) {
	        result <<= Byte.SIZE;
	        result |= (b[i] & 0xFF);
	    }
	    return result;
	}
}
