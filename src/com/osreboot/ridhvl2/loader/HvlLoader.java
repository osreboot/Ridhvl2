package com.osreboot.ridhvl2.loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.HvlLogger;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;

/**
 * Manages the loading and storing of resources. {@linkplain HvlLoader} itself is capable of storing a list of
 * loaders (referred to as "active" loaders) that are all guaranteed to be unique in type, as a convenience feature.
 * A loader handles the loading and storing of a single object type (as specified by <code>T</code>). Each stored
 * object is referenced by the integer order in which it was loaded, relative to the other objects loaded by that
 * specific loader. 
 * 
 * <p>
 * 
 * Each HvlLoader child class must implement functionality for {@linkplain #load(String, String)},
 * where it loads the resource specified by the concatenation of the two <code>String</code> arguments, with
 * the value of <code>File.separator</code> in between them (<code>basePathArg + File.separator + pathArg</code>), 
 * and stores the loaded resource in the <code>resources</code> array (with <code>resources.add(T)</code>). It is
 * strongly recommended that this implementation be strictly followed, as deviation may confuse users who are 
 * accustomed to these conventions.
 * 
 * @author os_reboot
 *
 * @param <T> the loader's resource type
 */
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

	/**
	 * A path commonly used to store resources, generally acts as the default path for loaders.
	 */
	public static final String 
	PATH_DEFAULT = "res";
	
	/**
	 * Label that identifies a loader's resource type. Two loaders with the same label cannot be active (in
	 * {@linkplain HvlLoader}'s global loader list) at the same time.
	 */
	public static final String 
	TYPELABEL_TEXTURE = "TEXTURE",
	TYPELABEL_SOUND = "SOUND",
	TYPELABEL_FONT = "FONT";

	private static ArrayList<HvlLoader<?>> loaders = new ArrayList<>();

	/**
	 * Adds a loader instance to the "active" loader list. All loaders in this list are guaranteed to be
	 * type-unique (as specified by the loader's {@linkplain #getTypeLabel()}). If the added loader is not 
	 * type-unique a {@linkplain DuplicateTypeLabelException} is thrown. If the added loader shares a supported
	 * resource extension with another active loader (as specified by {@linkplain #getTypeExtensions()}) a
	 * {@linkplain DuplicateTypeExtensionException} is thrown.
	 * 
	 * @param loaderArg the HvlLoader instance to add to the active list
	 * @throws DuplicateTypeLabelException if the added loader is not type-unique (as specified by the loader's 
	 * {@linkplain #getTypeLabel()})
	 * @throws DuplicateTypeExtensionException if the added loader shares a supported resource extension with 
	 * another active loader (as specified by {@linkplain #getTypeExtensions()})
	 */
	public static void addLoader(HvlLoader<?> loaderArg){
		for(HvlLoader<?> l : loaders){
			if(l.getTypeLabel().equalsIgnoreCase(loaderArg.getTypeLabel())) throw new DuplicateTypeLabelException();
			for(String s : l.typeExtensions){
				for(String s2 : loaderArg.typeExtensions){
					if(s.equalsIgnoreCase(s2)) throw new DuplicateTypeExtensionException();
				}
			}
		}
		HvlLogger.println(debug, "Adding a loader with type label " + loaderArg.getTypeLabel() + ".");
		loaders.add(loaderArg);
	}

	/**
	 * @return an unmodifiable version of the active loader list
	 */
	public static List<HvlLoader<?>> getLoaders(){
		return Collections.unmodifiableList(loaders);
	}

	/**
	 * Clears the active loader list. This should only be used when references to the loaders' resources are no
	 * longer needed.
	 */
	public static void clearLoaders(){
		HvlLogger.println(debug, "Clearing loader references!");
		loaders.forEach(l -> l.cleanup());
		loaders.clear();
	}

	protected ArrayList<T> resources;
	protected final String defaultPath;

	protected final String typeLabel;
	protected final String[] typeExtensions;

	/**
	 * Constructs an instance of HvlLoader with the specified <code>defaultPath</code>, <code>typeLabel</code> and
	 * base <code>typeExtension</code>. Only one <code>typeExtension</code> is required, however more may be added
	 * with the <code>typeExtensionsArg</code> vararg. Listed type extensions are for identification purposes only,
	 * however they should reflect the actual file extensions that the loader is capable of handling (utilities
	 * that use {@linkplain #canLoad(String)} will actively discard the resource if its extension is not explicitly
	 * listed).
	 * 
	 * @param defaultPathArg the base resource search path for the HvlLoader (usually set to {@linkplain #PATH_DEFAULT})
	 * @param typeLabelArg the label that identifies the intended resource type of the HvlLoader
	 * @param typeExtensionArg the base supported resource file extension
	 * @param typeExtensionsArg optional additional supported resource file extensions
	 */
	public HvlLoader(String defaultPathArg, String typeLabelArg, String typeExtensionArg, String... typeExtensionsArg){
		resources = new ArrayList<>();
		defaultPath = defaultPathArg;
		typeLabel = typeLabelArg;

		ArrayList<String> typeExtensionsTemp = new ArrayList<>();
		typeExtensionsTemp.add(typeExtensionArg);
		Collections.addAll(typeExtensionsTemp, typeExtensionsArg);
		typeExtensions = typeExtensionsTemp.toArray(new String[]{});
	}

	/**
	 * An alternative to {@linkplain #load(String, String)} that uses the loader's <code>defaultPath</code> as the
	 * base path. It is recommended to use this method over {@linkplain #load(String, String)}.
	 * 
	 * @param pathArg the complete path and file name to load (including extension)(e.g. "<code>REEEE.wav</code>"), 
	 * excluding the default path of the loader (see {@linkplain #getDefaultPath()})
	 * @return <code>true</code> if the load operation succeeded
	 */
	public final boolean load(String pathArg){
		return load(defaultPath, pathArg);
	}

	/**
	 * Loads a given resource into the loader's <code>resources</code> array. Searches in the file location
	 * specified by the concatenation of <code>basePathArg</code> and <code>pathArg</code>, with
	 * <code>File.separator</code> in between them (<code>basePathArg + File.separator + pathArg</code>). This
	 * operation usually throws an exception if the file is not found, however the exception type generally varies 
	 * depending on the HvlLoader implementation. For consistency, it is recommended that the alternative method
	 * {@linkplain #load(String)} be used over this one.
	 * 
	 * @param basePathArg the resource's base path (excluding file separator)(e.g. folder name, "<code>res</code>")
	 * @param pathArg the sub-path and file name to load (including extension)(e.g. "<code>REEEE.wav</code>")
	 * @return <code>true</code> if the load operation succeeded
	 */
	public abstract boolean load(String basePathArg, String pathArg);

	/**
	 * Checks if the HvlLoader supports the file extension of a resource (as specified by the loader's 
	 * {@linkplain HvlLoader#getTypeExtensions() getTypeExtensions()}).
	 * 
	 * @param pathArg the resource's complete file path (including name and extension)
	 * @return <code>true</code> if the resource's extension is supported by the HvlLoader
	 */
	public boolean canLoad(String pathArg){
		for(String s : typeExtensions){
			if(pathArg.toUpperCase().endsWith(s.toUpperCase())) return true;
		}
		return false;
	}

	/**
	 * Fetches a loaded resource. The <code>indexArg</code> represents the order in which the resource was loaded 
	 * (relative to its respective HvlLoader instance), with <code>0</code> representing the first resource that was
	 * loaded. For more on loading resources, see {@linkplain #load(String, String)}.
	 * 
	 * @param indexArg the index of the resource to get
	 * @return the resource specified by <code>indexArg</code>
	 */
	public T get(int indexArg){
		return resources.get(indexArg);
	}
	
	/**
	 * Called at the end of a loader's lifecycle to release any resources and terminate the loader cleanly.
	 */
	public void cleanup(){}

	/**
	 * @return all resources previously loaded by the HvlLoader
	 */
	public ArrayList<T> getResources(){
		return resources;
	}

	/**
	 * @return the HvlLoader's default resource search path
	 */
	public String getDefaultPath(){
		return defaultPath;
	}

	/**
	 * @return the HvlLoader's type label
	 */
	public String getTypeLabel(){
		return typeLabel;
	}

	/**
	 * @return the array of supported resource file extensions
	 */
	public String[] getTypeExtensions(){
		return typeExtensions;
	}

	/**
	 * Thrown if a HvlLoader added to the active loader list with {@linkplain HvlLoader#addLoader(HvlLoader)} is 
	 * not type-unique (as specified by the loader's {@linkplain HvlLoader#getTypeLabel() getTypeLabel()}).
	 * 
	 * @author os_reboot
	 *
	 */
	public static class DuplicateTypeLabelException extends RuntimeException{
		private static final long serialVersionUID = 3500327701596226197L;
	}

	/**
	 * Thrown if a HvlLoader added to the active loader list with {@linkplain HvlLoader#addLoader(HvlLoader)} is 
	 * not fully type-extension-unique (as specified by the loader's 
	 * {@linkplain HvlLoader#getTypeExtensions() getTypeExtensions()}).
	 * 
	 * @author os_reboot
	 *
	 */
	public static class DuplicateTypeExtensionException extends RuntimeException{
		private static final long serialVersionUID = -8549555216434970336L;
	}

}
