package com.woniu.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {

    public static Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getNowString() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return result;
    }

    public static String getTimeStampString(String date) throws ParseException {
        Calendar c = stringToDateTime(date);

        return getTimeStampString(c.getTime());
    }

    public static String getTimeStampString(Date date) {
        try {
            Calendar today = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            int offset = calcDayOffset(today, c);

            if (offset == 0) {
                //当天
                return (new SimpleDateFormat("HH:mm")).format(c.getTime());
            } else if (offset == -1) {
                return (new SimpleDateFormat("昨天 HH:mm")).format(c.getTime());
            } else if (offset == 1) {
                return (new SimpleDateFormat("明天 HH:mm")).format(c.getTime());
            } else if (offset == 2) {
                return (new SimpleDateFormat("后天 HH:mm")).format(c.getTime());
            }

            //本周
//            if (c.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR)) {
//                int weekDay = c.get(Calendar.DAY_OF_WEEK);
//                switch (weekDay) {
//                    case Calendar.MONDAY:
//                        return (new SimpleDateFormat("周一 HH:mm")).format(c.getTime());
//                    case Calendar.TUESDAY:
//                        return (new SimpleDateFormat("周二 HH:mm")).format(c.getTime());
//                    case Calendar.WEDNESDAY:
//                        return (new SimpleDateFormat("周三 HH:mm")).format(c.getTime());
//                    case Calendar.THURSDAY:
//                        return (new SimpleDateFormat("周四 HH:mm")).format(c.getTime());
//                    case Calendar.FRIDAY:
//                        return (new SimpleDateFormat("周五 HH:mm")).format(c.getTime());
//                    case Calendar.SATURDAY:
//                        return (new SimpleDateFormat("周六 HH:mm")).format(c.getTime());
//                    case Calendar.SUNDAY:
//                        return (new SimpleDateFormat("周日 HH:mm")).format(c.getTime());
//                }
//            }

            //本年
            if (c.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR)) {
                return (new SimpleDateFormat("MM-dd HH:mm")).format(c.getTime());
            }

            return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(c.getTime());
        } catch (Exception e) {
            return "未知日期";
        }
    }

    public static int calcDayOffset(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static int calcDayOffset(Calendar cal1, Calendar cal2) {

        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                    timeDistance += 366;
                } else {  //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else { //不同年
            return day2 - day1;
        }
    }

    public static Calendar stringToDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(str));

            return cal;
        } catch (Exception err) {
            return null;
        }
    }

    public static Calendar stringToDate(String str, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(str));

        return cal;
    }


    public static Calendar stringToDateTime(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(str));

        return cal;
    }

    public static java.sql.Date stringToSqlDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(str));

            java.util.Date utilDate = cal.getTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;

        } catch (Exception err) {
            return null;
        }
    }

    public static Timestamp stringToTimestamp(String str) {

        return stringToTimestamp(str, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToTimestamp(String str, String format) {
        try {
            if ("yyyyMMddHHmmss".equals(format) && str != null && str.length() > 0) {
                str = str.replace("-", "").replace(":", "").replace(" ", "");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(str);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
        return result;
    }

    public static String timestampToString(Timestamp timestamp, String format) {
        String result = new SimpleDateFormat(format).format(timestamp);
        return result;
    }

    public static Calendar timestampToCalendar(Timestamp timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp.getTime());

        return c;
    }

    public static String timestampToSql(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }

        String timeString = timestampToString(timestamp);
        return " to_date('" + timeString + "','yyyy-mm-dd hh24:mi:ss') ";
    }

//    public static String nullToMaxSql(String fieldName) {
//        return "nvl(" + fieldName + ",to_date('9999-12-31 23:59:59','yyyy-mm-dd hh24:mi:ss'))";
//    }

    public static String dateStringNextDaySql(String str) {
        Timestamp endDate = getNextDayTimestamp(str, 1);
        return timestampToSql(endDate);
    }

    public static String dateStringToTimestamSql(String str) {
        Timestamp timestamp = stringToTimestamp(str, "yyyy-MM-dd");
        return timestampToSql(timestamp);
    }

    public static String dateToString(int year, int month, int dayOfMonth) {
        Calendar d = Calendar.getInstance();
        d.set(year, month, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(d.getTime());
    }

    public static String dateToString(Date date) {
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(d.getTime());
    }

    public static String dateToSql(Date date) {
        if (date == null) {
            return "";
        }

        String timeString = dateToString(date);
        return " to_date('" + timeString + "','yyyy-mm-dd') ";
    }


    public static List<Date> getDateRange(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }

    public static List<java.sql.Date> getDateRange(java.sql.Date dStart, java.sql.Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(new java.sql.Date(cStart.getTime().getTime()));
        }
        return dateList;
    }

    public static List<Date> getDateRange(Calendar cStart, Calendar dEnd) {
        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(cStart.getTime());
        // 此日期是否在指定日期之后
        while (dEnd.getTime().after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }

    /**
     * 获取指定日期之间的所有年月
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 获取指定日期之间的所有年月
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<Date> getMonthBetweenDateList(String minDate, String maxDate) throws ParseException {
        ArrayList<Date> result = new ArrayList<Date>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(curr.getTime());
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 今天
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(Calendar.getInstance().getTime());
    }

    public static Timestamp getTodayTimestamp() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return new Timestamp(c.getTimeInMillis());
    }

    public static Timestamp getTomorrowTimestamp() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        c.add(Calendar.DATE, 1);

        return new Timestamp(c.getTimeInMillis());
    }

    public static Timestamp getYesterdayTimestamp() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        c.add(Calendar.DATE, -1);

        return new Timestamp(c.getTimeInMillis());
    }

    public static Date getTodayDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static java.sql.Date getTodaySqlDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        String todayString = format.format(cal.getTime());

        cal.setTime(format.parse(todayString));
        java.util.Date utilDate = cal.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }

    public static String getYesterday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return format.format(cal.getTime());
    }

    public static String getNextDay(int next) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, next);
        return format.format(cal.getTime());
    }

    public static Calendar getNextDay(String dateString, int next) {
        Calendar c = stringToDate(dateString);
        c.add(Calendar.DATE, next);
        return c;
    }

    public static java.sql.Date getNextDaySqlDate(java.sql.Date date, int next) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, next);

        java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
        return sqlDate;
    }

    public static java.sql.Date getNextDaySqlDate(String dateString, int next) {
        Calendar c = getNextDay(dateString, 1);

        java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
        return sqlDate;
    }

    public static Timestamp getNextDayTimestamp(String dateString, int next) {
        Calendar c = getNextDay(dateString, next);

        Timestamp timestamp = new Timestamp(c.getTime().getTime());
        return timestamp;
    }

    public static java.sql.Date getNextDaySqlDate(int next) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, next);

        java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());
        return sqlDate;
    }

    /**
     * 获取指定日期所在周的周一
     *
     * @return Date
     * @Methods Name getMonday
     */
    public static Date getMonday(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_WEEK, 2);//老外将周日定位第一天，周一取第二天
        return cDay.getTime();
    }

    public static String getMonday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getMonday(Calendar.getInstance().getTime()));
    }

    /**
     * 获取指定日期所在周日
     *
     * @return Date
     * @Methods Name getSunday
     */
    public static Date getSunday(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        if (Calendar.SUNDAY == cDay.getFirstDayOfWeek()) { //如果刚好是周日，直接返回
            return date;
        } else {//如果不是周日，加一周计算
            cDay.add(Calendar.DAY_OF_YEAR, 7);
            cDay.set(Calendar.DAY_OF_WEEK, 1);
            return cDay.getTime();
        }
    }

    public static String getSunday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getSunday(Calendar.getInstance().getTime()));
    }

    /**
     * 得到本月第一天的日期
     *
     * @return Date
     * @Methods Name getFirstDayOfMonth
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        return cDay.getTime();
    }

    public static String getFirstDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getFirstDayOfMonth(Calendar.getInstance().getTime()));
    }

    /**
     * 得到本月最后一天的日期
     *
     * @return Date
     * @Methods Name getLastDayOfMonth
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cDay.getTime();
    }

    public static String getLastDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getLastDayOfMonth(Calendar.getInstance().getTime()));
    }

    /**
     * 得到本季度第一天的日期
     *
     * @return Date
     * @Methods Name getFirstDayOfQuarter
     */
    public static Date getFirstDayOfQuarter(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int curMonth = cDay.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
            cDay.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
            cDay.set(Calendar.MONTH, Calendar.APRIL);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
            cDay.set(Calendar.MONTH, Calendar.JULY);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            cDay.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cDay.getTime();
    }

    public static String getFirstDayOfQuarter() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getFirstDayOfQuarter(Calendar.getInstance().getTime()));
    }

    /**
     * 得到本季度最后一天的日期
     *
     * @return Date
     * @Methods Name getLastDayOfQuarter
     */
    public static Date getLastDayOfQuarter(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int curMonth = cDay.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
            cDay.set(Calendar.MONTH, Calendar.MARCH);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
            cDay.set(Calendar.MONTH, Calendar.JUNE);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
            cDay.set(Calendar.MONTH, Calendar.AUGUST);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            cDay.set(Calendar.MONTH, Calendar.DECEMBER);
        }
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cDay.getTime();
    }

    public static String getLastDayOfQuarter() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getLastDayOfQuarter(Calendar.getInstance().getTime()));
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getFirstDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static String getFirstDayOfYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getFirstDayOfYear(Calendar.getInstance().get(Calendar.YEAR)));
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getLastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    public static String getLastDayOfYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(getLastDayOfYear(Calendar.getInstance().get(Calendar.YEAR)));
    }

    /**
     * 获取当前年的前一年
     *
     * @return int
     */
    public static int getLastYear() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return currentYear - 1;
    }

    /**
     * 获取年月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Timestamp getFirstDayOfYm(Integer year, Integer month) {
        Timestamp beginDate = null;
        if (null != year && year.intValue() > 1900) {
            if (null != month && month.intValue() > 0) {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month - 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                beginDate = new Timestamp(c.getTimeInMillis());
            } else {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(Calendar.YEAR, year);
                beginDate = new Timestamp(c.getTimeInMillis());
            }
        }
        return beginDate;
    }

    /**
     * 获取下一年/下一月第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Timestamp getFirstDayOfNextYm(Integer year, Integer month) {
        Timestamp endDate = null;
        if (null != year && year.intValue() > 1900) {
            if (null != month && month.intValue() > 0) {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month - 1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.add(Calendar.MONTH, 1);
                endDate = new Timestamp(c.getTimeInMillis());
            } else {
                Calendar c = Calendar.getInstance();
                c.clear();
                c.set(Calendar.YEAR, year);
                c.add(Calendar.YEAR, 1);
                endDate = new Timestamp(c.getTimeInMillis());
            }
        }
        return endDate;
    }
}
