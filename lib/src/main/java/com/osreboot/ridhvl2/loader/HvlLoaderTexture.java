package com.osreboot.ridhvl2.loader;

import java.io.File;

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
		resources.add(HvlTexture.load(basePathArg + File.separator + pathArg));
		return true;
	}

}
