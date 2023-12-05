package io.github.anoopsimon;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.base.Strings;
import io.github.anoopsimon.utils.Locators;
import org.openqa.selenium.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    public GUI(WebDriver driver, ExtentReports extent, ExtentTest test) {
        this.driver = driver;
        this.extent = extent;
        this.test = test;
    }

    private By parseLocator(String locatorString)
    {
        String locator= Locators.get(locatorString);
        System.out.println("Locator :" + locator);
        if(Strings.isNullOrEmpty(locatorString)) throw new RuntimeException(String.format("Locator key %s not found in locator/locator.properties file",locatorString));
        if (locator.startsWith("//") || locator.startsWith("("))
        {
            return By.xpath(locator);
        } else
        {
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
            String base64Screenshot = "data:image/jpg;base64,"+ts.getScreenshotAs(OutputType.BASE64);

            test.info("Screenshot Captured",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (Exception e) {
            test.fail("Failed to capture screenshot. Error: " + e.getMessage());
        }
    }

    /**
     * Refreshes the current page in the browser.
     */
    public void refresh() {
        driver.navigate().refresh();
    }

    /**
     * Navigates one item forward in the browser's history.
     */
    public void forward() {
        driver.navigate().forward();
    }

    /**
     * Navigates one item backward in the browser's history.
     */
    public void backward() {
        driver.navigate().back();
    }

    /**
     * Scrolls to the bottom of the web page.
     */
    public void scrollToBottomOfPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Scrolls to the top of the web page.
     */
    public void scrollToTopOfPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }

    /**
     * Scrolls to the middle of the web page.
     */
    public void scrollToMiddleOfPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
    }

    /**
     * Selects an option from a dropdown by its index.
     *
     * @param locator The locator of the dropdown element.
     * @param index   The index of the option to select.
     */
    public void selectFromDropdownByIndex(String locator, int index) {
        WebElement element = driver.findElement(parseLocator(locator));
        new Select(element).selectByIndex(index);
    }

    /**
     * Selects an option from a dropdown by its value attribute.
     *
     * @param locator The locator of the dropdown element.
     * @param value   The value attribute of the option to select.
     */
    public void selectFromDropdownByValue(String locator, String value) {
        WebElement element = driver.findElement(parseLocator(locator));
        new Select(element).selectByValue(value);
    }

    /**
     * Selects an option from a dropdown by its visible text.
     *
     * @param locator The locator of the dropdown element.
     * @param text    The visible text of the option to select.
     */
    public void selectFromDropdownByText(String locator, String text) {
        WebElement element = driver.findElement(parseLocator(locator));
        new Select(element).selectByVisibleText(text);
    }

    /**
     * Reads an HTML table and returns its data as a list of maps, where each map represents a row
     * with the column header as the key and the cell data as the value.
     *
     * @param tableLocator   The locator of the table.
     * @param headersLocator The locator of the header row.
     * @param rowLocator     The locator of the table rows.
     * @param cellLocator    The locator of the cells in a row.
     * @return A list of maps representing the rows of the table.
     */
    public List<Map<String, String>> readHtmlTable(String tableLocator, String headersLocator, String rowLocator, String cellLocator) {
        List<Map<String, String>> tableData = new ArrayList<>();
        List<WebElement> headers = driver.findElements(parseLocator(headersLocator));
        List<WebElement> rows = driver.findElements(parseLocator(rowLocator));

        for (WebElement row : rows) {
            Map<String, String> rowData = new HashMap<>();
            List<WebElement> cells = row.findElements(parseLocator(cellLocator));
            for (int i = 0; i < headers.size(); i++) {
                rowData.put(headers.get(i).getText(), cells.get(i).getText());
            }
            tableData.add(rowData);
        }

        return tableData;
    }

    /**
     * Performs a double click on the specified element.
     *
     * @param locator The locator of the element to double click.
     */
    public void doubleClick(String locator) {
        WebElement element = driver.findElement(parseLocator(locator));
        new Actions(driver).doubleClick(element).perform();
    }

    /**
     * Retrieves the visible text of the specified element.
     *
     * @param locator The locator of the element.
     * @return The visible text of the element.
     */
    public String getText(String locator) {
        return driver.findElement(parseLocator(locator)).getText();
    }
    /**
     * Retrieves all attributes of the specified element.
     *
     * @param locator The locator of the element.
     * @return A map containing attribute names and values.
     */
    public Map<String, String> getAllAttributes(String locator) {
        WebElement element = driver.findElement(parseLocator(locator));
        Map<String, String> attributes = new HashMap<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> attrs = (Map<String, Object>) js.executeScript(
                "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", element);
        for (String attrName : attrs.keySet()) {
            attributes.put(attrName, attrs.get(attrName).toString());
        }
        return attributes;
    }

    /**
     * Retrieves the value of a specific attribute of the specified element.
     *
     * @param locator   The locator of the element.
     * @param attribute The name of the attribute.
     * @return The value of the attribute.
     */
    public String getAttribute(String locator, String attribute) {
        return driver.findElement(parseLocator(locator)).getAttribute(attribute);
    }

    /**
     * Types text into an element character by character, with a delay between each character.
     *
     * @param locator        The locator of the element to type into.
     * @param text           The text to type.
     * @param delayInMillis  The delay between each character in milliseconds.
     */
    public void slowType(String locator, String text, long delayInMillis) {
        WebElement element = driver.findElement(parseLocator(locator));
        for (char ch : text.toCharArray()) {
            element.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public WebElement getElementIframe(String s){
        //TODO : check for null
        return findElementInNestedIframe(parseLocator(s));

    }

    /**
     * Recursively searches for an element within nested iframes.
     *
     * This method iterates through all iframes in the current DOM context.
     * It switches to each iframe and searches for the specified element.
     * If the element is not found in the current iframe, it recursively searches
     * within any nested iframes.
     *
     * The method employs WebDriverWait to handle loading times within iframes.
     * Implicit waits are temporarily disabled during the execution to optimize performance.
     *
     * @param elementLocator The locator of the element to type into.
     * @return WebElement The found WebElement, or null if the element is not found.
     */
    public WebElement findElementInNestedIframe(By elementLocator)
    {
        updateImplicitWaitTimeout(Duration.ofSeconds(1));

        // Check if the element is present in the current context
        if (!driver.findElements(elementLocator).isEmpty()) {
            return driver.findElement(elementLocator);
        }

        // Find all iframes in the current context
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        for (WebElement iframe : iframes) {

            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));

            // Recursively search for the element in the iframe
            WebElement element = findElementInNestedIframe(elementLocator);
            if (element != null) {
                return element;
            }

            // If not found, switch back to the parent frame and continue
            driver.switchTo().parentFrame();
        }

        // Re-enable implicit waits (if previously used)
        updateImplicitWaitTimeout(Duration.ofSeconds(10));

        // Return null if element is not found in any iframes
        return null;
    }

    private void updateImplicitWaitTimeout(Duration duration) {
         driver.manage().timeouts().implicitlyWait(duration);
    }

    public void hardWait(Duration ofSeconds)
    {
        try{
            Thread.sleep(ofSeconds.toMillis());
        }catch (Exception e){

        }
    }
}
