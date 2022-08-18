package com.osreboot.ridhvl2.menu.component;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;
import com.osreboot.ridhvl2.menu.component.HvlButton.HvlButtonState;
import com.osreboot.ridhvl2.template.HvlMouse;

public class HvlCheckbox extends HvlComponent{
	private static final long serialVersionUID = 6029038729850694290L;

	public static HvlCheckbox fromDefault(){
		return HvlDefault.applyIfExists(HvlCheckbox.class, new HvlCheckbox());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A5<Float, HvlEnvironment, HvlCheckbox, HvlButtonState, Boolean>>
	TAG_DRAW_STATE = new HvlTagTransient(HvlAction.A5.class, "draw_state");

	public static final HvlTagTransient<HvlButtonState> TAG_STATE = new HvlTag<>(HvlButtonState.class, "state");
	public static final HvlTagTransient<Boolean> TAG_ACTIVE = new HvlTag<>(Boolean.class, "active");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A1<HvlCheckbox>>
	TAG_CLICKED = new HvlTagTransient(HvlAction.A1.class, "clicked");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {
		if(!environment.isBlocked()){
			//TODO implement HvlCursor here
			if(HvlMouse.getX() > environment.getX() && HvlMouse.getX() < environment.getX() + environment.getWidth() &&
					HvlMouse.getY() > environment.getY() && HvlMouse.getY() < environment.getY() + environment.getHeight()){
				if(!HvlMouse.isButtonDown(HvlMouse.BUTTON_LEFT)){
					if(component.get(TAG_STATE) == HvlButtonState.ON)
						component.set(TAG_ACTIVE, !component.get(TAG_ACTIVE));
						component.get(TAG_CLICKED).run((HvlCheckbox)component);
					component.set(TAG_STATE, HvlButtonState.HOVER);
				}else component.set(TAG_STATE, HvlButtonState.ON);
			}else component.set(TAG_STATE, HvlButtonState.OFF);
		}else component.set(TAG_STATE, HvlButtonState.OFF);
	},
	DEFAULT_DRAW = (delta, environment, component) -> {
		component.get(TAG_DRAW_STATE).run(delta, environment,
				(HvlCheckbox)component, ((HvlCheckbox)component).getState(), ((HvlCheckbox)component).isActive());
	};

	public static final HvlAction.A1<HvlCheckbox>
	DEFAULT_CLICKED = (button) -> {};

	protected HvlCheckbox(HvlTagTransient<?>... tags){
		super(accumulate(tags, 
				TAG_DRAW_STATE,
				TAG_STATE,
				TAG_ACTIVE,
				TAG_CLICKED));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_DRAW_STATE, null);
		set(TAG_STATE, HvlButtonState.OFF);
		set(TAG_ACTIVE, false);
		set(TAG_CLICKED, DEFAULT_CLICKED);
	}

	public HvlCheckbox(HvlAction.A5<Float, HvlEnvironment, HvlCheckbox, HvlButtonState, Boolean> drawStateArg,
			HvlAction.A1<HvlCheckbox> clickedArg){
		this();
		HvlDefault.applyIfExists(HvlCheckbox.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
		set(TAG_CLICKED, clickedArg);
	}

	public HvlCheckbox(HvlAction.A5<Float, HvlEnvironment, HvlCheckbox, HvlButtonState, Boolean> drawStateArg){
		this();
		HvlDefault.applyIfExists(HvlCheckbox.class, this);
		set(TAG_DRAW_STATE, drawStateArg);
	}

	public HvlButtonState getState(){
		return get(TAG_STATE);
	}
	
	public HvlCheckbox active(boolean activeArg){
		return (HvlCheckbox)set(TAG_ACTIVE, activeArg);
	}
	
	public boolean isActive(){
		return get(TAG_ACTIVE);
	}
	
	public HvlCheckbox clicked(HvlAction.A1<HvlCheckbox> clickedArg){
		return (HvlCheckbox)set(TAG_CLICKED, clickedArg);
	}

}
