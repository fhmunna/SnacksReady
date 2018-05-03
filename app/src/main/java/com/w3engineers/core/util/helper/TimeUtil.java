package com.w3engineers.core.util.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/*
* ****************************************************************************
* * Copyright © 2018 W3 Engineers Ltd., All rights reserved.
* *
* * Created by:
* * Name : SUDIPTA KUMAR PAIK
* * Date : 2/1/18
* * Email : sudipta@w3engineers.com
* *
* * Purpose : TimeUtil for all type of date time related operations
* *
* * Last Edited by : SUDIPTA KUMAR PAIK on 2/1/18.
* * History:
* * 1:
* * 2:
* *
* * Last Reviewed by : SUDIPTA KUMAR PAIK on 2/1/18.
* ****************************************************************************
*/

public class TimeUtil {
    public static long SECOND = 1000L;
    public static long MINUTE = 60 * SECOND;
    public static long HALF_MINUTE = 30 * SECOND;
    public static long HOUR = 60 * MINUTE;
    public static long DAY = 24 * HOUR;

    private static String dateFormat1 = "yyyy-MM-dd HH:mm:ss";
    private static String dateFormat2 = "yyyy-MM-dd HH:mm:ss.SSS";
    private static String dateFormat4 = "MMM dd";
    private static String dateFormat5 = "MMMM";
    private static String dateFormat6 = "dd";
    private static String dateFormat7 = "EEE";
    private static String dateFormat8 = "yyyy";
    private static String dateFormat9 = "hh:mm aa";
    private static String dateFormat10 = "hh:mm";
    private static String dateFormat11 = "MMMM dd, hh:mm aa";
    private static String dateFormat12 = "yyyy-MM-dd";

    /*
    * Private constructor. Don't make it public
    * */
    private TimeUtil() {
    }

    public static String parseToLocalFromNS(long timeInNS) {
        long millis = TimeUnit.NANOSECONDS.toMillis(timeInNS);
        return parseToLocalFromMilli(millis);
    }

