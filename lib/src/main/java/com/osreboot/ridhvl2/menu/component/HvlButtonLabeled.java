package com.osreboot.ridhvl2.menu.component;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlColor;
import com.osreboot.ridhvl2.HvlMath;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlFont;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlButtonLabeled extends HvlButton{
	private static final long serialVersionUID = -220791134661836894L;

	public static HvlButtonLabeled fromDefault(){
		return (HvlButtonLabeled)HvlDefault.applyIfExists(HvlButtonLabeled.class, new HvlButtonLabeled());
	}

	public static final HvlTagTransient<HvlFont> TAG_FONT = new HvlTagTransient<>(HvlFont.class, "font");
	public static final HvlTagTransient<HvlColor> TAG_TEXT_COLOR = new HvlTagTransient<>(HvlColor.class, "text_color");

	public static final HvlTag<String> TAG_TEXT = new HvlTag<>(String.class, "text");
	public static final HvlTag<Float> TAG_TEXT_SCALE = new HvlTag<>(Float.class, "text_scale");
	public static final HvlTag<Float> TAG_TEXT_ALIGN_X = new HvlTag<>(Float.class, "text_align_x");
	public static final HvlTag<Float> TAG_TEXT_ALIGN_Y = new HvlTag<>(Float.class, "text_align_y");
	public static final HvlTag<Float> TAG_TEXT_OFFSET_X = new HvlTag<>(Float.class, "text_offset_x");
	public static final HvlTag<Float> TAG_TEXT_OFFSET_Y = new HvlTag<>(Float.class, "text_offset_y");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_TEXT_DRAW = (delta, environment, component) -> {
		float x = HvlMath.map(component.get(TAG_TEXT_ALIGN_X), 0f, 1f, environment.getX(),
				environment.getX() + environment.getWidth() - component.get(TAG_FONT).getWidth(component.get(TAG_TEXT), component.get(TAG_TEXT_SCALE)));
		float y = HvlMath.map(component.get(TAG_TEXT_ALIGN_Y), 0f, 1f, environment.getY(),
				environment.getY() + environment.getHeight() - component.get(TAG_FONT).getHeight(component.get(TAG_TEXT), component.get(TAG_TEXT_SCALE)));
		component.get(TAG_FONT).draw(component.get(TAG_TEXT), x + component.get(TAG_TEXT_OFFSET_X), y + component.get(TAG_TEXT_OFFSET_Y),
				component.get(TAG_TEXT_COLOR), component.get(TAG_TEXT_SCALE));
	},
	DEFAULT_DRAW = (delta, environment, component) -> {
		HvlButton.DEFAULT_DRAW.run(delta, environment, component);
		DEFAULT_TEXT_DRAW.run(delta, environment, component);
	},
	DEFAULT_DRAW_NOTEXT = HvlButton.DEFAULT_DRAW;

	protected HvlButtonLabeled(HvlTagTransient<?>... tags){
		super(accumulate(tags,
				TAG_FONT,
				TAG_TEXT_COLOR,
				TAG_TEXT,
				TAG_TEXT_SCALE,
				TAG_TEXT_ALIGN_X,
				TAG_TEXT_ALIGN_Y,
				TAG_TEXT_OFFSET_X,
				TAG_TEXT_OFFSET_Y));
		set(TAG_DRAW, DEFAULT_DRAW);
	}

	public HvlButtonLabeled(HvlFont fontArg, String textArg, HvlColor colorArg, float scaleArg,
			HvlAction.A4<Float, HvlEnvironment, HvlButton, HvlButtonState> drawStateArg,
			HvlAction.A1<HvlButton> clickedArg){
		this();
		HvlDefault.applyIfExists(HvlButtonLabeled.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
		set(TAG_CLICKED, clickedArg);
		set(TAG_FONT, fontArg);
		set(TAG_TEXT, textArg);
		set(TAG_TEXT_COLOR, colorArg);
		set(TAG_TEXT_SCALE, scaleArg);
		set(TAG_TEXT_ALIGN_X, 0f);
		set(TAG_TEXT_ALIGN_Y, 0f);
		set(TAG_TEXT_OFFSET_X, 0f);
		set(TAG_TEXT_OFFSET_Y, 0f);
	}

	public HvlButtonLabeled(HvlFont fontArg, String textArg, HvlColor colorArg, float scaleArg,
			HvlAction.A4<Float, HvlEnvironment, HvlButton, HvlButtonState> drawStateArg){
		this();
		HvlDefault.applyIfExists(HvlButtonLabeled.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
		set(TAG_FONT, fontArg);
		set(TAG_TEXT, textArg);
		set(TAG_TEXT_COLOR, colorArg);
		set(TAG_TEXT_SCALE, scaleArg);
		set(TAG_TEXT_ALIGN_X, 0f);
		set(TAG_TEXT_ALIGN_Y, 0f);
		set(TAG_TEXT_OFFSET_X, 0f);
		set(TAG_TEXT_OFFSET_Y, 0f);
	}

	public HvlButtonLabeled text(String textArg){
		return (HvlButtonLabeled)set(TAG_TEXT, textArg);
	}

	public String getText(){
		return get(TAG_TEXT);
	}
	
	public HvlButtonLabeled align(float xAlignArg, float yAlignArg){
		return (HvlButtonLabeled)set(TAG_TEXT_ALIGN_X, xAlignArg)
				.set(TAG_TEXT_ALIGN_Y, yAlignArg);
	}
	
	public HvlButtonLabeled offsetX(float xOffsetArg){
		return (HvlButtonLabeled)set(TAG_TEXT_OFFSET_X, xOffsetArg);
	}
	
	public HvlButtonLabeled offsetY(float yOffsetArg){
		return (HvlButtonLabeled)set(TAG_TEXT_OFFSET_Y, yOffsetArg);
	}
	
	public HvlButtonLabeled offset(float xOffsetArg, float yOffsetArg){
		return (HvlButtonLabeled)set(TAG_TEXT_OFFSET_X, xOffsetArg)
				.set(TAG_TEXT_OFFSET_Y, yOffsetArg);
	}
	
	public HvlButtonLabeled scale(float scaleArg){
		return (HvlButtonLabeled)set(TAG_TEXT_SCALE, scaleArg);
	}
	
	public HvlButtonLabeled autoSize(){
		return (HvlButtonLabeled)overrideWidth(get(TAG_FONT).getWidth(getText(), get(TAG_TEXT_SCALE)))
				.overrideHeight(get(TAG_FONT).getHeight(getText(), get(TAG_TEXT_SCALE)));
	}

}
