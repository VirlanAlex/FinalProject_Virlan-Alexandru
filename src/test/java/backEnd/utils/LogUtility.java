package backEnd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtility {
    public static Logger logger = LogManager.getLogger();

    //log care sa anunte inceputul testului
    public static void startTest(String testName){
        logger.info("***** Execution started: "+testName+" *****");
    }

    //log care sa logheze info
    public static void infoLog(String message){
        logger.info(message);
    }

    //log care sa logheze error
    public static void errorLog(String message){
        logger.error(message);
    }

    //log care sa anunte sfarsitului testului
    public static void finishTest(String testName){
        logger.info("***** Execution finished: "+testName+" *****");
    }
}
