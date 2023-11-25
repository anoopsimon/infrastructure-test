package io.github.anoopsimon;

import static io.github.anoopsimon.utils.Locators.*;

import io.github.anoopsimon.utils.FrameworkProperties;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest extends Hooks
{

    @Test
    public void shouldAnswerWithTrue()
    {
        //gui.goTo("file://C:\\Users\\s4514\\dev\\java\\java-tesst-framework\\src\\test\\resources\\app.html");
        gui.goTo(FrameworkProperties.get("app.url"));
        gui.click(get("google.searchbox"));
        gui.type(get("google.searchbox"),"selenium grid");
//        gui.type(get("usernameLocator"), "yourUsername");
//        gui.type(get("passwordLocator"), "yourPassword");
//        gui.scroll(get("formLocator"));

    }
}
