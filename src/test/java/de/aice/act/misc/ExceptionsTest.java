package de.aice.act.misc;

import java.io.IOException;
import org.junit.Test;

import static de.aice.act.misc.Exceptions.unchecked;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public final class ExceptionsTest {

	@Test
	public void unchecked_get_from_IOSupplier_should_return_value_if_no_exception_is_thrown() throws Exception {
		String expected = "Hello, World!";
		String result = unchecked(() -> expected);
		assertEquals(expected, result);
	}

	@Test
	public void unchecked_get_from_IOSupplier_should_throw_RuntimeException_if_it_fails() throws Exception {
		String message = "Hello, there!";
		try {
			UnsafeSupplier<String> supplier = () -> { throw new IOException(message); };
			unchecked(supplier);
			fail("unchecked should throw RuntimeException.");
		} catch (RuntimeException e) {
			assertEquals(message, e.getMessage());
		}
	}

	@Test
	public void unchecked_UnsafeActivity_should_do_nothing_more_than_executing_the_activity() throws Exception {
		Object[] store = new Object[1];
		unchecked(() -> store[0] = "Howdy!");
		assertNotNull(store[0]);
	}

	@Test
	public void unchecked_UnsafeActivity_should_throw_RuntimeException_if_it_fails() throws Exception {
		String message = "Hello, there!";
		try {
			UnsafeActivity activity = () -> { throw new IOException(message); };
			unchecked(activity);
			fail("unchecked should throw RuntimeException.");
		} catch (RuntimeException e) {
			assertEquals("java.io.IOException: " + message, e.getMessage());
		}
	}
}