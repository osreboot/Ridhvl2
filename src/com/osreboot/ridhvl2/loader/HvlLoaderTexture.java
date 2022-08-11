package com.osreboot.ridhvl2.loader;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import com.osreboot.ridhvl2.migration.HvlTexture;

/**
 * A standard HvlLoader implementation designed to handle images in the form of 
 * {@linkplain org.newdawn.slick.opengl.Texture Texture} objects.
 * 
 * @author os_reboot
 *
 */
public class HvlLoaderTexture extends HvlLoader<HvlTexture>{

	/**
	 * Constructs an HvlLoaderTexture instance with the default path being "res".
	 */
	public HvlLoaderTexture(){
		this("res");
	}

	/**
	 * Constructs an HvlLoaderTexture instance with a user-specified default path.
	 * 
	 * @param defaultPathArg the default path for the HvlLoaderTexture
	 */
	public HvlLoaderTexture(String defaultPathArg){
		super(defaultPathArg, HvlLoader.TYPELABEL_TEXTURE, "PNG", "JPG", "JPEG");
	}

	@Override
	public boolean load(String basePathArg, String pathArg){
		ByteBuffer image;
		int width, height;
		try(MemoryStack stack = MemoryStack.stackPush()){
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(true);
			image = STBImage.stbi_load(basePathArg + File.separator + pathArg, w, h, comp, 4);
			if(image == null) return false;
			
			width = w.get();
			height = h.get();
		}
		
		HvlTexture texture = new HvlTexture(GL11.glGenTextures(), width, height);
		texture.bind();
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
		
		resources.add(texture);
		
		return true;
	}

}
