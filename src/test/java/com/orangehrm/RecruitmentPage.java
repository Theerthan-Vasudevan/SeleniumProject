package com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecruitmentPage {
    private WebDriver browserInstance;
    private WebDriverWait elementWait;

    // Element identifiers
    private By recruitmentSection = By.xpath("//span[text()='Recruitment']");
    private By createNewBtn = By.xpath("//button[normalize-space()='Add']");
    private By firstNameInput = By.name("firstName");
    private By middleNameInput = By.name("middleName");
    private By lastNameInput = By.name("lastName");
    private By emailAddressField = By.xpath("//label[text()='Email']/parent::div/following-sibling::div//input");
    private By confirmSaveBtn = By.cssSelector("button[type='submit']");
    private By candidateListLink = By.linkText("Candidates");
    private By candidateDataGrid = By.cssSelector(".oxd-table");
    private By searchActionBtn = By.cssSelector("button[type='submit']");
    private By removeBtn = By.xpath("//i[contains(@class,'bi-trash')]");
    private By confirmRemovalBtn = By.xpath("//button[normalize-space()='Yes, Delete']");
    private By modifyBtn = By.xpath("//i[contains(@class,'bi-pencil')]");

    public RecruitmentPage(WebDriver browserInstance) {
        this.browserInstance = browserInstance;
        this.elementWait = new WebDriverWait(browserInstance, Duration.ofSeconds(15));
    }

    public void accessRecruitmentModule() {
        elementWait.until(ExpectedConditions.elementToBeClickable(recruitmentSection));
        browserInstance.findElement(recruitmentSection).click();
    }

    public void initiateNewCandidate() {
        elementWait.until(ExpectedConditions.elementToBeClickable(createNewBtn));
        browserInstance.findElement(createNewBtn).click();
    }

    public void inputFirstNameData(String firstName) {
        elementWait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        browserInstance.findElement(firstNameInput).sendKeys(firstName);
    }

    public void inputMiddleNameData(String middleName) {
        browserInstance.findElement(middleNameInput).sendKeys(middleName);
    }

    public void inputLastNameData(String lastName) {
        browserInstance.findElement(lastNameInput).sendKeys(lastName);
    }

    public void inputEmailAddress(String emailAddress) {
        browserInstance.findElement(emailAddressField).sendKeys(emailAddress);
    }

    public void saveNewCandidate() {
        browserInstance.findElement(confirmSaveBtn).click();
    }

    public void registerNewCandidate(String firstName, String middleName, String lastName, String emailAddress) {
        accessRecruitmentModule();
        initiateNewCandidate();
        inputFirstNameData(firstName);
        inputMiddleNameData(middleName);
        inputLastNameData(lastName);
        inputEmailAddress(emailAddress);
        saveNewCandidate();
    }

    public void viewAllCandidates() {
        accessRecruitmentModule();
        elementWait.until(ExpectedConditions.elementToBeClickable(candidateListLink));
        browserInstance.findElement(candidateListLink).click();
    }

    public boolean verifyCandidateGridIsVisible() {
        try {
            elementWait.until(ExpectedConditions.visibilityOfElementLocated(candidateDataGrid));
            return browserInstance.findElement(candidateDataGrid).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public void removeTopCandidate() {
        elementWait.until(ExpectedConditions.elementToBeClickable(removeBtn));
        browserInstance.findElements(removeBtn).get(0).click();
        elementWait.until(ExpectedConditions.elementToBeClickable(confirmRemovalBtn));
        browserInstance.findElement(confirmRemovalBtn).click();
    }

    public void modifyTopCandidate() {
        elementWait.until(ExpectedConditions.elementToBeClickable(modifyBtn));
        browserInstance.findElements(modifyBtn).get(0).click();
    }

    public void updateCandidateMiddleNameInfo(String updatedMiddleName) {
        elementWait.until(ExpectedConditions.visibilityOfElementLocated(middleNameInput));
        WebElement middleNameElement = browserInstance.findElement(middleNameInput);
        middleNameElement.clear();
        middleNameElement.sendKeys(updatedMiddleName);
        saveNewCandidate();
    }
}