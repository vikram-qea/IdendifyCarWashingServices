package com.selenium.pages;

import com.selenium.base.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FreeListing {
    private WebDriver driver;

    public FreeListing(WebDriver driver) {
        this.driver = driver;
    }

    public void goToFreeListing() {
        WebElement freeListing = driver.findElement(By.xpath("//li[@id='header_freelisting']/a"));
        freeListing.click();
    }

    public void signInToFreeListing() {
        WebElement phoneInput = driver.findElement(By.id("1"));

        phoneInput.sendKeys(Constants.PHONE_NUMBER + Keys.ENTER);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By errorLocator = By.className("entermobilenumber_error__text__uPM09");

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));

            System.out.println("Error: " + errorMsg.getText());

        } catch (TimeoutException e) {
            System.out.println("No validation error message appeared within the timeout.");
        }
    }
}