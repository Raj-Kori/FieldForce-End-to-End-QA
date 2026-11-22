package fieldforce.pages;

import org.openqa.selenium.By;
import fieldforce.utils.Elements;

public class signuppage {

    private final By signupLink = By.linkText("Sign up");
    private final By companyNameInput = By.cssSelector("input[name='companyName']");
    private final By contactNameInput = By.cssSelector("input[name='contactPersonName']");
    private final By countryCodeDropdown = By.xpath("//p[normalize-space()='+91']/parent::div");
    private final By phoneInput = By.cssSelector("input[name='mobile']");
    private final By emailInput = By.cssSelector("input[name='email']");
    private final By timezoneDropdown = By.xpath("//div[@role='combobox' and normalize-space()='India Standard Time']");
    private final By currencyDropdown = By.xpath("//div[@role='combobox' and contains(., 'Rupees')]");
    private final By backButton = By.xpath("//button[@type='button' and normalize-space()='Back']");
    private final By submitButton = By.xpath("//button[@type='submit']");

    public void clickRegisterHeaderLink() {
        Elements.clickElement(signupLink);
    }

    public void enterSignupDetails(String companyName, String contactName, String email, String phone) {
        Elements.entertext(companyNameInput, companyName);
        Elements.entertext(contactNameInput, contactName);
        Elements.entertext(emailInput, email);
        Elements.entertext(phoneInput, phone);
    }

    public void selectTimezone(String timezone) {
        Elements.clickElement(timezoneDropdown);
        By option = By.xpath("//ul[@role='listbox']//li[@role='option' and normalize-space()='" + timezone + "']");
        Elements.clickElement(option);
    }

    public void selectCurrency(String currency) {
        Elements.clickElement(currencyDropdown);
        By option = By.xpath("//ul[@role='listbox']//li[@role='option' and contains(normalize-space(), '" + currency + "')]");
        Elements.clickElement(option);
    }

    public void clickBack() {
        Elements.clickElement(backButton);
    }

    public void clickSubmit() {
        Elements.clickElement(submitButton);
    }
}