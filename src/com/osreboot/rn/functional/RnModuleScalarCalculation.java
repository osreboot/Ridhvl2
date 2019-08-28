package com.osreboot.rn.functional;

import java.util.ArrayList;

public class RnModuleScalarCalculation extends RnModule{

	private String reference, sourceReference;
	private double sourceScalar;
	private ArrayList<RnTransform> transforms;

	public RnModuleScalarCalculation(String referenceArg, String sourceReferenceArg, ArrayList<RnTransform> transformsArg){
		reference = referenceArg;
		sourceReference = sourceReferenceArg;
		transforms = transformsArg;
	}

	public RnModuleScalarCalculation(String referenceArg, double sourceScalarArg, ArrayList<RnTransform> transformsArg){
		reference = referenceArg;
		sourceScalar = sourceScalarArg;
		transforms = transformsArg;
	}

	@Override
	public void run(RnFunction parentArg){
		double scalar = sourceReference.equals(null) ?
				sourceScalar : parentArg.scalars.get(sourceReference);
		for(RnTransform transform : transforms)
			scalar = transform.apply(scalar);

		parentArg.scalars.put(reference, scalar);
	}

}
