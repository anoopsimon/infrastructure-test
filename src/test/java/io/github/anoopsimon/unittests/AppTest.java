package io.github.anoopsimon.unittests;

import io.github.anoopsimon.Hooks;
import io.github.anoopsimon.utils.FrameworkProperties;
import org.junit.Test;

import java.time.Duration;

public class AppTest extends Hooks
{

    @Test
    public void shouldAnswerWithTrue()
    {
        gui.goTo(FrameworkProperties.get("app.url"));
        gui.click("google.searchbox");
        gui.type("google.searchbox","selenium grid");
        gui.captureScreenshotToReport();

    }

    @Test
    public void iframeTest()
    {
        gui.goTo("file://"+System.getProperty("user.dir")+FrameworkProperties.get("iframe.app.url"));
        gui.getElementIframe("iframe.username").sendKeys("appoos");
        gui.hardWait(Duration.ofSeconds(5));

    }
}
