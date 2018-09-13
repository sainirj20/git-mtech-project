package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.traffic.congestion.CityCongestions;
import com.traffic.log.MyLogger;
import com.traffic.utils.StopWatch;

public class AppMain {

	public static void main(String[] args) throws IOException {
		Logger logger = MyLogger.getLogger(AppMain.class.getName());
		StopWatch stopWatch = new StopWatch();

		new CityCongestions().generateCongestion();
		logger.log(Level.INFO, stopWatch.totalTime());
	}
}
