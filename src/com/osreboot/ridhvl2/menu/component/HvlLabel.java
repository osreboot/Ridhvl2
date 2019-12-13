package com.osreboot.ridhvl2.menu.component;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlMath;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlFont;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlLabel extends HvlComponent{
	private static final long serialVersionUID = 6541669450721020192L;

	public static HvlLabel fromDefault(){
		return HvlDefault.applyIfExists(HvlLabel.class, new HvlLabel());
	}

	public static final HvlTagTransient<HvlFont> TAG_FONT = new HvlTagTransient<>(HvlFont.class, "font");
	public static final HvlTagTransient<Color> TAG_COLOR = new HvlTagTransient<>(Color.class, "color");

	public static final HvlTag<String> TAG_TEXT = new HvlTag<>(String.class, "text");
	public static final HvlTag<Float> TAG_SCALE = new HvlTag<>(Float.class, "scale");
	public static final HvlTag<Float> TAG_ALIGN_X = new HvlTag<>(Float.class, "align_x");
	public static final HvlTag<Float> TAG_ALIGN_Y = new HvlTag<>(Float.class, "align_y");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> 
	DEFAULT_DRAW = (delta, environment, component) -> {
		float x = HvlMath.map(component.get(TAG_ALIGN_X), 0f, 1f, environment.getX(),
				environment.getX() + environment.getWidth() - component.get(TAG_FONT).getWidth(component.get(TAG_TEXT), component.get(TAG_SCALE)));
		float y = HvlMath.map(component.get(TAG_ALIGN_Y), 0f, 1f, environment.getY(),
				environment.getY() + environment.getHeight() - component.get(TAG_FONT).getHeight(component.get(TAG_TEXT), component.get(TAG_SCALE)));
		component.get(TAG_FONT).draw(component.get(TAG_TEXT), x, y, component.get(TAG_COLOR), component.get(TAG_SCALE));
	};

	protected HvlLabel(HvlTagTransient<?>... tags){
		super(accumulate(tags,
				TAG_FONT,
				TAG_TEXT,
				TAG_COLOR,
				TAG_SCALE,
				TAG_ALIGN_X,
				TAG_ALIGN_Y));
		set(TAG_DRAW, DEFAULT_DRAW);
	}

	public HvlLabel(HvlFont fontArg, String textArg, Color colorArg, float scaleArg){
		this();
		HvlDefault.applyIfExists(HvlLabel.class, this);
		set(TAG_FONT, fontArg);
		set(TAG_TEXT, textArg);
		set(TAG_COLOR, colorArg);
		set(TAG_SCALE, scaleArg);
		set(TAG_ALIGN_X, 0f);
		set(TAG_ALIGN_Y, 0f);
	}
	
	public HvlLabel text(String textArg){
		return (HvlLabel)set(TAG_TEXT, textArg);
	}
	
	public String getText(){
		return get(TAG_TEXT);
	}
	
	public HvlLabel align(float xAlignArg, float yAlignArg){
		return (HvlLabel)set(TAG_ALIGN_X, xAlignArg)
				.set(TAG_ALIGN_Y, yAlignArg);
	}

}
