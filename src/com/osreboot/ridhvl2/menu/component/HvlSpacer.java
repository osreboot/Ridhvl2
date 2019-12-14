package com.osreboot.ridhvl2.menu.component;

import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlSpacer extends HvlComponent{
	private static final long serialVersionUID = 8287495163691593368L;
	
	public static HvlSpacer fromDefault(){
		return HvlDefault.applyIfExists(HvlSpacer.class, new HvlSpacer());
	}
	
	protected HvlSpacer(HvlTagTransient<?>... tags){
		super(accumulate(tags));
	}
	
	public HvlSpacer(float widthArg, float heightArg){
		this();
		HvlDefault.applyIfExists(HvlSpacer.class, this);
		set(TAG_OVERRIDE_WIDTH, widthArg);
		set(TAG_OVERRIDE_HEIGHT, heightArg);
	}
	
	public HvlSpacer(float sizeArg){
		this(sizeArg, sizeArg);
	}

}
