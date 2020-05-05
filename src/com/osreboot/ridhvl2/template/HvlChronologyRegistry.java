package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.loader.HvlLoader;

/**
 * Class that exists solely to provide a means of registering {@linkplain HvlChronology} events in all Ridhvl2 
 * event-containing classes.
 * 
 * @author os_reboot
 *
 */
public final class HvlChronologyRegistry {

	private HvlChronologyRegistry(){}
	
	public static final void registerRidhvlChronologies(){
		HvlChronology.registerChronologyInitialize(HvlDisplay.ACTION_INIT, HvlDisplay.LABEL, HvlDisplay.LAUNCH_CODE, HvlDisplay.CHRONO_INIT);
		HvlChronology.registerChronologyUpdate(HvlDisplay.ACTION_PRE_UPDATE, HvlDisplay.LABEL, HvlDisplay.LAUNCH_CODE, HvlDisplay.CHRONO_PRE_UPDATE);
		HvlChronology.registerChronologyUpdate(HvlDisplay.ACTION_POST_UPDATE, HvlDisplay.LABEL, HvlDisplay.LAUNCH_CODE, HvlDisplay.CHRONO_POST_UPDATE);
		HvlChronology.registerChronologyExit(HvlDisplay.ACTION_EXIT, HvlDisplay.LABEL, HvlDisplay.LAUNCH_CODE, HvlDisplay.CHRONO_EXIT);
		
//		HvlChronology.registerChronology(HvlPainter.class);

		HvlChronology.registerChronologyInitialize(HvlLoader.ACTION_INIT, HvlLoader.LABEL, HvlLoader.LAUNCH_CODE, HvlLoader.CHRONO_INIT);
		HvlChronology.registerChronologyExit(HvlLoader.ACTION_EXIT, HvlLoader.LABEL, HvlLoader.LAUNCH_CODE, HvlLoader.CHRONO_EXIT);
		
//		HvlChronology.registerChronology(HvlType.class);
//		HvlChronology.registerChronology(HvlEnvironment.class);
//		HvlChronology.registerChronology(HvlDefault.class);
//		HvlChronology.registerChronology(HvlMenu.class);
	}
	
}
