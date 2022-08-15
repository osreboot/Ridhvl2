package com.osreboot.ridhvl2.loader;

import java.io.File;

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
			HvlTexture texture = HvlTexture.load(basePathArg + File.separator + font.get(HvlFont.TAG_TEXTURE));
			
			font.setLoadedTexture(texture);
			font.setTexelNudge(true);
			resources.add(font);
			return true;
		}else return false;
	}

}
