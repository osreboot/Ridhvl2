package com.osreboot.ridhvl2.loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;

public abstract class HvlLoader<T> {

	public static final String LABEL = "HvlLoader";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_MIDDLE + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_MIDDLE + HvlChronology.CHRONOLOGY_EXIT_INTERVAL,
	LAUNCH_CODE = 3,
	LAUNCH_CODE_RAW = 8;//2^3

	public static boolean debug = false;

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		HvlLoader.debug = debug;
	};
	
	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		clearLoaders();
	};

	public static final String 
	PATH_DEFAULT = "res",
	TYPELABEL_TEXTURE = "TEXTURE",
	TYPELABEL_SOUND = "SOUND";

	private static ArrayList<HvlLoader<?>> loaders = new ArrayList<>();

	public static void addLoader(HvlLoader<?> loaderArg){
		for(HvlLoader<?> l : loaders){
			if(l.getTypeLabel().equalsIgnoreCase(loaderArg.getTypeLabel())) throw new DuplicateTypeLabelException();
			for(String s : l.typeExtensions){
				for(String s2 : loaderArg.typeExtensions){
					if(s.equalsIgnoreCase(s2)) throw new TypeOwnershipException();
				}
			}
		}
		HvlLogger.println(debug, "Adding a loader with type label " + loaderArg.getTypeLabel() + ".");
		loaders.add(loaderArg);
	}

	public static List<HvlLoader<?>> getLoaders(){
		return Collections.unmodifiableList(loaders);
	}

	public static void clearLoaders(){
		HvlLogger.println(debug, "Clearing loader references!");
		loaders.clear();
	}

	protected ArrayList<T> resources;
	protected String defaultPath;

	protected String typeLabel;
	protected String[] typeExtensions;

	public HvlLoader(String defaultPathArg, String typeLabelArg, String typeExtensionArg, String... typeExtensionsArg){
		resources = new ArrayList<>();
		defaultPath = defaultPathArg;
		typeLabel = typeLabelArg;

		ArrayList<String> typeExtensionsTemp = new ArrayList<>();
		typeExtensionsTemp.add(typeExtensionArg);
		Collections.addAll(typeExtensionsTemp, typeExtensionsArg);
		typeExtensions = typeExtensionsTemp.toArray(new String[]{});
	}

	public final boolean load(String pathArg){
		return load(defaultPath, pathArg);
	}

	public abstract boolean load(String basePathArg, String pathArg);

	public boolean canLoad(String pathArg){
		for(String s : typeExtensions){
			if(pathArg.toUpperCase().endsWith(s.toUpperCase())) return true;
		}
		return false;
	}

	public T get(int indexArg){
		return resources.get(indexArg);
	}

	public ArrayList<T> getResources(){
		return resources;
	}

	public String getDefaultPath(){
		return defaultPath;
	}

	public String getTypeLabel(){
		return typeLabel;
	}

	public String[] getTypeExtensions(){
		return typeExtensions;
	}

	public static class DuplicateTypeLabelException extends RuntimeException{
		private static final long serialVersionUID = 3500327701596226197L;
	}

	public static class TypeOwnershipException extends RuntimeException{
		private static final long serialVersionUID = -8549555216434970336L;
	}

}