    public static String parseToLocalFromMilli(long millis) {
        Date date = new Date(millis);
        DateFormat format = new SimpleDateFormat(dateFormat1, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static long parseToUtcFromMilli(String millis) {
        try {
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat(dateFormat2, new Locale("en"));
            Date objDate = dateFormatGmt.parse(millis);
            long currentdateInMillSec = objDate.getTime();
            return currentdateInMillSec;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long parseToLocalMilliFromMilli(long millis) {

        Date date = new Date(millis);
        DateFormat format = new SimpleDateFormat(dateFormat1, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        String defaultTimeString = format.format(date);
        long defaultTime = 0;
        try {
            defaultTime = format.parse(defaultTimeString).getTime();
            return defaultTime;
        } catch (ParseException e) {
            return millis;
        }
    }

    public static String getDate(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat4, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getDateFormat12(long milliSeconds) {
        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat12, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getOnlyMonth(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat5, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getOnlyDate(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat6, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getOnlyWeek(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat7, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getOnlyYear(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat8, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getOnlyTime(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat9, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static String getOnlyTimeHHMM(long milliSeconds) {

        Date date = new Date(milliSeconds);
        DateFormat format = new SimpleDateFormat(dateFormat10, Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }

    public static int getYear(long milli) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        return calendar.get(Calendar.YEAR);
    }

    public static String getFormatDateTime(long milliSeconds) {

        DateFormat format = new SimpleDateFormat(dateFormat11, Locale.getDefault());

        format.setTimeZone(TimeZone.getDefault());

        return format.format(new Date(milliSeconds));
    }

    public static long getTimeInMilli(long time, TimeUnit timeUnit) {

        return TimeUnit.MILLISECONDS.convert(time, timeUnit);
    }

    public static TimeUnit getUnit(String unit) {

        if ("s".equals(unit.toLowerCase(Locale.getDefault()))) return TimeUnit.SECONDS;
        if ("m".equals(unit.toLowerCase(Locale.getDefault()))) return TimeUnit.MINUTES;
        if ("h".equals(unit.toLowerCase(Locale.getDefault()))) return TimeUnit.HOURS;
        return TimeUnit.MILLISECONDS;
    }

    public static long currentTime() {
        return System.currentTimeMillis();
    }

    public static String timeStampWithoutDeliMeter() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String[] dateArray = dateFormat.format(date).split("/");

        StringBuilder builder = new StringBuilder();
        for (String s : dateArray) {
            builder.append(s);
        }
        String str = builder.toString();
        //    long dateValue = Integer.parseInt(builder.toString());

        return str;
    }

    public static String timeStampDeliMeter(String dateData) {

        try {
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date = format.parse(dateData);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            //Date date = new Date(dateData);
            String[] dateArray = dateFormat.format(date).split("/");

            StringBuilder builder = new StringBuilder();
            for (String s : dateArray) {
                builder.append(s);
            }
            String str = builder.toString();
            return str;

        } catch (Exception e) {

        }

        return "";
    }

    public static String toDelayTime(long newest, long oldest) {

        StringBuilder builder = new StringBuilder();

        Map<TimeUnit, Long> timeMap = getDateAsMapWithTimeUnit(newest, oldest);

        if (timeMap.containsKey(TimeUnit.HOURS)) {

            long hour = timeMap.get(TimeUnit.HOURS);

            if (hour > 0) {
                builder.append(hour);
                builder.append(" hour");
                builder.append(hour > 1 ? "s" : "");
            }
        }

        if (timeMap.containsKey(TimeUnit.MINUTES)) {
            long minute = timeMap.get(TimeUnit.MINUTES);

            if (minute > 0) {
                builder.append(" " + minute);
                builder.append(" minute");
                builder.append(minute > 1 ? "s" : "");
            }
        }

        return builder.toString();
    }

    public static String getTimeAgo(long newest, long oldest) {

        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        // TODO: localize
        final long diff = newest - oldest;
        if (diff < MINUTE_MILLIS) {
            return "Just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "A minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "An hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static int getYearFromSecond(int inputSeconds) {

        long currentTime = currentTime() / 1000;
        String time = String.valueOf(currentTime);
        int expectedTime = inputSeconds - Integer.parseInt(time);
        int days = expectedTime / 86400;

        return days;
    }

    /**
     * @param newestDate time in milliseconds
     * @param oldestDate time in milliseconds
     * @return corresponding map value
     */
    public static Map<TimeUnit, Long> getDateAsMapWithTimeUnit(long newestDate, long oldestDate) {

        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long milliRest = newestDate - oldestDate;

        if (oldestDate > newestDate) {
            milliRest = oldestDate - newestDate;
        }

        for (TimeUnit unit : units) {

            long diff = unit.convert(milliRest, TimeUnit.MILLISECONDS);
            long diffInMilliForUnit = unit.toMillis(diff);
            milliRest = milliRest - diffInMilliForUnit;
            result.put(unit, diff);
        }

        return result;
    }

    /**
     * Converts minutes to millis
     *
     * @param minutes time in minutes
     * @return corresponding millis value
     */
    public static long minutesToMillis(long minutes) {
        return minutes * 60 * 1000;
    }

    /**
     * Converts seconds to millis
     *
     * @param seconds time in seconds
     * @return corresponding millis value
     */
    public static long secondsToMillis(long seconds) {
        return seconds * 1000;
    }

    /**
     * Converts millis to minutes
     *
     * @param millis time in millis
     * @return time in minutes
     */
    public static long millisToMinutes(long millis) {
        return Math.round(millis / 60.0 / 1000.0);
    }

    /**
     * Converts millis to seconds
     *
     * @param millis time in millis
     * @return time in seconds
     */
    public static long millisToSeconds(long millis) {
        return Math.round(millis / 1000.0);
    }

    /**
     * @param millis millis count
     * @return add millis with current time timestamp
     */
    public static long timeAfterMillis(long millis) {
        return currentTime() + millis;
    }

    /**
     * @param time  millis
     * @param delay millis
     * @return true/false
     */
    public static boolean isDelayed(long time, long delay) {
        return currentTime() - time >= delay;
    }

    /**
     * @param time millis count
     * @return Returns difference with current timestamp
     */
    public static long differ(long time) {
        return currentTime() - time;
    }

    /**
     * @param time millis count
     * @return Returns difference with current timestamp
     */
    public static long differ2(long time) {
        return time - currentTime();
    }
}