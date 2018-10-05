package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.loader.HvlLoader;
import com.osreboot.ridhvl2.painter.HvlPainter;

public final class HvlChronologyRegistry {

	private HvlChronologyRegistry(){}
	
	public static final void registerRidhvlChronologies(){
		HvlChronology.registerChronology(HvlDisplay.class);
		HvlChronology.registerChronology(HvlPainter.class);
		HvlChronology.registerChronology(HvlLoader.class);
	}
	
}
