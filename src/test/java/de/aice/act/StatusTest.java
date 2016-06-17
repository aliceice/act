package de.aice.act;

import java.util.stream.Stream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class StatusTest {

	@Test
	public void values() throws Exception {
		Stream.of(Status.values())
			  .forEach(status -> assertEquals(status, Status.valueOf(status.name())));

	}
}