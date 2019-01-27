package com.sci.accounting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	// 把 unix time --> 11:11

	public static String getFormattedTime(long timeStamp) {

		// 時間格式化，傳入的參數是想要格式化的類型，一般 yyyy-MM-dd HH:mm:ss
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

		return simpleDateFormat.format(new Date(timeStamp));
	}

	// 2018-11-11
	public static String getFormattedDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}

	// 2018-11-11
	private static Date strToDate(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();    // 今天
	}

	// 2018-11-11 星期幾
	public static String getWeekDay(String date) {
		String[] weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strToDate(date));
		int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;    // 返回的是1-7，所以要-1
		return weekdays[index];
	}

	// 2018-11-11 月份
	public static String getDateTitle(String date) {
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strToDate(date));
		int monthIndex = calendar.get(Calendar.MONTH);    // 返回的是1-7，所以要-1
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return months[monthIndex] + " " + String.valueOf(day); // 2018-11-11 --> Nov 11
	}

}












