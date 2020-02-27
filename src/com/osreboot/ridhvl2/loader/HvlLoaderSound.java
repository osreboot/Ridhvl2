package com.osreboot.ridhvl2.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.Log;

/**
 * A standard HvlLoader implementation designed to handle sounds in the form of 
 * {@linkplain org.newdawn.slick.openal.Audio Audio} objects.
 * 
 * @author os_reboot
 *
 */
public class HvlLoaderSound extends HvlLoader<Audio>{
	
	/**
	 * Constructs an HvlLoaderSound instance with the default path being "res".
	 */
	public HvlLoaderSound(){
		this("res");
	}

	/**
	 * Constructs an HvlLoaderSound instance with a user-specified default path.
	 * 
	 * @param defaultPathArg the default path for the HvlLoaderSound
	 */
	public HvlLoaderSound(String defaultPathArg){
		super(defaultPathArg, HvlLoader.TYPELABEL_SOUND, "WAV");
		Log.setVerbose(false);
		
		// TODO fix this slick audio initialization reset hack
		try{
			Field fieldAudioLoaderInit = AudioLoader.class.getDeclaredField("inited");
			fieldAudioLoaderInit.setAccessible(true);
			fieldAudioLoaderInit.set(null, false);
			
			Field fieldSoundStoreInit = SoundStore.get().getClass().getDeclaredField("inited");
			fieldSoundStoreInit.setAccessible(true);
			fieldSoundStoreInit.set(SoundStore.get(), false);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean load(String basePathArg, String pathArg){
		try{
			//TODO find a better way to handle this
			if(pathArg.toUpperCase().endsWith("WAV")){
				resources.add(AudioLoader.getAudio("WAV", new BufferedInputStream(new FileInputStream(basePathArg + File.separator + pathArg))));
				return true;
			}else return false;
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void cleanup(){
		AL.destroy();
	}
	
}
