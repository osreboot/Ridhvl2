package com.osreboot.ridhvl2.template;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class HvlDisplayWindowed extends HvlDisplay{

	private DisplayMode mode;
	
	HvlDisplayWindowed(int refreshRateArg, boolean vsyncEnabledArg, boolean resizableArg, int widthArg, int heightArg){
		super(refreshRateArg, vsyncEnabledArg, resizableArg);
		mode = new DisplayMode(widthArg, heightArg);
	}

	@Override
	public void apply(){
		try{
			Display.setDisplayMode(mode);
			Display.setVSyncEnabled(isVsyncEnabled());
			Display.setResizable(isResizable());
			Display.create();
		}catch(LWJGLException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void unapply(){
		Display.destroy();
	}

	@Override
	public void preUpdate(float delta){
		
	}

	@Override
	public void postUpdate(float delta){
		Display.update();
		Display.sync(getRefreshRate());
	}

	@Override
	public void setVsyncEnabled(boolean vsyncEnabledArg){
		super.setVsyncEnabled(vsyncEnabledArg);
		Display.setVSyncEnabled(vsyncEnabledArg);
	}

	@Override
	public void setResizable(boolean resizableArg){
		super.setResizable(resizableArg);
		Display.setResizable(resizableArg);
	}
	
}
