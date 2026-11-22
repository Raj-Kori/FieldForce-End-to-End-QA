package hooks;

import fieldforce.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setUp(Scenario scenario) {
		System.out.println("\n------------------------------------------------");
		System.out.println(">>> STARTING SCENARIO: " + scenario.getName());
		System.out.println("------------------------------------------------");
		BaseClass.config();
	}

	@After
	public void tearDown() {
		BaseClass.teardown();
	}
}