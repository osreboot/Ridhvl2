package com.osreboot.ridhvl2.template;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlLogger;

public class HvlDisplayWindowed extends HvlDisplay{

	private DisplayMode initialMode;
	private String initialTitle;
	private boolean initialUndecorated;
	
	public HvlDisplayWindowed(int refreshRateArg, int widthArg, int heightArg, String titleArg, boolean resizableArg){
		super(refreshRateArg, false, resizableArg);
		initialMode = new DisplayMode(widthArg, heightArg);
		initialTitle = titleArg;
	}
	
	public HvlDisplayWindowed(int refreshRateArg, int widthArg, int heightArg, String titleArg, boolean resizableArg, boolean undecoratedArg){
		super(refreshRateArg, false, resizableArg);
		initialMode = new DisplayMode(widthArg, heightArg);
		initialTitle = titleArg;
		initialUndecorated = undecoratedArg;
	}

	@Override
	protected void apply(){
		try{
			Display.setDisplayMode(initialMode);
			Display.setVSyncEnabled(false);
			Display.setResizable(isResizable());
			Display.setTitle(initialTitle);
			Display.setFullscreen(false);
			if(initialUndecorated) System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
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
		
		if(Display.wasResized()){
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			GL11.glLoadIdentity();
			GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		}
		
		Display.sync(getRefreshRate());
		
		if(Display.isCloseRequested() && !HvlTemplate.newest().isExiting()){
			HvlTemplate.newest().setExiting();
		}
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
