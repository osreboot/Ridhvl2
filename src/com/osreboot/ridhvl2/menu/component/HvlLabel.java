package com.osreboot.ridhvl2.menu.component;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlFont;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlLabel extends HvlComponent{
	private static final long serialVersionUID = 6541669450721020192L;

	public static final HvlTagTransient<HvlFont> TAG_FONT = new HvlTagTransient<>(HvlFont.class, "font");
	public static final HvlTagTransient<Color> TAG_COLOR = new HvlTagTransient<>(Color.class, "color");

	public static final HvlTag<String> TAG_TEXT = new HvlTag<>(String.class, "text");
	public static final HvlTag<Float> TAG_SCALE = new HvlTag<>(Float.class, "scale");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> 
	DEFAULT_DRAW = (delta, environment, component) -> {
		component.get(TAG_FONT).draw(component.get(TAG_TEXT), environment.getX(), environment.getY(),
				component.get(TAG_COLOR), component.get(TAG_SCALE));
	};

	protected HvlLabel(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_FONT, TAG_TEXT, TAG_COLOR, TAG_SCALE));
		set(TAG_DRAW, DEFAULT_DRAW);
	}

	public HvlLabel(HvlFont fontArg, String textArg, Color colorArg, float scaleArg){
		this();
		set(TAG_FONT, fontArg);
		set(TAG_TEXT, textArg);
		set(TAG_COLOR, colorArg);
		set(TAG_SCALE, scaleArg);
	}

	@Override
	public <T> HvlLabel set(HvlTagTransient<T> tagArg, T valueArg){
		if(tagArg.equals(TAG_TEXT)){
			if(get(TAG_SCALE) != null){
				getEnvironment().setAndUnlockWidth(get(TAG_FONT).getWidth((String)valueArg, get(TAG_SCALE)));
				getEnvironment().setAndUnlockHeight(get(TAG_FONT).getHeight((String)valueArg, get(TAG_SCALE)));
			}else{
				getEnvironment().setAndUnlockWidth(get(TAG_FONT).getWidth((String)valueArg, 1.0f));
				getEnvironment().setAndUnlockHeight(get(TAG_FONT).getHeight((String)valueArg, 1.0f));
			}
		}else if(tagArg.equals(TAG_SCALE)){
			getEnvironment().setAndUnlockWidth(get(TAG_FONT).getWidth(get(TAG_TEXT), (float)valueArg));
			getEnvironment().setAndUnlockHeight(get(TAG_FONT).getHeight(get(TAG_TEXT), (float)valueArg));
		}
		return (HvlLabel)super.set(tagArg, valueArg);
	}

}
