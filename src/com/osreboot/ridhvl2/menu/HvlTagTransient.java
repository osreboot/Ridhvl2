package com.osreboot.ridhvl2.menu;

import java.util.Objects;

public class HvlTagTransient<T> {
	
	private final String key;

	private transient final Class<T> type;

	public HvlTagTransient(Class<T> typeArg, String keyArg){
		key = keyArg;
		type = typeArg;
	}

	public String getKey(){
		return key;
	}

	public Class<T> getType(){
		return type;
	}

	@Override
	public boolean equals(Object arg){
		if(arg instanceof HvlTagTransient<?>){
			return ((HvlTagTransient<?>)arg).key.equals(key);
		}else return false;
	}

	@Override
	public int hashCode(){
		return Objects.hash(key);
	}

}
