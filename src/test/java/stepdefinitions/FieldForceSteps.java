package stepdefination;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import fieldforce.BaseClass;
import fieldforce.pages.customerpage;
import fieldforce.pages.dashboardpage;
import fieldforce.pages.loginpage;
import fieldforce.utils.Elements;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FieldForceSteps {

    private static final String VALID_USER = "8433593380";
    private static final String VALID_PASS = "Hacker007";

    private final loginpage loginPage = new loginpage();
    private final dashboardpage dashboardPage = new dashboardpage();
    private final customerpage customerPage = new customerpage();

    private WebDriver driver() {
        return BaseClass.getDriver();
    }

    private WebDriverWait driverWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(15));
    }

    @Given("the user is on the FieldForce login page")
    public void verifyOnLoginPage() {
        driverWait().until(ExpectedConditions.urlContains("/auth/login"));
        Assert.assertTrue(driver().getCurrentUrl().contains("/auth/login"),
                "Expected to be on login page, but was: " + driver().getCurrentUrl());
    }

    // --- 1) Login Journey Steps ---

    @When("the user enters username {string} and password {string}")
    public void enterLoginCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("clicks the Sign In button")
    public void clickSignInButton() {
        if (loginPage.isSignInEnabled()) {
            loginPage.clickSignIn();
        }
    }

    @Then("the login result should be {string}")
    public void verifyLoginResult(String expectedOutcome) {
        if ("success".equals(expectedOutcome)) {
            driverWait().until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/dashboard"),
                    ExpectedConditions.not(ExpectedConditions.urlContains("/auth/login"))
            ));
            Assert.assertTrue(loginPage.isLoggedIn() || driver().getCurrentUrl().contains("/dashboard"),
                    "Expected successful login redirect, but URL was: " + driver().getCurrentUrl());
        } else if ("error".equals(expectedOutcome)) {
            String message = loginPage.getToastOrAlertText();
            boolean stayedOnLogin = driver().getCurrentUrl().contains("/auth/login");
            Assert.assertTrue((message != null && !message.trim().isEmpty()) || stayedOnLogin,
                    "Expected error toast/message or staying on login page for invalid credentials");
        } else if ("disabled_or_err".equals(expectedOutcome)) {
            boolean blockedByButton = !loginPage.isSignInEnabled();
            boolean stayedOnLogin = driver().getCurrentUrl().contains("/auth/login");
            Assert.assertTrue(blockedByButton || stayedOnLogin, "Expected blank login attempt to be blocked");
        }
    }

    @Given("the user logs in with valid credentials")
    public void loginWithValidCredentials() {
        loginPage.login(VALID_USER, VALID_PASS);
        driverWait().until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.not(ExpectedConditions.urlContains("/auth/login"))
        ));
        Assert.assertTrue(loginPage.isLoggedIn() || driver().getCurrentUrl().contains("/dashboard"),
                "Failed to log in with valid credentials. Current URL: " + driver().getCurrentUrl());
    }

    // --- 2) PunchIn Toast / Popup Steps ---

    @When("the user performs PunchIn on the dashboard")
    public void performPunchIn() {
        dashboardPage.clickPunchIn();
    }

    @Then("a toast or popup message should be displayed after PunchIn")
    public void verifyPunchInToast() {
        String toastText = dashboardPage.getPunchInToastText();
        Assert.assertTrue(toastText != null && !toastText.trim().isEmpty(),
                "PunchIn toast/popup message should not be empty");
    }

    // --- 3) Add Customer Steps ---

    @Given("navigates to the Add Customer section")
    public void navigateToAddCustomer() {
        dashboardPage.openCustomersSection();
        customerPage.clickAddCustomer();
    }

    @When("the user adds a customer with name {string}, mobile {string}, email {string}, and address {string}")
    public void addCustomerWithDetails(String name, String mobile, String email, String address) {
        customerPage.addCustomer(name, mobile, email, address);
    }

    @Then("the add customer outcome should be {string} for {string}")
    public void verifyAddCustomerOutcome(String expectedOutcome, String customerName) {
        if ("created".equals(expectedOutcome)) {
            Assert.assertTrue(customerPage.isCustomerVisible(customerName),
                    "Expected newly added customer to appear: " + customerName);
        } else {
            String feedback = customerPage.getFeedbackMessage();
            Assert.assertTrue(feedback != null && !feedback.trim().isEmpty(),
                    "Expected validation feedback after submitting Add Customer");
        }
    }

    @Then("the Submit button enabled state should be {word}")
    public void verifySubmitEnabledState(String expectedState) {
        boolean expectedEnabled = Boolean.parseBoolean(expectedState);
        Assert.assertEquals(isElementEnabled(By.xpath("//button[@type='submit']")), expectedEnabled);
    }

    private boolean isElementEnabled(By locator) {
        try {
            WebElement el = Elements.waitforElementtobeVisible(locator);
            String disabledAttr = el.getAttribute("disabled");
            String classAttr = el.getAttribute("class");
            return el.isEnabled()
                    && disabledAttr == null
                    && (classAttr == null || !classAttr.contains("Mui-disabled"));
        } catch (Exception e) {
            return false;
        }
    }
}