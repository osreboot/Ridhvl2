package com.osreboot.ridhvl2.loader;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import com.osreboot.ridhvl2.HvlConfig;
import com.osreboot.ridhvl2.menu.HvlFont;
import com.osreboot.ridhvl2.migration.HvlTexture;

/**
 * A standard HvlLoader implementation designed to handle fonts in the form of 
 * {@linkplain HvlFont} objects.
 * 
 * @author os_reboot
 *
 */
public class HvlLoaderFont extends HvlLoader<HvlFont>{

	/**
	 * Constructs an HvlLoaderFont instance with the default path being "res".
	 */
	public HvlLoaderFont(){
		this("res");
	}

	/**
	 * Constructs an HvlLoaderFont instance with a user-specified default path.
	 * 
	 * @param defaultPathArg the default path for the HvlLoaderFont
	 */
	public HvlLoaderFont(String defaultPathArg){
		super(defaultPathArg, HvlLoader.TYPELABEL_FONT, "HVLFT");
	}

	@Override
	public boolean load(String basePathArg, String pathArg){
		//TODO find a better way to handle this
		if(pathArg.toUpperCase().endsWith("HVLFT")){
			HvlFont font = HvlConfig.PJSON.load(basePathArg + File.separator + pathArg);

			
			ByteBuffer image;
			int width, height;
			try(MemoryStack stack = MemoryStack.stackPush()){
				IntBuffer w = stack.mallocInt(1);
				IntBuffer h = stack.mallocInt(1);
				IntBuffer comp = stack.mallocInt(1);

				STBImage.stbi_set_flip_vertically_on_load(true);
				image = STBImage.stbi_load(basePathArg + File.separator + font.get(HvlFont.TAG_TEXTURE), w, h, comp, 4);
				if(image == null) return false;

				width = w.get();
				height = h.get();
			}

			HvlTexture texture = new HvlTexture(GL11.glGenTextures(), width, height);
			texture.bind();

			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

			font.setLoadedTexture(texture);
			font.setTexelNudge(true);
			resources.add(font);
			return true;
		}else return false;
	}

}
