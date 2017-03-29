package com.mitrais.rms.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatter
{
	private static final SimpleDateFormat formatter =
		new SimpleDateFormat("yyyy-MM-dd");

	/**
	 *  @param input date to be formatted
	 *  @return String formmated date
	 **/
	public static String format(Date input)
	{
		return formatter.format(input);
	}

	/**
	 *  @param input to be parsed as Date
	 *  @return Date from input, or today
	 **/
	public static Date parse(String input)
	{
		try {
			return formatter.parse(input);
		} catch(ParseException e) {
			System.err.println(e.getMessage());
		}
		return new Date();
	}
}