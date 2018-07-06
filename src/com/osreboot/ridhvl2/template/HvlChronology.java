package com.osreboot.ridhvl2.template;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;

public class HvlChronology {

	public static final int 
	CHRONOLOGY_INIT_INTERVAL = 5,
	CHRONOLOGY_INIT_EARLIEST = 0,
	CHRONOLOGY_INIT_EARLY = 25,
	CHRONOLOGY_INIT_MIDDLE = 50,
	CHRONOLOGY_INIT_LATE = 75,
	CHRONOLOGY_INIT_LATEST = 100,
	CHRONOLOGY_UPDATE_INTERVAL = 5,
	CHRONOLOGY_UPDATE_PRE_EARLIEST = 0,
	CHRONOLOGY_UPDATE_PRE_EARLY = 12,
	CHRONOLOGY_UPDATE_PRE_MIDDLE = 25,
	CHRONOLOGY_UPDATE_PRE_LATE = 37,
	CHRONOLOGY_UPDATE_PRE_LATEST = 50,
	CHRONOLOGY_UPDATE_POST_EARLIEST = 51,
	CHRONOLOGY_UPDATE_POST_EARLY = 63,
	CHRONOLOGY_UPDATE_POST_MIDDLE = 75,
	CHRONOLOGY_UPDATE_POST_LATE = 87,
	CHRONOLOGY_UPDATE_POST_LATEST = 100;

	public static void registerChronology(Class<?> cArg){
		try{
			for(Field f : cArg.getFields()){
				if(f.isAnnotationPresent(HvlChronologyInitialize.class)){
					if(f.getType() == HvlAction.A0.class){
						//TODO instantiate initialize
					}else{
						HvlLogger.println("Invalid field type for registered initialize chronology in " + cArg.getSimpleName() + "!");
					}
				}else if(f.isAnnotationPresent(HvlChronologyUpdate.class)){
					if(f.getType() == HvlAction.A1.class){
						//TODO instantiate update
					}else{
						HvlLogger.println("Invalid field type for registered initialize chronology in " + cArg.getSimpleName() + "!");
					}
				}
			}
		}catch(Exception e){
			HvlLogger.println("Exception thrown trying to register chronology from " + cArg.getSimpleName() + "!");
			e.printStackTrace();
		}
	}

	private static class Initialize{

	}

	private static class Update{

	}

	private static ArrayList<HvlAction.A0> loadedInitialize = new ArrayList<>();
	private static ArrayList<HvlAction.A1<Float>> loadedPreUpdate = new ArrayList<>();
	private static ArrayList<HvlAction.A1<Float>> loadedPostUpdate = new ArrayList<>();

	protected static void initialize(){
		for(HvlAction.A0 a : loadedInitialize){
			try{
				a.run();
			}catch(Exception e){
				HvlLogger.println("Exception thrown by an initialize action!");
				e.printStackTrace();
			}
		}
	}

	protected static void preUpdate(float delta){
		for(HvlAction.A1<Float> a : loadedPreUpdate){
			try{
				a.run(delta);
			}catch(Exception e){
				HvlLogger.println("Exception thrown by a pre-update action!");
				e.printStackTrace();
			}
		}
	}

	protected static void postUpdate(float delta){
		for(HvlAction.A1<Float> a : loadedPostUpdate){
			try{
				a.run(delta);
			}catch(Exception e){
				HvlLogger.println("Exception thrown by a post-update action!");
				e.printStackTrace();
			}
		}
	}

}
