package com.osreboot.ridhvl2.menu;

import java.util.ArrayList;
import java.util.List;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.ridhvl2.template.HvlChronologyUpdate;
import com.osreboot.ridhvl2.template.HvlDisplay;

public final class HvlMenu {

	public static final String LABEL = "HvlMenu";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_LATE - (2 * HvlChronology.CHRONOLOGY_INIT_INTERVAL),
	CHRONO_PRE_UPDATE = HvlChronology.CHRONOLOGY_UPDATE_PRE_MIDDLE - HvlChronology.CHRONOLOGY_UPDATE_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_MIDDLE - HvlChronology.CHRONOLOGY_EXIT_INTERVAL,
	LAUNCH_CODE = 7,
	LAUNCH_CODE_RAW = 128;//2^7

	private static boolean active = false;
	public static boolean debug = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		active = HvlChronology.verifyLaunchCode(LAUNCH_CODE);
		HvlMenu.debug = debug;
		components = new ArrayList<>();
		tempEnvironment = new HvlEnvironmentMutable();
	};

	@HvlChronologyUpdate(label = LABEL, chronology = CHRONO_PRE_UPDATE, launchCode = LAUNCH_CODE)
	public static final HvlAction.A2<Boolean, Float> ACTION_PRE_UPDATE = (debug, delta) -> {
		advanceComponents();
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		active = false;
		HvlLogger.println(debug, "Clearing components!");
		components = null;
		componentsQueued = null;
		tempEnvironment = null;
	};

	private HvlMenu(){}

	private static ArrayList<HvlComponent> components, componentsQueued;
	private static HvlEnvironmentMutable tempEnvironment;

	private static void advanceComponents(){
		if(componentsQueued != null){
			components = componentsQueued;
			componentsQueued = null;
		}
	}

	public static void set(HvlComponent componentArg){
		if(active){
			if(componentsQueued == null)
				componentsQueued = new ArrayList<>();
			else componentsQueued.clear();
			componentsQueued.add(componentArg);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static List<HvlComponent> get(){
		if(active){
			return componentsQueued == null ? components : componentsQueued;
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static HvlComponent top(){
		if(active){
			return componentsQueued == null ? components.get(components.size() - 1) :
				componentsQueued.get(componentsQueued.size() - 1);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void push(HvlComponent componentArg){
		if(active){
			if(componentsQueued == null)
				componentsQueued = new ArrayList<>(components);
			componentsQueued.add(componentArg);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static HvlComponent pop(){
		if(active){
			if(componentsQueued == null)
				componentsQueued = new ArrayList<>(components);
			return componentsQueued.remove(componentsQueued.size() - 1);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void update(float delta){
		if(active){
			update(delta, HvlDisplay.getDisplay().getEnvironment());
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void update(float delta, HvlEnvironment environmentArg){
		if(active){
			tempEnvironment.set(environmentArg);
			boolean parentEnvironmentBlocked = tempEnvironment.isBlocked();
			for(int i = 0; i < components.size(); i++){
				tempEnvironment.setBlocked((i != components.size() - 1) || parentEnvironmentBlocked);
				components.get(i).update(delta, tempEnvironment);
			}
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void draw(float delta){
		if(active){
			for(HvlComponent component : components)
				component.draw(delta);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void operate(float delta){
		if(active){
			update(delta);
			draw(delta);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

	public static void operate(float delta, HvlEnvironment environmentArg){
		if(active){
			update(delta, environmentArg);
			draw(delta);
		}else throw new HvlChronology.InactiveException(LABEL, LAUNCH_CODE);
	}

}
