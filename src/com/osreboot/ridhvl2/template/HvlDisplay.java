package com.osreboot.ridhvl2.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;

public abstract class HvlDisplay {

	public static final String LABEL = "HvlDisplay";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_EARLIEST + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_EARLIEST + HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_LATEST - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	LAUNCH_CODE = 1,
	LAUNCH_CODE_RAW = 2;//2^1

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		try{
			DisplayMode[] displays = Display.getAvailableDisplayModes();
			for(DisplayMode mode : displays)
				addFullscreenDisplay(new HvlDisplayFullscreen(mode, false));
			HvlLogger.println(debug, "Found " + getFullscreenDisplays().size() + " supported fullscreen display modes.");
		}catch(LWJGLException e){
			e.printStackTrace();
		}

		if(getDisplay() != null){
			getDisplay().apply();
			displayInitialized = true;
		}else throw new NullDisplayException();
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_PRE_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		getDisplay().preUpdate(delta);
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_POST_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_POST_UPDATE = (debug, delta) -> {
		getDisplay().postUpdate(delta);
	};

	private static HvlDisplay display;
	private static boolean displayInitialized = false;

	private static ArrayList<HvlDisplayFullscreen> fullscreenDisplays = new ArrayList<>();

	public static HvlDisplay getDisplay(){
		return display;
	}

	public static void setDisplay(HvlDisplay displayArg){
		if(displayArg == null) throw new NullDisplayException();
		if(display != null && displayInitialized) display.unapply();//TODO smooth transition (i.e. don't destroy display)
		display = displayArg;
		if(displayInitialized) display.apply();
	}

	private static void addFullscreenDisplay(HvlDisplayFullscreen displayArg){
		fullscreenDisplays.add(displayArg);
	}
	
	public static List<HvlDisplayFullscreen> getFullscreenDisplays(){
		return Collections.unmodifiableList(fullscreenDisplays);
	}
	
	private int refreshRate;
	private boolean vsyncEnabled, resizable;
	//TODO iconPath

	HvlDisplay(int refreshRateArg, boolean vsyncEnabledArg, boolean resizableArg){
		refreshRate = refreshRateArg;
		vsyncEnabled = vsyncEnabledArg;
		resizable = resizableArg;
	}

	protected abstract void apply();
	protected abstract void unapply();

	protected abstract void preUpdate(float delta);
	protected abstract void postUpdate(float delta);

	public boolean isVsyncEnabled(){
		return vsyncEnabled;
	}

	public void setVsyncEnabled(boolean vsyncEnabledArg){
		vsyncEnabled = vsyncEnabledArg;
	}

	public int getRefreshRate(){
		return refreshRate;
	}

	public void setRefreshRate(int refreshRateArg){
		if(refreshRateArg < 1){
			throw new InvalidRefreshRateException();
		}else refreshRate = refreshRateArg;
	}

	public boolean isResizable(){
		return resizable;
	}

	public void setResizable(boolean resizableArg){
		resizable = resizableArg;
	}

	public static class InvalidRefreshRateException extends RuntimeException{
		private static final long serialVersionUID = -5066247796713528811L;
	}

	public static class NullDisplayException extends RuntimeException{
		private static final long serialVersionUID = -5076487588403656161L;
	}

}
