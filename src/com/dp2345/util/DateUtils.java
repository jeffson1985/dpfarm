package com.dp2345.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String PATTERN_DATE = "yyyy-MM-dd";

    public static final String PATTERN_DATE2 = "MM-dd";

    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static Date date(int year, int month, int day) {
        return date(year, month, day, 0, 0, 0);
    }

    public static Date date(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(year, month, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date getOneDayEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    public static Date getOneDayStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date parseDate(String string, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        return sdf.parse(string);
    }

    /**
     * <b>获取本周星期一：formatted as yyyy-MM-dd</b>
     * 
     * @return
     */
    public static String getMondayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取星期一：根据指定日期和日期模式</b>
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期模式
     * @return
     */
    public static String getMondayOfWeek(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本周星期天：formatted as yyyy-MM-dd</b>
     * 
     * @return
     */
    public static String getSundayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取星期天：根据指定日期和日期模式</b>
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期模式
     * @return
     */
    public static String getSundayOfWeek(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月第一天：formatted as yyyy-MM-dd</b>
     * 
     * @return
     */
    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月第一天：</b>
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期模式
     * @return
     */
    public static String getFirstDayOfMonth(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月最后一天：formatted as yyyy-MM-dd</b>
     * 
     * @return
     */
    public static String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月最后一天：</b>
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期模式
     * @return
     */
    public static String getLastDayOfMonth(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return dateFormat.format(calendar.getTime());
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

}
