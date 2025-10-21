package com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver browserDriver;
    private WebDriverWait driverWait;

    // Element selectors
    private By userInputField = By.name("username");
    private By passInputField = By.name("password");
    private By submitBtn = By.cssSelector("button[type='submit']");
    private By mainDashboardTitle = By.xpath("//h6[text()='Dashboard']");

    public LoginPage(WebDriver browserDriver) {
        this.browserDriver = browserDriver;
        this.driverWait = new WebDriverWait(browserDriver, Duration.ofSeconds(15));
    }

    public void openLoginScreen() {
        browserDriver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    public void inputUserCredentials(String userID) {
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(userInputField));
        browserDriver.findElement(userInputField).sendKeys(userID);
    }

    public void inputPasswordCredentials(String userPassword) {
        browserDriver.findElement(passInputField).sendKeys(userPassword);
    }

    public void performLogin() {
        browserDriver.findElement(submitBtn).click();
    }

    public boolean verifyDashboardIsVisible() {
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(mainDashboardTitle));
            return browserDriver.findElement(mainDashboardTitle).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public void performFullLogin(String userID, String userPassword) {
        openLoginScreen();
        inputUserCredentials(userID);
        inputPasswordCredentials(userPassword);
        performLogin();
    }
}