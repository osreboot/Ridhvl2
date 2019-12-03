package com.osreboot.ridhvl2.menu.component;

import static com.osreboot.ridhvl2.HvlStatics.hvlEnvironment;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlMath;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlArranger extends HvlContainer{
	private static final long serialVersionUID = 3570176741498793473L;

	public static final HvlTag<Boolean> TAG_HORIZONTAL = new HvlTag<>(Boolean.class, "horizontal");
	public static final HvlTag<Float> TAG_ALIGN_X = new HvlTag<>(Float.class, "align_x");
	public static final HvlTag<Float> TAG_ALIGN_Y = new HvlTag<>(Float.class, "align_y");

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
			float currentX = lockedCount > 0 ? environment.getX() : 
				HvlMath.map(component.get(TAG_ALIGN_X), 0, 1f, environment.getX(), environment.getX() + environment.getWidth() - unlockedSize);
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				float interpolatedY = HvlMath.map(child.getEnvironment().isHeightLocked() ? 0f : component.get(TAG_ALIGN_Y), 0, 1f, 
						environment.getY(), environment.getY() + environment.getHeight() - child.getEnvironment().getHeight()); //TODO replace with HvlMath.lerp
				child.update(delta, hvlEnvironment(currentX, interpolatedY, 
						child.getEnvironment().isWidthLocked() ? lockedSize : 0, environment.getHeight()));
				currentX += child.getEnvironment().getWidth();
			}
		}else{
			int lockedCount = 0;
			float unlockedSize = 0;
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				if(child.getEnvironment().isHeightLocked()){
					lockedCount++;
				}else unlockedSize += child.getEnvironment().getHeight();
			}

			float lockedSize = lockedCount > 0 ? (environment.getHeight() - unlockedSize)/lockedCount : 0;
			float currentY = lockedCount > 0 ? environment.getY() : 
				HvlMath.map(component.get(TAG_ALIGN_Y), 0, 1f, environment.getY(), environment.getY() + environment.getHeight() - unlockedSize);
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				float interpolatedX = HvlMath.map(child.getEnvironment().isWidthLocked() ? 0f : component.get(TAG_ALIGN_X), 0, 1f, 
						environment.getX(), environment.getX() + environment.getWidth() - child.getEnvironment().getWidth()); //TODO replace with HvlMath.lerp
				child.update(delta, hvlEnvironment(interpolatedX, currentY, 
						environment.getWidth(), child.getEnvironment().isHeightLocked() ? lockedSize : 0));
				currentY += child.getEnvironment().getHeight();
			}
		}
	};

	protected HvlArranger(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_HORIZONTAL, TAG_ALIGN_X, TAG_ALIGN_Y));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_HORIZONTAL, false);
		set(TAG_ALIGN_X, 0f);
		set(TAG_ALIGN_Y, 0f);
	}

	public HvlArranger(boolean horizontalArg){
		this();
		set(TAG_HORIZONTAL, horizontalArg);
	}

}
