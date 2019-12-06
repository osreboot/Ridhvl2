package com.osreboot.ridhvl2.menu.component;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlButton extends HvlComponent{
	private static final long serialVersionUID = 6029038729850694290L;

	public static final HvlButton fromDefault(){
		return HvlDefault.applyIfExists(HvlButton.class, new HvlButton());
	}

	public static enum HvlButtonState{
		OFF, HOVER, ON;

		private HvlButtonState(){}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A3<Float, HvlEnvironment, HvlComponent>>
	TAG_DRAW_OFF = new HvlTagTransient(HvlAction.A3.class, "draw_off"),
	TAG_DRAW_HOVER = new HvlTagTransient(HvlAction.A3.class, "draw_hover"),
	TAG_DRAW_ON = new HvlTagTransient(HvlAction.A3.class, "draw_on");

	public static final HvlTagTransient<HvlButtonState> TAG_STATE = new HvlTag<>(HvlButtonState.class, "state");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A1<HvlButton>>
	TAG_CLICKED = new HvlTagTransient(HvlAction.A1.class, "clicked");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {
		if(!environment.isBlocked()){
			//TODO implement HvlCursor here
			if(Mouse.getX() > environment.getX() && Mouse.getX() < environment.getX() + environment.getWidth() &&
					Display.getHeight() - Mouse.getY() > environment.getY() &&
					Display.getHeight() - Mouse.getY() < environment.getY() + environment.getHeight()){
				if(!Mouse.isButtonDown(0)){
					if(component.get(TAG_STATE) == HvlButtonState.ON)
						component.get(TAG_CLICKED).run((HvlButton)component);
					component.set(TAG_STATE, HvlButtonState.HOVER);
				}else component.set(TAG_STATE, HvlButtonState.ON);
			}else component.set(TAG_STATE, HvlButtonState.OFF);
		}else component.set(TAG_STATE, HvlButtonState.OFF);
	},
	DEFAULT_DRAW = (delta, environment, component) -> {
		if(component.get(TAG_STATE) == HvlButtonState.ON)
			component.get(TAG_DRAW_ON).run(delta, environment, component);
		else if(component.get(TAG_STATE) == HvlButtonState.HOVER && component.get(TAG_DRAW_HOVER) != null)
			component.get(TAG_DRAW_HOVER).run(delta, environment, component);
		else component.get(TAG_DRAW_OFF).run(delta, environment, component);
	};

	public static final HvlAction.A1<HvlButton>
	DEFAULT_CLICKED = (button) -> {
		HvlLogger.println("Default action triggered for button click!");
	};

	protected HvlButton(HvlTagTransient<?>... tags){
		super(accumulate(tags, 
				TAG_DRAW_OFF,
				TAG_DRAW_HOVER,
				TAG_DRAW_ON,
				TAG_STATE,
				TAG_CLICKED));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_DRAW_OFF, HvlComponent.DEFAULT_DRAW);
		set(TAG_DRAW_HOVER, null);
		set(TAG_DRAW_ON, HvlComponent.DEFAULT_DRAW);
		set(TAG_STATE, HvlButtonState.OFF);
		set(TAG_CLICKED, DEFAULT_CLICKED);
	}

	public HvlButton(HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOffArg,
			HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawHoverArg,
			HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOnArg,
			HvlAction.A1<HvlButton> clickedArg){
		this();
		HvlDefault.applyIfExists(HvlButton.class, this);
		set(TAG_DRAW_OFF, drawOffArg);
		set(TAG_DRAW_HOVER, drawHoverArg);
		set(TAG_DRAW_ON, drawOnArg);
		set(TAG_CLICKED, clickedArg);
	}

	public HvlButton(HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOffArg,
			HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOnArg,
			HvlAction.A1<HvlButton> clickedArg){
		this();
		HvlDefault.applyIfExists(HvlButton.class, this);
		set(TAG_DRAW_OFF, drawOffArg);
		set(TAG_DRAW_ON, drawOnArg);
		set(TAG_CLICKED, clickedArg);
	}

	public HvlButton(HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOffArg,
			HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawHoverArg,
			HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOnArg){
		this();
		HvlDefault.applyIfExists(HvlButton.class, this);
		set(TAG_DRAW_OFF, drawOffArg);
		set(TAG_DRAW_HOVER, drawHoverArg);
		set(TAG_DRAW_ON, drawOnArg);
	}

	public HvlButton(HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOffArg,
			HvlAction.A3<Float, HvlEnvironment, HvlComponent> drawOnArg){
		this();
		HvlDefault.applyIfExists(HvlButton.class, this);
		set(TAG_DRAW_OFF, drawOffArg);
		set(TAG_DRAW_ON, drawOnArg);
	}

}
