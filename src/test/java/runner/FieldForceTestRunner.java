package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/Features/fieldforce_complete.feature",
    glue = {"stepdefination", "hooks"},
    tags = "@login or @punchin or @addCustomer",
    plugin = {"pretty", "html:target/fieldforce-report.html"}
)
public class fieldforceTestRunner extends AbstractTestNGCucumberTests {
}