package com.osreboot.ridhvl2.menu;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.template.HvlDisplay;

public abstract class HvlComponent extends HvlTaggableOpen{
	private static final long serialVersionUID = 906931523772672751L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final HvlTagTransient<HvlAction.A3<Float, HvlEnvironment, HvlComponent>>
	TAG_UPDATE = new HvlTagTransient(HvlAction.A3.class, "update"),
	TAG_DRAW = new HvlTagTransient(HvlAction.A3.class, "draw");

	public static final HvlTag<String> TAG_NAME = new HvlTag<>(String.class, "name");
	public static final HvlTag<Float> TAG_OVERRIDE_X = new HvlTag<>(Float.class, "override_x");
	public static final HvlTag<Float> TAG_OVERRIDE_Y = new HvlTag<>(Float.class, "override_y");
	public static final HvlTag<Float> TAG_OVERRIDE_WIDTH = new HvlTag<>(Float.class, "override_width");
	public static final HvlTag<Float> TAG_OVERRIDE_HEIGHT = new HvlTag<>(Float.class, "override_height");
	public static final HvlTag<Boolean> TAG_OVERRIDE_BLOCKED = new HvlTag<>(Boolean.class, "override_blocked");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {},
	DEFAULT_DRAW = (delta, environment, component) -> {};

	private transient final HvlEnvironment environment;

	protected HvlComponent(HvlTagTransient<?>... tags){
		super(accumulate(tags,
				TAG_UPDATE,
				TAG_DRAW,
				TAG_NAME,
				TAG_OVERRIDE_X,
				TAG_OVERRIDE_Y,
				TAG_OVERRIDE_WIDTH,
				TAG_OVERRIDE_HEIGHT,
				TAG_OVERRIDE_BLOCKED));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_NAME, "");
		set(TAG_OVERRIDE_X, null);
		set(TAG_OVERRIDE_Y, null);
		set(TAG_OVERRIDE_WIDTH, null);
		set(TAG_OVERRIDE_HEIGHT, null);
		set(TAG_OVERRIDE_BLOCKED, null);
		environment = new HvlEnvironment();
	}

	public final void update(float delta){
		environment.forceMutate(HvlDisplay.getDisplay().getEnvironment());
		if(get(TAG_OVERRIDE_X) != null) environment.forceMutateX(get(TAG_OVERRIDE_X));
		if(get(TAG_OVERRIDE_Y) != null) environment.forceMutateY(get(TAG_OVERRIDE_Y));
		if(get(TAG_OVERRIDE_WIDTH) != null) environment.forceMutateWidth(get(TAG_OVERRIDE_WIDTH));
		if(get(TAG_OVERRIDE_HEIGHT) != null) environment.forceMutateHeight(get(TAG_OVERRIDE_HEIGHT));
		if(get(TAG_OVERRIDE_BLOCKED) != null) environment.forceMutateBlocked(get(TAG_OVERRIDE_BLOCKED));

		get(TAG_UPDATE).run(delta, environment, this);
	}

	public final void update(float delta, HvlEnvironment environmentArg){
		environment.forceMutate(environmentArg);
		if(get(TAG_OVERRIDE_X) != null) environment.forceMutateX(get(TAG_OVERRIDE_X));
		if(get(TAG_OVERRIDE_Y) != null) environment.forceMutateY(get(TAG_OVERRIDE_Y));
		if(get(TAG_OVERRIDE_WIDTH) != null) environment.forceMutateWidth(get(TAG_OVERRIDE_WIDTH));
		if(get(TAG_OVERRIDE_HEIGHT) != null) environment.forceMutateHeight(get(TAG_OVERRIDE_HEIGHT));
		if(get(TAG_OVERRIDE_BLOCKED) != null) environment.forceMutateBlocked(get(TAG_OVERRIDE_BLOCKED));

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

	public final HvlEnvironment getLastEnvironment(){
		return environment;
	}

	public HvlComponent name(String nameArg){
		return set(TAG_NAME, nameArg);
	}

	public String getName(){
		return get(TAG_NAME);
	}

	public HvlComponent overrideX(float xArg){
		return set(TAG_OVERRIDE_X, xArg);
	}

	public HvlComponent overrideY(float yArg){
		return set(TAG_OVERRIDE_Y, yArg);
	}

	public HvlComponent overridePosition(float xArg, float yArg){
		return set(TAG_OVERRIDE_X, xArg)
				.set(TAG_OVERRIDE_Y, yArg);
	}

	public HvlComponent overrideWidth(float widthArg){
		return set(TAG_OVERRIDE_WIDTH, widthArg);
	}

	public HvlComponent overrideHeight(float heightArg){
		return set(TAG_OVERRIDE_HEIGHT, heightArg);
	}

	public HvlComponent overrideSize(float widthArg, float heightArg){
		return set(TAG_OVERRIDE_WIDTH, widthArg)
				.set(TAG_OVERRIDE_HEIGHT, heightArg);
	}

}
