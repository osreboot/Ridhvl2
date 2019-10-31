package com.osreboot.ridhvl2.menu;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class HvlTaggable implements Serializable{
	private static final long serialVersionUID = -7986790903295832118L;

	private HashMap<HvlTag<?>, Object> properties;
	private transient HashSet<HvlTag<?>> dirtyTags;

	public HvlTaggable(HvlTag<?>... tags){
		properties = new HashMap<>();

		for(HvlTag<?> tag : tags)
			properties.put(tag, null);
	}

	public <T extends Serializable> void set(HvlTag<T> tagArg, T valueArg){
		if(!properties.containsKey(tagArg))
			throw new TagMismatchException();
		else properties.put(tagArg, valueArg);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T get(HvlTag<T> tagArg){
		if(!properties.containsKey(tagArg))
			throw new TagMismatchException();
		else{
			// Fix for Gson automatically casting values from ambiguous "Object" fields
			if(dirtyTags == null)
				recastTags();
			if(dirtyTags.contains(tagArg)){
				if(HvlType.hasRecast(tagArg.getTypeName()))
					properties.put(tagArg, HvlType.recast(tagArg.getTypeName(), properties.get(tagArg)));
				dirtyTags.remove(tagArg);
			}
			
			return (T)properties.get(tagArg);
		}
	}

	public Set<HvlTag<?>> validTags(){
		return properties.keySet();
	}

	private void recastTags(){
		dirtyTags = new HashSet<>();
		for(HvlTag<?> tag : properties.keySet())
			dirtyTags.add(tag);
	}
	
	public static class TagMismatchException extends RuntimeException{
		private static final long serialVersionUID = -8599901093855663636L;
	}

}
