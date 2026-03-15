package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtility {
    private static final Logger logger = LogManager.getLogger(LogUtility.class);

    private LogUtility() {
    }

    public static void startTest(String testName) {
        logger.info("========================================");
        logger.info("TEST STARTED: {}", testName);
        logger.info("========================================");
    }

    public static void infoLog(String message) {
        logger.info("{}", message);
    }

    public static void debugLog(String message) {
        logger.debug("{}", message);
    }

    public static void warnLog(String message) {
        logger.warn("{}", message);
    }

    public static void errorLog(String message) {
        logger.error("{}", message);
    }

    public static void passLog(String testName) {
        logger.info("----------------------------------------");
        logger.info("TEST PASSED: {}", testName);
        logger.info("----------------------------------------");
    }

    public static void failLog(String testName, String reason) {
        logger.error("----------------------------------------");
        logger.error("TEST FAILED: {}", testName);
        logger.error("REASON: {}", reason);
        logger.error("----------------------------------------");
    }

    public static void skipLog(String testName) {
        logger.warn("----------------------------------------");
        logger.warn("TEST SKIPPED: {}", testName);
        logger.warn("----------------------------------------");
    }

    public static void finishTest(String testName) {
        logger.info("========================================");
        logger.info("TEST FINISHED: {}", testName);
        logger.info("========================================");
    }
}
