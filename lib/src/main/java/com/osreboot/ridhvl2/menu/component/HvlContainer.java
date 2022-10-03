package com.osreboot.ridhvl2.menu.component;

import java.util.ArrayList;
import java.util.List;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.menu.HvlComponent;
import com.osreboot.ridhvl2.menu.HvlEnvironment;
import com.osreboot.ridhvl2.menu.HvlTag;
import com.osreboot.ridhvl2.menu.HvlTagTransient;

public abstract class HvlContainer extends HvlComponent{
	private static final long serialVersionUID = -694629727484246566L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final HvlTag<ArrayList<HvlComponent>> TAG_CHILDREN = new HvlTag(ArrayList.class, "children");

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_UPDATE = (delta, environment, component) -> {
		for(HvlComponent child : component.get(TAG_CHILDREN))
			child.update(delta);
	};

	public static final HvlAction.A3<Float, HvlEnvironment, HvlComponent>
	DEFAULT_DRAW = (delta, environment, component) -> {
		for(HvlComponent child : component.get(TAG_CHILDREN))
			child.draw(delta);
	};

	protected HvlContainer(HvlTagTransient<?>... tags){
		super(accumulate(tags, TAG_CHILDREN));
		set(TAG_UPDATE, DEFAULT_UPDATE);
		set(TAG_DRAW, DEFAULT_DRAW);
		set(TAG_CHILDREN, new ArrayList<>());
	}

	public HvlComponent add(HvlComponent childArg){
		get(TAG_CHILDREN).add(childArg);
		return this;
	}

	public HvlComponent get(String nameArg){
		for(HvlComponent child : get(TAG_CHILDREN)){
			if(child.getName().equals(nameArg))
				return child;
		}
		return null;
	}

	public HvlComponent get(int indexArg){
		return get(TAG_CHILDREN).get(indexArg);
	}

	public void remove(String nameArg){
		get(TAG_CHILDREN).remove(get(nameArg));
	}

	public void remove(int indexArg){
		get(TAG_CHILDREN).remove(indexArg);
	}

	@SuppressWarnings("unchecked")
	public <T extends HvlComponent> T find(String nameArg){
		if(getName().equals(nameArg)) return (T)this;
		for(HvlComponent child : get(TAG_CHILDREN)){
			if(child.getName().equals(nameArg))
				return (T)child;
		}
		for(HvlComponent child : get(TAG_CHILDREN)){
			if(child instanceof HvlContainer){
				HvlComponent searchResult = ((HvlContainer)child).find(nameArg);
				if(searchResult != null) return (T)searchResult;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends HvlComponent> List<T> findAll(String nameArg){
		ArrayList<T> output = new ArrayList<>();
		for(HvlComponent child : get(TAG_CHILDREN)){
			if(child.getName().equals(nameArg))
				output.add((T)child);
		}
		for(HvlComponent child : get(TAG_CHILDREN)){
			if(child instanceof HvlContainer){
				List<T> searchResult = ((HvlContainer)child).findAll(nameArg);
				output.addAll(searchResult);
			}
		}
		return output;
	}

}
