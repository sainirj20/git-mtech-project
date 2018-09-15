package main;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.traffic.congestion.CityCongestionsService;
import com.traffic.log.MyLogger;
import com.traffic.utils.PropertiesUtil;
import com.traffic.utils.StopWatch;

public class AppMain extends TimerTask {
	private final Logger logger = MyLogger.getLogger(AppMain.class.getName());
	
	private final StopWatch stopWatch = new StopWatch();
	private final CityCongestionsService cityCongestions = new CityCongestionsService();

	@Override
	public void run() {
		logger.log(Level.INFO, stopWatch.reset());
		try {
			PropertiesUtil.reloadProperties();
			cityCongestions.processCongestions();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
		logger.log(Level.INFO, stopWatch.lap());
	}

	public static void main(String[] args) throws IOException {
		TimerTask timerTask = new AppMain();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 30 * 1 * 1000);
	}

}
