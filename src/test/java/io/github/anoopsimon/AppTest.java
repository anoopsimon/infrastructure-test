package io.github.anoopsimon;

import static io.github.anoopsimon.utils.Locators.*;

import io.github.anoopsimon.utils.FrameworkProperties;
import org.junit.Test;

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
}
