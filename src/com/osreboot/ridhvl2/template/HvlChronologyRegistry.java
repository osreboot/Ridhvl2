package com.osreboot.ridhvl2.template;

import com.osreboot.ridhvl2.loader.HvlLoader;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlMenu;
import com.osreboot.ridhvl2.menu.HvlType;
import com.osreboot.ridhvl2.painter.HvlPainter;

/**
 * Class that exists solely to provide a means of registering {@linkplain HvlChronology} events in all Ridhvl2 
 * event-containing classes.
 * 
 * @author os_reboot
 *
 */
public final class HvlChronologyRegistry {

	private HvlChronologyRegistry(){}
	
	/**
	 * Registers the {@linkplain HvlChronology} events in all Ridhvl2 event-containing classes. This includes, but is
	 * not limited to, the events in:
	 * 
	 * <p>
	 * 
	 * {@linkplain HvlDisplay}
	 * <br>
	 * {@linkplain HvlPainter}
	 * <br>
	 * {@linkplain HvlLoader}
	 * <br>
	 * {@linkplain HvlType}
	 * <br>
	 * {@linkplain HvlDefault}
	 * <br>
	 * {@linkplain HvlMenu}
	 * 
	 * <p>
	 * 
	 * For more information on registering events in general, see HvlChronology's class comment.
	 */
	public static final void registerRidhvlChronologies(){
		HvlChronology.registerChronology(HvlDisplay.class);
		HvlChronology.registerChronology(HvlPainter.class);
		HvlChronology.registerChronology(HvlLoader.class);
		HvlChronology.registerChronology(HvlType.class);
		HvlChronology.registerChronology(HvlEnvironment.class);
		HvlChronology.registerChronology(HvlDefault.class);
		HvlChronology.registerChronology(HvlMenu.class);
	}
	
}
