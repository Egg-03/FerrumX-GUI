package com.ferrumx.ui.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import com.ferrumx.ui.secondary.ExceptionUI;

public class DateTime {
	private DateTime() {
		throw new IllegalStateException("Utility Class");
	}

	public static String getDate() {
		try {
			LocalDateTime ldt = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
			Date myDate = sdf.parse(ldt.format(dtf));
			sdf.applyPattern("EEEEE, d MMMMM yyyy");
			return sdf.format(myDate);
		} catch (ParseException e) {
			new ExceptionUI("Date Parsing Error", e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
			return "Date Unavailable";
		}
	}
}
