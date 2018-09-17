//package com.traffic.log;
//
//import java.util.logging.ConsoleHandler;
//import java.util.logging.Handler;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import com.traffic.utils.PropertiesUtil;
//public class MyLogger {
//	private 
//	final Logger logger;
//
//	private MyLogger(String className) {
//		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
//		init();
//	}
//
//	private void init() {
//		logger.setUseParentHandlers(false);
//
//		Handler consoleHandler = new ConsoleHandler();
//		consoleHandler.setFormatter(new MyFormatter());
//
//		Level level = Level.FINE;
//		switch (PropertiesUtil.getPropertyValue("logger.level").toUpperCase()) {
//		case "SEVERE":
//			level = Level.SEVERE;
//			break;
//		case "WARNING":
//			level = Level.WARNING;
//			break;
//		case "INFO":
//			level = Level.INFO;
//			break;
//		case "CONFIG":
//			level = Level.CONFIG;
//			break;
//		default:
//			break;
//		}
//		logger.setLevel(level);
//		consoleHandler.setLevel(level);
//		logger.addHandler(consoleHandler);
//		logger.addHandler(new MyFileHandler().getHandler());
//
//	}
//
//	public static Logger getLogger(String className) {
//		MyLogger myLogger = new MyLogger(className);
//		return myLogger.logger;
//	}
//
//}