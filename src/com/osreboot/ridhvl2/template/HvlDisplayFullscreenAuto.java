package com.osreboot.ridhvl2.template;

import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl2.HvlLogger;

/**
 * An implementation of {@linkplain HvlDisplay} that automatically selects and applies the best
 * {@linkplain HvlDisplayFullscreen}. Displays are valued (in order) by bits per pixel, refresh rate and the 
 * product of the display's width and height.
 * 
 * <p>
 * 
 * {@linkplain #setResizable(boolean)} is disabled for HvlDisplayFullscreenAuto.
 * 
 * <p>
 * 
 * In order to change significant display properties that aren't included as instance methods, instantiate a new
 * HvlDisplay subclass and apply it with {@linkplain HvlDisplay#setDisplay(HvlDisplay)}.
 * 
 * @author os_reboot
 *
 */
public class HvlDisplayFullscreenAuto extends HvlDisplay{

	private HvlDisplayFullscreen autoDisplay;

	/**
	 * Constructs an HvlDisplayFullscreenAuto. Note that the display is not selected until {@linkplain #apply()} is
	 * called.
	 * 
	 * @param vsyncArg the value of the HvlDisplay's vertical-sync option
	 */
	public HvlDisplayFullscreenAuto(boolean vsyncArg){
		super(144, vsyncArg, false);
		autoDisplay = null;
	}

	@Override
	protected void apply(){
		for(HvlDisplayFullscreen display : getFullscreenDisplays()){
			if(autoDisplay == null) autoDisplay = display;
			if(display.getDisplayMode().getWidth() * display.getDisplayMode().getHeight() > autoDisplay.getDisplayMode().getWidth() * autoDisplay.getDisplayMode().getHeight())
				autoDisplay = display;
			if(display.getRefreshRate() > autoDisplay.getRefreshRate())
				autoDisplay = display;
			if(display.getDisplayMode().getBitsPerPixel() > autoDisplay.getDisplayMode().getBitsPerPixel())
				autoDisplay = display;
		}
		
		if(autoDisplay == null) throw new NullDisplayException();
		setDisplay(autoDisplay);
		autoDisplay.apply();
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