package com.osreboot.ridhvl2.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.Log;

/**
 * A standard HvlLoader implementation designed to handle images in the form of 
 * {@linkplain org.newdawn.slick.opengl.Texture Texture} objects.
 * 
 * @author os_reboot
 *
 */
public class HvlLoaderTexture extends HvlLoader<Texture>{

	public HvlLoaderTexture(){
		super("res", HvlLoader.TYPELABEL_TEXTURE, "PNG");
		Log.setVerbose(false);
	}
	
	public HvlLoaderTexture(String defaultPathArg){
		super(defaultPathArg, HvlLoader.TYPELABEL_TEXTURE, "PNG");
		Log.setVerbose(false);
	}

	@Override
	public boolean load(String basePathArg, String pathArg){
		try{
			//TODO bypass power-of-two textures not working
			resources.add(TextureLoader.getTexture("PNG", new FileInputStream(basePathArg + File.separator + pathArg)));
			return true;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}

}
