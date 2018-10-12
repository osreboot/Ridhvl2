package com.osreboot.ridhvl2.template;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;

/**
 * Handles the instantiation and execution of general-purpose sequential events intended to be run on program 
 * initialize, program update and program exit. Event sequences are triggered with {@linkplain #initialize()}, 
 * {@linkplain #preUpdate(float)}, {@linkplain #postUpdate(float)} and {@linkplain #exit()}. 
 * {@linkplain HvlTemplateI} calls these methods appropriately. Consequently this class should only be used by 
 * internal Ridhvl2 processes, or processes that intend to alter Ridhvl2's fundamental behavior in some way.
 * 
 * <p>
 * 
 * Classes containing events need to be registered via the {@linkplain #registerChronology(Class)} method. 
 * {@linkplain com.osreboot.ridhvl2.template.HvlChronologyRegistry HvlChronologyRegistry}'s
 * {@linkplain com.osreboot.ridhvl2.template.HvlChronologyRegistry#registerRidhvlChronologies() 
 * registerRidhvlChronologies()} method registers all Ridhvl event-containing classes. Once all events are 
 * registered, {@linkplain #loadEvents(long, long)} must be called with <code>launchCode</code> and 
 * <code>debugCode</code> arguments in order to make events specified by the given <code>launchCode</code>
 * active. Finally, calling the {@linkplain #initialize()} method runs all active initialize events and, 
 * similarly, repeatedly calling {@linkplain #preUpdate(float)} and {@linkplain #postUpdate(float)} runs all 
 * active update events and {@linkplain #exit()} runs all active exit events.
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
	LAUNCH_CODE = 0, //functionally does nothing, exists for structure purposes
	LAUNCH_CODE_RAW = 1,//2^0 //functionally does nothing, exists for structure purposes
	DEBUG_LAUNCH_CODE = 0,
	DEBUG_LAUNCH_CODE_RAW = 1;

	private HvlChronology(){}
	
	/**
	 * Standard values that represent possible sequence indexes for events. Each event occupies one index on this
	 * scale, and that index cannot be occupied by another active event, with no exceptions. Each event type has
	 * its own scale. Initialize events range from {@linkplain #CHRONOLOGY_INIT_EARLIEST} to 
	 * {@linkplain #CHRONOLOGY_INIT_LATEST}. Pre-update events range from 
	 * {@linkplain #CHRONOLOGY_UPDATE_PRE_EARLIEST} to {@linkplain #CHRONOLOGY_UPDATE_PRE_LATEST}. Post-update
	 * events range from {@linkplain #CHRONOLOGY_UPDATE_POST_EARLIEST} to 
	 * {@linkplain #CHRONOLOGY_UPDATE_POST_LATEST}. Finally, exit events range from {@linkplain #CHRONOLOGY_EXIT_EARLIEST} to 
	 * {@linkplain #CHRONOLOGY_EXIT_LATEST}. All unoccupied event sequence indexes are automatically removed
	 * upon {@linkplain #loadEvents(long, long)}, and event sequences are compressed so each active event occurs
	 * in its respective order relative to other active events.
	 * 
	 * <p>
	 * 
	 * By convention, it is frowned upon to place an event directly on a sequence marker. Events should be offset
	 * from sequence markers by their respective <code>INTERVAL</code> value, which provides room for events to be 
	 * inserted in between any two other events, if such an order is absolutely necessary. <code>EARLIEST</code>
	 * and <code>LATEST</code> markers should also be avoided, as these are the absolute minimum and maximum
	 * sequence indexes that may be used for events, respectively.
	 */
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
	CHRONOLOGY_EXIT_LATEST = 100;

	private static long launchCode = -1, debugLaunchCode = -1;

	/**
	 * @return HvlChronology's current launch code. This returns <code>-1</code> before 
	 * {@linkplain #loadEvents(long, long)} is called.
	 */
	public static long getLaunchCode(){
		return launchCode;
	}

	/**
	 * @return HvlChronology's current debug launch code. This returns <code>-1</code> before 
	 * {@linkplain #loadEvents(long, long)} is called.
	 */
	public static long getDebugLaunchCode(){
		return debugLaunchCode;
	}

	/**
	 * @return <code>true</code> if HvlChronology's debug output is enabled by the current launch code
	 */
	public static boolean getDebugOutput(){
		return verifyDebugLaunchCode(DEBUG_LAUNCH_CODE);
	}

	/**
	 * Searches a class for fields with {@linkplain HvlChronologyInitialize}, {@linkplain HvlChronologyUpdate} and
	 * {@linkplain HvlChronologyExit} annotations. If a field is found, that field's value is then cast to its
	 * respective HvlChronology subclass, where it waits to be activated and loaded into the active event list.
	 * This method can throw a large number of exceptions, primarily relating to reflection-based circumstances
	 * where a field is of an improper type (to be cast) or is inaccessible.
	 * 
	 * <p>
	 * 
	 * For more on properly formatting these fields, see {@linkplain HvlChronology}'s class comment or
	 * {@linkplain com.osreboot.ridhvl2.test.TestChronology TestChronology}. {@linkplain HvlDisplay} also contains
	 * a great example of HvlChronology event field declaration.
	 * 
	 * @param cArg the class to search for HvlChronology event fields
	 */
	@SuppressWarnings("unchecked")
	public static void registerChronology(Class<?> cArg){
		try{
			for(Field f : cArg.getFields()){
				if(f.isAnnotationPresent(HvlChronologyInitialize.class)){
					if(f.getType() == HvlAction.A1.class){
						//TODO sort out this warning with a proper check
						new Initialize((HvlAction.A1<Boolean>)f.get(null), f.getAnnotation(HvlChronologyInitialize.class));
					}else{
						HvlLogger.println("Invalid field type for registered initialize event in " + cArg.getSimpleName() + "!");
					}
				}else if(f.isAnnotationPresent(HvlChronologyUpdate.class)){
					if(f.getType() == HvlAction.A2.class){
						//TODO sort out this warning with a proper check
						new Update((HvlAction.A2<Boolean, Float>)f.get(null), f.getAnnotation(HvlChronologyUpdate.class));
					}else{
						HvlLogger.println("Invalid field type for registered update event in " + cArg.getSimpleName() + "!");
					}
				}else if(f.isAnnotationPresent(HvlChronologyExit.class)){
					if(f.getType() == HvlAction.A1.class){
						//TODO sort out this warning with a proper check
						new Exit((HvlAction.A1<Boolean>)f.get(null), f.getAnnotation(HvlChronologyExit.class));
					}else{
						HvlLogger.println("Invalid field type for registered exit event in " + cArg.getSimpleName() + "!");
					}
				}
			}
		}catch(Exception e){
			//TODO allow this exception to pass and stop the program as intended
			HvlLogger.println("Exception thrown trying to register event from " + cArg.getSimpleName() + "!");
			e.printStackTrace();
		}
	}

	/**
	 * Tests the bit of the current launch code at <code>codeArg</code> index to see if that event is active. If
	 * {@linkplain #loadEvents(long, long)} has not yet been called, this will always return <code>true</code>
	 * (based on the rules of two's compliment).
	 * 
	 * @param codeArg the bit of the current launch code to test
	 * @return <code>true</code> if the bit at <code>codeArg</code> is set
	 */
	public static boolean verifyLaunchCode(int codeArg){
		return BigInteger.valueOf(launchCode).testBit(codeArg);
	}

	/**
	 * Tests the bit of the current debug launch code at <code>codeArg</code> index to see if that event is set to 
	 * debug mode. If {@linkplain #loadEvents(long, long)} has not yet been called, this will always return 
	 * <code>true</code> (based on the rules of two's compliment).
	 * 
	 * @param codeArg the bit of the current debug launch code to test
	 * @return <code>true</code> if the bit at <code>codeArg</code> is set
	 */
	public static boolean verifyDebugLaunchCode(int codeArg){
		return BigInteger.valueOf(debugLaunchCode).testBit(codeArg);
	}

	/**
	 * Selectively activates events stored in HvlChronology's subclass queues based on whether or not each event
	 * is explicitly enabled by <code>launchCodeArg</code>. Activated events are given a debug mode value based
	 * on whether or not each event is explicitly debug-enabled by <code>debugLaunchCodeArg</code>.
	 * 
	 * <p>
	 * 
	 * Default launch codes are supplied by {@linkplain HvlTemplateI} instances. <code>Long.MAX_VALUE</code> enables
	 * all registered events, however this will become ineffective if Ridhvl2 ever includes events that require
	 * radio-button-like behavior (it currently does not). <code>Long.MAX_VALUE - 1</code> as 
	 * <code>debugLaunchCodeArg</code> is typically good practice, as this will enable debug output from all active 
	 * events other than HvlChronology itself, which can produce extremely lengthy console print-outs on
	 * initialization.
	 * 
	 * <p>
	 * 
	 * TODO Custom launch codes can also be generated by Ridhvl2TK's launch code editor.
	 * 
	 * <p>
	 * 
	 * NOTE: this method should only be called once on program initialize! If, in the same Java program, Ridhvl2 is
	 * being completely reinitialized, then {@linkplain #unloadEvents()} must be called when the first instance of
	 * Ridhvl2 shuts down.
	 * 
	 * @param launchCodeArg the value specifying which events should be activated for this Ridhvl2 instance
	 * @param debugLaunchCodeArg the value specifying which events should have debug mode enabled for this Ridhvl2 
	 * instance
	 * @throws InvalidLoadConfigurationException if either <code>launchCodeArg</code> or 
	 * <code>debugLaunchCodeArg</code> are less than 0
	 * @throws AlreadyLoadedException if this method has already been called during the current Ridhvl2 instance
	 * @throws PredefinedChronologyException if two events made active by <code>launchCodeArg</code> share the same
	 * chronology value
	 */
	public static void loadEvents(long launchCodeArg, long debugLaunchCodeArg){
		if(launchCodeArg < 0 || debugLaunchCodeArg < 0) throw new InvalidLoadConfigurationException();
		if(launchCode >= 0 || debugLaunchCode >= 0) throw new AlreadyLoadedException();
		launchCode = launchCodeArg;
		debugLaunchCode = debugLaunchCodeArg;

		//load initialize events
		HashMap<Integer, Initialize> preInits = new HashMap<>();
		for(Initialize chrono : Initialize.queue){
			if(verifyLaunchCode(chrono.annotation.launchCode())){
				if(!preInits.containsKey(chrono.annotation.chronology())){
					preInits.put(chrono.annotation.chronology(), chrono);
				}else{
					HvlLogger.println("Error when trying to load initialize event " + chrono.annotation.label() + " to occupied slot " + chrono.annotation.chronology() + "!");
					throw new PredefinedChronologyException();
				}
			}else HvlLogger.println(getDebugOutput(), "Launch code disabled initialize event from " + chrono.annotation.label() + ".");
		}
		for(int i = CHRONOLOGY_INIT_EARLIEST; i <= CHRONOLOGY_INIT_LATEST; i++){
			if(preInits.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading initialize event from " + preInits.get(i).annotation.label() + " to slot " + i + 
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
					HvlLogger.println("Error when trying to load update event " + chrono.annotation.label() + " to occupied slot " + chrono.annotation.chronology() + "!");
					throw new PredefinedChronologyException();
				}
			}else HvlLogger.println(getDebugOutput(), "Launch code disabled update event from " + chrono.annotation.label() + ".");
		}
		for(int i = CHRONOLOGY_UPDATE_PRE_EARLIEST; i <= CHRONOLOGY_UPDATE_PRE_LATEST; i++){
			if(preUpdates.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading pre-update event from " + preUpdates.get(i).annotation.label() + " to slot " + i + 
						(verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()) ? " (debug)." : "."));
				loadedPreUpdate.put(preUpdates.get(i).action, verifyDebugLaunchCode(preUpdates.get(i).annotation.launchCode()));
			}
		}
		for(int i = CHRONOLOGY_UPDATE_POST_EARLIEST; i <= CHRONOLOGY_UPDATE_POST_LATEST; i++){
			if(preUpdates.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading post-update event from " + preUpdates.get(i).annotation.label() + " to slot " + i + 
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
					HvlLogger.println("Error when trying to load exit event " + chrono.annotation.label() + " to occupied slot " + chrono.annotation.chronology() + "!");
					throw new PredefinedChronologyException();
				}
			}else HvlLogger.println(getDebugOutput(), "Launch code disabled exit event from " + chrono.annotation.label() + ".");
		}
		for(int i = CHRONOLOGY_EXIT_EARLIEST; i <= CHRONOLOGY_EXIT_LATEST; i++){
			if(preExits.containsKey(i)){
				HvlLogger.println(getDebugOutput(), "Loading exit event from " + preExits.get(i).annotation.label() + " to slot " + i + 
						(verifyDebugLaunchCode(preExits.get(i).annotation.launchCode()) ? " (debug)." : "."));
				loadedExit.put(preExits.get(i).action, verifyDebugLaunchCode(preExits.get(i).annotation.launchCode()));
			}
		}
	}

	/**
	 * Unloads all active events, resets internal launch codes and clears all HvlChronology subclass queues. This
	 * method is intended to be called only when completely resetting Ridhvl2. After calling this method, all events
	 * need to be re-registered with {@linkplain #registerChronology(Class)}. {@linkplain #loadEvents(long, long)} 
	 * may be called for the second time in a single Java program instance if this method is called first.
	 */
	public static void unloadEvents(){
		HvlLogger.println(getDebugOutput(), "Unloading all events.");
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

	/**
	 * Represents an unloaded HvlChronology initialize event. This class is an {@linkplain HvlAction.A1} wrapper 
	 * that includes data to identify the action as an HvlChronology event.
	 * 
	 * @author os_reboot
	 *
	 */
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

		/**
		 * @return the event's label
		 */
		public String getAnnotationLabel(){
			return annotation.label();
		}

		/**
		 * @return the event's launch code
		 */
		public int getAnnotationLaunchCode(){
			return annotation.launchCode();
		}

		/**
		 * @return the event's chronology
		 */
		public int getAnnotationChronology(){
			return annotation.chronology();
		}

	}

	/**
	 * @return all registered initialize events in the form of {@linkplain HvlChronology.Initialize Initialize} 
	 * instances
	 */
	public static List<Initialize> getInitializeQueue(){
		return Collections.unmodifiableList(Initialize.queue);
	}

	/**
	 * Represents an unloaded HvlChronology update event. This class is an {@linkplain HvlAction.A2} wrapper that 
	 * includes data to identify the action as an HvlChronology event.
	 * 
	 * @author os_reboot
	 *
	 */
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

		/**
		 * @return the event's label
		 */
		public String getAnnotationLabel(){
			return annotation.label();
		}

		/**
		 * @return the event's launch code
		 */
		public int getAnnotationLaunchCode(){
			return annotation.launchCode();
		}

		/**
		 * @return the event's chronology
		 */
		public int getAnnotationChronology(){
			return annotation.chronology();
		}

	}

	/**
	 * @return all registered update events in the form of {@linkplain HvlChronology.Update Update} instances
	 */
	public static List<Update> getUpdateQueue(){
		return Collections.unmodifiableList(Update.queue);
	}

	/**
	 * Represents an unloaded HvlChronology exit event. This class is an {@linkplain HvlAction.A1} wrapper that 
	 * includes data to identify the action as an HvlChronology event.
	 * 
	 * @author os_reboot
	 *
	 */
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

		/**
		 * @return the event's label
		 */
		public String getAnnotationLabel(){
			return annotation.label();
		}

		/**
		 * @return the event's launch code
		 */
		public int getAnnotationLaunchCode(){
			return annotation.launchCode();
		}

		/**
		 * @return the event's chronology
		 */
		public int getAnnotationChronology(){
			return annotation.chronology();
		}

	}

	/**
	 * @return all registered exit events in the form of {@linkplain HvlChronology.Exit Exit} instances
	 */
	public static List<Exit> getExitQueue(){
		return Collections.unmodifiableList(Exit.queue);
	}

	private static LinkedHashMap<HvlAction.A1<Boolean>, Boolean> loadedInitialize = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A2<Boolean, Float>, Boolean> loadedPreUpdate = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A2<Boolean, Float>, Boolean> loadedPostUpdate = new LinkedHashMap<>();
	private static LinkedHashMap<HvlAction.A1<Boolean>, Boolean> loadedExit = new LinkedHashMap<>();
	
	/**
	 * Runs all active initialize events in the relative sequence specified by their respective 
	 * <code>chronology</code> values. For information on registering events see 
	 * {@linkplain #registerChronology(Class)} and for information on properly activating events see 
	 * {@linkplain #loadEvents(long, long)}.
	 */
	public static void initialize(){
		for(HvlAction.A1<Boolean> a : loadedInitialize.keySet()){
			try{
				a.run(loadedInitialize.get(a));
			}catch(Exception e){
				HvlLogger.println("Exception thrown by an initialize event!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Runs all active pre-update events (events with <code>chronology</code> values less than 
	 * {@linkplain #CHRONOLOGY_UPDATE_POST_EARLIEST}) in the relative sequence specified by their respective 
	 * <code>chronology</code> values. For information on registering events see 
	 * {@linkplain #registerChronology(Class)} and for information on properly activating events see 
	 * {@linkplain #loadEvents(long, long)}.
	 * 
	 * @param delta the time (in seconds) since the last program update
	 */
	public static void preUpdate(float delta){
		for(HvlAction.A2<Boolean, Float> a : loadedPreUpdate.keySet()){
			try{
				a.run(loadedPreUpdate.get(a), delta);
			}catch(Exception e){
				HvlLogger.println("Exception thrown by a pre-update event!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Runs all active post-update events (events with <code>chronology</code> values greater than 
	 * {@linkplain #CHRONOLOGY_UPDATE_PRE_LATEST}) in the relative sequence specified by their respective 
	 * <code>chronology</code> values. For information on registering events see 
	 * {@linkplain #registerChronology(Class)} and for information on properly activating events see 
	 * {@linkplain #loadEvents(long, long)}.
	 * 
	 * @param delta the time (in seconds) since the last program update
	 */
	public static void postUpdate(float delta){
		for(HvlAction.A2<Boolean, Float> a : loadedPostUpdate.keySet()){
			try{
				a.run(loadedPostUpdate.get(a), delta);
			}catch(Exception e){
				HvlLogger.println("Exception thrown by a post-update event!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Runs all active exit events in the relative sequence specified by their respective <code>chronology</code> 
	 * values. For information on registering events see {@linkplain #registerChronology(Class)} and for information
	 * on properly activating events see {@linkplain #loadEvents(long, long)}.
	 */
	public static void exit(){
		for(HvlAction.A1<Boolean> a : loadedExit.keySet()){
			try{
				a.run(loadedExit.get(a));
			}catch(Exception e){
				HvlLogger.println("Exception thrown by an exit event!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Thrown if an attempt is made to load HvlChronology event sequences twice in the same Ridhvl2 instance with 
	 * {@linkplain HvlChronology#loadEvents(long, long)}, without calling {@linkplain HvlChronology#unloadEvents()}
	 * first.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class AlreadyLoadedException extends RuntimeException{
		private static final long serialVersionUID = 8909278026333909960L;
	}
	
	/**
	 * Thrown if an attempt is made to register an HvlChronology event with a <code>chronology</code> value outside 
	 * of its appropriate boundaries. See:
	 * 
	 * <p>
	 * 
	 * {@linkplain HvlChronology#CHRONOLOGY_INIT_EARLIEST}
	 * <br>
	 * {@linkplain HvlChronology#CHRONOLOGY_INIT_LATEST}
	 * <br>
	 * {@linkplain HvlChronology#CHRONOLOGY_UPDATE_PRE_EARLIEST}
	 * <br>
	 * {@linkplain HvlChronology#CHRONOLOGY_UPDATE_POST_LATEST}
	 * <br>
	 * {@linkplain HvlChronology#CHRONOLOGY_EXIT_EARLIEST}
	 * <br>
	 * {@linkplain HvlChronology#CHRONOLOGY_EXIT_LATEST}
	 * 
	 * @author os_reboot
	 *
	 */
	public static class InvalidChronologyException extends RuntimeException{
		private static final long serialVersionUID = 9195870151660493054L;
	}

	/**
	 * Thrown if an attempt is made to register an HvlChronology event with a <code>launchCode</code> value of less
	 * than 1.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class InvalidLaunchCodeException extends RuntimeException{
		private static final long serialVersionUID = -7657584019043303889L;
	}

	/**
	 * Thrown if an attempt is made to call {@linkplain HvlChronology#loadEvents(long, long)} with either
	 * <code>launchCodeArg</code> or <code>debugLaunchCodeArg</code> being less than 0.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class InvalidLoadConfigurationException extends RuntimeException{
		private static final long serialVersionUID = 2633380302403141329L;
	}

	/**
	 * Thrown if an attempt is made during {@linkplain HvlChronology#loadEvents(long, long)} to assign an 
	 * HvlChronology event to a chronology slot already occupied by another event. This is usually caused by two
	 * registered events with the same chronology being activated and loaded within the same Ridhvl2 instance.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class PredefinedChronologyException extends RuntimeException{
		private static final long serialVersionUID = -3134726851926392146L;
	}

	/**
	 * Thrown if an attempt is made to access a utility that hasn't yet been activated within a Ridhvl2 instance.
	 * This is usually caused by a launch code that doesn't properly suit a Ridhvl2 program's needs. See
	 * {@linkplain HvlChronology#loadEvents(long, long)} for more on launch codes.
	 * 
	 * @author os_reboot
	 *
	 */
	public static class InactiveException extends RuntimeException{
		private static final long serialVersionUID = -7857410333110944252L;

		public InactiveException(String labelArg, int launchCodeArg){
			super("Tried to access inactive " + labelArg + "! Use its launch code (" + launchCodeArg +
					") when loading to activate it.");
		}

	}

}
