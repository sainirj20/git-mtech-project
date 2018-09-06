package com.traffic.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class HistoryKeyMaker {
	private final String pattern = "yyyy-MMMMM-dd-E-HH-mm";
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	private final int roundTo = 10;	
	private final int dayTime[] = { 6, 21 };	// 6am-10pm
	private final int weekend[] = { 1, 7 };		// Sunday=1 and Saturday=7

	public String getKey() {
		Calendar today = Calendar.getInstance();
		int roundMin = today.get(Calendar.MINUTE) / roundTo;
		today.set(Calendar.MINUTE, roundMin * roundTo);
		String key = simpleDateFormat.format(today.getTime());
		return key;
	}

	public List<String> getTodaysKeys() {
		List<String> workingHours = new LinkedList<>();
		List<String> nonWorkingHours = new LinkedList<>();

		Calendar cal = Calendar.getInstance();
		int currentHour = cal.get(Calendar.HOUR_OF_DAY);
		int currentMin = cal.get(Calendar.MINUTE);
		for (int i = 0; i <= currentHour; i++) {
			for (int j = 0; j < 60; j += roundTo) {
				cal.set(Calendar.MINUTE, j);
				cal.set(Calendar.HOUR_OF_DAY, i);
				int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);

				if (hourOfDay == currentHour && minute > currentMin) {
					break;
				}

				if (dayTime[0] <= hourOfDay && hourOfDay <= dayTime[1]) {
					workingHours.add(simpleDateFormat.format(cal.getTime()));
				} else {
					nonWorkingHours.add(simpleDateFormat.format(cal.getTime()));
				}
			}
		}
		return (dayTime[0] <= currentHour && currentHour <= dayTime[1]) ? workingHours : nonWorkingHours;
	}

	public List<String> getLastWeeksKeys() {
		List<String> weekends = new LinkedList<>();
		List<String> weekdays = new LinkedList<>();
		Calendar cal = Calendar.getInstance();
		int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int roundMin = cal.get(Calendar.MINUTE) / roundTo;
		cal.set(Calendar.MINUTE, roundMin * roundTo);
		for (int i = 0; i < 7; i++) {
			cal.add(Calendar.DATE, -1);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == weekend[0] || dayOfWeek == weekend[1]) {
				weekends.add(simpleDateFormat.format(cal.getTime()));
			} else {
				weekdays.add(simpleDateFormat.format(cal.getTime()));
			}
		}
		return (currentDayOfWeek == weekend[0] || currentDayOfWeek == weekend[1]) ? weekends : weekdays;
	}
}
