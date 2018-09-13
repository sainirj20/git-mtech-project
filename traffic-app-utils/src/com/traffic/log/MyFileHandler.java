package com.traffic.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MyFileHandler {

	private class MyFilter implements Filter {
		@Override
		public boolean isLoggable(LogRecord log) {
			return (log.getLevel() == Level.FINE) ? false : true;
		}
	}

	public Handler getHandler() {
		Handler fileHandler = null;
		try {
			fileHandler = new FileHandler("traffic-apps.log", true);
			fileHandler.setFormatter(new MyFormatter());
			fileHandler.setFilter(new MyFilter());
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		return fileHandler;
	}

}
