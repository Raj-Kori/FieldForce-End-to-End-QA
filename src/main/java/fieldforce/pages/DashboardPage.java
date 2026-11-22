package fieldforce.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fieldforce.BaseClass;
import fieldforce.utils.Elements;

public class dashboardpage {

    private final By spinner = By.xpath(
            "//*[contains(@class,'MuiCircularProgress') or contains(@class,'spinner') or contains(@class,'loading')]"
    );
    private final By punchInElement = By.xpath(
            "//*[self::button or self::a or self::div or self::span][@role='button' or contains(translate(translate(normalize-space(), '- ', ''), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'punchin') or contains(translate(translate(normalize-space(), '- ', ''), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'punch') or contains(translate(translate(normalize-space(), '- ', ''), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'checkin')] | //*[@aria-label][contains(translate(translate(@aria-label, '- ', ''), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'punch') or contains(translate(translate(@aria-label, '- ', ''), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'check')]"
    );
    private final By toastMessage = By.xpath(
            "//*[@data-rht-toaster]//*[string-length(normalize-space()) > 0] | //*[@role='status'] | //*[@role='alert'] | //*[contains(@class,'toast') or contains(@class,'Toast') or contains(@class,'Snackbar') or contains(@class,'MuiAlert')][string-length(normalize-space()) > 0]"
    );
    private final By myCustomersToggle = By.xpath(
            "//a[contains(@class,'_navLink_')][.//span[normalize-space()='My Customers']]"
    );
    private final By myCustomerSubLink = By.xpath(
            "//a[@href='/customers' and .//span[normalize-space()='My Customer']]"
    );

    private void waitForSpinnerToFinish() {
        try {
            new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(15))
                    .until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        } catch (Exception ignored) {
        }
    }

    public void clickPunchIn() {
        waitForSpinnerToFinish();
        WebElement btn = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(25))
                .until(ExpectedConditions.presenceOfElementLocated(punchInElement));
        try {
            ((JavascriptExecutor) BaseClass.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", btn);
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) BaseClass.getDriver()).executeScript("arguments[0].click();", btn);
        }
    }

    public String getPunchInToastText() {
        try {
            new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(12))
                    .until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
            return Elements.getElementText(toastMessage).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void openCustomersSection() {
        waitForSpinnerToFinish();
        try {
            WebElement subLink = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(myCustomerSubLink));
            subLink.click();
        } catch (Exception e) {
            WebElement parentToggle = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(20))
                    .until(ExpectedConditions.presenceOfElementLocated(myCustomersToggle));
            try {
                ((JavascriptExecutor) BaseClass.getDriver())
                        .executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].click();", parentToggle);
            } catch (Exception ex) {
                parentToggle.click();
            }
            WebElement subLink = new WebDriverWait(BaseClass.getDriver(), Duration.ofSeconds(15))
                    .until(ExpectedConditions.elementToBeClickable(myCustomerSubLink));
            subLink.click();
        }
    }
}