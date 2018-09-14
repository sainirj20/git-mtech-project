package com.traffic.log;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

class MyFormatter extends Formatter {
	@Override
	public String format(LogRecord record) {
		if (record.getLevel().equals(Level.INFO)) {
			return record.getMessage() + "\n";
		}else if (record.getLevel().equals(Level.SEVERE)) {
			return record.getThrown() + "\n";
		}
		
		return "[" + new Date(record.getMillis()) + "] " + record.getLevel() + " " + record.getSourceClassName() + " "
				+ record.getSourceMethodName() + " :: " + record.getMessage() + "\n";
	}
}
