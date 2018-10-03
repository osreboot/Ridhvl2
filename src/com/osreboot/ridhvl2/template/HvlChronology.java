package com.osreboot.ridhvl2.template;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;

/**
 * Handles the instantiation and execution of sequential events that run on program initialize and program 
 * update.
 * 
 * <p>
 * 
 * Classes containing events need to be registered via the {@linkplain #registerChronology(Class)} method. 
 * {@linkplain com.osreboot.ridhvl2.template.HvlChronologyRegistry HvlChronologyRegistry}'s
 * {@linkplain com.osreboot.ridhvl2.template.HvlChronologyRegistry#registerRidhvlChronologies() 
 * registerRidhvlChronologies()} method registers all Ridhvl event-containing classes. Once all events are 
 * registered, {@linkplain #loadChronologies(long, long)} must be called with <code>launchCode</code> and 
 * <code>debugCode</code> arguments in order to make events specified by the given <code>launchCode</code>
 * active. Finally, calling the {@linkplain #initialize()} method runs all active initialize events and, 
 * similarly, repeatedly calling {@linkplain #preUpdate(float)} and {@linkplain #postUpdate(float)} runs all 
 * active update events.
 * 
 * <p>
 * 
 * In order for a registered event to be recognized, it needs to declare a <code>label</code>, a 
 * <code>chronology</code>, and a <code>launchCode</code>. The <code>label</code> is used solely for logging 
 * purposes and identification in Ridhvl2Toolkit. The <code>chronology</code> describes the event's order of 
 * execution. Two events of the same type cannot have the same chronology. The <code>launchCode</code> is used 
 * for determining if an event should be queued for execution by the end-user. Programs that declare 
 * HvlChronology events should (by practice) always include constant variables describing their 
 * <code>launchCode</code> values, in the following format:
 * 
 * <p>
 * 
 * <code>public static final int LAUNCH_CODE = 1, LAUNCH_CODE_RAW = (int)Math.pow(2, LAUNCH_CODE);</code>
 * 
 * <p>
 * 
 * See {@linkplain com.osreboot.ridhvl2.test.TestChronology TestChronology} for an example of proper 
 * implementation and formatting.
 * 
 * @author os_reboot
 *
 */
public final class HvlChronology {

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
	CHRONOLOGY_UPDATE_POST_LATEST = 100,
	CHRONOLOGY_EXIT_INTERVAL = 5,
	CHRONOLOGY_EXIT_EARLIEST = 0,
	CHRONOLOGY_EXIT_EARLY = 25,
	CHRONOLOGY_EXIT_MIDDLE = 50,
	CHRONOLOGY_EXIT_LATE = 75,
	CHRONOLOGY_EXIT_LATEST = 100,
	LAUNCH_CODE = 0, //functionally does nothing, exists for structure purposes
	LAUNCH_CODE_RAW = 1, //functionally does nothing, exists for structure purposes
	DEBUG_LAUNCH_CODE = 0,
	DEBUG_LAUNCH_CODE_RAW = 1;

	private HvlChronology(){}

	private static long launchCode = -1, debugLaunchCode = -1;

	public static long getLaunchCode(){
		return launchCode;
	}

	public static long getDebugLaunchCode(){
		return debugLaunchCode;
	}

