package com.osreboot.ridhvl2.migration;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class HvlTexture{

	public static HvlTexture load(String path){
		ByteBuffer image;
		int width, height;
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);

			image = STBImage.stbi_load(path, w, h, comp, 4);

			width = w.get();
			height = h.get();
		}

		HvlTexture texture = new HvlTexture(GL11.glGenTextures(), width, height);
		texture.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
		STBImage.stbi_image_free(image);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		return texture;
	}
	
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
