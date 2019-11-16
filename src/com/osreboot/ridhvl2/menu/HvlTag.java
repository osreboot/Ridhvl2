package com.osreboot.ridhvl2.menu;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HvlTag<T extends Serializable> extends HvlTagTransient<T> implements Serializable {
	private static final long serialVersionUID = 2701661974742349892L;

	private final String typeName;
	
	@JsonCreator
	public HvlTag(@JsonProperty("key") String keyArg, @JsonProperty("typeName") String typeNameArg){
		super(HvlType.getCachedForName(typeNameArg), keyArg);
		
		typeName = typeNameArg;
	}

	public HvlTag(Class<T> typeArg, String keyArg){
		super(typeArg, keyArg);
		
		typeName = typeArg.getName();
		HvlType.cacheForName(typeArg);
	}

	public String getTypeName(){
		return typeName;
	}

	@Override
	public boolean equals(Object arg){
		if(arg instanceof HvlTag<?>){
			return ((HvlTag<?>)arg).typeName.equals(typeName) &&
					((HvlTag<?>)arg).getKey().equals(getKey());
		}else return false;
	}

	@Override
	public int hashCode(){
		return Objects.hash(typeName, getKey());
	}

}
