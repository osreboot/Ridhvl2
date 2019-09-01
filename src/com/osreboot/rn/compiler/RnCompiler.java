package com.osreboot.rn.compiler;

import java.util.HashSet;

import com.osreboot.ridhvl2.HvlAction;
import com.osreboot.ridhvl2.template.HvlChronology;
import com.osreboot.ridhvl2.template.HvlChronologyExit;
import com.osreboot.ridhvl2.template.HvlChronologyInitialize;
import com.osreboot.rn.functional.RnFunction;

public final class RnCompiler {

	public static final String LABEL = "RnCompiler";
	public static final int 
	CHRONO_INIT = HvlChronology.CHRONOLOGY_INIT_MIDDLE + HvlChronology.CHRONOLOGY_INIT_INTERVAL,
	CHRONO_EXIT = HvlChronology.CHRONOLOGY_EXIT_MIDDLE + HvlChronology.CHRONOLOGY_EXIT_INTERVAL,
	LAUNCH_CODE = 4,
	LAUNCH_CODE_RAW = 16;//2^4
	
	private static HashSet<RnMatcher> matchers = new HashSet<>();

	@HvlChronologyInitialize(label = LABEL, chronology = CHRONO_INIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_INIT = debug -> {
		//TODO register matchers here
	};

	@HvlChronologyExit(label = LABEL, chronology = CHRONO_EXIT, launchCode = LAUNCH_CODE)
	public static final HvlAction.A1<Boolean> ACTION_EXIT = debug -> {
		matchers.clear();
	};
	
	private RnCompiler(){}
	
	public static void registerMatcher(RnMatcher matcherArg){
		if(matchers.contains(matcherArg))
			throw new DuplicateMatcherException();
		else matchers.add(matcherArg);
	}
	
	public static RnFunction compile(String rnArg){
		String[] matchedRn = new String[rnArg.length()];
		
		return null;//TODO
	}
	
	public static class DuplicateMatcherException extends RuntimeException{
		private static final long serialVersionUID = -4314656720440083848L;
	}
	
	public static class InvalidSyntaxException extends RuntimeException{
		private static final long serialVersionUID = 7401912974979318832L;
	}
	
}
