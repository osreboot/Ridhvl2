package com.osreboot.ridhvl2.menu.component;

import java.util.ArrayList;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;
import com.osreboot.ridhvl2.template.HvlEnvironment;

public abstract class HvlContainer extends HvlComponent{
	private static final long serialVersionUID = -694629727484246566L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final HvlTag<ArrayList<HvlComponent>> TAG_CHILDREN = new HvlTag(ArrayList.class, "children");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> DEFAULT_UPDATE = (delta, environment, component) -> {
		for(HvlComponent child : component.get(TAG_CHILDREN))
			child.update(delta);
	};

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> DEFAULT_DRAW = (delta, environment, component) -> {
		for(HvlComponent child : component.get(TAG_CHILDREN))
			child.draw(delta);
	};

	protected HvlContainer(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_CHILDREN));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_CHILDREN, new ArrayList<>());
	}

}
