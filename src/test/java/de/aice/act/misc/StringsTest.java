package de.aice.act.misc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class StringsTest {

	@Test
	public void defaultString_returns_EMPTY_if_input_is_null() throws Exception {
		assertEquals(Strings.EMPTY, Strings.defaultString(null));
	}
}