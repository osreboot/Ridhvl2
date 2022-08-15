package com.osreboot.ridhvl2.menu;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public abstract class HvlTaggableOpen extends HvlTaggable{
	private static final long serialVersionUID = 72496113562406391L;
	
	@JsonSerialize(using = TagMapSerializer.class)
	@JsonDeserialize(using = TagMapDeserializer.class)
	private HashMap<HvlTag<?>, Object> openProperties;
	private transient HashMap<HvlTagTransient<?>, Object> openPropertiesTransient;
	
	public HvlTaggableOpen(HvlTagTransient<?>... tags){
		super(tags);
		
		openProperties = new HashMap<>();
		openPropertiesTransient = new HashMap<>();
	}
	
	public <T> HvlTaggable addOpen(HvlTagTransient<T> tagArg){
		if(tagArg instanceof HvlTag<?>){
			if(openProperties.containsKey(tagArg))
				throw new TagExistsException();
			else openProperties.put((HvlTag<?>)tagArg, null);
		}else{
			if(openPropertiesTransient.containsKey(tagArg))
				throw new TagExistsException();
			else openPropertiesTransient.put(tagArg, null);
		}
		return this;
	}

	public <T> HvlTaggable setOpen(HvlTagTransient<T> tagArg, T valueArg){
		if(tagArg instanceof HvlTag<?>){
			if(!openProperties.containsKey(tagArg))
				throw new TagMismatchException();
			else openProperties.put((HvlTag<?>)tagArg, valueArg);
		}else{
			if(!openPropertiesTransient.containsKey(tagArg))
				throw new TagMismatchException();
			else openPropertiesTransient.put(tagArg, valueArg);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T getOpen(HvlTagTransient<T> tagArg){
		if(tagArg instanceof HvlTag<?>){
			if(!openProperties.containsKey(tagArg))
				throw new TagMismatchException();
			else return (T)openProperties.get(tagArg);
		}else{
			if(!openPropertiesTransient.containsKey(tagArg))
				throw new TagMismatchException();
			else return (T)openPropertiesTransient.get(tagArg);
		}
	}
	
	public Set<HvlTagTransient<?>> validTagsOpen(){
		HashSet<HvlTagTransient<?>> output = new HashSet<>(openProperties.keySet());
		output.addAll(openPropertiesTransient.keySet());
		return output;
	}
	
	public Set<HvlTagTransient<?>> validTagsAll(){
		HashSet<HvlTagTransient<?>> output = new HashSet<>(openProperties.keySet());
		output.addAll(openPropertiesTransient.keySet());
		output.addAll(validTags());
		return output;
	}
	
	public static class TagExistsException extends RuntimeException{
		private static final long serialVersionUID = 7288677452678608780L;
	}

}
