package com.osreboot.ridhvl2.menu;

import java.io.Serializable;
import java.util.HashMap;

public abstract class HvlTaggable implements Serializable{
	private static final long serialVersionUID = -7986790903295832118L;
	
	private HashMap<HvlTag<?>, Object> properties;
	
	public HvlTaggable(HvlTag<?>... tags){
		properties = new HashMap<>();
		
		for(HvlTag<?> tag : tags)
			properties.put(tag, null);
	}
	
	public <T> void set(HvlTag<T> tagArg, T valueArg){
		if(!properties.containsKey(tagArg))
			throw new TagMismatchException();
		else properties.put(tagArg, valueArg);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(HvlTag<T> tagArg){
		if(!properties.containsKey(tagArg))
			throw new TagMismatchException();
		else return (T)properties.get(tagArg);
	}
	
	public static class TagMismatchException extends RuntimeException{
		private static final long serialVersionUID = -8599901093855663636L;
	}
	
}
