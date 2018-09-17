package main;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.traffic.congestion.CityCongestionsService;
import com.traffic.utils.PropertiesUtil;
import com.traffic.utils.StopWatch;

public class AppMain extends TimerTask {
	private final StopWatch stopWatch = new StopWatch();
	private final CityCongestionsService cityCongestions = new CityCongestionsService();

	@Override
	public void run() {
		System.out.println(stopWatch.reset());
		try {
			PropertiesUtil.reloadProperties();
			cityCongestions.processCongestions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(stopWatch.lap());
	}

	public static void main(String[] args) throws IOException {
		TimerTask timerTask = new AppMain();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 30 * 1 * 1000);
	}

}
