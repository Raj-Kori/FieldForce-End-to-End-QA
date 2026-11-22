package fieldforce.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fieldforce.BaseClass;
import fieldforce.utils.Elements;

public class loginpage {

    private final By emailOrMobileInput = By.xpath(
            "//input[@placeholder='Enter Email or Mobile Number' or contains(@placeholder,'Email') or contains(@placeholder,'Mobile') or @type='tel' or @name='email' or @name='mobile' or @name='username' or @name='emailOrMobile'] | //label[contains(normalize-space(),'Email') or contains(normalize-space(),'Mobile')]/following::input[1]"
    );
    private final By passwordInput = By.xpath(
            "//input[@placeholder='Password' or @type='password' or @name='password']"
    );
    private final By signInButton = By.xpath(
            "//button[@type='submit' or normalize-space()='Sign In' or normalize-space()='Sign in']"
    );
    private final By toastOrAlertMessage = By.xpath(
            "//*[@data-rht-toaster]//*[string-length(normalize-space()) > 0] | //*[@role='status'] | //*[@role='alert'] | //*[contains(@class,'MuiFormHelperText-root') and string-length(normalize-space()) > 0] | //*[contains(@class,'error') and string-length(normalize-space()) > 0 and not(self::style) and not(self::script)]"
    );
    private final By dashboardIndicator = By.xpath(
            "//span[normalize-space()='Dashboard'] | //p[normalize-space()='Dashboard'] | //*[contains(normalize-space(),\"Today's Stats\")]"
    );

    public void enterUsername(String username) {
        String currentUrl = BaseClass.getDriver().getCurrentUrl();
        if (currentUrl.contains("/customers") || currentUrl.contains("/dashboard")) {
            return;
        }
        WebElement field = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(25))
                .until(ExpectedConditions.visibilityOfElementLocated(emailOrMobileInput));
        field.clear();
        if (username != null && !username.isEmpty()) {
            field.sendKeys(username);
        }
    }

    public void enterPassword(String password) {
        if (password != null && !password.isEmpty()) {
            Elements.entertext(passwordInput, password);
        } else {
            Elements.waitforElementtobeVisible(passwordInput).clear();
        }
    }

    public boolean isSignInEnabled() {
        WebElement btn = Elements.waitforElementtobeVisible(signInButton);
        String disabledAttr = btn.getAttribute("disabled");
        String classAttr = btn.getAttribute("class");
        return btn.isEnabled()
                && disabledAttr == null
                && (classAttr == null || !classAttr.contains("Mui-disabled"));
    }

    public void clickSignIn() {
        Elements.clickElement(signInButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        if (isSignInEnabled()) {
            clickSignIn();
        }
    }

    public String getToastOrAlertText() {
        try {
            WebElement msg = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(8))
                    .until(ExpectedConditions.visibilityOfElementLocated(toastOrAlertMessage));
            return msg.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLoggedIn() {
        WebDriverWait wait = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/dashboard"),
                    ExpectedConditions.visibilityOfElementLocated(dashboardIndicator)
            ));
        } catch (Exception ignored) {
        }
        String currentUrl = BaseClass.getDriver().getCurrentUrl();
        return currentUrl.contains("/dashboard")
                || (!currentUrl.contains("/auth/login") && !BaseClass.getDriver().findElements(dashboardIndicator).isEmpty());
    }
}