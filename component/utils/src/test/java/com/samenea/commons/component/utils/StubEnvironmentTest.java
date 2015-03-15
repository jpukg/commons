package com.samenea.commons.component.utils;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

public class StubEnvironmentTest {

	StubEnvironment stubEnvironment;

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_second_is_more_than_59() {
		stubEnvironment = new StubEnvironment(0, 0, 0, 0, 0, 69);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_second_is_less_than_0() {
		stubEnvironment = new StubEnvironment(0, 0, 0, 0, 0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_minute_is_more_than_59() {
		stubEnvironment = new StubEnvironment(0, 0, 0, 0, 65, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_minute_is_less_than_0() {
		stubEnvironment = new StubEnvironment(0, 0, 0, 0, -2, 0);
	}


	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_hour_is_more_than_23() {
		stubEnvironment = new StubEnvironment(0, 0, 0, 25, 0, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_hour_is_less_than_0() {
		stubEnvironment = new StubEnvironment(0, 0, 0, -3, 0, 0);
	}
	
	@Test
	public void should_return_date_compatible_with_arguments(){
		final int year = 1987;
		final int month = 7;
		final int day = 24;
		final int hour = 23;
		final int minute = 12;
		final int second = 20;
		stubEnvironment = new StubEnvironment(year,month,day,hour,minute,second);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,day,hour,minute,second);
		Assert.assertEquals(calendar.getTime().toString(), stubEnvironment.getCurrentDate().toString());
	}


}
