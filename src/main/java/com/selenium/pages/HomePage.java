package com.selenium.pages;

import com.selenium.base.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import org.testng.Assert;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openSite() {
        driver.get(Constants.BASE_URL);
    }

    public void searchForService(String service) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main-auto")));
            search.sendKeys(service + Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}