	public static boolean getDebugOutput(){
		return verifyDebugLaunchCode(DEBUG_LAUNCH_CODE);
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
						HvlLogger.println("Invalid field type for registered update chronology in " + cArg.getSimpleName() + "!");
					}
				}else if(f.isAnnotationPresent(HvlChronologyExit.class)){
					if(f.getType() == HvlAction.A1.class){
						//TODO sort out this warning with a proper check
						new Exit((HvlAction.A1<Boolean>)f.get(null), f.getAnnotation(HvlChronologyExit.class));
					}else{
						HvlLogger.println("Invalid field type for registered exit chronology in " + cArg.getSimpleName() + "!");
					}
				}
			}
		}catch(Exception e){
			//TODO allow this exception to pass and stop the program as intended
			HvlLogger.println("Exception thrown trying to register chronology from " + cArg.getSimpleName() + "!");
			e.printStackTrace();
		}
	}

	public static boolean verifyLaunchCode(int codeArg){
		return BigInteger.valueOf(launchCode).testBit(codeArg);
	}

	public static boolean verifyDebugLaunchCode(int codeArg){
		return BigInteger.valueOf(debugLaunchCode).testBit(codeArg);
	}

	public static void loadChronologies(long launchCodeArg, long debugLaunchCodeArg){
		if(launchCodeArg < 0 || debugLaunchCodeArg < 0) throw new InvalidLoadConfigurationException();
		launchCode = launchCodeArg;
		debugLaunchCode = debugLaunchCodeArg;

		//load initialize events
		HashMap<Integer, Initialize> preInits = new HashMap<>();
		for(Initialize chrono : Initialize.queue){
			if(verifyLaunchCode(chrono.annotation.launchCode())){
				if(!preInits.containsKey(chrono.annotation.chronology())){
					preInits.put(chrono.annotation.chronology(), chrono);
				}else{
					HvlLogger.println("Error when trying to load initialize action " + chrono.annotation.label() + " to occupied slot " + chrono.annotation.chronology() + "!");
					throw new PredefinedChronologyException();
				}
			}else HvlLogger.println(getDebugOutput(), "Launch code disabled initialize action from " + chrono.annotation.label() + ".");
		}
		for(int i = CHRONOLOGY_INIT_EARLIEST; i <= CHRONOLOGY_INIT_LATEST; i++){
			if(preInits.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading initialize action from " + preInits.get(i).annotation.label() + " to slot " + i + 
						(verifyDebugLaunchCode(preInits.get(i).annotation.launchCode()) ? " (debug)." : "."));
				loadedInitialize.put(preInits.get(i).action, verifyDebugLaunchCode(preInits.get(i).annotation.launchCode()));
			}
		}

		//load update events
		HashMap<Integer, Update> preUpdates = new HashMap<>();
		for(Update chrono : Update.queue){
			if(verifyLaunchCode(chrono.annotation.launchCode())){
				if(!preUpdates.containsKey(chrono.annotation.chronology())){
					preUpdates.put(chrono.annotation.chronology(), chrono);
				}else{
					HvlLogger.println("Error when trying to load update action " + chrono.annotation.label() + " to occupied slot " + chrono.annotation.chronology() + "!");
					throw new PredefinedChronologyException();
				}
			}else HvlLogger.println(getDebugOutput(), "Launch code disabled update action from " + chrono.annotation.label() + ".");
		}
		for(int i = CHRONOLOGY_UPDATE_PRE_EARLIEST; i <= CHRONOLOGY_UPDATE_PRE_LATEST; i++){
			if(preUpdates.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading pre-update action from " + preUpdates.get(i).annotation.label() + " to slot " + i + 
						(verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()) ? " (debug)." : "."));
				loadedPreUpdate.put(preUpdates.get(i).action, verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()));
			}
		}
		for(int i = CHRONOLOGY_UPDATE_POST_EARLIEST; i <= CHRONOLOGY_UPDATE_POST_LATEST; i++){
			if(preUpdates.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading post-update action from " + preUpdates.get(i).annotation.label() + " to slot " + i + 
						(verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()) ? " (debug)." : "."));
				loadedPostUpdate.put(preUpdates.get(i).action, verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()));
			}
		}

		//load exit events
		HashMap<Integer, Exit> preExits = new HashMap<>();
		for(Exit chrono : Exit.queue){
			if(verifyLaunchCode(chrono.annotation.launchCode())){
				if(!preExits.containsKey(chrono.annotation.chronology())){
					preExits.put(chrono.annotation.chronology(), chrono);
				}else{
					HvlLogger.println("Error when trying to load exit action " + chrono.annotation.label() + " to occupied slot " + chrono.annotation.chronology() + "!");
					throw new PredefinedChronologyException();
				}
			}else HvlLogger.println(getDebugOutput(), "Launch code disabled exit action from " + chrono.annotation.label() + ".");
		}
		for(int i = CHRONOLOGY_EXIT_EARLIEST; i <= CHRONOLOGY_EXIT_LATEST; i++){
			if(preExits.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading exit action from " + preExits.get(i).annotation.label() + " to slot " + i + 
						(verifyDebugLaunchCode(preExits.get(i).annotation.launchCode()) ? " (debug)." : "."));
				loadedExit.put(preExits.get(i).action, verifyDebugLaunchCode(preExits.get(i).annotation.launchCode()));
			}
		}
	}

	public static void unloadChronologies(){
		HvlLogger.println(getDebugOutput(), "Unloading all actions.");
		launchCode = -1;
		debugLaunchCode = -1;
		loadedInitialize.clear();
		loadedPreUpdate.clear();
		loadedPostUpdate.clear();
		loadedExit.clear();
		Initialize.queue.clear();
		Update.queue.clear();
		Exit.queue.clear();
	}

	public static final class Initialize{

		private static ArrayList<Initialize> queue = new ArrayList<>();

		private HvlAction.A1<Boolean> action;
		private HvlChronologyInitialize annotation;

		private Initialize(HvlAction.A1<Boolean> actionArg, HvlChronologyInitialize annotationArg){
			if(annotationArg.launchCode() < 1) throw new InvalidLaunchCodeException();
			if(annotationArg.chronology() < CHRONOLOGY_INIT_EARLIEST || annotationArg.chronology() > CHRONOLOGY_INIT_LATEST)
				throw new InvalidChronologyException();
			action = actionArg;
			annotation = annotationArg;
			queue.add(this);
		}

		public String getAnnotationLabel(){
			return annotation.label();
		}

		public int getAnnotationLaunchCode(){
			return annotation.launchCode();
		}

		public int getAnnotationChronology(){
			return annotation.chronology();
		}

	}

	public static ArrayList<Initialize> getInitializeQueue(){
		return new ArrayList<>(Initialize.queue);
	}

	public static final class Update{

		private static ArrayList<Update> queue = new ArrayList<>();

		private HvlAction.A2<Boolean, Float> action;
		private HvlChronologyUpdate annotation;

		private Update(HvlAction.A2<Boolean, Float> actionArg, HvlChronologyUpdate annotationArg){
			if(annotationArg.launchCode() < 1) throw new InvalidLaunchCodeException();
			if(annotationArg.chronology() < CHRONOLOGY_UPDATE_PRE_EARLIEST || annotationArg.chronology() > CHRONOLOGY_UPDATE_POST_LATEST)
				throw new InvalidChronologyException();
			action = actionArg;
			annotation = annotationArg;
			queue.add(this);
		}

		public String getAnnotationLabel(){
			return annotation.label();
		}

		public int getAnnotationLaunchCode(){
			return annotation.launchCode();
		}

		public int getAnnotationChronology(){
			return annotation.chronology();
		}

	}

	public static ArrayList<Update> getUpdateQueue(){
		return new ArrayList<>(Update.queue);
	}

	public static final class Exit{

		private static ArrayList<Exit> queue = new ArrayList<>();

		private HvlAction.A1<Boolean> action;
		private HvlChronologyExit annotation;

		private Exit(HvlAction.A1<Boolean> actionArg, HvlChronologyExit annotationArg){
			if(annotationArg.launchCode() < 1) throw new InvalidLaunchCodeException();
			if(annotationArg.chronology() < CHRONOLOGY_EXIT_EARLIEST || annotationArg.chronology() > CHRONOLOGY_EXIT_LATEST)
				throw new InvalidChronologyException();
			action = actionArg;
			annotation = annotationArg;
			queue.add(this);
		}

		public String getAnnotationLabel(){
			return annotation.label();
		}

		public int getAnnotationLaunchCode(){
			return annotation.launchCode();
		}

		public int getAnnotationChronology(){
			return annotation.chronology();
		}

	}

	public static ArrayList<Exit> getExitQueue(){
		return new ArrayList<>(Exit.queue);
	}

	private static LinkedHashMap<HvlAction.A1<Boolean>, Boolean> loadedInitialize = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A2<Boolean, Float>, Boolean> loadedPreUpdate = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A2<Boolean, Float>, Boolean> loadedPostUpdate = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A1<Boolean>, Boolean> loadedExit = new LinkedHashMap<>();
	
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
	
	public static void exit(){
		for(HvlAction.A1<Boolean> a : loadedExit.keySet()){
			try{
				a.run(loadedExit.get(a));
			}catch(Exception e){
				HvlLogger.println("Exception thrown by an exit action!");
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

	public static class InactiveException extends RuntimeException{
		private static final long serialVersionUID = -7857410333110944252L;

		public InactiveException(String labelArg, int launchCodeArg){
			super("Tried to access inactive " + labelArg + "! Use its launch code (" + launchCodeArg +
					") when loading to activate it.");
		}

	}

}
