package com.osreboot.ridhvl2.template;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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

	private static long launchCode = -1, debugLaunchCode = -1;

	public static long getLaunchCode(){
		return launchCode;
	}

	public static long getDebugLaunchCode(){
		return debugLaunchCode;
	}
	
	public static boolean getDebugOutput(){
		return verifyDebugLaunchCode(1);
	}

	@SuppressWarnings("unchecked")
	public static void registerChronology(Class<?> cArg){
		try{
			for(Field f : cArg.getFields()){
				if(f.isAnnotationPresent(HvlChronologyInitialize.class)){
					if(f.getType() == HvlAction.A1.class){
						//TODO sort out this warning with a proper check
						new Initialize((HvlAction.A1<Boolean>)f.get(null), f.getAnnotation(HvlChronologyInitialize.class));
					}else{
						HvlLogger.println("Invalid field type for registered initialize chronology in " + cArg.getSimpleName() + "!");
					}
				}else if(f.isAnnotationPresent(HvlChronologyUpdate.class)){
					if(f.getType() == HvlAction.A2.class){
						//TODO sort out this warning with a proper check
						new Update((HvlAction.A2<Boolean, Float>)f.get(null), f.getAnnotation(HvlChronologyUpdate.class));
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

	private static boolean verifyLaunchCode(int codeArg){
		return BigInteger.valueOf(launchCode).testBit(codeArg - 1);
	}
	
	private static boolean verifyDebugLaunchCode(int codeArg){
		return BigInteger.valueOf(debugLaunchCode).testBit(codeArg - 1);
	}

	public static void loadChronologies(long launchCodeArg, long debugLaunchCodeArg){
		if(launchCodeArg < 0 || debugLaunchCodeArg < 0) throw new InvalidLoadConfigurationException();
		launchCode = launchCodeArg;
		debugLaunchCode = debugLaunchCodeArg;
		HashMap<Integer, Initialize> preInits = new HashMap<>();
		for(Initialize chrono : Initialize.queue){
			if(!preInits.containsKey(chrono.annotation.chronology())){
				if(verifyLaunchCode(chrono.annotation.launchCode()))
					preInits.put(chrono.annotation.chronology(), chrono);
				//TODO else debug output
			}else{
				HvlLogger.println("Error when trying to register initialize action " + chrono.annotation.label() + ".");
				throw new PredefinedChronologyException();
			}
		}
		for(int i = CHRONOLOGY_INIT_EARLIEST; i <= CHRONOLOGY_INIT_LATEST; i++){
			//TODO debug printout
			if(preInits.containsKey(i)) loadedInitialize.put(preInits.get(i).action, verifyDebugLaunchCode(preInits.get(i).annotation.launchCode()));
		}

		HashMap<Integer, Update> preUpdates = new HashMap<>();
		for(Update chrono : Update.queue){
			if(!preUpdates.containsKey(chrono.annotation.chronology())){
				if(verifyLaunchCode(chrono.annotation.launchCode()))
					preUpdates.put(chrono.annotation.chronology(), chrono);
				//TODO else debug output
			}else{
				HvlLogger.println("Error when trying to register update action " + chrono.annotation.label() + ".");
				throw new PredefinedChronologyException();
			}
		}
		for(int i = CHRONOLOGY_UPDATE_PRE_EARLIEST; i <= CHRONOLOGY_UPDATE_PRE_LATEST; i++){
			//TODO debug printout
			if(preUpdates.containsKey(i)) loadedPreUpdate.put(preUpdates.get(i).action, verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()));
		}
		for(int i = CHRONOLOGY_UPDATE_POST_EARLIEST; i <= CHRONOLOGY_UPDATE_POST_LATEST; i++){
			//TODO debug printout
			if(preUpdates.containsKey(i)) loadedPostUpdate.put(preUpdates.get(i).action, verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()));
		}
	}

	private static class Initialize{
		private static ArrayList<Initialize> queue = new ArrayList<>();

		private HvlAction.A1<Boolean> action;
		private HvlChronologyInitialize annotation;

		private Initialize(HvlAction.A1<Boolean> actionArg, HvlChronologyInitialize annotationArg){
			if(annotationArg.launchCode() < 1) throw new InvalidLaunchCodeException();
			action = actionArg;
			annotation = annotationArg;
			queue.add(this);
		}

	}

	private static class Update{
		private static ArrayList<Update> queue = new ArrayList<>();

		private HvlAction.A2<Boolean, Float> action;
		private HvlChronologyUpdate annotation;

		private Update(HvlAction.A2<Boolean, Float> actionArg, HvlChronologyUpdate annotationArg){
			if(annotationArg.launchCode() < 1) throw new InvalidLaunchCodeException();
			action = actionArg;
			annotation = annotationArg;
			queue.add(this);
		}

	}

	private static LinkedHashMap<HvlAction.A1<Boolean>, Boolean> loadedInitialize = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A2<Boolean, Float>, Boolean> loadedPreUpdate = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A2<Boolean, Float>, Boolean> loadedPostUpdate = new LinkedHashMap<>();

	public static void initialize(){
		for(HvlAction.A1<Boolean> a : loadedInitialize.keySet()){
			try{
				a.run(loadedInitialize.get(a));
			}catch(Exception e){
				HvlLogger.println("Exception thrown by an initialize action!");
				e.printStackTrace();
			}
		}
	}

	public static void preUpdate(float delta){
		for(HvlAction.A2<Boolean, Float> a : loadedPreUpdate.keySet()){
			try{
				a.run(loadedPreUpdate.get(a), delta);
			}catch(Exception e){
				HvlLogger.println("Exception thrown by a pre-update action!");
				e.printStackTrace();
			}
		}
	}

	public static void postUpdate(float delta){
		for(HvlAction.A2<Boolean, Float> a : loadedPostUpdate.keySet()){
			try{
				a.run(loadedPostUpdate.get(a), delta);
			}catch(Exception e){
				HvlLogger.println("Exception thrown by a post-update action!");
				e.printStackTrace();
			}
		}
	}

	public static class InvalidChronologyException extends RuntimeException{
		private static final long serialVersionUID = 9195870151660493054L;
	}

	public static class InvalidLaunchCodeException extends RuntimeException{
		private static final long serialVersionUID = -7657584019043303889L;
	}
	
	public static class InvalidLoadConfigurationException extends RuntimeException{
		private static final long serialVersionUID = 2633380302403141329L;
	}

	public static class PredefinedChronologyException extends RuntimeException{
		private static final long serialVersionUID = -3134726851926392146L;
	}

}
