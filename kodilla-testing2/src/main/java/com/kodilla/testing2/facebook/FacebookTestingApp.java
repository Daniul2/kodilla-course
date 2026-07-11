package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FacebookTestingApp {

    public static final String XPATH_COOKIE_ACCEPT =
            "//button[@data-cookiebanner='accept_button']";

    public static final String XPATH_CREATE_ACCOUNT =
            "//a[@aria-label='Create new account']";

    public static final String XPATH_FIRST_NAME_INPUT =
            "//label[normalize-space(text())='First name']/preceding-sibling::input";
    public static final String XPATH_SURNAME_INPUT =
            "//label[normalize-space(text())='Surname']/preceding-sibling::input";

    public static final String XPATH_DAY_COMBOBOX =
            "//div[@role='combobox' and @aria-label='Select day']";
    public static final String XPATH_MONTH_COMBOBOX =
            "//div[@role='combobox' and @aria-label='Select month']";
    public static final String XPATH_YEAR_COMBOBOX =
            "//div[@role='combobox' and @aria-label='Select year']";

    public static final String XPATH_GENDER_COMBOBOX =
            "//span[normalize-space(text())='Select your gender']/ancestor::div[@role='combobox'][1]";

    public static final String XPATH_MOBILE_OR_EMAIL_INPUT =
            "//span[normalize-space(text())='Mobile number or email address']/following::input[1]";
    public static final String XPATH_PASSWORD_INPUT =
            "//span[normalize-space(text())='Password']/following::input[1]";

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get("https://www.facebook.com/");

            acceptCookiesIfPresent(driver, wait);
            openRegistrationForm(wait);
            fillName(wait);
            fillBirthday(wait);
            fillGender(wait);
            fillContactAndPassword(wait);

        } finally {
            driver.quit();
        }
    }

    private static void acceptCookiesIfPresent(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement cookieButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(XPATH_COOKIE_ACCEPT)));
            cookieButton.click();
            System.out.println("Cookie banner accepted.");
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already dismissed.");
        }
    }

    private static void openRegistrationForm(WebDriverWait wait) {
        WebElement createAccountButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(XPATH_CREATE_ACCOUNT)));
        createAccountButton.click();
        System.out.println("Registration form opened.");
    }

    private static void fillName(WebDriverWait wait) {
        WebElement firstNameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_FIRST_NAME_INPUT)));
        firstNameInput.sendKeys("Jan");

        WebElement surnameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_SURNAME_INPUT)));
        surnameInput.sendKeys("Kowalski");

        System.out.println("Name fields filled successfully.");
    }

    private static void fillBirthday(WebDriverWait wait) {
        String selectedDay = selectComboboxOption(wait, XPATH_DAY_COMBOBOX, "15");
        String selectedMonth = selectComboboxOption(wait, XPATH_MONTH_COMBOBOX, "June");
        String selectedYear = selectComboboxOption(wait, XPATH_YEAR_COMBOBOX, "1998");

        System.out.println("Birthday fields filled successfully: "
                + selectedDay + " " + selectedMonth + " " + selectedYear);
    }

    private static void fillGender(WebDriverWait wait) {
        String selectedGender = selectComboboxOption(wait, XPATH_GENDER_COMBOBOX, "Male");
        System.out.println("Gender field filled successfully: " + selectedGender);
    }

    private static void fillContactAndPassword(WebDriverWait wait) {
        WebElement mobileOrEmailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_MOBILE_OR_EMAIL_INPUT)));
        mobileOrEmailInput.sendKeys("jan.kowalski.test@example.com");

        WebElement passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_PASSWORD_INPUT)));
        passwordInput.sendKeys("TestPassword123!");

        System.out.println("Contact and password fields filled successfully.");
    }

    private static String selectComboboxOption(WebDriverWait wait, String comboboxXpath, String optionText) {
        WebElement combobox = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(comboboxXpath)));
        combobox.click();

        String optionXpath = "//div[@role='option'][normalize-space(.)='" + optionText + "']";
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
        option.click();

        return optionText;
    }
}
