package com.osreboot.ridhvl2;

import java.io.Serializable;

public class HvlColor implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final HvlColor
	TRANSPARENT		= new HvlColor(1f, 1f, 1f, 0f),
	WHITE			= new HvlColor(1f, 1f, 1f),
	LIGHT_GRAY		= new HvlColor(0.7f, 0.7f, 0.7f),
	GRAY			= new HvlColor(0.5f, 0.5f, 0.5f),
	DARK_GRAY		= new HvlColor(0.3f, 0.3f, 0.3f),
	BLACK			= new HvlColor(0f, 0f, 0f),
	RED				= new HvlColor(1f, 0f, 0f),
	GREEN			= new HvlColor(0f, 1f, 0f),
	BLUE			= new HvlColor(0f, 0f, 1f),
	YELLOW			= new HvlColor(1f, 1f, 0f),
	CYAN			= new HvlColor(0f, 1f, 1f),
	MAGENTA			= new HvlColor(1f, 0f, 1f),
	PINK			= new HvlColor(255, 175, 175),
	ORANGE			= new HvlColor(255, 200, 0);

	public float r, g, b, a;

	public HvlColor(float rArg, float gArg, float bArg, float aArg){
		r = rArg;
		g = gArg;
		b = bArg;
		a = aArg;
	}

	public HvlColor(float rArg, float gArg, float bArg){
		this(rArg, gArg, bArg, 1f);
	}

	public HvlColor(int rArg, int gArg, int bArg, int aArg){
		this(rArg / 255f, gArg / 255f, bArg / 255f, aArg / 255f);
	}

	public HvlColor(int rArg, int gArg, int bArg){
		this(rArg / 255f, gArg / 255f, bArg / 255f, 1f);
	}

	public HvlColor(int iArg){
		int rInt = (iArg & 0x00FF0000) >> 16;
		int gInt = (iArg & 0x0000FF00) >> 8;
		int bInt = (iArg & 0x000000FF);
		int aInt = (iArg & 0xFF000000) >> 24;

		if(aInt < 0) aInt += 256;
		if(aInt == 0) aInt = 255;

		r = rInt / 255f;
		g = gInt / 255f;
		b = bInt / 255f;
		a = aInt / 255f;
	}

	public HvlColor(HvlColor colorArg){
		this(colorArg.r, colorArg.g, colorArg.b, colorArg.a);
	}
	
	public HvlColor(){
		this(0f, 0f, 0f, 1f);
	}

	@Override
	public boolean equals(Object objectArg){
		return objectArg instanceof HvlColor objectColor &&
				objectColor.r == r && objectColor.g == g && objectColor.b == b && objectColor.a == a;
	}

	@Override
	public int hashCode(){
		return (int)(r + g + b + a) * 255;
	}

	@Override
	public String toString(){
		return "[" + r + "," + g + "," + b + "," + a + "]";
	}

}
