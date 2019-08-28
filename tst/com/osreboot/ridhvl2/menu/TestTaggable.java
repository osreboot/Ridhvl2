package com.osreboot.ridhvl2.menu;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class TestTaggable {

	private static final HvlTag<String> TEST_TAG_VALID = new HvlTag<>(String.class, "test.valid");
	private static final HvlTag<String> TEST_TAG_INVALID = new HvlTag<>(String.class, "test.invalid");

	private static class DummyTaggable extends HvlTaggable{
		private static final long serialVersionUID = -8081851904852816669L;

		private DummyTaggable(){
			super(TEST_TAG_VALID);
		}

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

	@Test(expected = HvlTaggable.TagMismatchException.class)
	public void testSetInvalid(){
		// Arrange
		DummyTaggable taggable = new DummyTaggable();

		// Act, Assert
		taggable.set(TEST_TAG_INVALID, "VALUE");
	}
	
	@Test(expected = HvlTaggable.TagMismatchException.class)
	public void testGetInvalid(){
		// Arrange
		DummyTaggable taggable = new DummyTaggable();

		// Act, Assert
		taggable.get(TEST_TAG_INVALID);
	}

}
