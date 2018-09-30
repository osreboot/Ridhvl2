package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.loader.HvlLoader;
import com.osreboot.ridhvl2.loader.HvlLoaderTexture;

/**
 * A complete implementation of Ridhvl2's core functionality. This template handles managing 
 * {@linkplain HvlChronology} states, assigning {@linkplain HvlDisplay} instances, creating 
 * {@linkplain HvlLoader} instances and running an {@linkplain HvlTimer} loop. Programs intended to make the most
 * of Ridhvl2 should extend this class.
 * 
 * @author os_reboot
 *
 */
public abstract class HvlTemplateI extends HvlTemplate{

	/**
	 * Constructs an instance of HvlTemplateI with default launch codes. This is the recommended constructor.
	 * 
	 * @param displayArg the initial {@linkplain HvlDisplay} instance to use
	 */
	public HvlTemplateI(HvlDisplay displayArg){
		this(displayArg, Long.MAX_VALUE, Long.MAX_VALUE - 1);
		//TODO different debug value when Ridhvl2 is more robust
	}
	
	/**
	 * Constructs an instance of HvlTemplateI with user-specified launch codes. This constructor is not
	 * recommended for those unfamiliar with the inner workings of Ridhvl2.
	 * 
	 * @param displayArg the initial {@linkplain HvlDisplay} instance to use
	 * @param launchCodeArg the {@linkplain HvlChronology} launch code to use
	 * @param debugLaunchCodeArg the {@linkplain HvlChronology} debug launch code to use
	 */
	public HvlTemplateI(HvlDisplay displayArg, long launchCodeArg, long debugLaunchCodeArg){
		super(0);
		
		//Set the timer's max delta to something reasonable for games
		getTimer().setMaxDelta(HvlTimer.MAXDELTA_DECISECOND);
		
		//Register and load Ridhvl2's utilities
		HvlChronologyRegistry.registerRidhvlChronologies();
		HvlChronology.loadChronologies(launchCodeArg, debugLaunchCodeArg);
		
		//Set the HvlDisplay specified by the user
		HvlDisplay.setDisplay(displayArg);
		
		//Add HvlLoader instances so the user can load resources
		HvlLoader.addLoader(new HvlLoaderTexture());
		
		//Initialize Ridhvl2's utilities
		HvlChronology.initialize();
		
		//Start the timer loop
		start();
	}
	
	@Override
	public final void preUpdate(float delta){
		HvlChronology.preUpdate(delta);
	}
	
	@Override
	public final void postUpdate(float delta){
		HvlChronology.postUpdate(delta);
		
		if(isExiting()){
			HvlChronology.unloadChronologies();
			HvlLoader.clearLoaders();
			HvlDisplay.exit();
		}
	}

}
