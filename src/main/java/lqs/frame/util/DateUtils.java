package lqs.frame.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期类型工具类
 * 
 * @author liqingshan 2015-12-03
 *
 */
public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger("lqs.frame.util.DateUtils");
	String[] pattern = new String[] { "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyyMMdd", "yyyy-MM-dd", "yyyy/MM/dd",
			"yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };

	/**
	 * 判断指定日期是否是周末（默认按"yyyy-MM-dd"日期格式转化）
	 * 
	 * @param date
	 *            指定日期格式字符串
	 * @return true：周末；false：非周末
	 * 
	 */
	public static boolean isWeekend(String date) {
		return isWeekend(date, DatePatterns.POPULAR_DATE);
	}

	/**
	 * 判断指定日期是否是周末
	 * 
	 * @param date
	 *            指定日期格式字符串
	 * @param pattern
	 *            日期格式
	 * @return true：周末；false：非周末
	 */
	private static boolean isWeekend(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date time = null;

		try {
			time = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 * @return true：闰年；false：非闰年
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
			return true;
		return false;
	}

	/**
	 * 获取指定年的月份中包含的天数
	 * 
	 * @param year
	 *            指定年
	 * @param month
	 *            指定月
	 * @return 指定月份包含的天数
	 */
	public static int getDays(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			return 31;
		else if (month == 2) {
			if (isLeapYear(year))
				return 29;
			else
				return 28;
		} else
			return 30;
	}

	/**
	 * 依据给定的开始时间、结束时间按求出相应的日期集合
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 日期集合
	 */
	public static List<String> getBetweenDate(String beginDate, String endDate) {

		List<String> dateList = new ArrayList<String>();

		try {

			SimpleDateFormat sdf = new SimpleDateFormat(DatePatterns.POPULAR_DATE);
			Date begin = sdf.parse(beginDate);
			Date end = sdf.parse(endDate);

			if (begin.after(end)) { // 开始时间不能大于结束时间
				System.out.println(beginDate + "不能大于" + endDate);
				logger.error(beginDate + "不能大于" + endDate);

				return dateList;
			}

			Calendar beginCalendar = GregorianCalendar.getInstance();
			beginCalendar.setTime(begin);
			Calendar endCalendar = GregorianCalendar.getInstance();
			endCalendar.setTime(end);
			int beginYear = beginCalendar.get(Calendar.YEAR); // 开始年份
			int beginMonth = beginCalendar.get(Calendar.MONTH) + 1; // 开始月份
			int beginDay = beginCalendar.get(Calendar.DAY_OF_MONTH); // 开始天
			int endYear = endCalendar.get(Calendar.YEAR);
			int endMonth = endCalendar.get(Calendar.MONTH) + 1; // 结束月份
			int endDay = endCalendar.get(Calendar.DAY_OF_MONTH); // 结束天

			for (int i = beginYear; i <= endYear; i++) {
				int tempEndMonth = 12;
				int tempBeginMonth = 1;

				if (i == endYear)
					tempEndMonth = endMonth;

				if (i == beginYear)
					tempBeginMonth = beginMonth;

				for (int j = tempBeginMonth; j <= tempEndMonth; j++) {
					int tempDays = getDays(i, j);
					int tempBeginDays = 1;

					if (i == endYear && j == tempEndMonth)
						tempDays = endDay;

					if (i == beginYear && j == tempBeginMonth)
						tempBeginDays = beginDay;

					for (int k = tempBeginDays; k <= tempDays; k++) {
						dateList.add(i + "-" + (j < 10 ? "0" + j : j) + "-" + (k < 10 ? "0" + k : k));
					}
				}
			}

		} catch (Exception e) {
			dateList.clear();
			e.printStackTrace();
			logger.error("日期类型转化错误！");
		}

		return dateList;
	}

	/**
	 * 格式化日期（默认按"yyyy-MM-dd"日期格式转化）
	 * 
	 * @param date
	 *            日期
	 * @return 日期格式字符串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DatePatterns.POPULAR_DATE);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 获取当前时间的日期部分（默认按"yyyy-MM-dd"日期格式转化）
	 * 
	 * @return 当前时间的日期部分
	 */
	public static String getCurrentDateStr() {
		return formatDate(new Date());
	}

	/**
	 * 获取当前时间的日期部分
	 * 
	 * @param pattern
	 *            日期格式
	 * @return 当前时间的日期部分
	 */
	public static String getCurrentDateStr(String pattern) {
		return formatDate(new Date(), pattern);
	}

	/**
	 * 获取当前日期，不带时、分、秒
	 * 
	 * @return 当前日期
	 */
	public static Date getCurrentDate() {
		Date date = parseDate(formatDate(new Date()));
		return date;
	}

	/**
	 * 获得当前时间，带时、分、秒
	 * 
	 * @return 当前时间
	 */
	public static Date getCurrentDateTime() {
		return new Date();
	}

	/**
	 * 把字符串类型的日期转换成Date类型（默认按"yyyy-MM-dd"日期格式转化）
	 * 
	 * @param date
	 *            日期格式字符串
	 * @return 日期类型的值
	 */
	public static Date parseDate(String date) {
		return parseDate(date, DatePatterns.POPULAR_DATE);
	}

	/**
	 * 把字符串类型的日期转换成Date类型
	 * 
	 * @param date
	 *            日期格式字符串
	 * @param 日期格式
	 * @return 日期类型
	 */
	public static Date parseDate(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date rtn = null;

		try {
			rtn = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("日期类型转化错误！");
		}

		return rtn;
	}

	/**
	 * 获取指定日期的年部分
	 * 
	 * @param date
	 *            指定日期
	 * @return 第几年
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取指定日期的月部分
	 * 
	 * @param date
	 *            指定日期
	 * @return 所在年的第几月
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的天部分
	 * 
	 * @param date
	 *            指定日期
	 * @return 所在月的第几天
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的小时部分
	 * 
	 * @param date
	 *            指定日期
	 * @return 所在天的第几小时
	 */
	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定时间的日期部分
	 * 
	 * @param datetime
	 *            指定时间
	 * @return 日期部分
	 */
	public static Date getDate(Date datetime) {
		String date = new SimpleDateFormat(DatePatterns.POPULAR_DATE).format(datetime);
		return parseDate(date);
	}

	/**
	 * 增加指定天数，可以跨月
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param days
	 *            指定天数
	 * @return 增加后的日期
	 */
	public static Date addDay(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	/**
	 * 增加指定天数（默认按"yyyy-MM-dd"日期格式转化），可以跨月
	 * 
	 * @param date
	 *            指定的日期格式字符串
	 * @param days
	 *            指定天数
	 * @return 增加后的日期字符串（"yyyy-MM-dd"格式）
	 */
	public static String addDay(String date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		cal.add(Calendar.DATE, days);

		return formatDate(cal.getTime());
	}

	/**
	 * 增加指定小时数，可以超过当天
	 * 
	 * @param date
	 *            指定日期
	 * @param hours
	 *            增加的小时数
	 * @return 增加后的日期
	 */
	public static Date addHour(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);

		return cal.getTime();
	}

	/**
	 * 指定日期增加小时（默认按"yyyy-MM-dd"日期格式转化），可以超过当天
	 * 
	 * @param date
	 *            指定的日期格式字符串
	 * @param hours
	 *            增加的小时数
	 * @return 增加小时后的日期字符串（"yyyy-MM-dd HH:mm:ss"日期格式）
	 */
	public static String addHour(String date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		cal.add(Calendar.HOUR, hours);

		return formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 指定日期增加分钟（默认按"yyyy-MM-dd"日期格式转化），可以超过当前小时
	 * 
	 * @param date
	 *            指定日期
	 * @param minutes
	 * @return
	 */
	public static Date addMinute(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);

		return cal.getTime();
	}

	/**
	 * 增加指定小时数，可以超过当前小时
	 * 
	 * @param date
	 *            指定日期格式字符串
	 * @param minutes
	 * @return
	 */
	public static String addMinute(String date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		cal.add(Calendar.MINUTE, minutes);

		return formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * @param date
	 *            指定日期
	 * @param seconds
	 * @return
	 */
	public static Date addSecond(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);

		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 *            指定日期格式字符串
	 * @param seconds
	 * @return
	 */
	public static String addSecond(String date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		cal.add(Calendar.SECOND, seconds);

		return formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param d1
	 *            第一个日期
	 * @param d2
	 *            第二个日期
	 * @return 两个日期相差的天数
	 */
	public static long getDateDiffDays(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取两个日期相差的小时数
	 * 
	 * @param d1
	 *            第一个日期
	 * @param d2
	 *            第二个日期
	 * @return 两个日期相差的小时数
	 */
	public static long getDateDiffHours(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / (60 * 60 * 1000);
	}

	/**
	 * 获取两个日期相差的分钟数
	 * 
	 * @param d1
	 *            第一个日期
	 * @param d2
	 *            第二个日期
	 * @return 两个日期相差的分钟数
	 */
	public static long getDateDiffMinutes(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / (60 * 1000);
	}

	/**
	 * 获取两个日期相差的秒数
	 * 
	 * @param d1
	 *            第一个日期
	 * @param d2
	 *            第二个日期
	 * @return 两个日期相差的秒数
	 */
	public static long getDateDiffSeconds(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / (1000);
	}

	/**
	 * 获取本周的周一日期字符串
	 * 
	 * @return 周一的日期字符串（默认按"yyyy-MM-dd"日期格式转化）
	 */
	public static String getDateOfWeekMondy() {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return formatDate(calendar.getTime());
	}

	/**
	 * 获取指定日期的时间戳[java.sql.Timestamp]
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期对应的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		String nowTime = new SimpleDateFormat(DatePatterns.POPULAR_DATE24TIME).format(date);

		return Timestamp.valueOf(nowTime);
	}

}
