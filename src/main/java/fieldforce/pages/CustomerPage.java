package fieldforce.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fieldforce.BaseClass;

public class customerpage {

    private final By spinner = By.xpath(
            "//*[contains(@class,'MuiCircularProgress') or contains(@class,'spinner') or contains(@class,'loading')]"
    );
    private final By myCustomersToggle = By.xpath(
            "//a[contains(@class,'_navLink_')][.//span[normalize-space()='My Customers']]"
    );
    private final By myCustomerSubLink = By.xpath(
            "//a[@href='/customers' and .//span[normalize-space()='My Customer']]"
    );

    // Exact locators from Field Force Connect /customers
    private final By manageButton = By.xpath(
            "//button[@type='button' and @aria-haspopup='true' and contains(normalize-space(), 'Manage')]"
    );
    private final By newCustomerMenuItem = By.xpath(
            "//span[normalize-space()='New Customer'] | //*[normalize-space()='New Customer' and not(self::script) and not(self::style)]"
    );
    private final By leadCustomerNameInput = By.xpath(
            "//input[@name='LeadName' or @placeholder='Enter Lead/Customer Name']"
    );
    private final By contactPersonNameInput = By.xpath(
            "//input[@name='PersonName' or @placeholder='Enter Contact Person Name']"
    );
    private final By mobileInput = By.xpath(
            "//input[@name='MobileNo' or @placeholder='Enter Mobile No']"
    );
    private final By emailInput = By.xpath(
            "//input[@name='Email' or @name='email' or @placeholder='Enter Email' or contains(@placeholder,'Email')]"
    );
    private final By addressInput = By.xpath(
            "//input[@name='Address' or @name='address' or @placeholder='Click to select address' or contains(translate(@placeholder,'A','a'),'address')] | //textarea[@name='Address' or @name='address' or contains(translate(@placeholder,'A','a'),'address')]"
    );
    private final By saveButton = By.xpath(
            "//button[@type='button'][.//span[normalize-space()='Save'] or normalize-space()='Save'] | //button[@type='submit']"
    );

    // Table search & feedback
    private final By searchInput = By.xpath(
            "//label[normalize-space()='Search']/following::input[1] | //input[@type='text' and contains(@class,'MuiInputBase-input')]"
    );
    private final By feedbackLocator = By.xpath(
            "//*[@data-rht-toaster]//*[string-length(normalize-space()) > 0] | //*[@role='alert'] | //*[@role='status'] | //*[contains(@class,'MuiFormHelperText-root') and string-length(normalize-space()) > 0]"
    );

    private WebDriver driver() {
        return BaseClass.getDriver();
    }

    private WebDriverWait waitFor(long seconds) {
        return new WebDriverWait(driver(), Duration.ofSeconds(seconds));
    }

    private void waitForSpinnerToFinish() {
        try {
            waitFor(15).until(ExpectedConditions.invisibilityOfElementLocated(spinner));
        } catch (Exception ignored) {
        }
    }

