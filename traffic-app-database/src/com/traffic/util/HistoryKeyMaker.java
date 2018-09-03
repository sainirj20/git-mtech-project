package com.traffic.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class HistoryKeyMaker {
	private final String pattern = "yyyy-MMMMM-dd-E-HH-mm";
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	private final int dayTime[] = { 6, 21 }; // 6am-10pm
	private final int weekend[] = { 1, 7 }; // Sunday=1 and Saturday=7

	public String getKey() {
		Calendar today = Calendar.getInstance();
		int roundMin = today.get(Calendar.MINUTE) / 10;
		today.set(Calendar.MINUTE, roundMin * 10);
		String key = simpleDateFormat.format(today.getTime());
		return key;
	}

	public Map<String, String> getTodaysKeys() {
		Map<String, String> workingHours = new LinkedHashMap<>();
		Map<String, String> nonWorkingHours = new LinkedHashMap<>();

		Calendar cal = Calendar.getInstance();
		int currentHour = cal.get(Calendar.HOUR_OF_DAY);
		int currentMin = cal.get(Calendar.MINUTE);
		for (int i = 0; i <= currentHour; i++) {
			for (int j = 0; j < 60; j += 10) {
				cal.set(Calendar.MINUTE, j);
				cal.set(Calendar.HOUR_OF_DAY, i);
				int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);

				if (hourOfDay == currentHour && minute > currentMin) {
					break;
				}

				if (dayTime[0] <= hourOfDay && hourOfDay <= dayTime[1]) {
					workingHours.put(hourOfDay + ":" + minute, simpleDateFormat.format(cal.getTime()));
				} else {
					nonWorkingHours.put(hourOfDay + ":" + minute, simpleDateFormat.format(cal.getTime()));
				}

			}
		}
		return (dayTime[0] <= currentHour && currentHour <= dayTime[1]) ? workingHours : nonWorkingHours;
	}

	public Map<Integer, String> getLastWeeksKeys() {
		Map<Integer, String> weekends = new LinkedHashMap<>();
		Map<Integer, String> weekdays = new LinkedHashMap<>();
		Calendar cal = Calendar.getInstance();
		int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int roundMin = cal.get(Calendar.MINUTE) / 10;
		cal.set(Calendar.MINUTE, roundMin * 10);
		for (int i = 0; i < 7; i++) {
			cal.add(Calendar.DATE, -1);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			if (dayOfWeek == weekend[0] || dayOfWeek == weekend[1]) {
				weekends.put(dayOfWeek, simpleDateFormat.format(cal.getTime()));
			} else {
				weekdays.put(dayOfWeek, simpleDateFormat.format(cal.getTime()));
			}

		}
		return (currentDayOfWeek == weekend[0] || currentDayOfWeek == weekend[1]) ? weekends : weekdays;
	}

}
