package io.github.anoopsimon;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.anoopsimon.utils.FrameworkProperties;
import io.github.anoopsimon.utils.GridChecker;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Hooks {
    private  ExtentReports extent;
    private WebDriver driver ;
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



        EdgeOptions edgeOptions = new EdgeOptions();
        if(executionMode.equalsIgnoreCase("grid"))
        {
            String gridUrl=FrameworkProperties.get("grid.url");
            GridChecker.checkGridAvailability(gridUrl);
            test.log(Status.INFO,"Grid URL : "+ gridUrl);

            //chromeOptions.setCapability("browserVersion", "100");
            edgeOptions.setCapability("platformName", "Linux");
            edgeOptions.setCapability("se:name", "My simple test");
            edgeOptions.setCapability("se:sampleMetadata", "Sample metadata value");

            driver = new RemoteWebDriver(new URL(gridUrl), edgeOptions);
        }
        else if (executionMode.equalsIgnoreCase("local")){
            driver = new EdgeDriver();
        }


        gui = new GUI(driver, extent, test);

    }

    @After
    public void cleanup()
    {
        if(driver!=null) driver.quit();
        extent.flush();
    }
}
