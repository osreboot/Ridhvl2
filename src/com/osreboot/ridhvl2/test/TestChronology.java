package com.osreboot.ridhvl2.test;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;

class TestChronology {

//	public static void main(String[] args){
//		HvlChronology.registerChronology(TestChronologyOne.class);
//		HvlChronology.registerChronology(TestChronologyTwo.class);
//		HvlChronology.registerChronology(TestChronologyDuplicate.class);
//		HvlChronology.loadChronologies(
//				TestChronologyOne.LAUNCH_CODE + TestChronologyTwo.LAUNCH_CODE, 
//				HvlChronology.LAUNCH_CODE + TestChronologyTwo.LAUNCH_CODE);
//
//		HvlChronology.initialize();
//		HvlChronology.preUpdate(1f);
//		HvlChronology.postUpdate(1f);
//	}

	public static class TestChronologyOne {

		public static final int LAUNCH_CODE_RAW = 1, LAUNCH_CODE = (int)Math.pow(2, LAUNCH_CODE_RAW);

		@HvlChronologyInitialize(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE, launchCode = LAUNCH_CODE_RAW)
		public static final HvlAction.A1<Boolean> ACTION_INIT = (debug) -> {
			HvlLogger.println(debug, TestChronology.class, "1 - Initialized!");
		};

		@HvlChronologyUpdate(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE, launchCode = LAUNCH_CODE_RAW)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = (debug, delta) -> {
			HvlLogger.println(debug, TestChronology.class, "1 - Updated!");
		};

	}

	public static class TestChronologyTwo {

		public static final int LAUNCH_CODE_RAW = 2, LAUNCH_CODE = (int)Math.pow(2, LAUNCH_CODE_RAW);

		@HvlChronologyInitialize(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE + 1, launchCode = LAUNCH_CODE_RAW)
		public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "2 - Initialized!");
		};

		@HvlChronologyUpdate(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE - 1, launchCode = LAUNCH_CODE_RAW)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = (debug, delta) -> {
			HvlLogger.println(debug, TestChronology.class, "2 - Updated!");
		};

	}

	public static class TestChronologyDuplicate {

		public static final int LAUNCH_CODE_RAW = 3, LAUNCH_CODE = (int)Math.pow(2, LAUNCH_CODE_RAW);

		@HvlChronologyInitialize(label = "TestChronoDuplicate", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE, launchCode = LAUNCH_CODE_RAW)
		public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
			HvlLogger.println(debug, TestChronology.class, "DUPE - Initialized!");
		};

		@HvlChronologyUpdate(label = "TestChronoDuplicate", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE, launchCode = LAUNCH_CODE_RAW)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = (debug, delta) -> {
			HvlLogger.println(debug, TestChronology.class, "DUPE - Updated!");
		};

	}


}
