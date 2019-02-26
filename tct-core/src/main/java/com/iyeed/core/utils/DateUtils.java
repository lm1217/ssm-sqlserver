package com.iyeed.core.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能描述:
 *
 * @Auther guanghua.deng
 * @Date 2018/8/20 15:17
 */
public class DateUtils {
    public static Date strToDate(String dateStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(dateStr, pos);
        return date;
    }

    public static String dateToStr(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String str = formatter.format(date);
        return str;
    }

    public static String initDateStrByMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

    public static void main(String[] args) {
        String dateStr = "2018-08-20 12:21:12";
        String format = "yyyy-MM-dd HH:mm:ss";
        System.out.println("args = [" + strToDate(dateStr, format) + "]");

        System.out.println("args = [" + dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "]");
    }
}
