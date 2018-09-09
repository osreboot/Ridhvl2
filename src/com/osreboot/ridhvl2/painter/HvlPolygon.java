package com.osreboot.ridhvl2.painter;

import com.osreboot.ridhvl2.HvlCoord;

public class HvlPolygon {

	private HvlCoord[] vertices, uvs;
	
	protected HvlPolygon(HvlCoord[] vArgs, HvlCoord[] uvArgs){
		if(vArgs.length < 3 || uvArgs.length < 3) throw new InvalidVertexCountException();
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
		if(vArgs.length < 3) throw new InvalidVertexCountException();
		vertices = vArgs;
	}
	
	protected void setVertices(HvlCoord v0Arg, HvlCoord v1Arg, HvlCoord v2Arg, HvlCoord... vArgs){
		vertices[0] = v0Arg;
		vertices[1] = v1Arg;
		vertices[2] = v2Arg;
		for(int i = 0; i < vArgs.length; i++)
			vertices[3 + i] = vArgs[i];
	}
	
	protected void setUVs(HvlCoord[] uvArgs){
		if(uvArgs.length < 3) throw new InvalidVertexCountException();
		uvs = uvArgs;
	}
	
	protected void setUVs(HvlCoord uv0Arg, HvlCoord uv1Arg, HvlCoord uv2Arg, HvlCoord... uvArgs){
		uvs[0] = uv0Arg;
		uvs[1] = uv1Arg;
		uvs[2] = uv2Arg;
		for(int i = 0; i < uvArgs.length; i++)
			uvs[3 + i] = uvArgs[i];
	}
	
	public static class InvalidVertexCountException extends RuntimeException{
		private static final long serialVersionUID = 5873291550044448526L;
	}
	
}
