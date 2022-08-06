package com.osreboot.ridhvl2.menu;

import java.util.HashMap;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;

public final class HvlDefault{

	public static final String LABEL = "HvlDefault";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_LATE - HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_MIDDLE + (3 * HvlChronology.CHRONOLOGY_EXIT_INTERVAL),
	LAUNCH_CODE = 6,
	LAUNCH_CODE_RAW = 64;//2^6

	private static boolean active = false;
	public static boolean debug = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		HvlDefault.debug = debug;
		defaults = new HashMap<>();
		HvlLogger.println(debug, "Ready to assign defaults.");
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		active = false;
		HvlLogger.println(debug, "Clearing defaults!");
		defaults = null;
	};

	private HvlDefault(){}

	private static HashMap<String, HvlTaggableOpen> defaults;

	public static void setDefaults(HashMap<String, HvlTaggableOpen> defaultsArg){
		if(active){
			defaults = defaultsArg;
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static HashMap<String, HvlTaggableOpen> getDefaults(){
		if(active){
			return defaults;
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static <T extends HvlTaggableOpen> void put(String typeArg, T taggableArg){
		if(active){
			defaults.put(typeArg, taggableArg);
			HvlLogger.println(debug, "Assigned default for type \"" + typeArg + "\".");
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static <T extends HvlTaggableOpen> void put(T taggableArg){
		put(taggableArg.getClass().getName(), taggableArg);
	}

	public static <T extends HvlTaggableOpen> void put(Class<T> typeArg, T taggableArg){
		put(typeArg.getName(), taggableArg);
	}

	public static <T extends HvlTaggableOpen> boolean exists(String typeArg){
		if(active){
			return defaults.containsKey(typeArg);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static <T extends HvlTaggableOpen> boolean exists(Class<T> typeArg){
		return exists(typeArg.getName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends HvlTaggableOpen> T applyIfExists(String typeArg, T taggableArg){
		if(active){
			if(exists(typeArg)){
				HvlTaggableOpen defaultTaggable = defaults.get(typeArg);

				for(HvlTagTransient tag : taggableArg.validTags())
					taggableArg.set(tag, defaultTaggable.get(tag));

				for(HvlTagTransient tag : defaultTaggable.validTagsOpen()){
					if(!taggableArg.validTagsOpen().contains(tag)) taggableArg.addOpen(tag);
					taggableArg.setOpen(tag, defaultTaggable.getOpen(tag));
				}

				HvlLogger.println(debug, "Applied default of type \"" + typeArg + "\".");
				return taggableArg;
			}
			return null;
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static <T extends HvlTaggableOpen> T applyIfExists(Class<T> typeArg, T taggableArg){
		if(active){
			return applyIfExists(typeArg.getName(), taggableArg);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

}
