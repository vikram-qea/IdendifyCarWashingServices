package com.selenium.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class ServicePage {
	private WebDriver driver;

	public ServicePage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean printFiveCarWashingServices() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int count = 0;

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("resultbox")));
			List<WebElement> cards = driver.findElements(By.className("resultbox"));

			String[] keywords = {"car", "wash", "washing", "cleaning", "service"};

			System.out.println("--- 5 Car Washing Services ---");

			for (WebElement card : cards) {
				if (count >= 5) break;

				try {
					String name = card.findElement(By.className("resultbox_title_anchor")).getText();
					String nameLower = name.toLowerCase();

					boolean hasKeywords = false;
					for (String key : keywords) {
						if (nameLower.contains(key)) {
							hasKeywords = true;
							break;
						}
					}

					String ratingStr = card.findElement(By.className("resultbox_totalrate")).getText();
					double rating = Double.parseDouble(ratingStr.trim());

					String votesText = card.findElement(By.className("resultbox_countrate")).getText();
					int votes = Integer.parseInt(votesText.replaceAll("[^0-9]", ""));

					if (hasKeywords && rating > 4.0 && votes > 20) {
						String phone = "No Phone Found";
						try {
							phone = card.findElement(By.className("callcontent")).getText().trim();
						} catch (NoSuchElementException e) {
							phone = card.findElement(By.className("callNowAnchor")).getText().trim();
						}

						count++;
						System.out.println(count + ". " + name);
						System.out.println("   Phone No: " + phone);
						System.out.println("-------------------------------------------");
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			if (count == 0) System.out.println("No matching services found.");

		} catch (TimeoutException e) {
			System.out.println("Error: Results page did not load.");
		}

		return count > 0;
	}

	public boolean applyRatingFilter() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			WebElement filterBtn = driver.findElement(By.xpath("//span[@id='all_filters_btn']/parent::button"));
			filterBtn.click();

			WebElement ratingOption = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//span[@aria-label='4.0+']")));

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", ratingOption);

			WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//button[text()='Apply Filters']")));
			applyBtn.click();

			// wait for the dialog to close
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//aside[@role='dialog']")));

			return true;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}
}