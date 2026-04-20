package com.selenium.pages;

import com.selenium.base.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class GymCategory {
    private final WebDriver driver;

    public GymCategory(WebDriver driver) {
        this.driver = driver;
    }

    public void openGymCategory() {
        driver.get(Constants.BASE_URL);

        try {
            WebElement gymButton = driver.findElement(By.xpath("//div[text()='Gym']/parent::a"));
            gymButton.click();
        } catch (Exception e) {
            System.out.println("LOG: Failed to click Gym button !" + e.getMessage());
        }
    }

    public boolean printFiveGymServices() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int count = 0;
        
        try {
            // Anchor to the main results container ID
            WebElement mainContainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mainContent")));

            // Find cards inside this container using a CSS selector that targets the result cards
            // 'div[role="none"]' is the specific role used for each gym card in your HTML
            List<WebElement> cards = mainContainer.findElements(By.cssSelector("div[role='none'].resultbox"));

            System.out.println("\n--- TOP 5 GYM SERVICES (Rating > 4.0 & Votes > 20) ---");

            for (WebElement card : cards) {
                if (count >= 5) break;

                try {
                    // Using relative XPaths from the card container
                    String name = card.findElement(By.xpath(".//span[contains(@class, 'resultbox_title_anchor')]")).getText().trim();

                    String ratingStr = card.findElement(By.xpath(".//li[contains(@class, 'resultbox_totalrate')]")).getText().trim();
                    double rating = Double.parseDouble(ratingStr.replaceAll("[^0-9.]", ""));

                    String votesText = card.findElement(By.xpath(".//li[contains(@class, 'resultbox_countrate')]")).getText().trim();
                    int votes = Integer.parseInt(votesText.replaceAll("[^0-9]", ""));

                    if (rating > 4.0 && votes > 20) {
                        count++;
                        System.out.println(count + ". " + name);
                        System.out.println("   Rating : " + rating + " | Votes: " + votes);
                        System.out.println("-------------------------------------------");
                    }
                } catch (Exception e) {
                    // Skip non-gym elements (like ads or 'Get List' boxes)
                    continue;
                }
            }

            if (count == 0) {
                System.out.println("LOG: No gyms met the criteria. Check if the page fully loaded.");
            }

        } catch (Exception e) {
            System.out.println("Critical Error: Result container not found.");
        }
        
        return count > 0;
    }
}