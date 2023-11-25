package io.github.anoopsimon;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.base.Strings;
import io.github.anoopsimon.utils.Locators;
import org.openqa.selenium.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class GUI {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    public GUI(WebDriver driver, ExtentReports extent, ExtentTest test) {
        this.driver = driver;
        this.extent = extent;
        this.test = test;
    }

    private By parseLocator(String locatorString) {
        String locator= Locators.get(locatorString);
        if(Strings.isNullOrEmpty(locatorString)) throw new RuntimeException(String.format("Locator key %s not found in locator/locator.properties file",locatorString));
        if (locator.startsWith("//") || locator.startsWith("(")) {
            return By.xpath(locator);
        } else {
            return By.cssSelector(locator);
        }
    }

    public void click(String locator) {

            WebElement element = driver.findElement(parseLocator(locator));
            element.click();
            test.pass("Clicked on element with locator: " + locator);

    }

    public void type(String locator, String text) {

            WebElement element = driver.findElement(parseLocator(locator));
            element.sendKeys(text);
            test.pass("Typed text '" + text + "' into element with locator: " + locator);

    }

    public void type(String locator, Keys keys) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            element.sendKeys(keys);
            test.pass("Press keyboard Key  '" + keys.name() + "' into element with locator: " + locator);
        } catch (Exception e) {
            test.fail("Failed to type in element with locator: " + locator + ". Error: " + e.getMessage());
        }
    }

    public void scroll(String locator) {
        try {
            // Implementation of scroll depends on your preference, you can use JavascriptExecutor for instance
            WebElement element = driver.findElement(parseLocator(locator));
            // Scroll to the element
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            test.pass("Scrolled to element with locator: " + locator);
        } catch (Exception e) {
            test.fail("Failed to scroll to element with locator: " + locator + ". Error: " + e.getMessage());
        }
    }

    public void goTo(String url)
    {
        driver.get(url);
    }

    public void captureScreenshotToReport() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);

            test.info("Screenshot Captured",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
            test.fail("Failed to capture screenshot. Error: " + e.getMessage());
        }
    }
}
