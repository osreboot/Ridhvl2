package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.painter.HvlPainter;

public final class HvlChronologyRegistry {

	public static final void registerRidhvlChronologies(){
		HvlChronology.registerChronology(HvlDisplay.class);
		HvlChronology.registerChronology(HvlPainter.class);
	}
	
}
