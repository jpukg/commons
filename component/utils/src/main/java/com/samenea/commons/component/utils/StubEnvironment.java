package com.samenea.commons.component.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * with this class you can access to Environment variables.
 * @author soroosh
 *
 */
public class StubEnvironment extends DefaultEnvironment {
	private final Calendar calendar = Calendar.getInstance();
	private final int year;
	private final int month;
	private final int day;
	private final int hour;
	private final int minute;
	private final int second;

	public StubEnvironment(int year, int month, int day, int hour, int minute,
			int second) {
		if (second < 0 || second > 59) {
			throw new IllegalArgumentException("second must be between 0 and 59 but it's " + second);
		}
		
		if (minute < 0 || minute > 59) {
			throw new IllegalArgumentException("minute must be between 0 and 59 but it's " + minute);
		}
		
		if (hour < 0 || hour > 23) {
			throw new IllegalArgumentException("second must be between 0 and 23 but it's " + hour);
		}
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;

	}

	public StubEnvironment(int year, int month, int day) {
		this(year, month, day, 0, 0, 0);
	}

	@Override
	public Date getCurrentDate() {
		calendar.set(this.year, this.month, this.day, this.hour, this.minute,this.second);
		return calendar.getTime();
	}

}
