package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlPolygon {

	private HvlCoord[] vertices, uvs;
	
	public HvlPolygon(HvlCoord[] vArgs, HvlCoord[] uvArgs){
		vertices = vArgs;
		uvs = uvArgs;
	}
	
	protected HvlCoord[] getVertices(){
		return vertices;
	}
	
	protected HvlCoord[] getUVs(){
		return uvs;
	}
	
	protected void setVertices(HvlCoord[] vArgs){
		vertices = vArgs;
	}
	
	protected void setUVs(HvlCoord[] uvArgs){
		uvs = uvArgs;
	}
	
}
