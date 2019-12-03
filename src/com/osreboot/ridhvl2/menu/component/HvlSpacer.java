package com.osreboot.ridhvl2.menu.component;

import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlSpacer extends HvlComponent{
	private static final long serialVersionUID = 8287495163691593368L;
	
	protected HvlSpacer(HvlTagTransient<?>... tags){
		super(accumulate(tags));
	}
	
	public HvlSpacer(float widthArg, float heightArg){
		this();
		getEnvironment().setAndUnlockWidth(widthArg);
		getEnvironment().setAndUnlockHeight(heightArg);
	}
	
	public HvlSpacer(float sizeArg){
		this(sizeArg, sizeArg);
	}

}
