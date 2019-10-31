package com.osreboot.ridhvl2.menu;

import java.io.Serializable;
import java.util.Objects;

public class HvlTag<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 2701661974742349892L;

	private final String key, typeName;

	private transient final Class<T> type;

	public HvlTag(String keyArg, String typeNameArg){
		key = keyArg;
		typeName = typeNameArg;

		type = HvlType.getCachedForName(typeNameArg);
	}

	public HvlTag(Class<T> typeArg, String keyArg){
		key = keyArg;
		typeName = typeArg.getName();

		type = typeArg;
		HvlType.cacheForName(typeArg);
	}

	public String getTypeName(){
		return typeName;
	}

	public String getKey(){
		return key;
	}

	public Class<T> getType(){
		return type;
	}

	@Override
	public boolean equals(Object arg){
		if(arg instanceof HvlTag<?>){
			return ((HvlTag<?>)arg).typeName.equals(typeName) &&
					((HvlTag<?>)arg).key.equals(key);
		}else return false;
	}

	@Override
	public int hashCode(){
		return Objects.hash(typeName, key);
	}

}
