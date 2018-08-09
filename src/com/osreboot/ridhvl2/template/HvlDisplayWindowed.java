package com.osreboot.ridhvl2.template;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.osreboot.ridhvl2.HvlLogger;

public class HvlDisplayWindowed extends HvlDisplay{

	private DisplayMode initialMode;
	private String initialTitle;
	
	public HvlDisplayWindowed(int refreshRateArg, int widthArg, int heightArg, String titleArg, boolean resizableArg){
		super(refreshRateArg, false, resizableArg);
		initialMode = new DisplayMode(widthArg, heightArg);
		initialTitle = titleArg;
	}

	@Override
	protected void apply(){
		try{
			Display.setDisplayMode(initialMode);
			Display.setVSyncEnabled(false);
			Display.setResizable(isResizable());
			Display.setTitle(initialTitle);
			Display.setFullscreen(false);
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
	}

	@Override
	public void setVsyncEnabled(boolean vsyncEnabledArg){
		super.setVsyncEnabled(false);
		HvlLogger.println("VSync cannot be enabled with a windowed display!");
	}

	@Override
	public void setResizable(boolean resizableArg){
		super.setResizable(resizableArg);
		Display.setResizable(resizableArg);
	}
	
}
