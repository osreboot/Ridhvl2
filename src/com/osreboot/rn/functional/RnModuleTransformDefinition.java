package com.osreboot.rn.functional;

public class RnModuleTransformDefinition extends RnModule{

	private String reference;
	private RnTransform transform;
	
	public RnModuleTransformDefinition(String referenceArg, RnTransform transformArg){
		reference = referenceArg;
		transform = transformArg;
	}
	
	@Override
	public void run(RnFunction parentArg){
		parentArg.transforms.put(reference, transform);
	}

}
