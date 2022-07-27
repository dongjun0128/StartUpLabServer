package com.startuplab.common.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtilOld {
  private SimpleDateFormat yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
  private SimpleDateFormat yyyy_mm = new SimpleDateFormat("yyyy-MM");
  // yyyyMMddHHmmss
  // calendar1.compareTo(calendar2);
  //
  // calendar1 > calendar2 : 1, 크다
  // calendar1 == calendar2 : 0, 같다
  // calendar1 < calendar2 : -1, 작다
  /*
   * G 기원 (텍스트) AD y 년 (수치) 1996 M 월 (텍스트와 수치) July & 07 d 일 (수치) 10 h 오전/오후때 (1 ∼
   * 12) (수치) 12 H 하루에 있어서의 때 (0 ∼ 23) (수치) 0 m 분 (수치) 30 s 초 (수치) 55 S 밀리 세컨드
   * (수치) 978 E 요일 (텍스트) Tuesday D 해에 있어서의 날 (수치) 189 F 달에 있어서의 요일 (수치) 2 (7 월의 제
   * 2 수요일) w 해에 있어서의 주 (수치) 27 W 달에 있어서의 주 (수치) 2 a 오전/오후 (텍스트) PM k 하루에 있어서의 때
   * (1 ∼ 24) (수치) 24 K 오전/오후때 (0 ∼ 11) (수치) 0 z 타임 존 (텍스트) Pacific Standard Time
   */
  private List<String> holidays;

  public Date getTomorrow() {
    return addDay(new Date(), 1);
  }

  public String getTomorrowStr() {
    Date d = getTomorrow();
    return toStr(d, "yyyy-MM-dd");
  }

  public String getTodayString() {
    Date date = new Date();
    return toStr(date, "yyyyMMdd");
  }

  public String getTodayStringKr() {
    Date date = new Date();
    return toStr(date, "yyyy-MM-dd");
  }

  public String get10YearLaterStringKr() {
    Date date = new Date();
    return toStr(addYear(date, 10), "yyyy-MM-dd");
  }

  public boolean isHoliday(Date date) {
    String dayString = toStr(date, "yyyyMMdd");
    if (holidays.contains(dayString))
      return true;
    return false;
  }

  public boolean isTodayHoliday() {
    return isHoliday(new Date());
  }

  public boolean isHolidayOrWeekend(Date date) {
    if (isWeekend(date))
      return true;
    if (isHoliday(date))
      return true;
    return false;
  }

  public boolean isTodayHolidayOrWeekend() {
    return isHolidayOrWeekend(new Date());
  }

  public boolean isWeekend(Date date) {
    // 1:일~7:토
    // String wds[] = { "일", "월", "화", "수", "목", "금", "토" };
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    Integer wd = cal.get(Calendar.DAY_OF_WEEK);
    if (wd != null && (wd.equals(1) || wd.equals(7))) {
      return true;
    }
    return false;
  }

  public boolean isBetweenTime(String time1, String time2) {
    boolean result = false;
    try {
      Date now = new Date();
      String toDay = toStr(now, "yyyy-MM-dd");
      Date before = toDateFromParserDate(toDay + " " + time1);
      Date after = toDateFromParserDate(toDay + " " + time2);
      if ((now.compareTo(before) >= 0) && now.compareTo(after) == -1) {
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public boolean isBetweenTimeForEvent(String time1, String time2) {
    boolean result = false;
    try {
      Date now = new Date();
      String toDay = toStr(now, "yyyyMMdd");
      Date before = toDate(toDay + time1);
      Date after = toDate(toDay + time2);
      if ((now.compareTo(before) >= 0) && now.compareTo(after) == -1) {
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public boolean isBetweenDate(Date before, Date after, Date now) {
    boolean result = false;
    try {
      if (before == null || after == null || now == null)
        return result;
      if ((now.compareTo(before) >= 0) && now.compareTo(after) <= 0) {
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public boolean isBetweenDate(Date before, Date after) {
    return isBetweenDate(before, after, new Date());
  }

  public boolean isSameDay(Date date1, Date date2) {
    boolean result = false;
    String date1Str = toStr(date1, "yyyyMMdd");
    String date2Str = toStr(date2, "yyyyMMdd");
    if (date2Str.equals(date1Str))
      result = true;
    return result;
  }

  public boolean isToday(Date date1) {
    return isSameDay(date1, new Date());
  }

  public String getSurveyKey() {
    Date date = new Date();
    return toStr(date, "yyyyMMdd");
  }

  public String getDisplayDate(LocalDateTime date) {
    return toStr(toDateFromLocalDateTime(date), "yyyy.MM.dd.");
  }

  public String getDisplayDate(Date date) {
    return toStr(date, "yyyy.MM.dd.");
  }

  public String getPrintFormat(LocalDateTime date) {
    return getPrintFormat(toDateFromLocalDateTime(date));
  }

  public String getPrintFormat(Date date) {
    return toStr(date, "yyyy.MM.dd. HH:mm");
  }

  public String getDisplayTime(Date date) {
    // return toStr(date,"a h시 m분");
    return toStr(date, "HH:mm");
  }

  public String getBatchtimeStr(String timeStr) {
    String returnstr = "";
    Date date = toDate(timeStr, "HHmm");
    // if (timeStr.substring(2,4).equals("00")){
    // returnstr = toStr(date,"a h시");
    // }else {
    // returnstr = toStr(date,"a h시 m분");
    // }
    returnstr = toStr(date, "HH:mm");
    return returnstr;
  }

  public String getScheduleTimeStr(String dateStr) {
    Date date = toDate(dateStr);
    // return toStr(date,"a h시 m분");
    return toStr(date, "HH:mm");
  }

  public String getOnlyTime(String dateStr) {
    Date date = toDate(dateStr);
    return toStr(date, "HH시");
  }

  public String getOnlyTimeAMPM(String dateStr) {
    Date date = toDate(dateStr);
    return toStr(date, "a hh시");
  }

  public String getScheduleDayStr(String dateStr) {
    Date date = toDate(dateStr);
    String week = getWDN(date);
    String day = toStr(date, "M월 d일");
    return day + "(" + week + ")";
  }

  public String getPickupDateStr(Date date) {
    String day = toStr(date, "MM.dd. HH:mm");
    return "(" + day + ")";
  }

  public String getPickupDateStrForBaseBall(Date date) {
    String day = toStr(date, "HH:mm");
    return "(" + day + ")";
  }

  public String getScheduleDateStr(String dateStr) {
    Date date = toDate(dateStr);
    String week = getWDN(date);
    String day = toStr(date, "dd일");
    return day + "(" + week + ")";
  }

  public Integer getDayOfWeekNo(Date date) {
    // 0 "일", 1 "월", 2 "화", 3 "수", 4 "목", 5 "금", 6 "토"
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int wd = cal.get(Calendar.DAY_OF_WEEK);
    return wd - 1;
  }

  public String getWDN(Date date) {
    String wds[] = {"일", "월", "화", "수", "목", "금", "토"};
    Integer wd = getDayOfWeekNo(date);
    return wds[wd];
  }

  public Integer getTodayWeekNo() {
    return getDayOfWeekNo(new Date());
  }

  public String toStrAsapDeliveryTime(Date date) {
    String dateFormat = "mm";
    Integer mm = CUtil.objectToInteger(toStr(date, dateFormat));
    dateFormat = "yyyy년 M월 d일 HH시";
    if (!mm.equals(0))
      dateFormat = "yyyy년 M월 d일 HH시 mm분";
    return toStr(date, dateFormat);
  }

  public String toStrForHuman(Date date) {
    String dateFormat = "yyyy-MM-dd HH:mm";
    return toStr(date, dateFormat);
  }

  public String toStrParserFormat(Date date) {
    String dateFormat = "yyyy-MM-dd HH:mm";
    return toStr(date, dateFormat);
  }

  public String toStrForCheckNewStart(Date date) {
    String str = toStr(date, "M월 d일에");
    Date now = new Date();
    int min = diffMin(now, date);
    if (min < 60) {
      str = "조금 전에";
    } else if (min < 360) {
      str = "아까";
    } else {
      if (isSameDay(now, date)) {
        str = "오늘";
      } else if (isSameDay(addDay(now, -1), date)) {
        str = "어제";
      }
    }
    return str;
  }

  public String toStr(Date date) {
    String dateFormat = "yyyyMMddHHmmss";
    return toStr(date, dateFormat);
  }

  public Date truncateDate(Date date) {
    String str = toStr(date, "yyyyMMdd");
    return toDate(str);
  }

  public String toStr(Date date, String dateFormat) {
    SimpleDateFormat df = new SimpleDateFormat(dateFormat);
    return df.format(date);
  }

  public Date toDateFromParserDate(String date) {
    if (date == null || date.equals(""))
      return null;
    int len = date.length();
    String dateFormat = "";
    switch (len) {
      case 19:
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        break;
      case 16:
        dateFormat = "yyyy-MM-dd HH:mm";
        break;
      case 13:
        dateFormat = "yyyy-MM-dd HH";
        break;
      case 10:
        dateFormat = "yyyy-MM-dd";
        break;
    }
    return toDate(date, dateFormat);
  }

  public String toStringFromParserDate(String date) {
    String newDateFormat = "yyyyMMddHHmmss";
    Date newDate = toDateFromParserDate(date);
    return toStr(newDate, newDateFormat);
  }

  public Date toDateFromLocalDateTime(LocalDateTime date) {
    return java.sql.Timestamp.valueOf(date);
  }

  public Date toDate(String date) {
    int len = date.length();
    String dateFormat = "";
    switch (len) {
      case 14:
        dateFormat = "yyyyMMddHHmmss";
        break;
      case 12:
        dateFormat = "yyyyMMddHHmm";
        break;
      case 10:
        dateFormat = "yyyyMMddHH";
        break;
      case 8:
        dateFormat = "yyyyMMdd";
        break;
    }
    Date newDate = toDate(date, dateFormat);
    return newDate;
  }

  public Date toDate(String date, String dateFormat) {
    try {
      SimpleDateFormat df = new SimpleDateFormat(dateFormat);
      Date newDate = (Date) df.parse(date);
      return newDate;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Date addYear(Date date, int year) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.YEAR, year);
    return cal.getTime();
  }

  public Date addMonth(Date date, int months) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MONTH, months);
    return cal.getTime();
  }

  public Date addDay(Date date, int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DAY_OF_MONTH, day);
    return cal.getTime();
  }

  public Date addHour(Date date, int param) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.HOUR, param);
    return cal.getTime();
  }

  public Date addMin(Date date, int param) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MINUTE, param);
    return cal.getTime();
  }

  public Date addSec(Date date, int sec) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.SECOND, sec);
    return cal.getTime();
  }

  public Date firstDayofMonth(Date date) throws Exception {
    String str = yyyy_mm.format(date) + "-1";
    return yyyy_mm_dd.parse(str);
  }

  public Date lastDayofMonth(Date date) throws Exception {
    Date firstDay = firstDayofMonth(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(firstDay);
    cal.add(Calendar.MONTH, 1);
    cal.add(Calendar.DAY_OF_MONTH, -1);
    return cal.getTime();
  }

  public int diffSec(Date date1, Date date2) {
    Long term0 = (date1.getTime() - date2.getTime()) / 1000;
    int term = new BigDecimal(term0).intValueExact();
    return term;
  }

  public int diffMin(Date date1, Date date2) {
    Long term0 = (date1.getTime() - date2.getTime()) / (60 * 1000);
    int term = new BigDecimal(term0).intValueExact();
    return term;
  }

  public int diffHour(Date date1, Date date2) {
    Long term0 = (date1.getTime() - date2.getTime()) / (60 * 60 * 1000);
    int term = new BigDecimal(term0).intValueExact();
    return term;
  }

  public int diffDay(Date date1, Date date2) {
    Long term0 = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
    int term = new BigDecimal(term0).intValueExact();
    return term;
  }

  public int diffMonth(Date date1, Date date2) {
    Long term0 = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
    int term = new BigDecimal(term0).intValueExact();
    return term;
  }

  public int getMonthThDay(int year, int month, int dayofweek, int weeks) {
    month = month - 1;
    Calendar cacheCalendar = Calendar.getInstance();
    cacheCalendar.set(Calendar.DAY_OF_WEEK, dayofweek);
    cacheCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, weeks);
    cacheCalendar.set(Calendar.MONTH, month);
    cacheCalendar.set(Calendar.YEAR, year);
    return cacheCalendar.get(Calendar.DATE);
  }

  public Date getMonthThDate(int year, int month, int dayofweek, int weeks) {
    int day = getMonthThDay(year, month, dayofweek, weeks);
    month = month - 1;
    Calendar cacheCalendar = Calendar.getInstance();
    cacheCalendar.set(Calendar.MONTH, month);
    cacheCalendar.set(Calendar.YEAR, year);
    cacheCalendar.set(Calendar.DATE, day);
    return cacheCalendar.getTime();
  }

  public Date getMonthThDate(Date date, int dayofweek, int weeks) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int yyyy = cal.get(Calendar.YEAR);
    int mm = cal.get(Calendar.MONTH) + 1;
    return getMonthThDate(yyyy, mm, dayofweek, weeks);
  }

  public Date toStrMinCeil(Date date) {
    Date result = date;
    try {
      String dateFormat = "mm";
      String returnDateFormat = "yyyyMMddHH";
      String yyyyMMddHHmm = "";
      Integer mm = CUtil.objectToInteger(toStr(date, dateFormat));
      if (0 == mm) {
        yyyyMMddHHmm = toStr(date, returnDateFormat) + "00";
      } else if (0 < mm && mm <= 30) {
        yyyyMMddHHmm = toStr(date, returnDateFormat) + "30";
      } else {
        date = addHour(date, 1);
        yyyyMMddHHmm = toStr(date, returnDateFormat) + "00";
      }
      return toDate(yyyyMMddHHmm);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
