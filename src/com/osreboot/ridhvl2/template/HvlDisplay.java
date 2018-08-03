package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.HvlAction;

public abstract class HvlDisplay {

	public static final String LABEL = "HvlDisplay";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_EARLIEST + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_EARLIEST + HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_LATEST - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	LAUNCH_CODE_RAW = 1,
	LAUNCH_CODE = 2;//2^1

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
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
	
	public static final int DEFAULT_REFRESH_RATE = 144;
	public static final boolean 
	DEFAULT_VSYNC = false,
	DEFAULT_RESIZABLE = false;
	
	private static HvlDisplay display;
	private static boolean displayInitialized = false;
	
	public static HvlDisplay getDisplay(){
		return display;
	}

	public static void setDisplayWindowed(int refreshRateArg, boolean vsyncArg, boolean resizableArg, int widthArg, int heightArg){
		if(display != null && displayInitialized) display.unapply();
		display = new HvlDisplayWindowed(refreshRateArg, vsyncArg, resizableArg, widthArg, heightArg);
		if(displayInitialized) display.apply();
	}
	
	public static void setDisplayWindowed(int widthArg, int heightArg){
		int refreshRate = DEFAULT_REFRESH_RATE;
		boolean vsync = DEFAULT_VSYNC;
		boolean resizable = DEFAULT_RESIZABLE;
		if(display != null){
			refreshRate = display.getRefreshRate();
			vsync = display.isVsyncEnabled();
			resizable = display.isResizable();
			if(displayInitialized) display.unapply();
		}
		display = new HvlDisplayWindowed(refreshRate, vsync, resizable, widthArg, heightArg);
		if(displayInitialized) display.apply();
	}
	
	private int refreshRate;
	private boolean vsyncEnabled, resizable;

	public HvlDisplay(int refreshRateArg, boolean vsyncEnabledArg, boolean resizableArg){
		refreshRate = refreshRateArg;
		vsyncEnabled = vsyncEnabledArg;
		resizable = resizableArg;
	}

	public abstract void apply();
	public abstract void unapply();

	public abstract void preUpdate(float delta);
	public abstract void postUpdate(float delta);

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
		refreshRate = refreshRateArg;
	}
	
	public boolean isResizable(){
		return resizable;
	}

	public void setResizable(boolean resizableArg){
		resizable = resizableArg;
	}

	public static class NullDisplayException extends RuntimeException{
		private static final long serialVersionUID = -5076487588403656161L;
	}

}
