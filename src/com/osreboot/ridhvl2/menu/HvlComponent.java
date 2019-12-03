package com.osreboot.ridhvl2.menu;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.template.HvlDisplay;

public abstract class HvlComponent extends HvlTaggableOpen{
	private static final long serialVersionUID = 906931523772672751L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A3<Float, HvlEnvironment, HvlComponent>>
	TAG_UPDATE = new HvlTagTransient(HvlAction.A3.class, "update"),
	TAG_DRAW = new HvlTagTransient(HvlAction.A3.class, "draw");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {},
	DEFAULT_DRAW = (delta, environment, component) -> {};

	private transient final HvlEnvironment environment;

	protected HvlComponent(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_UPDATE, TAG_DRAW));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		environment = new HvlEnvironment();
	}

	public final void update(float delta){
		environment.copyFrom(HvlDisplay.getDisplay().getEnvironment());
		environment.setRestricted(true);
		
		get(TAG_UPDATE).run(delta, environment, this);
	}

	public final void update(float delta, HvlEnvironment environmentArg){
		environment.copyFrom(environmentArg);
		environment.setRestricted(true);
		
		get(TAG_UPDATE).run(delta, environment, this);
	}

	public final void draw(float delta){
		get(TAG_DRAW).run(delta, environment, this);
	}

	public final void operate(float delta){
		update(delta);
		draw(delta);
	}

	public final void operate(float delta, HvlEnvironment environmentArg){
		update(delta, environmentArg);
		draw(delta);
	}

	public HvlEnvironment getEnvironment(){
		return environment;
	}

	@Override
	public <T> HvlComponent set(HvlTagTransient<T> tagArg, T valueArg){
		return (HvlComponent)super.set(tagArg, valueArg);
	}

	@Override
	public <T> HvlComponent addOpen(HvlTagTransient<T> tagArg){
		return (HvlComponent)super.addOpen(tagArg);
	}

	@Override
	public <T> HvlComponent setOpen(HvlTagTransient<T> tagArg, T valueArg){
		return (HvlComponent)super.setOpen(tagArg, valueArg);
	}

}
