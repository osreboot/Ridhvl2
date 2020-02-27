package com.osreboot.ridhvl2.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.osreboot.ridhvl2.HvlConfig;
import com.osreboot.ridhvl2.menu.HvlFont;

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
		try{
			//TODO find a better way to handle this
			if(pathArg.toUpperCase().endsWith("HVLFT")){
				HvlFont font = HvlConfig.PJSON.load(basePathArg + File.separator + pathArg);
				String textureExtension = font.get(HvlFont.TAG_TEXTURE).substring(font.get(HvlFont.TAG_TEXTURE).lastIndexOf('.') + 1);
				Texture texture = TextureLoader.getTexture(textureExtension, new FileInputStream(basePathArg + File.separator + font.get(HvlFont.TAG_TEXTURE)));
				font.setLoadedTexture(texture);
				resources.add(font);
				return true;
			}else return false;
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
}
