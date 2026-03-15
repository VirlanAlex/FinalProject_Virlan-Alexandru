package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

@Plugin(name = "AllureAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class AllureAppender extends AbstractAppender {

    protected AllureAppender(String name, Filter filter,
                             org.apache.logging.log4j.core.Layout<? extends Serializable> layout,
                             boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions, org.apache.logging.log4j.core.config.Property.EMPTY_ARRAY);
    }

    @PluginFactory
    public static AllureAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") org.apache.logging.log4j.core.Layout<? extends Serializable> layout) {

        if (name == null) {
            LOGGER.error("AllureAppender: no name provided");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new AllureAppender(name, filter, layout, true);
    }

    @Override
    public void append(LogEvent event) {
        String message = event.getMessage().getFormattedMessage();
        Level level = event.getLevel();

        try {
            if (level.isMoreSpecificThan(Level.ERROR)) {
                Allure.step("[ERROR] " + message, Status.FAILED);
            } else if (level.isMoreSpecificThan(Level.WARN)) {
                Allure.step("[WARN] " + message, Status.BROKEN);
            } else {
                Allure.step("[INFO] " + message);
            }
        } catch (Exception e) {
            // Allure lifecycle not active (e.g. outside test context) — ignore silently
        }
    }
}
