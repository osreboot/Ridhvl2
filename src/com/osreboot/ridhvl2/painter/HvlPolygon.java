package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlPolygon {

	private HvlCoord[] vertices, uvs;
	
	public HvlPolygon(HvlCoord[] vArgs, HvlCoord[] uvArgs){
		vertices = vArgs;
		uvs = uvArgs;
	}
	
	public HvlCoord[] getVertices(){
		return vertices;
	}
	
	public HvlCoord[] getUVs(){
		return uvs;
	}
	
	public void setVertices(HvlCoord[] vArgs){
		vertices = vArgs;
	}
	
	public void setUVs(HvlCoord[] uvArgs){
		uvs = uvArgs;
	}
	
}
