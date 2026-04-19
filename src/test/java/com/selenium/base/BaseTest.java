package com.selenium.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = DriverSetup.getDriver("chrome");
    }

    @AfterClass
    public void closeDriver() throws InterruptedException {
        System.out.println("Quiting the driver in 10 seconds...");
        Thread.sleep(10000);
        DriverSetup.quitDriver();
    }
}