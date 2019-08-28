package com.osreboot.rn.functional;

public class RnModuleIn extends RnModule{

	private String reference;
	
	public RnModuleIn(String referenceArg){
		reference = referenceArg;
	}
	
	@Override
	public void run(RnFunction parentArg){
		parentArg.scalars.put(reference, parentArg.inputs.get(reference));
	}

}
