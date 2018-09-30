package com.osreboot.ridhvl2.template;

import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl2.HvlLogger;

public class HvlDisplayFullscreenAuto extends HvlDisplay{

	private HvlDisplayFullscreen autoDisplay;

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
			Display.destroy();
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