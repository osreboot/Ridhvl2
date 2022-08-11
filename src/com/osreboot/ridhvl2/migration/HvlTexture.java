package com.osreboot.ridhvl2.migration;

import org.lwjgl.opengl.GL11;

public class HvlTexture{

	public final int id, width, height;
	
	public HvlTexture(int idArg, int widthArg, int heightArg){
		id = idArg;
		width = widthArg;
		height = heightArg;
	}
	
	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public int getImageWidth(){
		return width;
	}
	
	public int getImageHeight(){
		return height;
	}
	
}
