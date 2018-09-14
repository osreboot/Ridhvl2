package com.osreboot.ridhvl2.template;

public abstract class HvlTemplateI extends HvlTemplate{

	public HvlTemplateI(HvlDisplay displayArg){
		this(displayArg, Long.MAX_VALUE, Long.MAX_VALUE - 1);//TODO different debug value later
	}
	
	public HvlTemplateI(HvlDisplay displayArg, long launchCodeArg, long debugLaunchCodeArg){
		super(0);
		
		HvlChronologyRegistry.registerRidhvlChronologies();
		HvlChronology.loadChronologies(launchCodeArg, debugLaunchCodeArg);
		
		HvlDisplay.setDisplay(displayArg);
		
		HvlChronology.initialize();
		
		start();
	}
	
	@Override
	public final void preUpdate(float delta){
		HvlChronology.preUpdate(delta);
	}
	
	@Override
	public final void postUpdate(float delta){
		HvlChronology.postUpdate(delta);
	}

}
