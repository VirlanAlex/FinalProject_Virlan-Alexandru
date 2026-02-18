package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtility {
    private static final Logger logger = LogManager.getLogger(LogUtility.class);

    private LogUtility() {}
    public static void startTest(String testName){
        logger.info("***** Execution started: {} *****", testName);
    }
    public static void infoLog(String message){
        logger.info("{}", message);
    }
    public static void debugLog(String message){
        logger.debug("{}", message);
    }
    public static void warnLog(String message){
        logger.warn("{}", message);
    }
    public static void errorLog(String message){
        logger.error("{}", message);
    }
    public static void finishTest(String testName){
        logger.info("***** Execution finished: {} *****", testName);
    }
}