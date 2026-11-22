package fieldforce.test;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import fieldforce.BaseClass;


public class field_force_BaseTest {
	protected WebDriver driver;
@BeforeMethod
public void setup (Method method)
{
    System.out.println("\n------------------------------------------------");
    System.out.println(">>> STARTING TEST: " + method.getName());
    System.out.println("------------------------------------------------");
	BaseClass.config();
	driver=BaseClass.getDriver();
}
@AfterMethod
public void teardown()
{
	 BaseClass.teardown();
}
}
