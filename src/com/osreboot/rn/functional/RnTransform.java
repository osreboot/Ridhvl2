package com.osreboot.rn.functional;

import com.osreboot.ridhvl2.HvlAction;

final class RnTransform {

	private Operation operation;
	private double scalar;
	
	protected RnTransform(Operation operationArg, double scalarArg){
		operation = operationArg;
		scalar = scalarArg;
	}
	
	public double apply(double xArg){
		return operation.apply.run(xArg, scalar);
	}
	
	public Operation getOperation(){
		return operation;
	}

	public double getScalar(){
		return scalar;
	}

	enum Operation{

		ADD((a, b) -> {return a + b;}),
		SUBTRACT((a, b) -> {return a - b;}),
		MULTIPLY((a, b) -> {return a * b;}),
		DIVIDE((a, b) -> {return a / b;});

		HvlAction.A2r<Double, Double, Double> apply;
		
		Operation(HvlAction.A2r<Double, Double, Double> applyArg){
			apply = applyArg;
		}

	}

}
