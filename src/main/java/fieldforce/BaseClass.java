package fieldforce;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseClass {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String BaseURL = "https://test.fieldforceconnect.com/auth/login";

    public static void config() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-background-networking");
        options.addArguments("--remote-allow-origins=*");

        WebDriver localDriver = new ChromeDriver(options);
        driver.set(localDriver);

        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        getDriver().get(BaseURL);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void teardown() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } finally {
                driver.remove();
            }
        }
    }
}