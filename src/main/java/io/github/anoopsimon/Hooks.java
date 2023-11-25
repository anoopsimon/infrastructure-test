package io.github.anoopsimon;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.anoopsimon.utils.FrameworkProperties;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Hooks {
    private  ExtentReports extent;

    protected GUI gui;
    @Before
    public void setUp() throws MalformedURLException {
        // Initialize ExtentReports
        extent= new ExtentReports();
        extent.attachReporter(new ExtentSparkReporter("Report.html"));
        String executionMode = FrameworkProperties.get("web.execution.mode");

        // Initialize ExtentTest
        ExtentTest test = extent.createTest("MyTest");
        test.log(Status.INFO,"Browser execution mode "+ executionMode);


        WebDriver driver =null;
        EdgeOptions edgeOptions = new EdgeOptions();
        if(executionMode.equalsIgnoreCase("grid"))
        {
            //chromeOptions.setCapability("browserVersion", "100");
            edgeOptions.setCapability("platformName", "Linux");
            edgeOptions.setCapability("se:name", "My simple test");
            edgeOptions.setCapability("se:sampleMetadata", "Sample metadata value");

            driver = new RemoteWebDriver(new URL(FrameworkProperties.get("grid.url")), edgeOptions);
        }
        else if (executionMode.equalsIgnoreCase("local")){
            driver = new EdgeDriver();
        }


        gui = new GUI(driver, extent, test);

    }

    @After
    public void cleanp(){
        extent.flush();
    }
}
