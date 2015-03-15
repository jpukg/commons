package com.samenea.commons.component.utils.persian;

/**
 * DateUtil class to convert Date
 *
 * @author payam faryadres
 * @date 12-11-2012
 */

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.aspectj.weaver.patterns.PerSingleton;
import org.springframework.util.Assert;

import com.ghasemkiani.util.DateFields;
import com.ghasemkiani.util.SimplePersianCalendar;
import com.ghasemkiani.util.icu.PersianCalendar;
import com.ghasemkiani.util.icu.PersianDateFormat;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

public final class DateUtil {
	/**
	 * this method convert string persianDate to Date
	 * 
	 * @param persianDate
	 *            must not be null, Persian date to a string must be in format
	 *            yyyy/mm/dd
	 * @throws SamenRuntimeException
	 *             When not properly formatted input string
	 * @return Date The value of the Date
	 */
	public static java.util.Date toEnglishDate(String persianDate) {
		Assert.notNull(persianDate, "persianDate must not be null");
		if (persianDate != "" && persianDate.length() > 0) {
			int year, month, day;
			String[] arrayDate = persianDate.split("/");
			try {
				if (arrayDate.length == 3) {
					year = Integer.parseInt(arrayDate[0]);
					month = Integer.parseInt(arrayDate[1]);
					day = Integer.parseInt(arrayDate[2]);
					PersianCalendar persianCalendar = new PersianCalendar(
							new ULocale("fa", "IR", ""));
					persianCalendar.set(year, month - 1, day, 0, 0, 0);

					return persianCalendar.getTime();
				} else {
					throw new SamenRuntimeException(
							"Input string is not formatted correctly");

				}
			} catch (Exception e) {
				throw new SamenRuntimeException(
						"Input string is not formatted correctly");
			}
		}
		return null;

	}

	/**
	 * this method convert string persianDate&Time to Date
	 * 
	 * @param persianDate
	 *            must not be null, Persian date to a string must be in format
	 *            yyyy/mm/dd
	 * 
	 * @param time
	 *            must not be null,time date to a string must be in format
	 *            hh:mm:ss
	 * @throws SamenRuntimeException
	 *             When not properly formatted input string
	 * @return Date The value of the Date
	 */
	public static java.util.Date toEnglishDate(String persianDate, String time) {
		Assert.notNull(persianDate, "persianDate must not be null");
		Assert.notNull(time, "time must not be null");
		try {
			if (persianDate != "" && persianDate.length() > 0 && time != null
					&& time.length() > 0) {
				int year, month, day;
				String[] arrayDate = persianDate.split("/");
				String[] arraytime = time.split(":");
				int hours = Integer.parseInt(arraytime[0]);
				int minute = Integer.parseInt(arraytime[1]);
				int second = Integer.parseInt(arraytime[2]);
				year = Integer.parseInt(arrayDate[0]);
				month = Integer.parseInt(arrayDate[1]);
				day = Integer.parseInt(arrayDate[2]);
				PersianCalendar persiancalendar = new PersianCalendar(
						new ULocale("fa", "IR", ""));
				persiancalendar
						.set(year, month - 1, day, hours, minute, second);
				return persiancalendar.getTime();
			}
		} catch (Exception ex) {
			throw new SamenRuntimeException(
					"Input string is not formatted correctly");
		}
		return null;

	}

	/**
	 * this method convert englishDate to persianDate
	 * 
	 * @param date
	 *            must not be null
	 * @return String ,return persian date to string
	 */

	public static String toString(Date date) {
		Assert.notNull(date, "date must not be null");

		if (date != null) {

			PersianCalendar persiancalendar = new PersianCalendar(
					TimeZone.getTimeZone("Asia/Tehran"));
			DateFormat dateformat = persiancalendar.getDateTimeFormat(
					DateFormat.DATE_FIELD, -1, new ULocale("fa", "IR", ""));
			return dateformat.format(date);
		}
		return null;

	}

	/**
	 * this method convert englishDate to persianDate
	 * and return to the specified format persianDate
	 * 
	 * @param Date date 
	 *            must not be null
	 * @param PersianDateFormat dateformat must not be null           
	 * @return String ,return to the specified format string
	 */

	public static String toString(Date date, PersianDateFormat dateformat) {
		Assert.notNull(date, "date must not be null");
		Assert.notNull(dateformat, "dateformat must not be null");
		if (date != null && dateformat!=null) {

			PersianCalendar persiancalendar = new PersianCalendar(new ULocale(
					"fa", "IR", ""));
			dateformat.setCalendar(persiancalendar);
			dateformat.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));

			return dateformat.format(date);
		}
		return null;

	}

   
	


}
