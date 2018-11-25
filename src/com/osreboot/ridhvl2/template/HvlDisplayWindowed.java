package com.osreboot.ridhvl2.template;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.osreboot.ridhvl2.HvlLogger;

/**
 * An implementation of {@linkplain HvlDisplay} that handles a windowed LWJGL 
 * {@linkplain org.lwjgl.opengl.Display Display}.
 * 
 * <p>
 * 
 * {@linkplain #setVsyncEnabled(boolean)} is disabled for HvlDisplayWindowed.
 * 
 * <p>
 * 
 * In order to change significant display properties that aren't included as instance methods, instantiate a new
 * HvlDisplay subclass and apply it with {@linkplain HvlDisplay#setDisplay(HvlDisplay)}.
 * 
 * @author os_reboot
 *
 */
public class HvlDisplayWindowed extends HvlDisplay{

	private DisplayMode initialMode;
	private String initialTitle;
	private boolean initialUndecorated;
	
	/**
	 * Constructor that defaults to a decorated window. Use 
	 * {@linkplain #HvlDisplayWindowed(int, int, int, String, boolean, boolean)} to supply a custom decorated value.
	 * 
	 * <p>
	 * 
	 * See {@linkplain HvlDisplayWindowed} for more information.
	 * 
	 * @param refreshRateArg the maximum refresh rate of the display
	 * @param widthArg the width (in pixels) of the display
	 * @param heightArg the height (in pixels) of the display
	 * @param titleArg the title of the window
	 * @param resizableArg whether or not the window is resizable
	 */
	public HvlDisplayWindowed(int refreshRateArg, int widthArg, int heightArg, String titleArg, boolean resizableArg){
		super(refreshRateArg, false, resizableArg);
		initialMode = new DisplayMode(widthArg, heightArg);
		initialTitle = titleArg;
		initialUndecorated = false;
	}
	
	/**
	 * Constructor that takes a custom decorated value. Use 
	 * {@linkplain #HvlDisplayWindowed(int, int, int, String, boolean)} to assume a default decorated value.
	 * 
	 * <p>
	 * 
	 * See {@linkplain HvlDisplayWindowed} for more information.
	 * 
	 * @param refreshRateArg the maximum refresh rate of the display
	 * @param widthArg the width (in pixels) of the display
	 * @param heightArg the height (in pixels) of the display
	 * @param titleArg the title of the window
	 * @param resizableArg whether or not the window is resizable
	 * @param undecoratedArg whether or not the window is undecorated
	 */
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
			else System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
			//TODO more complete support for this setting
			
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

	/**
	 * This method is disabled for HvlDisplayWindowed.
	 * 
	 * @param vsyncEnabledArg <code>vsyncEnabled</code> is always false, regardless of this value
	 */
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