    public void openMyCustomerList() {
        waitForSpinnerToFinish();
        if (!driver().getCurrentUrl().contains("/customers")) {
            try {
                WebElement subLink = waitFor(3).until(ExpectedConditions.elementToBeClickable(myCustomerSubLink));
                scrollAndClick(subLink);
            } catch (Exception e) {
                WebElement parentToggle = waitFor(20).until(ExpectedConditions.presenceOfElementLocated(myCustomersToggle));
                scrollAndClick(parentToggle);

                WebElement subLink = waitFor(15).until(ExpectedConditions.elementToBeClickable(myCustomerSubLink));
                scrollAndClick(subLink);
            }
            try {
                waitFor(20).until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("/customers"),
                        ExpectedConditions.presenceOfElementLocated(manageButton)
                ));
            } catch (Exception e) {
                String base = driver().getCurrentUrl().replaceAll("/[^/]*$", "");
                driver().get(base + "/customers");
            }
        }
        waitForSpinnerToFinish();
        waitFor(30).until(ExpectedConditions.visibilityOfElementLocated(manageButton));
    }

    public void openNewCustomerModal() {
        openMyCustomerList();

        try {
            WebElement existingMenuItem = waitFor(3).until(ExpectedConditions.presenceOfElementLocated(newCustomerMenuItem));
            if (existingMenuItem.isDisplayed()) {
                scrollAndClick(existingMenuItem);
                waitFor(15).until(ExpectedConditions.visibilityOfElementLocated(leadCustomerNameInput));
                return;
            }
        } catch (Exception ignored) {
        }

        WebElement manageBtn = waitFor(20).until(ExpectedConditions.presenceOfElementLocated(manageButton));
        scrollAndClick(manageBtn);

        WebElement newCust = waitFor(20).until(ExpectedConditions.presenceOfElementLocated(newCustomerMenuItem));
        scrollAndClick(newCust);

        waitFor(15).until(ExpectedConditions.visibilityOfElementLocated(leadCustomerNameInput));
    }

    public void clickAddCustomer() {
        openNewCustomerModal();
    }

    public void addCustomer(String name, String mobile, String email, String address) {
        addCustomer(name, "", mobile, email, address);
    }

    public void addCustomer(String name, String contactPerson, String mobile, String email, String address) {
        if (name != null && !name.isEmpty()) {
            typeIfPresent(leadCustomerNameInput, name);
        }
        if (contactPerson != null && !contactPerson.isEmpty()) {
            typeIfPresent(contactPersonNameInput, contactPerson);
        }
        if (mobile != null && !mobile.isEmpty()) {
            typeIfPresent(mobileInput, mobile);
        }
        if (email != null && !email.isEmpty()) {
            typeIfPresent(emailInput, email);
        }
        if (address != null && !address.isEmpty()) {
            typeIfPresent(addressInput, address);
        }
        clickSave();
    }

    public void clickSave() {
        WebElement saveBtn = waitFor(15).until(ExpectedConditions.presenceOfElementLocated(saveButton));
        scrollAndClick(saveBtn);
    }

    public String getFeedbackMessage() {
        try {
            WebElement msg = waitFor(12).until(ExpectedConditions.visibilityOfElementLocated(feedbackLocator));
            return msg.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isCustomerVisible(String customerName) {
        waitForSpinnerToFinish();
        By[] searchLocators = new By[] {
                By.xpath("//input[@placeholder='Search' or contains(@placeholder,'Search')]"),
                By.xpath("//label[normalize-space()='Search']/following::input[1]"),
                By.xpath("//input[@type='text' and contains(@class,'MuiInputBase-input')]")
        };

        for (By locator : searchLocators) {
            try {
                WebElement searchBox = new WebDriverWait(driver(), Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOfElementLocated(locator));
                searchBox.clear();
                searchBox.sendKeys(customerName);
                searchBox.sendKeys(Keys.ENTER);
                break;
            } catch (Exception ignored) {
            }
        }

        waitForSpinnerToFinish();

        By cellLocator = By.xpath(
                "//tbody[contains(@class,'MuiTableBody-root')]//tr//*[contains(normalize-space(),'" + customerName + "')]"
        );
        try {
            return waitFor(25).until(ExpectedConditions.visibilityOfElementLocated(cellLocator)).isDisplayed();
        } catch (Exception e) {
            String feedback = getFeedbackMessage();
            System.out.println("[DEBUG] Customer '" + customerName + "' not found. Feedback/validation text: " + feedback);
            return false;
        }
    }

    private void typeIfPresent(By locator, String value) {
        WebElement el = waitFor(15).until(ExpectedConditions.visibilityOfElementLocated(locator));
        try {
            el.clear();
        } catch (InvalidElementStateException e) {
            try {
                scrollAndClick(el);
                el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                el.sendKeys(Keys.BACK_SPACE);
            } catch (Exception ignored) {
            }
        }
        el.sendKeys(value);
    }

    private void scrollAndClick(WebElement element) {
        try {
            ((JavascriptExecutor) driver()).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element
            );
        } catch (Exception ignored) {
        }
        try {
            waitFor(5).until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", element);
        }
    }
    
}