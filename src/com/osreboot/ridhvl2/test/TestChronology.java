package com.osreboot.ridhvl2.test;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;

public class TestChronology {

	public TestChronology(){
		HvlChronology.registerChronology(TestChronologyOne.class);
		HvlChronology.registerChronology(TestChronologyTwo.class);
		HvlChronology.registerChronology(TestChronologyDuplicate.class);
		
		HvlChronology.loadChronologies(
				TestChronologyOne.LAUNCH_CODE_RAW + TestChronologyTwo.LAUNCH_CODE_RAW, 
				HvlChronology.LAUNCH_CODE_RAW + TestChronologyOne.LAUNCH_CODE_RAW);

		HvlChronology.initialize();
		HvlChronology.preUpdate(1f);
		HvlChronology.postUpdate(1f);
		
		HvlChronology.unloadChronologies();
		
		HvlChronology.loadChronologies(
				TestChronologyOne.LAUNCH_CODE_RAW + TestChronologyTwo.LAUNCH_CODE_RAW, 
				HvlChronology.LAUNCH_CODE_RAW + TestChronologyOne.LAUNCH_CODE_RAW + TestChronologyTwo.LAUNCH_CODE_RAW);

		HvlChronology.initialize();
		HvlChronology.preUpdate(1f);
		HvlChronology.postUpdate(1f);
		
		HvlChronology.unloadChronologies();
		
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
