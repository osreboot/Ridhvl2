package com.osreboot.rn.functional;

public class RnModuleOut extends RnModule{

	private String reference;
	
	public RnModuleOut(String referenceArg){
		reference = referenceArg;
	}

	@Override
	public void run(RnFunction parentArg){
		parentArg.outputs.put(reference, parentArg.scalars.get(reference));
	}
	
}
