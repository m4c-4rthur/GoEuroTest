/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.goeurotest.utils;

//import com.asset.lbccore.exception.LBCException;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Mina Initializes and manages logging format and hwhw.
 */
public class LogUtil {

    private static Logger logger;

    public static void initialize() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("log4j.properties");
        if (url != null) {
            PropertyConfigurator.configure(url);
        } else {
            PropertyConfigurator.configure("log4j.properties");
        }

        logger = Logger.getRootLogger();

        info("***************** Loggers Initialized *********************");

    }

    public static void initialize(String log4jFile) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(log4jFile);
        if (url != null) {
            PropertyConfigurator.configure(url);
        } else {
            PropertyConfigurator.configure("log4j.properties");
        }

        logger = Logger.getRootLogger();

        info("***************** Loggers Initialized *********************");

    }

    public static Logger getLoggerInstance() {
        if (logger != null) {
            return logger;
        } else {
            initialize(null);
            return logger;
        }
    }

    private static StackTraceElement getStackTraceOfCaller() {
        StackTraceElement st[] = Thread.currentThread().getStackTrace();
        return st[3];
    }

    public static void debug(String msg) {

        StackTraceElement caller = getStackTraceOfCaller();
        try {
            logger.debug("[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName() + "(): " + msg);
        } catch (ClassNotFoundException ex) {
            logger.error(caller.getClassName() + " | " + caller.getMethodName() + "() |  " + msg);
        }
    }

    public static void info(String msg) {

        StackTraceElement caller = getStackTraceOfCaller();
        try {
            logger.info("[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName() + "(): " + msg);
        } catch (ClassNotFoundException ex) {
            logger.error(caller.getClassName() + "." + caller.getMethodName() + "() | " + msg);
        }
    }

    public static void time(long timeMsec, String... msgs) {

        StackTraceElement caller = getStackTraceOfCaller();
        try {
            logger.info("[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName() + "() out in ["
                    + (System.currentTimeMillis() - timeMsec) + "] msec " + mergeStrings(msgs));
        } catch (ClassNotFoundException ex) {
            logger.error(caller.getClassName() + "." + caller.getMethodName() + "() | " + "Failed to log time");
        }
    }

    private static String mergeStrings(String... arrayOfStrings) {
        String oneString = "";
        StringBuilder sb = null;
        if (arrayOfStrings != null && arrayOfStrings.length != 0) {
            sb = new StringBuilder();

            for (String msg : arrayOfStrings) {
                sb.append(msg).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (sb != null) {
            oneString = sb.toString();
        }
        return oneString;
    }

//    public static void error(String msg, Exception e) {
//
//        StackTraceElement caller = getStackTraceOfCaller();
//
//        try {
//            logger.debug("Exception in " + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName()
//                    + "(): " + msg + " | " + "EMsg[" + e.getMessage() + "] =>");
//
//            logger.error(((Class) Class.forName(caller.getClassName())).getSimpleName() + "."
//                    + caller.getMethodName() + "() error=>", e);
//        } catch (ClassNotFoundException ex) {
//            logger.debug(caller.getClassName() + " | " + caller.getMethodName() + "() | " + msg + " | " + "EMsg["
//                    + e.getMessage() + "] =>", e);
//        }
//    }
    public static void error(String msg) {

        StackTraceElement caller = getStackTraceOfCaller();

        try {
            logger.debug("Exception in " + "[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName()
                    + "(): " + msg + " =>");

            logger.error("[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName() + "() error=>");
        } catch (ClassNotFoundException ex) {
            logger.debug(caller.getClassName() + " | " + caller.getMethodName() + "() | " + msg);
        }
    }

    public static void error(String msg, Throwable th) {

        StackTraceElement caller = getStackTraceOfCaller();

        try {
            logger.debug("Exception in " + "[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName()
                    + "(): " + msg + " | " + "EMsg[" + th.getMessage() + "] =>");

            logger.error("[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "."
                    + caller.getMethodName() + "() error=>", th);
        } catch (ClassNotFoundException ex) {
            logger.debug(caller.getClassName() + " | " + caller.getMethodName() + "() | " + msg + " | " + "EMsg["
                    + th.getMessage() + "] =>", th);
        }
    }

    public static void error(String msg, Throwable th, Object... obj) {
        StackTraceElement caller = getStackTraceOfCaller();
        try {
            logger.debug("Exception in " + "[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "." + caller.getMethodName()
                    + "(): " + msg + " | " + "EMsg[" + th.getMessage() + "]. There are extra details, please check in error_log.");

            logger.error("[" + Thread.currentThread().getName() + "]" + ((Class) Class.forName(caller.getClassName())).getSimpleName() + "."
                    + caller.getMethodName() + "() error=>", th);
        } catch (ClassNotFoundException ex) {
            logger.debug(caller.getClassName() + " | " + caller.getMethodName() + "() | " + msg + " | " + "EMsg["
                    + th.getMessage() + "] =>", th);
        }
    }

    private static String[] stringizeObjects(Object... objs) {
        String strings[] = null;
        try {
            int length = objs.length;
            strings = new String[length];
            for (int x = 0; x < objs.length; x++) {
                Object obj = objs[x];
                strings[x] = obj.toString();
            }
        } catch (Exception e) {
            logger.error("Internal application logging error", e);
        }
        return strings;
    }
}
