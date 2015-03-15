package com.samenea.commons.component.utils.persian;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ghasemkiani.util.icu.PersianDateFormat;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

public class DateUtilTest {

	@Test(expected = SamenRuntimeException.class)
	public void should_throw_exception_when_input_incorrect_formatted_toEnglishDate() {

		DateUtil.toEnglishDate("1391/1");
	}

	@Test
	public void should_return_null_toEnglishDate() {
		Assert.assertNull(DateUtil.toEnglishDate(""));

	}

	@Test(expected = SamenRuntimeException.class)
	public void should_throw_exception_when_input_incorrect_formatted_toEnglishDateTime() {

		DateUtil.toEnglishDate("1391/12/1", "12:22");
	}

	@Test
	public void should_return_null_toEnglishDateTime() {
		Assert.assertNull(DateUtil.toEnglishDate("", ""));

	}

	@Test
	public void should_get_date() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 10, 19,0,0,0);
		Assert.assertEquals(calendar.getTime().toString(), DateUtil
				.toEnglishDate("1391/08/29").toString());

	}

	@Test
	public void should_get_dateTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 10, 19,10,10,05);
		Assert.assertEquals(calendar.getTime().toString(),
				DateUtil.toEnglishDate("1391/08/29", "10:10:05").toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void should_return_null_toString() {

		DateUtil.toString(null);
	}

	@Test
	public void should_get_date_toString() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 10, 12,0,0,0);
		
		Assert.assertEquals("۱۳۹۱/۰۸/۲۲", DateUtil.toString(calendar.getTime()).toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void should_return_argumentException_toString() {
		java.util.Date date = new java.util.Date("2012/11/12 10");
		PersianDateFormat dateFormat = new PersianDateFormat(
				"yyyy/MM/dd hh:mm:ss", new ULocale("fa", "IR", ""));
		DateUtil.toString(date, dateFormat);
	}

	@Test
	public void should_get_pseriandate_with_format() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 10, 12,10,10,0);
		PersianDateFormat dateFormat = new PersianDateFormat(
				"yyyy/MM/dd hh:mm:ss ", new ULocale("fa", "IR", ""));
		Assert.assertEquals("۱۳۹۱/۰۸/۲۲ ۱۰:۱۰:۰۰ ",
				DateUtil.toString(calendar.getTime(), dateFormat).toString());

	}

}
