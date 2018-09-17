package main;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.traffic.congestion.CityCongestionsService;
import com.traffic.utils.PropertiesUtil;
import com.traffic.utils.StopWatch;

public class AppMain {
	private final long executionPeriod = 3 * StopWatch.MINUTE;

	private final StopWatch stopWatch = new StopWatch();
	private final CityCongestionsService cityCongestions = new CityCongestionsService();

	private class Task extends TimerTask {
		@Override
		public void run() {
			System.out.println(stopWatch.reset());
			try {
				PropertiesUtil.reloadProperties();
				cityCongestions.processCongestions();
				System.out.print("sleeping now.. will wake up in : ");
				stopWatch.showTimer(executionPeriod);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void start() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), 0, executionPeriod);
	}

	public static void main(String[] args) throws IOException {
		AppMain main = new AppMain();
		main.start();
	}

}
