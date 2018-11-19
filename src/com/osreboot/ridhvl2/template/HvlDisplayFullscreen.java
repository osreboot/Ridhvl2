package com.osreboot.ridhvl2.template;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.osreboot.ridhvl2.HvlLogger;

/**
 * An implementation of {@linkplain HvlDisplay} that exclusively tracks one (fullscreen) LWJGL
 * {@linkplain org.lwjgl.opengl.Display DisplayMode}. HvlDisplayFullscreen should only be instantiated by internal
 * Ridhvl2 processes. Use {@linkplain HvlDisplay#getFullscreenDisplays()} to get all available instances.
 * Alternatively, use {@linkplain HvlDisplayFullscreenAuto} to automatically select the best available
 * HvlDisplayFullscreen instance.
 * 
 * <p>
 * 
 * {@linkplain #setResizable(boolean)} is disabled for HvlDisplayFullscreen.
 * 
 * @author os_reboot
 *
 */
public class HvlDisplayFullscreen extends HvlDisplay{

	private DisplayMode mode;

	HvlDisplayFullscreen(DisplayMode modeArg, boolean vsyncArg){
		super(modeArg.getFrequency(), vsyncArg, false);
		mode = modeArg;
	}
	
	/**
	 * Gets the LWJGL {@linkplain org.lwjgl.opengl.Display DisplayMode}. This can be used to get information about
	 * the display.
	 * 
	 * @return the LWJGL DisplayMode
	 */
	DisplayMode getDisplayMode(){
		return mode;
	}

	@Override
	protected void apply(){
		try{
			Display.setDisplayMode(mode);
			Display.setVSyncEnabled(isVsyncEnabled());
			Display.setResizable(false);
			Display.setFullscreen(true);
			Display.create();
		}catch(LWJGLException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void unapply(){
		Display.destroy();
	}

	@Override
	protected void preUpdate(float delta){

	}

	@Override
	protected void postUpdate(float delta){
		Display.update();
		Display.sync(getRefreshRate());
		
		if(Display.isCloseRequested() && !HvlTemplate.newest().isExiting()){
			HvlTemplate.newest().setExiting();
		}
	}

	@Override
	public void setVsyncEnabled(boolean vsyncEnabledArg){
		super.setVsyncEnabled(vsyncEnabledArg);
		Display.setVSyncEnabled(vsyncEnabledArg);
	}

	@Override
	public void setResizable(boolean resizableArg){
		super.setResizable(false);
		HvlLogger.println("Resizability cannot be modified with a fullscreen display!");
	}

}
