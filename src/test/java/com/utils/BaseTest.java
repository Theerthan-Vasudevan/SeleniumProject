package com.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver webBrowser;

    @BeforeMethod
    public void initializeTestEnvironment() {
        System.out.println("� Initializing test browser environment...");
        
        ChromeOptions browserConfig = new ChromeOptions();
        browserConfig.addArguments("--no-sandbox");
        browserConfig.addArguments("--disable-dev-shm-usage");
        browserConfig.addArguments("--disable-blink-features=AutomationControlled");
        browserConfig.setExperimentalOption("useAutomationExtension", false);
        browserConfig.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        webBrowser = new ChromeDriver(browserConfig);
        webBrowser.manage().window().maximize();
        webBrowser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webBrowser.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        
        System.out.println("✅ Test environment ready");
    }

    @AfterMethod
    public void cleanupTestEnvironment() {
        if (webBrowser != null) {
            System.out.println("� Cleaning up test environment...");
            webBrowser.quit();
            System.out.println("✅ Test environment cleaned");
        }
    }
}