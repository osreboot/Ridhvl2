package com.osreboot.ridhvl2.menu;

import java.util.HashMap;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;

public final class HvlType {

	public static final String LABEL = "HvlType";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_EARLY - (2 * HvlChronology.CHRONOLOGY_INIT_INTERVAL),
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_LATE + (2 * HvlChronology.CHRONOLOGY_EXIT_INTERVAL),
	LAUNCH_CODE = 4,
	LAUNCH_CODE_RAW = 16;//2^4

	public static boolean debug = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		HvlType.debug = debug;
		
		loadedClasses = new HashMap<>();
		registerAutomaticCasts();
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		clearAutomaticCasts();
		clearForNameCache();
	};
	
	private HvlType(){}
	
	private static HashMap<String, Class<?>> loadedClasses;
	
	private static HashMap<String, HvlAction.A1r<Object, Object>> automaticCasts;

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getCachedForName(String typeNameArg){
		try{
			if(loadedClasses.containsKey(typeNameArg))
				return (Class<T>)loadedClasses.get(typeNameArg);
			else{
				Class<T> loadedClass = (Class<T>)Class.forName(typeNameArg);
				loadedClasses.put(typeNameArg, loadedClass);
				return loadedClass;
			}
		}catch(ClassNotFoundException exception){
			throw new RuntimeException();//TODO formalize this exception
		}
	}

	public static void cacheForName(Class<?> typeArg){
		if(!loadedClasses.containsKey(typeArg.getName()))
			loadedClasses.put(typeArg.getName(), typeArg);
	}
	
	private static void clearForNameCache(){
		HvlLogger.println(debug, "Clearing class name cache.");
		loadedClasses.clear();
	}
	
	private static void registerAutomaticCasts(){
		HvlLogger.println(debug, "Populating re-cast map.");
		
		automaticCasts = new HashMap<>();
		automaticCasts.put("java.lang.Float", (objectArg) -> {
			if(objectArg instanceof Double)
				return ((Double)objectArg).floatValue();
			else return (float)objectArg;
		});
	}
	
	protected static boolean hasRecast(String typeNameArg){
		return automaticCasts.containsKey(typeNameArg);
	}
	
	protected static Object recast(String typeNameArg, Object objectArg){
		if(automaticCasts.containsKey(typeNameArg))
			return automaticCasts.get(typeNameArg).run(objectArg);
		else throw new RuntimeException();//TODO formalize this exception
	}
	
	private static void clearAutomaticCasts(){
		HvlLogger.println(debug, "Clearing re-cast map!");
		automaticCasts.clear();
	}
	
}
