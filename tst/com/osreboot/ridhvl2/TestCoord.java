package com.osreboot.ridhvl2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class TestCoord {

	private static final int ABSOLUTE_TEST_RANGE = 5;

	@Test
	public void testConstructorDefault(){
		// Arrange, Act
		HvlCoord coord = new HvlCoord();

		// Assert
		assertEquals(0, coord.x, 0);
		assertEquals(0, coord.y, 0);
	}

	@Test
	public void testConstructorManual(){
		for(float x = -ABSOLUTE_TEST_RANGE; x <= ABSOLUTE_TEST_RANGE; x++){
			for(float y = -ABSOLUTE_TEST_RANGE; y <= ABSOLUTE_TEST_RANGE; y++){
				// Arrange, Act
				HvlCoord coord = new HvlCoord(x, y);

				// Assert
				assertEquals(x, coord.x, 0);
				assertEquals(y, coord.y, 0);
			}
		}
	}

	@Test
	public void testConstructorClone(){
		for(float x = -ABSOLUTE_TEST_RANGE; x <= ABSOLUTE_TEST_RANGE; x++){
			for(float y = -ABSOLUTE_TEST_RANGE; y <= ABSOLUTE_TEST_RANGE; y++){
				// Arrange
				HvlCoord parentCoord = new HvlCoord(x, y);

				// Act
				HvlCoord coord = new HvlCoord(parentCoord);

				// Assert
				assertEquals(x, coord.x, 0);
				assertEquals(y, coord.y, 0);
			}
		}
	}

	@Test
	public void testSet(){
		for(float x = -ABSOLUTE_TEST_RANGE; x <= ABSOLUTE_TEST_RANGE; x++){
			for(float y = -ABSOLUTE_TEST_RANGE; y <= ABSOLUTE_TEST_RANGE; y++){
				// Arrange
				HvlCoord coord = new HvlCoord();

				// Act
				coord.set(x, y);

				// Assert
				assertEquals(x, coord.x, 0);
				assertEquals(y, coord.y, 0);
			}
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals(){
		for(float x = -ABSOLUTE_TEST_RANGE; x <= ABSOLUTE_TEST_RANGE; x++){
			for(float y = -ABSOLUTE_TEST_RANGE; y <= ABSOLUTE_TEST_RANGE; y++){
				for(float x2 = -ABSOLUTE_TEST_RANGE; x2 <= ABSOLUTE_TEST_RANGE; x2++){
					for(float y2 = -ABSOLUTE_TEST_RANGE; y2 <= ABSOLUTE_TEST_RANGE; y2++){
						// Arrange
						HvlCoord coord = new HvlCoord(x, y);

						// Act, Assert
						if(x == x2 && y == y2)
							assertTrue(coord.equals(new HvlCoord(x2, y2)));
						else assertFalse(coord.equals(new HvlCoord(x2, y2)));
						assertFalse(coord.equals(null));
						assertFalse(coord.equals(""));
					}
				}
			}
		}
	}

	@Test
	public void testToString(){
		for(float x = -ABSOLUTE_TEST_RANGE; x <= ABSOLUTE_TEST_RANGE; x++){
			for(float y = -ABSOLUTE_TEST_RANGE; y <= ABSOLUTE_TEST_RANGE; y++){
				// Arrange
				HvlCoord coord = new HvlCoord(x, y);

				// Act, Assert
				assertEquals("[" + x + "," + y + "]", coord.toString());
			}
		}
	}

}
