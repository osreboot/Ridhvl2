package com.osreboot.ridhvl2.menu;

import java.io.Serializable;

public class HvlTag<T> implements Serializable {
	private static final long serialVersionUID = 2701661974742349892L;
	
	private final Class<T> type;
	private final String key;
	
	public HvlTag(Class<T> typeArg, String keyArg){
		type = typeArg;
		key = keyArg;
	}

	public Class<T> getType(){
		return type;
	}

	public String getKey(){
		return key;
	}
	
}
