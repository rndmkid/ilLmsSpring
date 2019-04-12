package com.st.lms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalculations {

	//gets today's date in yyyy-MM-dd
	public static Date getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(new Date());
		
		Date utilDate = null;
		try {
			utilDate = sdf.parse(currentTime); //in utilDate
		} catch (ParseException e) {
			System.out.println("Problem with getting today's date!");
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  //in sqlDate
		
		return sqlDate;
	}
	
	//get today's date 7 days (1 week) from now
	public static Date getTodayPlus7() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(new Date());
		
		Date utilDate = null;
		try {
			utilDate = sdf.parse(currentTime);
		} catch (ParseException e) {
			System.out.println("Problem with trying to get future date!");
		}
		
		//convert utilDate to calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(utilDate);
		cal.add(Calendar.DATE, 7);
		
		Date currentTimePlus7 = cal.getTime();  //calendar to utilDate
		java.sql.Date sqlDate = new java.sql.Date(currentTimePlus7.getTime()); //to sqlDate
		
		return sqlDate;
	}
	
	public static Date convertStringtoDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date utilDate = null;
		try {
			utilDate = sdf.parse(strDate);
		} catch (ParseException e) {
			System.out.println("Problem parsing String to Date!");
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		return sqlDate;
	}
}