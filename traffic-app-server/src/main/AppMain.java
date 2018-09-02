package main;

import java.io.IOException;

import com.traffic.congestion.CityCongestions;
import com.traffic.utils.StopWatch;

public class AppMain {

	public static void main(String[] args) throws IOException {
		StopWatch stopWatch = new StopWatch();
		new CityCongestions().generateCongestion();
		System.out.println(stopWatch.totalTime());
	}

}
