package com.osreboot.ridhvl2.menu.component;

import static com.osreboot.ridhvl2.HvlStatics.hvlDraw;
import static com.osreboot.ridhvl2.HvlStatics.hvlQuadc;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlColor;
import com.osreboot.ridhvl2.HvlMath;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlRule extends HvlComponent{
	private static final long serialVersionUID = -6293558927367622718L;

	public static HvlRule fromDefault(){
		return HvlDefault.applyIfExists(HvlRule.class, new HvlRule());
	}

	public static final HvlTagTransient<HvlColor> TAG_COLOR = new HvlTagTransient<>(HvlColor.class, "color");

	public static final HvlTag<Boolean> TAG_HORIZONTAL = new HvlTag<>(Boolean.class, "horizontal");
	public static final HvlTag<Float> TAG_SIZE = new HvlTag<>(Float.class, "size");
	public static final HvlTag<Boolean> TAG_UNIT_SIZE = new HvlTag<>(Boolean.class, "unit_size");
	public static final HvlTag<Float> TAG_THICKNESS = new HvlTag<>(Float.class, "thickness");
	public static final HvlTag<Float> TAG_ALIGN_X = new HvlTag<>(Float.class, "align_x");
	public static final HvlTag<Float> TAG_ALIGN_Y = new HvlTag<>(Float.class, "align_y");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> 
	DEFAULT_DRAW = (delta, environment, component) -> {
		float width;
		float height;
		if(component.get(TAG_HORIZONTAL)){
			width = component.get(TAG_UNIT_SIZE) ? component.get(TAG_SIZE) * environment.getWidth() : component.get(TAG_SIZE);
			height = component.get(TAG_THICKNESS);
		}else{
			width = component.get(TAG_THICKNESS);
			height = component.get(TAG_UNIT_SIZE) ? component.get(TAG_SIZE) * environment.getHeight() : component.get(TAG_SIZE);
		}
		float y = HvlMath.map(component.get(TAG_ALIGN_Y), 0, 1f,
				environment.getY() + (height / 2), environment.getY() + environment.getHeight() - (height / 2));
		float x = HvlMath.map(component.get(TAG_ALIGN_X), 0, 1f,
				environment.getX() + (width / 2), environment.getX() + environment.getWidth() - (width / 2));
		hvlDraw(hvlQuadc(x, y, width, height), component.get(TAG_COLOR));
	};
	
	protected HvlRule(HvlTagTransient<?>... tags){
		super(accumulate(tags,
				TAG_COLOR,
				TAG_HORIZONTAL,
				TAG_SIZE,
				TAG_UNIT_SIZE,
				TAG_THICKNESS,
				TAG_ALIGN_X,
				TAG_ALIGN_Y));
		set(TAG_DRAW, DEFAULT_DRAW);
	}
	
	public HvlRule(boolean horizontalArg, float sizeArg, float thicknessArg, HvlColor colorArg){
		this();
		HvlDefault.applyIfExists(HvlRule.class, this);
		set(TAG_COLOR, colorArg);
		set(TAG_HORIZONTAL, horizontalArg);
		set(TAG_SIZE, sizeArg);
		set(TAG_UNIT_SIZE, true);
		set(TAG_THICKNESS, thicknessArg);
		set(TAG_ALIGN_X, 0f);
		set(TAG_ALIGN_Y, 0f);
	}
	
	public HvlRule align(float xAlignArg, float yAlignArg){
		return (HvlRule)set(TAG_ALIGN_X, xAlignArg)
				.set(TAG_ALIGN_Y, yAlignArg);
	}

}
