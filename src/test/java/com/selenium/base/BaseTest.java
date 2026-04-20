package com.selenium.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = DriverSetup.getDriver("chrome");
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    @AfterClass(alwaysRun = true)
    public void closeDriver() {
        if (driver != null) {
            System.out.println("Closing the browser session...");
            DriverSetup.quitDriver();
        }
    }
}