package com.osreboot.ridhvl2.template;

import java.awt.DisplayMode;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.migration.Display;

/**
 * An instantiable wrapper that serves as a combination of LWJGL's {@linkplain Display} and {@linkplain DisplayMode}
 * classes. The static aspects of this utility facilitate the swapping and manipulation of a single active instance
 * of HvlDisplay, which is itself responsible for handling the program's current LWJGL DisplayMode.
 * 
 * <p>
 * 
 * Users looking for instances to feed to {@linkplain HvlTemplateI} constructors should refer to one of HvlDisplay's
 * many subclasses, such as {@linkplain HvlDisplayFullscreenAuto} or {@linkplain HvlDisplayWindowed}.
 * 
 * @author os_reboot
 *
 */
public abstract class HvlDisplay {

	public static final String LABEL = "HvlDisplay";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_EARLIEST + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_EARLIEST + HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_LATEST - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_LATE + HvlChronology.CHRONOLOGY_EXIT_INTERVAL,
	LAUNCH_CODE = 1,
	LAUNCH_CODE_RAW = 2;//2^1
	
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
	
	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		if(getDisplay() != null && isDisplayInitialized()){
			getDisplay().unapply();
			display = null;
		}
		displayInitialized = false;
	};

	private static HvlDisplay display;
	private static boolean displayInitialized = false;

	/**
	 * @return the active HvlDisplay instance
	 */
	public static HvlDisplay getDisplay(){
		return display;
	}

	/**
	 * Sets the active HvlDisplay instance and applies it with {@linkplain #apply()}. If an HvlDisplay instance is
	 * already active, it is first unapplied with {@linkplain #unapply()}.
	 * 
	 * @param displayArg the HvlDisplay instance to set as the active display
	 * @throws NullDisplayException if the supplied HvlDisplay instance is null
	 */
	public static void setDisplay(HvlDisplay displayArg){
		if(displayArg == null) throw new NullDisplayException();
		
		if(display != null && displayInitialized) display.unapply();
		//TODO smooth transition (i.e. don't destroy display)
		
		display = displayArg;
		if(displayInitialized) display.apply();
	}
	
	private static boolean isDisplayInitialized(){
		return displayInitialized;
	}

	private int refreshRate;
	private boolean vsyncEnabled, resizable;
	//TODO iconPath

	HvlDisplay(int refreshRateArg, boolean vsyncEnabledArg, boolean resizableArg){
		refreshRate = refreshRateArg;
		vsyncEnabled = vsyncEnabledArg;
		resizable = resizableArg;
	}
	
	public abstract long getId();

	/**
	 * Called when the HvlDisplay is set as the active display.
	 */
	protected abstract void apply();
	
	/**
	 * Called when the HvlDisplay is set as inactive.
	 */
	protected abstract void unapply();

	/**
	 * Called once per update, before {@linkplain HvlTemplate} updates.
	 * 
	 * @param delta the time (in seconds) since the last update
	 */
	protected abstract void preUpdate(float delta);
	
	/**
	 * Called once per update, after {@linkplain HvlTemplate} updates.
	 * 
	 * @param delta the time (in seconds) since the last update
	 */
	protected abstract void postUpdate(float delta);

	/**
	 * @return if the HvlDisplay's vertical-sync option is enabled
	 */
	public boolean isVsyncEnabled(){
		return vsyncEnabled;
	}

	/**
	 * Sets the HvlDisplay's vertical-sync option. This may be disabled on HvlDisplay implementations that don't 
	 * support vsync.
	 * 
	 * @param vsyncEnabledArg the new <code>vsyncEnabled</code> value
	 */
	public void setVsyncEnabled(boolean vsyncEnabledArg){
		vsyncEnabled = vsyncEnabledArg;
	}

	/**
	 * @return the HvlDisplay's current refresh rate
	 */
	public int getRefreshRate(){
		return refreshRate;
	}

	/**
	 * Sets the HvlDisplay's refresh rate. This may be disabled on HvlDisplay implementations that don't support
	 * changing refresh rates.
	 * 
	 * @param refreshRateArg the new <code>refreshRate</code> value
	 * @throws InvalidRefreshRateException if <code>refreshRateArg</code> is less than 1
	 */
	public void setRefreshRate(int refreshRateArg){
		if(refreshRateArg < 1){
			throw new InvalidRefreshRateException();
		}else refreshRate = refreshRateArg;
	}

	/**
	 * @return if the HvlDisplay's resizability option is enabled
	 */
	public boolean isResizable(){
		return resizable;
	}

	/**
	 * Sets the HvlDisplay's resizability option. This may be disabled on HvlDisplay implementatons that don't
	 * support resizable displays.
	 * 
	 * @param resizableArg
	 */
	public void setResizable(boolean resizableArg){
		resizable = resizableArg;
	}
	
	public abstract HvlEnvironment getEnvironment();

	/**
	 * Thrown if an attempt is made to change a HvlDisplay's <code>refreshRate</code> to a value less than 1.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class InvalidRefreshRateException extends RuntimeException{
		private static final long serialVersionUID = -5066247796713528811L;
	}

	/**
	 * Thrown if, when HvlDisplay's {@linkplain HvlChronology} initialize event is called, a HvlDisplay instance has
	 * not been set as the active instance. This exception is not thrown if HvlDisplay isn't activated by the
	 * HvlChronology launch code.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class NullDisplayException extends RuntimeException{
		private static final long serialVersionUID = -5076487588403656161L;
	}

}
