package com.osreboot.ridhvl2.template;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.osreboot.ridhvl2.HvlLogger;

public class HvlDisplayFullscreen extends HvlDisplay{

	private DisplayMode mode;

	HvlDisplayFullscreen(DisplayMode modeArg, boolean vsyncArg){
		super(modeArg.getFrequency(), vsyncArg, false);
		mode = modeArg;
	}
	
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
