package com.selenium.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.base.BaseTest;
import com.selenium.pages.FreeListing;
import com.selenium.pages.GymCategory;
import com.selenium.pages.HomePage;
import com.selenium.pages.ServicePage;

public class JustDialTest extends BaseTest {

    HomePage home;
    ServicePage servicePage;
    FreeListing freeListing;
    GymCategory gym;
    WebDriverWait wait;

    @BeforeClass
    public void init() {
        home = new HomePage(driver);
        servicePage = new ServicePage(driver);
        freeListing = new FreeListing(driver);
        gym = new GymCategory(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // TC-01: Launch website
    @Test(priority = 1)
    public void TC_01_VerifyHomePageLoad() {
        home.openSite();
        Assert.assertTrue(driver.findElement(By.id("main-auto")).isDisplayed(), "TC-01: Home page failed to load!");
    }

    // TC-02: Search for car washing services
    @Test(priority = 2, dependsOnMethods = "TC_01_VerifyHomePageLoad")
    public void TC_02_SearchCarWashServices() throws InterruptedException {
        home.searchForService("Car Washing Services in Chennai");
        Thread.sleep(3000);
        Assert.assertTrue(driver.findElements(By.className("resultbox")).size() > 0, "TC-02: Search results not displayed!");
    }

    // TC-03: Apply rating filter via UI
    @Test(priority = 3, dependsOnMethods = "TC_02_SearchCarWashServices")
    public void TC_03_ApplyRatingFilter() throws InterruptedException {
        boolean filterApplied = servicePage.applyRatingFilter();
        Assert.assertTrue(filterApplied, "TC-03: Failed to apply Rating filter via UI!");
        Thread.sleep(2000);
    }

    // TC-04: Check the votes count should be greater than 0
    @Test(priority = 4, dependsOnMethods = "TC_03_ApplyRatingFilter")
    public void TC_04_ValidateVotesCriteria() {
        boolean hasVotes = !driver.findElements(By.className("resultbox_countrate")).isEmpty();
        Assert.assertTrue(hasVotes, "TC-04: Vote counts not found on results page!");
    }

    // TC-05: Iterate and print 5 car washing services
    @Test(priority = 5, dependsOnMethods = "TC_04_ValidateVotesCriteria")
    public void TC_05_PrintTopFiveCarWash() {
        boolean printed = servicePage.printFiveCarWashingServices();
        Assert.assertTrue(printed, "TC-05: No Car Wash services found matching criteria!");
    }

    // TC-06: Navigate to Free Listing Page
    @Test(priority = 6)
    public void TC_06_VerifyFreeListingNavigation() {
        freeListing.goToFreeListing();
        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("free-listing"), "TC-06: Free Listing page didn't open!");
    }

    // TC-07: Fill the form with invalid phone no.
    @Test(priority = 7, dependsOnMethods = "TC_06_VerifyFreeListingNavigation")
    public void TC_07_EnterInvalidPhoneInFreeListing() {
        Assert.assertTrue(driver.findElement(By.id("1")).isDisplayed(), "TC-07: Phone input field not found!");
    }

    // TC-08: Submit the free listing form
    @Test(priority = 8, dependsOnMethods = "TC_07_EnterInvalidPhoneInFreeListing")
    public void TC_08_SubmitInvalidForm() {
        freeListing.signInToFreeListing();
        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("free-listing"), "TC-08: Form submitted incorrectly!");
    }

    // TC-09: Check error message & display
    @Test(priority = 9, dependsOnMethods = "TC_08_SubmitInvalidForm")
    public void TC_09_VerifyErrorMessage() {
        boolean isErrorVisible = !driver.findElements(By.xpath("//div[@id='listyourbusiness']/div/span[2]")).isEmpty();
        Assert.assertTrue(isErrorVisible, "TC-09: Error message was NOT displayed!");
    }

    // TC-10: Go back to home & navigate to gym
    @Test(priority = 10)
    public void TC_10_ReturnToHomeAndVerifyGym() throws InterruptedException {
        gym.openGymCategory();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("gyms"), "TC-10: Failed to navigate to Gym category!");
    }

    // TC-11: Verify gym services ui is loaded or not
    @Test(priority = 11, dependsOnMethods = "TC_10_ReturnToHomeAndVerifyGym")
    public void TC_11_VerifyGymServices() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mainContent")));
        
        boolean isLoaded = !driver.findElements(By.id("mainContent")).isEmpty();
        Assert.assertTrue(isLoaded, "TC-11: Gym items not loaded!");
    }

    // TC-12: Finally print the gym services
    @Test(priority = 12, dependsOnMethods = "TC_11_VerifyGymServices")
    public void TC_12_PrintGymServices() {
        boolean printed = gym.printFiveGymServices();
        Assert.assertTrue(printed, "Printed All gym Services.");
    }
}