package com.osreboot.ridhvl2.menu.component;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;
import com.osreboot.ridhvl2.template.HvlMouse;

public class HvlButton extends HvlComponent{
	private static final long serialVersionUID = 6029038729850694290L;

	public static HvlButton fromDefault(){
		return HvlDefault.applyIfExists(HvlButton.class, new HvlButton());
	}

	public static enum HvlButtonState{
		OFF, HOVER, ON;

		private HvlButtonState(){}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A4<Float, HvlEnvironment, HvlButton, HvlButtonState>>
	TAG_DRAW_STATE = new HvlTagTransient(HvlAction.A4.class, "draw_state");

	public static final HvlTagTransient<HvlButtonState> TAG_STATE = new HvlTag<>(HvlButtonState.class, "state");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A1<HvlButton>>
	TAG_CLICKED = new HvlTagTransient(HvlAction.A1.class, "clicked");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {
		if(!environment.isBlocked()){
			//TODO implement HvlCursor here
			if(HvlMouse.getX() > environment.getX() && HvlMouse.getX() < environment.getX() + environment.getWidth() &&
					HvlMouse.getY() > environment.getY() && HvlMouse.getY() < environment.getY() + environment.getHeight()){
				if(!HvlMouse.isButtonDown(HvlMouse.BUTTON_LEFT)){
					if(component.get(TAG_STATE) == HvlButtonState.ON)
						component.get(TAG_CLICKED).run((HvlButton)component);
					component.set(TAG_STATE, HvlButtonState.HOVER);
				}else component.set(TAG_STATE, HvlButtonState.ON);
			}else component.set(TAG_STATE, HvlButtonState.OFF);
		}else component.set(TAG_STATE, HvlButtonState.OFF);
	},
	DEFAULT_DRAW = (delta, environment, component) -> {
		component.get(TAG_DRAW_STATE).run(delta, environment,
				(HvlButton)component, ((HvlButton)component).getState());
	};

	public static final HvlAction.A1<HvlButton>
	DEFAULT_CLICKED = (button) -> {
		HvlLogger.println("Default action triggered for button click!");
	};

	protected HvlButton(HvlTagTransient<?>... tags){
		super(accumulate(tags, 
				TAG_DRAW_STATE,
				TAG_STATE,
				TAG_CLICKED));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_DRAW_STATE, null);
		set(TAG_STATE, HvlButtonState.OFF);
		set(TAG_CLICKED, DEFAULT_CLICKED);
	}

	public HvlButton(HvlAction.A4<Float, HvlEnvironment, HvlButton, HvlButtonState> drawStateArg,
			HvlAction.A1<HvlButton> clickedArg){
		this();
		HvlDefault.applyIfExists(HvlButton.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
		set(TAG_CLICKED, clickedArg);
	}

	public HvlButton(HvlAction.A4<Float, HvlEnvironment, HvlButton, HvlButtonState> drawStateArg){
		this();
		HvlDefault.applyIfExists(HvlButton.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
	}

	public HvlButtonState getState(){
		return get(TAG_STATE);
	}
	
	public HvlButton clicked(HvlAction.A1<HvlButton> clickedArg){
		return (HvlButton)set(TAG_CLICKED, clickedArg);
	}

}
