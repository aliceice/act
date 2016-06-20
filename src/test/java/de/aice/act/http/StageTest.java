package de.aice.act.http;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import de.aice.act.ActException;
import org.junit.Test;

import static de.aice.act.http.Stage.safeStage;
import static de.aice.act.misc.Exceptions.unchecked;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public final class StageTest {

	@Test
	public void basic_stage_should_not_catch_exceptions() throws Exception {
		new Thread(() -> unchecked(() -> {
			Stage stage = Stage.basicStage(r -> {throw new ActException();});
			stage.start(Exit.never());
		})).start();
		try {
			new JdkRequest("http://localhost:8080/").fetch();
			fail("Should not catch exceptions");
		} catch (Exception e) {
			assertEquals("Failed GET request to http://localhost:8080/", e.getMessage());
		}
	}

	@Test
	public void safe_stage_should_continue_to_run_if_an_exception_is_thrown() throws Exception {
		String message = "Failure!";
		Exit.OnSignal exit = new Exit.OnSignal();
		new Thread(() -> unchecked(() -> {
			Stage stage = safeStage(r -> {throw new ActException(message);});
			stage.start(exit);
		})).start();
		
		Response response = new JdkRequest("http://localhost:8080/").fetch();
		
		assertEquals(500, response.status());
		assertEquals("Internal Server Error", response.reason());
		assertEquals(message, response.body());
		
		exit.stop();
		Thread.sleep(1000);
	}
}