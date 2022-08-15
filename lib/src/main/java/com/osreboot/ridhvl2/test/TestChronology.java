package com.osreboot.ridhvl2.test;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;

/**
 * A test class for {@linkplain HvlChronology}. This test registers a set of HvlChronology events, loads a launch
 * code-specified subset of those events, executes HvlChronology's base program stages, unloads the events and 
 * finally loads a different subset of the events and repeats the process.
 * 
 * <p>
 * 
 * The expected test output consists of two separate pairs of loading and unloading stages, with event-originating 
 * debug output in between each loading and unloading pair.
 * 
 * @author os_reboot
 *
 */
public class TestChronology {
	
	public TestChronology(){
		HvlChronology.registerChronology(TestChronologyOne.class);
		HvlChronology.registerChronology(TestChronologyTwo.class);
		HvlChronology.registerChronology(TestChronologyDuplicate.class);
		
		HvlChronology.loadEvents(
				TestChronologyOne.LAUNCH_CODE_RAW + TestChronologyTwo.LAUNCH_CODE_RAW, 
				HvlChronology.LAUNCH_CODE_RAW + TestChronologyOne.LAUNCH_CODE_RAW);

		HvlChronology.initialize();
		HvlChronology.preUpdate(1f);
		HvlChronology.postUpdate(1f);
		HvlChronology.exit();
		
		HvlChronology.unloadEvents();
		
		HvlChronology.registerChronology(TestChronologyOne.class);
		HvlChronology.registerChronology(TestChronologyTwo.class);
		HvlChronology.registerChronology(TestChronologyDuplicate.class);
		
		HvlChronology.loadEvents(
				TestChronologyOne.LAUNCH_CODE_RAW + TestChronologyTwo.LAUNCH_CODE_RAW, 
				HvlChronology.LAUNCH_CODE_RAW + TestChronologyOne.LAUNCH_CODE_RAW + TestChronologyTwo.LAUNCH_CODE_RAW);

		HvlChronology.initialize();
		HvlChronology.preUpdate(1f);
		HvlChronology.postUpdate(1f);
		HvlChronology.exit();
		
		HvlChronology.unloadEvents();
		
		//TODO intentionally throw and catch exception for duplicate actions
	}

	public static class TestChronologyOne {

		public static final int LAUNCH_CODE = 1, LAUNCH_CODE_RAW = (int)Math.pow(2, LAUNCH_CODE);

		@HvlChronologyInitialize(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE, launchCode = LAUNCH_CODE)
		public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "1 - Initialized!");
		};

		@HvlChronologyUpdate(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE, launchCode = LAUNCH_CODE)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = (debug, delta) -> {
			HvlLogger.println(debug, TestChronology.class, "1 - Updated!");
		};
		
		@HvlChronologyExit(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_EXIT_MIDDLE, launchCode = LAUNCH_CODE)
		public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "1 - Exited!");
		};

	}

	public static class TestChronologyTwo {

		public static final int LAUNCH_CODE = 2, LAUNCH_CODE_RAW = (int)Math.pow(2, LAUNCH_CODE);

		@HvlChronologyInitialize(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE + 1, launchCode = LAUNCH_CODE)
		public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "2 - Initialized!");
		};

		@HvlChronologyUpdate(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE - 1, launchCode = LAUNCH_CODE)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = (debug, delta) -> {
			HvlLogger.println(debug, TestChronology.class, "2 - Updated!");
		};
		
		@HvlChronologyExit(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_EXIT_MIDDLE + HvlChronology.CHRONOLOGY_EXIT_INTERVAL, launchCode = LAUNCH_CODE)
		public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "2 - Exited!");
		};

	}

	public static class TestChronologyDuplicate {

		public static final int LAUNCH_CODE = 3, LAUNCH_CODE_RAW = (int)Math.pow(2, LAUNCH_CODE);

		@HvlChronologyInitialize(label = "TestChronoDuplicate", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE, launchCode = LAUNCH_CODE)
		public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "DUPE - Initialized!");
		};

		@HvlChronologyUpdate(label = "TestChronoDuplicate", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE, launchCode = LAUNCH_CODE)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = (debug, delta) -> {
			HvlLogger.println(debug, TestChronology.class, "DUPE - Updated!");
		};

	}


}
