package com.osreboot.ridhvl2.menu.component;

import static com.osreboot.ridhvl2.HvlStatics.hvlEnvironment;

import java.util.ArrayList;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlMath;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlDefault;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public class HvlArranger extends HvlContainer{
	private static final long serialVersionUID = 3570176741498793473L;

	public static HvlArranger fromDefault(){
		return (HvlArranger)HvlDefault.applyIfExists(HvlArranger.class, new HvlArranger())
				.set(TAG_CHILDREN, new ArrayList<>());
	}

	public static final HvlTag<Boolean> TAG_HORIZONTAL = new HvlTag<>(Boolean.class, "horizontal");
	public static final HvlTag<Float> TAG_ALIGN_X = new HvlTag<>(Float.class, "align_x");
	public static final HvlTag<Float> TAG_ALIGN_Y = new HvlTag<>(Float.class, "align_y");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent> 
	DEFAULT_UPDATE = (delta, environment, component) -> {
		//TODO refactor this function to use 'native' and 'override' terminology instead of 'locked' and 'unlocked'
		if(component.get(TAG_HORIZONTAL)){
			int lockedCount = 0;
			float unlockedSize = 0;
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				if(child.get(TAG_OVERRIDE_WIDTH) == null){
					lockedCount++;
				}else unlockedSize += child.get(TAG_OVERRIDE_WIDTH);
			}

			float lockedSize = lockedCount > 0 ? (environment.getWidth() - unlockedSize)/lockedCount : 0;
			float currentX = lockedCount > 0 ? environment.getX() : 
				HvlMath.map(component.get(TAG_ALIGN_X), 0, 1f, environment.getX(), environment.getX() + environment.getWidth() - unlockedSize);
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				float interpolatedY = child.get(TAG_OVERRIDE_HEIGHT) == null ? environment.getY() : 
					HvlMath.map(component.get(TAG_ALIGN_Y), 0, 1f, 
							environment.getY(), environment.getY() + environment.getHeight() - child.get(TAG_OVERRIDE_HEIGHT)); //TODO replace with HvlMath.lerp
				child.update(delta, hvlEnvironment(currentX, interpolatedY, 
						child.get(TAG_OVERRIDE_WIDTH) == null ? lockedSize : 0, environment.getHeight(), environment.isBlocked()));
				currentX += child.getLastEnvironment().getWidth();
			}
		}else{
			int lockedCount = 0;
			float unlockedSize = 0;
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				if(child.get(TAG_OVERRIDE_HEIGHT) == null){
					lockedCount++;
				}else unlockedSize += child.get(TAG_OVERRIDE_HEIGHT);
			}

			float lockedSize = lockedCount > 0 ? (environment.getHeight() - unlockedSize)/lockedCount : 0;
			float currentY = lockedCount > 0 ? environment.getY() : 
				HvlMath.map(component.get(TAG_ALIGN_Y), 0, 1f, environment.getY(), environment.getY() + environment.getHeight() - unlockedSize);
			for(HvlComponent child : component.get(TAG_CHILDREN)){
				float interpolatedX = child.get(TAG_OVERRIDE_WIDTH) == null ? environment.getX() : 
					HvlMath.map(component.get(TAG_ALIGN_X), 0, 1f,
							environment.getX(), environment.getX() + environment.getWidth() - child.get(TAG_OVERRIDE_WIDTH)); //TODO replace with HvlMath.lerp
				child.update(delta, hvlEnvironment(interpolatedX, currentY, 
						environment.getWidth(), child.get(TAG_OVERRIDE_HEIGHT) == null ? lockedSize : 0, environment.isBlocked()));
				currentY += child.getLastEnvironment().getHeight();
			}
		}
	};

	protected HvlArranger(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_HORIZONTAL, TAG_ALIGN_X, TAG_ALIGN_Y));
		set(TAG_CHILDREN, new ArrayList<>());
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_HORIZONTAL, false);
		set(TAG_ALIGN_X, 0f);
		set(TAG_ALIGN_Y, 0f);
	}

	public HvlArranger(boolean horizontalArg, float xAlignArg, float yAlignArg){
		this();
		HvlDefault.applyIfExists(HvlArranger.class, this);
		set(TAG_CHILDREN, new ArrayList<>());
		set(TAG_HORIZONTAL, horizontalArg);
		set(TAG_ALIGN_X, xAlignArg);
		set(TAG_ALIGN_Y, yAlignArg);
	}

	public HvlArranger(boolean horizontalArg){
		this();
		HvlDefault.applyIfExists(HvlArranger.class, this);
		set(TAG_CHILDREN, new ArrayList<>());
		set(TAG_HORIZONTAL, horizontalArg);
	}

	public HvlArranger align(float xAlignArg, float yAlignArg){
		return (HvlArranger)set(TAG_ALIGN_X, xAlignArg)
				.set(TAG_ALIGN_Y, yAlignArg);
	}

	public HvlArranger horizontal(boolean horizontalArg){
		return (HvlArranger)set(TAG_HORIZONTAL, horizontalArg);
	}

}
