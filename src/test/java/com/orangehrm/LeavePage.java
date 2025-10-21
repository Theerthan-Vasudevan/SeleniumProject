package com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LeavePage {
    private WebDriver webDriver;
    private WebDriverWait waitCondition;

    // Web element identifiers
    private By leaveMenuOption = By.xpath("//span[text()='Leave']");
    private By requestLeaveBtn = By.linkText("Apply");
    private By typeOfLeaveSelector = By.xpath("//label[text()='Leave Type']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By availableLeaveOptions = By.xpath("//div[@role='option']/span");
    private By startDateInput = By.xpath("//label[text()='From Date']/parent::div/following-sibling::div//input");
    private By endDateInput = By.xpath("//label[text()='To Date']/parent::div/following-sibling::div//input");
    private By reasonTextArea = By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea");
    private By submitLeaveBtn = By.cssSelector("button[type='submit']");
    private By personalLeaveTab = By.linkText("My Leave");
    private By leaveRecordsGrid = By.cssSelector(".oxd-table");

    public LeavePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.waitCondition = new WebDriverWait(webDriver, Duration.ofSeconds(15));
    }

    public void accessLeaveModule() {
        waitCondition.until(ExpectedConditions.elementToBeClickable(leaveMenuOption));
        webDriver.findElement(leaveMenuOption).click();
    }

    public void initiateLeaveRequest() {
        waitCondition.until(ExpectedConditions.elementToBeClickable(requestLeaveBtn));
        webDriver.findElement(requestLeaveBtn).click();
    }

    public void chooseLeaveCategory() {
        waitCondition.until(ExpectedConditions.elementToBeClickable(typeOfLeaveSelector));
        webDriver.findElement(typeOfLeaveSelector).click();
        waitCondition.until(ExpectedConditions.visibilityOfElementLocated(availableLeaveOptions));
        webDriver.findElements(availableLeaveOptions).get(1).click(); // Select second available option
    }

    public void setStartDate(String dateValue) {
        WebElement startDate = webDriver.findElement(startDateInput);
        startDate.clear();
        startDate.sendKeys(dateValue);
    }

    public void setEndDate(String dateValue) {
        WebElement endDate = webDriver.findElement(endDateInput);
        endDate.clear();
        endDate.sendKeys(dateValue);
    }

    public void addLeaveReason(String reasonText) {
        webDriver.findElement(reasonTextArea).sendKeys(reasonText);
    }

    public void submitLeaveApplication() {
        webDriver.findElement(submitLeaveBtn).click();
    }

    public void processLeaveRequest(String startDate, String endDate, String reason) {
        accessLeaveModule();
        initiateLeaveRequest();
        chooseLeaveCategory();
        setStartDate(startDate);
        setEndDate(endDate);
        addLeaveReason(reason);
        submitLeaveApplication();
    }

    public void viewPersonalLeaveRecords() {
        accessLeaveModule();
        waitCondition.until(ExpectedConditions.elementToBeClickable(personalLeaveTab));
        webDriver.findElement(personalLeaveTab).click();
    }

    public boolean validateLeaveTablePresence() {
        try {
            waitCondition.until(ExpectedConditions.visibilityOfElementLocated(leaveRecordsGrid));
            return webDriver.findElement(leaveRecordsGrid).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }
}