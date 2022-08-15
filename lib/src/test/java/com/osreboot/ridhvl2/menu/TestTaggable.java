package com.osreboot.ridhvl2.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.osreboot.ridhvl2.template.HvlChronology;

public final class TestTaggable {

	private static final HvlTag<String> TEST_TAG_VALID = new HvlTag<>(String.class, "test.valid");
	private static final HvlTag<String> TEST_TAG_INVALID = new HvlTag<>(String.class, "test.invalid");

	private static class DummyTaggable extends HvlTaggable{
		private static final long serialVersionUID = -8081851904852816669L;

		private DummyTaggable(){
			super(TEST_TAG_VALID);
		}

	}
	
	@BeforeAll
	public static void setup(){
		HvlChronology.registerChronology(HvlType.class);
		HvlChronology.loadEvents(Long.MAX_VALUE, Long.MAX_VALUE);

		HvlChronology.initialize();
	}
	
	@AfterAll
	public static void cleanup(){
		HvlChronology.unloadEvents();
	}
	
	@Test
	public void testSetGetValid(){
		// Arrange
		DummyTaggable taggable = new DummyTaggable();

		// Act, Assert
		taggable.set(TEST_TAG_VALID, "VALUE");
		assertEquals("VALUE", taggable.get(TEST_TAG_VALID));
	}
	
	@Test
	public void testGetValidNull(){
		// Arrange
		DummyTaggable taggable = new DummyTaggable();

		// Act, Assert
		assertEquals(null, taggable.get(TEST_TAG_VALID));
	}

	@Test
	public void testSetInvalid(){
		// Arrange
		DummyTaggable taggable = new DummyTaggable();

		// Act, Assert
		assertThrows(HvlTaggable.TagMismatchException.class, () -> taggable.set(TEST_TAG_INVALID, "VALUE"));
	}
	
	@Test
	public void testGetInvalid(){
		// Arrange
		DummyTaggable taggable = new DummyTaggable();

		// Act, Assert
		assertThrows(HvlTaggable.TagMismatchException.class, () -> taggable.get(TEST_TAG_INVALID));
	}

}
