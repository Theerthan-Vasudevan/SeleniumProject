package com.BMICalculator.BMICalculator.StepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;

public class BMICalculatorSteps {
    WebDriver driver;

    @Given("the user launches the Chrome browser")
    public void the_user_launches_the_chrome_browser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @When("the user opens the calculator.net homepage")
    public void the_user_opens_the_calculator_net_homepage() {
        driver.get("https://www.calculator.net/");
    }

    @When("the user searches for {string} calculator")
    public void the_user_searches_for_calculator(String searchTerm) {
        driver.findElement(By.id("calcSearchTerm")).sendKeys(searchTerm);
        driver.findElement(By.xpath("//*[@id=\"calcSearchOut\"]/div[2]/a")).click();
    }

    @When("the user enters age as {string}")
    public void the_user_enters_age_as(String age) {
        driver.findElement(By.name("cage")).clear();
        driver.findElement(By.name("cage")).sendKeys(age);
    }

    @When("the user selects gender as {string}")
    public void the_user_selects_gender_as(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            driver.findElement(By.xpath("//*[@id=\"calinputtable\"]/tbody/tr[2]/td[2]/label[1]")).click();
        } else {
            driver.findElement(By.xpath("//*[@id=\"calinputtable\"]/tbody/tr[2]/td[2]/label[2]")).click();
        }
    }

    @When("the user enters height as {string}")
    public void the_user_enters_height_as(String height) {
        driver.findElement(By.name("cheightmeter")).clear();
        driver.findElement(By.name("cheightmeter")).sendKeys(height);
    }

    @When("the user enters weight as {string}")
    public void the_user_enters_weight_as(String weight) {
        driver.findElement(By.name("ckg")).clear();
        driver.findElement(By.name("ckg")).sendKeys(weight);
    }

    @When("the user clicks the calculate button")
    public void the_user_clicks_the_calculate_button() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div[2]/table/tbody/tr/td/form/table[4]/tbody/tr/td/input[3]")).click();
        Thread.sleep(5000);
    }
    


    @Then("the user closes the browser")
    public void the_user_closes_the_browser() {
        driver.quit();
    }
}
