package com.osreboot.rn.functional;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.osreboot.rn.functional.RnTransform.Operation;

public final class TestImplRnFunction {

	private static final int ABSOLUTE_TEST_RANGE = 100;

	private static final String 
	REFERENCE_IN = "x",
	REFERENCE_OUT = "y";

	private static RnFunction createSingleVariableFunctionWithTransforms(RnTransform... transformsArg){
		ArrayList<RnModule> modules = new ArrayList<>();
		modules.add(new RnModuleIn(REFERENCE_IN));

		ArrayList<RnTransform> scalarCalculationTransforms = new ArrayList<>();
		for(RnTransform transform : transformsArg)
			scalarCalculationTransforms.add(transform);

		modules.add(new RnModuleScalarCalculation(REFERENCE_OUT, REFERENCE_IN, scalarCalculationTransforms));
		modules.add(new RnModuleOut(REFERENCE_OUT));

		return new RnFunction(new String[]{REFERENCE_IN}, new String[]{REFERENCE_OUT}, modules);
	}

	/**
	 * Equivalent script:
	 * 
	 * in x;
	 * y=x;
	 * out y;
	 */
	@Test
	public void testImplRnFunctionEquals(){
		// Arrange
		RnFunction function = createSingleVariableFunctionWithTransforms();

		for(double i = -ABSOLUTE_TEST_RANGE; i <= ABSOLUTE_TEST_RANGE; i++){
			// Act
			function.supply(REFERENCE_IN, i);
			function.execute();

			// Assert
			assertEquals(i, function.retrieve(REFERENCE_OUT), 0);
		}
	}

	/**
	 * Equivalent script:
	 * 
	 * in x;
	 * y=x>*2;
	 * out y;
	 */
	@Test
	public void testImplRnFunctionSingleTransform(){
		// Arrange
		RnFunction function = createSingleVariableFunctionWithTransforms(
				new RnTransform(Operation.MULTIPLY, 2)
				);

		for(double i = -ABSOLUTE_TEST_RANGE; i <= ABSOLUTE_TEST_RANGE; i++){
			// Act
			function.supply(REFERENCE_IN, i);
			function.execute();

			// Assert
			assertEquals(i * 2, function.retrieve(REFERENCE_OUT), 0);
		}
	}

	/**
	 * Equivalent script:
	 * 
	 * in x;
	 * y=x>/2>-1;
	 * out y;
	 */
	@Test
	public void testImplRnFunctionDoubleTransform(){
		// Arrange
		RnFunction function = createSingleVariableFunctionWithTransforms(
				new RnTransform(Operation.DIVIDE, 2),
				new RnTransform(Operation.SUBTRACT, 1)
				);

		for(double i = -ABSOLUTE_TEST_RANGE; i <= ABSOLUTE_TEST_RANGE; i++){
			// Act
			function.supply(REFERENCE_IN, i);
			function.execute();

			// Assert
			assertEquals((i / 2.0) - 1, function.retrieve(REFERENCE_OUT), 0);
		}
	}
	
	/**
	 * Equivalent script:
	 * 
	 * in x;
	 * y=x>+1>*2;
	 * out y;
	 */
	@Test
	public void testImplRnFunctionDoubleTransformOutOfOrder(){
		// Arrange
		RnFunction function = createSingleVariableFunctionWithTransforms(
				new RnTransform(Operation.ADD, 1),
				new RnTransform(Operation.MULTIPLY, 2)
				);

		for(double i = -ABSOLUTE_TEST_RANGE; i <= ABSOLUTE_TEST_RANGE; i++){
			// Act
			function.supply(REFERENCE_IN, i);
			function.execute();

			// Assert
			assertEquals((i + 1) * 2, function.retrieve(REFERENCE_OUT), 0);
		}
	}

}
