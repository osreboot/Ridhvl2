package com.osreboot.rn.functional;

import java.util.ArrayList;
import java.util.HashMap;

public final class RnFunction {

	HashMap<String, Double> inputs;
	HashMap<String, Double> outputs;
	
	HashMap<String, Double> scalars;
	HashMap<String, RnTransform> transforms;
	
	private ArrayList<RnModule> modules;
	
	public RnFunction(String[] inputsArg, String[] outputsArg, ArrayList<RnModule> modulesArg){
		inputs = new HashMap<>();
		outputs = new HashMap<>();
		
		for(String input : inputsArg)
			inputs.put(input, 0.0);
		
		for(String output : outputsArg)
			outputs.put(output, 0.0);
		
		scalars = new HashMap<>();
		transforms = new HashMap<>();
		
		modules = modulesArg;
	}
	
	public void supply(String keyArg, double valueArg){
		if(!inputs.containsKey(keyArg))
			throw new InvalidKeyException();
		else inputs.put(keyArg, valueArg);
	}
	
	public void execute(){
		scalars.clear();
		transforms.clear();
		for(RnModule module : modules)
			module.run(this);
	}
	
	public double retrieve(String keyArg){
		if(!outputs.containsKey(keyArg))
			throw new InvalidKeyException();
		else return outputs.get(keyArg);
	}
	
	public static class InvalidKeyException extends RuntimeException{
		private static final long serialVersionUID = -2612360682908939334L;
	}
	
}
