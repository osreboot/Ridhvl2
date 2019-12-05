package com.osreboot.ridhvl2.menu;

import java.util.ArrayList;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;
import com.osreboot.ridhvl2.template.HvlDisplay;

public final class HvlEnvironment{

	public static final String LABEL = "HvlEnvironment";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_EARLY + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_POST_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_POST_MIDDLE - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_MIDDLE + (2 * HvlChronology.CHRONOLOGY_EXIT_INTERVAL),
	LAUNCH_CODE = 5,
	LAUNCH_CODE_RAW = 32;//2^5

	private static boolean active = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		if(active){
			//This only fires if checking is truly enabled by native Ridhvl systems.
			HvlLogger.println(debug, "Environment safety checking enabled.");
		}
		restrictedEnvironments = new ArrayList<>();
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_POST_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_POST_UPDATE = (debug, delta) -> {
		unrestrictAllEnvironments();
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		unrestrictAllEnvironments();
		active = false;
	};

	private static ArrayList<HvlEnvironment> restrictedEnvironments;

	private static void unrestrictAllEnvironments(){
		for(HvlEnvironment environment : restrictedEnvironments){
			environment.restricted = false;
		}
		restrictedEnvironments.clear();
	}

	private float x, y, width, height;
	private boolean restricted, xLocked, yLocked, widthLocked, heightLocked;

	public HvlEnvironment(){
		copyFrom(HvlDisplay.getDisplay().getEnvironment());
		setLock(true, true, true, true);
	}

	public HvlEnvironment(HvlEnvironment environment){
		copyFrom(environment);
		setLock(true, true, true, true);
	}

	public HvlEnvironment(float xArg, float yArg, float widthArg, float heightArg){
		x = xArg;
		y = yArg;
		width = widthArg;
		height = heightArg;
		setLock(false, false, false, false);
	}

	void deepCopyFrom(HvlEnvironment environment){
		x = environment.x;
		y = environment.y;
		width = environment.width;
		height = environment.height;
		xLocked = environment.xLocked;
		yLocked = environment.yLocked;
		widthLocked = environment.widthLocked;
		heightLocked = environment.heightLocked;
	}
	
	void copyFrom(HvlEnvironment environment){
		if(xLocked) x = environment.x;
		if(yLocked) y = environment.y;
		if(widthLocked) width = environment.width;
		if(heightLocked) height = environment.height;
	}

	public void copyFromAndUnlock(HvlEnvironment environment){
		if(active && restricted)
			throw new EnvironmentRestrictedException();
		else{
			x = environment.x;
			y = environment.y;
			width = environment.width;
			height = environment.height;
			setLock(false, false, false, false);
		}
	}

	protected void setRestricted(boolean restrictedArg){
		if(active){
			if(restrictedArg)
				restrictedEnvironments.add(this);
			else restrictedEnvironments.remove(this);
		}

		restricted = restrictedArg;
	}

	public void setLock(boolean xLockedArg, boolean yLockedArg, boolean widthLockedArg, boolean heightLockedArg){
		if(active && restricted)
			throw new EnvironmentRestrictedException();
		else{
			xLocked = xLockedArg;
			yLocked = yLockedArg;
			widthLocked = widthLockedArg;
			heightLocked = heightLockedArg;
		}
	}

	public void setAndUnlockX(float xArg){
		if(active && restricted)
			throw new EnvironmentRestrictedException("x");
		else{
			x = xArg;
			xLocked = false;
		}
	}

	public void setAndUnlockY(float yArg){
		if(active && restricted)
			throw new EnvironmentRestrictedException("y");
		else{
			y = yArg;
			yLocked = false;
		}
	}

	public void setAndUnlockWidth(float widthArg){
		if(active && restricted)
			throw new EnvironmentRestrictedException("width");
		else{
			width = widthArg;
			widthLocked = false;
		}
	}

	public void setAndUnlockHeight(float heightArg){
		if(active && restricted)
			throw new EnvironmentRestrictedException("height");
		else{
			height = heightArg;
			heightLocked = false;
		}
	}

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public float getWidth(){
		return width;
	}

	public float getHeight(){
		return height;
	}

	public boolean isLockChangeRestricted(){
		return restricted;
	}

	public boolean isXLocked(){
		return xLocked;
	}

	public boolean isYLocked(){
		return yLocked;
	}

	public boolean isWidthLocked(){
		return widthLocked;
	}

	public boolean isHeightLocked(){
		return heightLocked;
	}

	/**
	 * Thrown if an attempt is made to alter a HvlEnvironment's value lock mask during a time in which lock mask
	 * changing is marked as restricted. This is usually the case in between {@linkplain HvlComponent}
	 * {@linkplain HvlComponent#update(float) update(float)} and {@linkplain HvlComponent#draw(float) draw(float)}
	 * calls.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class EnvironmentRestrictedException extends RuntimeException{
		private static final long serialVersionUID = -4135908564366215034L;

		public EnvironmentRestrictedException(String valueArg){
			super("Tried to change the environment value of '" + valueArg + "' during a restricted time period!");
		}

		public EnvironmentRestrictedException(){
			super("Tried to change an environment during a restricted time period!");
		}

	}

}
