package backEnd.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Plugin(name = "AllureAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class AllureAppender extends AbstractAppender {

    protected AllureAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions, Property.EMPTY_ARRAY);
    }

    @PluginFactory
    public static AllureAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new AllureAppender(name, filter, layout, true);
    }

    @Override
    public void append(LogEvent event) {
        try {
            String message = new String(getLayout().toByteArray(event), StandardCharsets.UTF_8).trim();
            if (!message.isEmpty()) {
                Allure.step(message);
            }
        } catch (Exception e) {
            System.err.println("Failed to write log to Allure: " + e.getMessage());
        }
    }

    public static void attachScreenshot(WebDriver driver) {
        try {
            if (driver instanceof TakesScreenshot) {
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                Allure.addAttachment(
                        "Screenshot on failure",
                        "image/png",
                        new ByteArrayInputStream(Base64.getDecoder().decode(base64Screenshot)),
                        ".png"
                );
            }
        } catch (Exception e) {
            Allure.step("Screenshot capture failed: " + e.getMessage());
        }
    }
}
