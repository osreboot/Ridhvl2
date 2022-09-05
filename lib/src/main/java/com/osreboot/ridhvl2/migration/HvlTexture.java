package com.osreboot.ridhvl2.migration;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class HvlTexture{

	public static HvlTexture load(String path){
		ByteBuffer image;
		int width, height, comp;
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);

			image = STBImage.stbi_load(path, w, h, c, 4);

			width = w.get();
			height = h.get();
			comp = c.get();
		}

		int widthPo2 = 2;
		int heightPo2 = 2;
		while(widthPo2 < width) widthPo2 *= 2;
		while(heightPo2 < height) heightPo2 *= 2;

		HvlTexture texture = new HvlTexture(GL11.glGenTextures(), width, height, (float)width / (float)widthPo2, (float)height / (float)heightPo2);
		texture.bind();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		if(widthPo2 != width || heightPo2 != height){
			ByteBuffer imageResized = BufferUtils.createByteBuffer(widthPo2 * heightPo2 * comp);
			
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					imageResized.putInt((y * widthPo2 + x) * comp, image.getInt((y * width + x) * comp));
				}
			}
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, widthPo2, heightPo2, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageResized);
		}else{
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
		}
		
		STBImage.stbi_image_free(image);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		return texture;
	}

	public final int id, width, height;
	public final float uCrop, vCrop;

	public HvlTexture(int idArg, int widthArg, int heightArg, float uCropArg, float vCropArg){
		id = idArg;
		width = widthArg;
		height = heightArg;
		uCrop = uCropArg;
		vCrop = vCropArg;
	}

	public void bind(){
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

}
