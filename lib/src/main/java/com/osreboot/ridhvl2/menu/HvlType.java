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

	private static boolean active = false;
	public static boolean debug = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		HvlType.debug = debug;

		loadedClasses = new HashMap<>();
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		clearForNameCache();
		active = false;
	};

	private HvlType(){}

	private static HashMap<String, Class<?>> loadedClasses;

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getCachedForName(String typeNameArg){
		if(active){
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
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void cacheForName(Class<?> typeArg){
		if(active){
			if(!loadedClasses.containsKey(typeArg.getName()))
				loadedClasses.put(typeArg.getName(), typeArg);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	private static void clearForNameCache(){
		if(active){
			HvlLogger.println(debug, "Clearing class name cache.");
			loadedClasses.clear();
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

}
