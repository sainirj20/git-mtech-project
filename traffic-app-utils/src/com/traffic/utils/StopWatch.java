package com.traffic.utils;

import java.util.Date;

public class StopWatch {
	public static final long MINUTE = 60 * 1000;
	private long startTime = System.currentTimeMillis();

	public StopWatch() {
		startTime = System.currentTimeMillis();
	}

	public String reset() {
		startTime = System.currentTimeMillis();
		return "\n[job started at " + new Date(startTime) + "]";
	}

	public String lap() {
		long lapTime = System.currentTimeMillis();
		String result = "[That took :: " + getFormatedTime(lapTime) + "]";
		startTime = System.currentTimeMillis();
		return result;
	}

	public void showTimer(long stopTime) {
		long currentTime = System.currentTimeMillis() - startTime;
		while ((stopTime - currentTime) > MINUTE) {
			long min = ((stopTime - currentTime) / MINUTE) + 1;
			System.out.print(min + "min... ");
			try {
				Thread.sleep(MINUTE);
			} catch (InterruptedException e) {
			}
			currentTime = System.currentTimeMillis() - startTime;
		}
		System.out.println((stopTime - currentTime) / 1000 + "sec... ");
	}

	private String getFormatedTime(long stopTime) {
		long endTimeInSeconds = (stopTime - startTime) / 1000;
		long minutes = endTimeInSeconds / 60;
		long seconds = endTimeInSeconds % 60;
		long miliSeconds = stopTime % 1000;
		StringBuilder sb = new StringBuilder();
		if (minutes > 0) {
			sb.append(minutes + " min " + seconds + " sec " + miliSeconds + " ms");
		} else if (seconds > 0) {
			sb.append(seconds + " sec " + miliSeconds + " ms");
		} else {
			sb.append(miliSeconds + " ms");
		}
		return sb.toString();
	}
}
