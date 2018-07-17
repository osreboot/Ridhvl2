package com.osreboot.ridhvl2.test;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;

class TestChronology {

	public static void main(String[] args){
		HvlChronology.registerChronology(TestChronologyOne.class);
		HvlChronology.registerChronology(TestChronologyTwo.class);
		//HvlChronology.registerChronology(TestChronologyDuplicate.class);
		HvlChronology.loadChronologies(6, 7);
		System.out.println(HvlChronology.getDebugOutput());
		
		HvlChronology.initialize();
		HvlChronology.preUpdate(1f);
		HvlChronology.postUpdate(1f);
	}
	
	public static class TestChronologyOne {

		@HvlChronologyInitialize(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE, launchCode = 2)
		public static final HvlAction.A1<Boolean> ACTION_INIT = new HvlAction.A1<Boolean>(){
			@Override
			public void run(Boolean debug) {
				System.out.println("1 - Initialized! Debug is " + debug);
			}
		};
		
		@HvlChronologyUpdate(label = "TestChrono1", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE, launchCode = 2)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = new HvlAction.A2<Boolean, Float>() {
			@Override
			public void run(Boolean debug, Float delta) {
				System.out.println("1 - Updated! Debug is " + debug);
			}
		};
		
	}
	
	public static class TestChronologyTwo {

		@HvlChronologyInitialize(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE + 1, launchCode = 3)
		public static final HvlAction.A1<Boolean> ACTION_INIT = new HvlAction.A1<Boolean>(){
			@Override
			public void run(Boolean debug) {
				System.out.println("2 - Initialized! Debug is " + debug);
			}
		};
		
		@HvlChronologyUpdate(label = "TestChrono2", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE - 1, launchCode = 3)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = new HvlAction.A2<Boolean, Float>() {
			@Override
			public void run(Boolean debug, Float delta) {
				System.out.println("2 - Updated! Debug is " + debug);
			}
		};
		
	}
	
	public static class TestChronologyDuplicate {

		@HvlChronologyInitialize(label = "TestChronoDuplicate", chronology = HvlChronology.CHRONOLOGY_INIT_MIDDLE, launchCode = 2)
		public static final HvlAction.A1<Boolean> ACTION_INIT = new HvlAction.A1<Boolean>(){
			@Override
			public void run(Boolean debug) {
				
			}
		};
		
		@HvlChronologyUpdate(label = "TestChronoDuplicate", chronology = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE, launchCode = 2)
		public static final HvlAction.A2<Boolean, Float> ACTION_UPDATE = new HvlAction.A2<Boolean, Float>() {
			@Override
			public void run(Boolean debug, Float delta) {
				
			}
		};
		
	}

	
}
