package com.sunc.cwy.util;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期工具类
 */
public class DateUtil {

    public static final String DATAFORMAT_STR = "yyyy-MM-dd";
    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";
    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";
    public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";
    private static DateFormat dateFormat = null;
    private static DateFormat dateTimeFormat = null;
    private static DateFormat zhcnDateFormat = null;
    private static DateFormat zhcnDateTimeFormat = null;
    private static String hmsRegex = "([\\s\\S]*)(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?([\\s\\S]*)";

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        zhcnDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        zhcnDateTimeFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    }

    public DateUtil() {
    }

    private static DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase("yyyy-MM-dd")) {
            return dateFormat;
        } else if (formatStr.equalsIgnoreCase("yyyy-MM-dd HH:mm:ss")) {
            return dateTimeFormat;
        } else if (formatStr.equalsIgnoreCase("yyyy年MM月dd日")) {
            return zhcnDateFormat;
        } else {
            return (DateFormat) (formatStr.equalsIgnoreCase("yyyy年MM月dd日HH时mm分ss秒") ? zhcnDateTimeFormat : new SimpleDateFormat(formatStr));
        }
    }

    public static Date getDateByMS(long ms) {
        Date d = new Date(ms);
        return d;
    }

    public static String getStringByMS(long ms) {
        Date d = new Date(ms);
        return dateToDateString(d);
    }

    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(5, now.get(5) - day);
        return now.getTime();
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(5, now.get(5) + day);
        return now.getTime();
    }

    public static Date getDate(String dateTimeStr) {
        if (dateTimeStr != null && dateTimeStr.length() != 0) {
            Pattern p = Pattern.compile(hmsRegex);
            Matcher m = p.matcher(dateTimeStr);
            boolean b = m.matches();
            return b ? getDate(dateTimeStr, "yyyy-MM-dd HH:mm:ss") : getDate(dateTimeStr, "yyyy-MM-dd");
        } else {
            return null;
        }
    }

    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr != null && !dateTimeStr.equals("")) {
                DateFormat sdf = getDateFormat(formatStr);
                Date d = sdf.parse(dateTimeStr);
                return d;
            } else {
                return null;
            }
        } catch (ParseException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static String dateToDateString(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(11) == 0 && c.get(12) == 0 && c.get(13) == 0 ? dateToDateString(date, "yyyy-MM-dd") : dateToDateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    public static String getTimeString(String dateTime) {
        return getTimeString(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getTimeString(String dateTime, String formatStr) {
        Date d = getDate(dateTime, formatStr);
        String s = dateToDateString(d);
        return s.substring("yyyy-MM-dd HH:mm:ss".indexOf(72));
    }

    public static Date transferDate(String date) throws Exception {
        if (date != null && date.length() >= 1) {
            if (date.length() != 8) {
                throw new Exception("日期格式错误");
            } else {
                String con = "-";
                String yyyy = date.substring(0, 4);
                String mm = date.substring(4, 6);
                String dd = date.substring(6, 8);
                int month = Integer.parseInt(mm);
                int day = Integer.parseInt(dd);
                if (month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                    String str = yyyy + con + mm + con + dd;
                    return getDate(str, "yyyy-MM-dd");
                } else {
                    throw new Exception("日期格式错误");
                }
            }
        } else {
            return null;
        }
    }

    public static String getYYYYMMDDDate(Date date) {
        if (date == null) {
            return null;
        } else {
            String yyyy = String.valueOf(getYear(date));
            String mm = String.valueOf(getMonth(date));
            String dd = String.valueOf(getDay(date));
            mm = StringUtil.rightAlign(mm, 2, "0");
            dd = StringUtil.rightAlign(dd, 2, "0");
            return yyyy + mm + dd;
        }
    }

    public static String getHHMMSSDate(Date date) {
        if (date == null) {
            return null;
        } else {
            String hh = String.valueOf(getHour(date));
            String min = String.valueOf(getMin(date));
            String ss = String.valueOf(getSecond(date));
            hh = StringUtil.rightAlign(hh, 2, "0");
            min = StringUtil.rightAlign(min, 2, "0");
            ss = StringUtil.rightAlign(ss, 2, "0");
            return hh + ":" + min + ":" + ss;
        }
    }

    public static String getYYYYMMDDHHMMSSDate(Date date) {
        if (date == null) {
            return null;
        } else {
            String yyyy = String.valueOf(getYear(date));
            String mm = String.valueOf(getMonth(date));
            String dd = String.valueOf(getDay(date));
            String hh = String.valueOf(getHour(date));
            String min = String.valueOf(getMin(date));
            String ss = String.valueOf(getSecond(date));
            mm = StringUtil.rightAlign(mm, 2, "0");
            dd = StringUtil.rightAlign(dd, 2, "0");
            hh = StringUtil.rightAlign(hh, 2, "0");
            min = StringUtil.rightAlign(min, 2, "0");
            ss = StringUtil.rightAlign(ss, 2, "0");
            return yyyy + mm + dd + hh + min + ss;
        }
    }

    public static String getYYYYMMDDH24MISSDate(Date date) {
        return dateToDateString(date, "yyyyMMdd HH:mm:ss");
    }

    public static String getYYYYMMDDH24MISSDateByYYYY_MM_ddH24MISS(String date) {
        return StringUtil.isEmptyString(date) ? date : getYYYYMMDDH24MISSDate(getDate(date, "yyyy-MM-dd HH:mm:ss"));
    }

    public static String getYYYY_MM_ddH24MISSDateByYYYYMMDDH24MISS(String date) {
        if (StringUtil.isEmptyString(date)) {
            return date;
        } else {
            Date d = getDate(date, "yyyyMMdd HH:mm:ss");
            return dateToDateString(d, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static String getYYYYMMDDH24MISSDate(String date) {
        return StringUtil.isEmptyString(date) ? date : getYYYYMMDDH24MISSDate(getDate(date, "yyyy-MM-dd"));
    }

    public static String getYYYYMMDDH24MISSDateByyyyy_MM_ddHHmmss(String date) {
        return StringUtil.isEmptyString(date) ? date : getYYYYMMDDH24MISSDate(getDate(date, "yyyy-MM-dd HH:mm:ss"));
    }

    public static String getYYYYMMDDH24MISSDateByyyyyMMddHHmmss(String date) {
        if (StringUtil.isEmptyString(date)) {
            return date;
        } else {
            Date d = getDate(date, "yyyyMMddHHmmss");
            return getYYYYMMDDH24MISSDate(d);
        }
    }

    public static String getYYYYMMDDHHMMSSDate(String date) {
        return getYYYYMMDDHHMMSSDate(getDate(date, "yyyy-MM-dd"));
    }

    public static String getYYYYMMDDHHMMSSDateByYYYYMMDDH24MISS(String date) {
        if (StringUtil.isEmptyString(date)) {
            return date;
        } else {
            Date d = getDate(date, "yyyyMMdd HH:mm:ss");
            return getYYYYMMDDHHMMSSDate(d);
        }
    }

    public static String getYYYY_MM_DDDateByYYYYMMDDH24MISS(String date) {
        if (StringUtil.isEmptyString(date)) {
            return date;
        } else {
            Date d = getDate(date, "yyyyMMdd HH:mm:ss");
            return dateToDateString(d, "yyyy-MM-dd");
        }
    }

    public static String getYYYYMMDDDate(String date) {
        return StringUtil.isEmptyString(date) ? date : getYYYYMMDDDate(getDate(date, "yyyy-MM-dd"));
    }

    public static String getCurDate() {
        return dateToDateString(Calendar.getInstance().getTime(), "yyyy-MM-dd");
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getCurZhCNDate() {
        return dateToDateString(new Date(), "yyyy年MM月dd日");
    }

    public static String getCurDateTime() {
        return dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), "yyyy年MM月dd日HH时mm分ss秒");
    }

    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(5, days);
        return now.getTime();
    }

    public static Date getInternalDateByMon(Date d, int months) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(2, months);
        return now.getTime();
    }

    public static Date getInternalDateByYear(Date d, int years) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(1, years);
        return now.getTime();
    }

    public static Date getInternalDateBySec(Date d, int sec) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(13, sec);
        return now.getTime();
    }

    public static Date getInternalDateByMin(Date d, int min) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(12, min);
        return now.getTime();
    }

    public static Date getInternalDateByHour(Date d, int hours) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(11, hours);
        return now.getTime();
    }

    public static String getFormateStr(String DateString) {
        String patternStr1 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
        String patternStr2 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
        String patternStr3 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日";
        String patternStr4 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日[0-9]{1,2}时[0-9]{1,2}分[0-9]{1,2}秒";
        Pattern p = Pattern.compile(patternStr1);
        Matcher m = p.matcher(DateString);
        boolean b = m.matches();
        if (b) {
            return "yyyy-MM-dd";
        } else {
            p = Pattern.compile(patternStr2);
            m = p.matcher(DateString);
            b = m.matches();
            if (b) {
                return "yyyy-MM-dd HH:mm:ss";
            } else {
                p = Pattern.compile(patternStr3);
                m = p.matcher(DateString);
                b = m.matches();
                if (b) {
                    return "yyyy年MM月dd日";
                } else {
                    p = Pattern.compile(patternStr4);
                    m = p.matcher(DateString);
                    b = m.matches();
                    return b ? "yyyy年MM月dd日HH时mm分ss秒" : null;
                }
            }
        }
    }

    public static String getZhCNDateTime(String dateStr) {
        Date d = getDate(dateStr);
        return dateToDateString(d, "yyyy年MM月dd日HH时mm分ss秒");
    }

    public static String getZhCNDate(String dateStr) {
        Date d = getDate(dateStr, "yyyy-MM-dd");
        return dateToDateString(d, "yyyy年MM月dd日");
    }

    public static String getDateStr(String dateStr, String fmtFrom, String fmtTo) {
        Date d = getDate(dateStr, fmtFrom);
        return dateToDateString(d, fmtTo);
    }

    public static long compareDateStr(String time1, String time2) {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    public static long compareDateStr(Date time1, Date time2) {
        return time2.getTime() - time1.getTime();
    }

    public static int getDaysBetween(Date date1, Date date2) {
        long l = compareDateStr(date1, date2);
        int days = (int) (l / 86400000L);
        return days;
    }

    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd = hours.multiply(new BigDecimal(3600000));
        return bd.longValue();
    }

    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(12);
    }

    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(11);
    }

    public static int getWeek(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(7);
    }

    public static int getQuarter(Date d) {
        int month = getMonth(d);
        switch (month) {
            case 1:
            case 2:
            case 3:
                return 1;
            case 4:
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
            case 9:
                return 3;
            case 10:
            case 11:
            case 12:
                return 4;
            default:
                return 1;
        }
    }

    public static int getSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(13);
    }

    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(5);
    }

    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(2) + 1;
    }

    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(1);
    }

    public static String getYearMonthOfLastMon(Date d) {
        Date newdate = getInternalDateByMon(d, -1);
        String year = String.valueOf(getYear(newdate));
        String month = String.valueOf(getMonth(newdate));
        return year + month;
    }

    public static String getCurYearMonth() {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyyMM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(now.getTime());
    }

    public static Date getNextMonth(String year, String month) {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, "yyyy-MM-dd");
        return getInternalDateByMon(date, 1);
    }

    public static Date getLastMonth(String year, String month) {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, "yyyy-MM-dd");
        return getInternalDateByMon(date, -1);
    }

    public static String getSingleNumDate(Date d) {
        return dateToDateString(d, "yyyy-MM-dd");
    }

    public static String getHalfYearBeforeStr(Date d) {
        return dateToDateString(getInternalDateByMon(d, -6), "yyyy-MM-dd");
    }

    public static String getInternalDateByLastDay(Date d, int days) {
        return dateToDateString(getInternalDateByDay(d, days), "yyyy-MM-dd");
    }

    public static String addDate(int field, int amount) {
        int temp = 0;
        if (field == 1) {
            temp = 1;
        }

        if (field == 2) {
            temp = 2;
        }

        if (field == 3) {
            temp = 5;
        }

        String Time = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            cal.add(temp, amount);
            Time = sdf.format(cal.getTime());
            return Time;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static int getCurentMonthDay() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(5);
    }

    public static int getMonthDay(String date) {
        Date strDate = getDate(date, "yyyy-MM-dd");
        return getMonthDay(strDate);
    }

    public static String getStringDate(Calendar cal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    public static String getStringDateTime(Calendar cal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(cal.getTime());
    }

    public static String getCurRQ() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    public static String getCurSJ() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public static String getCurDateMonth() {
        return dateToDateString(Calendar.getInstance().getTime(), "yyyy-MM");
    }

    public static String getCurYearStart() {
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        return year + "-01-01";
    }

    public static String getCurMonthStart() {
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        int month = c.get(2) + 1;
        return year + "-" + month + "-01";
    }

    public static long getApartDays(Date date, Date date2) {
        long quot = date.getTime() - date2.getTime();
        quot = quot / 1000L / 60L / 60L / 24L;
        return quot;
    }

    public static long getApartHours(Date date, Date date2) {
        long quot = date.getTime() - date2.getTime();
        quot = quot / 1000L / 60L / 60L;
        return quot;
    }

    public static Date getOtherDate(Date date, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }

        cal.add(1, year);
        cal.add(2, month);
        cal.add(5, day);
        return cal.getTime();
    }

    public static String getWeekDayZh(Calendar cal) {
        String[] days = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        if (cal == null) {
            cal = Calendar.getInstance();
        }

        return days[cal.get(7) - 1];
    }

    /*public static void main(String[] args) {
        System.out.println(getFormateStr("2012-5-14 10:20:15 000") + "1");
        Calendar c = Calendar.getInstance();
        int year = c.get(1);
        int month = c.get(2) + 1;
        System.out.println(year + "-" + month + "-01");
        System.out.println(dateToDateString(getLastMonth("2011", "9")));
        System.out.println(getMonth(new Date()));
    }*/
}