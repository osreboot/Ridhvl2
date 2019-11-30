package com.osreboot.ridhvl2.menu.component;

import static com.osreboot.ridhvl2.HvlStatics.hvlEnvironment;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;
import com.osreboot.ridhvl2.template.HvlEnvironment;

public class HvlArranger extends HvlContainer{
	private static final long serialVersionUID = 3570176741498793473L;

	public static final HvlTag<Boolean> TAG_HORIZONTAL = new HvlTag<>(Boolean.class, "horizontal");
	public static final HvlTag<Float> TAG_ALIGN = new HvlTag<>(Float.class, "align");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> 
	DEFAULT_UPDATE = (delta, environment, component) -> {
		if(component.get(TAG_HORIZONTAL)){
			int lockedCount = 0;
			float unlockedSize = 0;
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				if(child.getEnvironment().isWidthLocked()){
					lockedCount++;
				}else unlockedSize += child.getEnvironment().getWidth();
			}

			float lockedSize = lockedCount > 0 ? (environment.getWidth() - unlockedSize)/lockedCount : 0;
			float currentX = environment.getX(); //TODO factor in align here
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				child.update(delta, hvlEnvironment(currentX, environment.getY(), 
						child.getEnvironment().isWidthLocked() ? lockedSize : 0, environment.getHeight()));
				currentX += child.getEnvironment().getWidth();
			}
		}else{

		}
	};

	protected HvlArranger(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_HORIZONTAL, TAG_ALIGN));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_HORIZONTAL, false);
		set(TAG_ALIGN, 0f);
	}

	public HvlArranger(boolean horizontal){
		this();
		set(TAG_HORIZONTAL, horizontal);
	}

}
