package com.startuplab.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
  private final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
  private final String yyyyMMddHHmm = "yyyyMMddHHmm";
  private final String yyyyMMddHH = "yyyyMMddHH";
  private final String yyyyMMdd = "yyyyMMdd";
  // private final String yyyy_MM_dd = "yyyy-MM-dd";
  // private final String yyyy_MM = "yyyy-MM";
  // private final String yyyymmdd = "yyyyMMdd";
  private final String date_kr = "yyyy년 M월 dd일";
  private final String date_kr_without_year = "M월 dd일";
  /*
  yyyyMMddHHmmss
  yyyy : 년도
  MM : 월
  dd : 일
  hh : 시간
  mm : 분
  ss : 초
  SSS 밀리초
  
  calendar1.compareTo(calendar2);
  calendar1 > calendar2 : 1, 크다
  calendar1 == calendar2 : 0, 같다
  calendar1 < calendar2 : -1, 작다
  */

  public TimeUtil() {
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public LocalDateTime toLocalDateTime(String string, String dateFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return LocalDateTime.parse(string, formatter);
  }

  public String toString(LocalDateTime datetime, String dateFormat) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return datetime.format(formatter);
  }

  public String toString(LocalDateTime date) {
    return toString(date, "yyyyMMddHHmmss");
  }

  public LocalDateTime toLocalDateTime(String datetime) {
    int len = datetime.length();
    String dateFormat = "";
    switch (len) {
      case 14:
        dateFormat = this.yyyyMMddHHmmss;
        break;
      case 12:
        dateFormat = this.yyyyMMddHHmm;
        break;
      case 10:
        dateFormat = this.yyyyMMddHH;
        break;
      case 8:
        dateFormat = this.yyyyMMdd;
        break;
    }
    return toLocalDateTime(datetime, dateFormat);
  }

  public Long diffSec(LocalDateTime before, LocalDateTime after) {
    // before < after = +, before > after = -
    return ChronoUnit.SECONDS.between(before, after);
  }

  public Long diffMin(LocalDateTime before, LocalDateTime after) {
    // before < after = +, before > after = -
    return ChronoUnit.MINUTES.between(before, after);
  }

  public Long diffHour(LocalDateTime before, LocalDateTime after) {
    // before < after = +, before > after = -
    return ChronoUnit.HOURS.between(before, after);
  }

  public Long diffDay(LocalDateTime before, LocalDateTime after) {
    // before < after = +, before > after = -
    return ChronoUnit.DAYS.between(before, after);
  }

  public Long diffMon(LocalDateTime before, LocalDateTime after) {
    // before < after = +, before > after = -
    return ChronoUnit.MONTHS.between(before, after);
  }

  public Long diffYear(LocalDateTime before, LocalDateTime after) {
    // before < after = +, before > after = -
    return ChronoUnit.YEARS.between(before, after);
  }

  public LocalDateTime dateTodatetime(LocalDate date) {
    return date.atTime(0, 0);
  }

  public LocalDateTime addDay(LocalDateTime datetime, int day) {
    return LocalDateTime.from(datetime.plusDays(day));
  }

  public LocalDateTime addHour(LocalDateTime datetime, int hour) {
    return LocalDateTime.from(datetime.plusHours(hour));
  }

  public LocalDateTime addMin(LocalDateTime datetime, int min) {
    return LocalDateTime.from(datetime.plusMinutes(min));
  }

  public LocalDateTime addSec(LocalDateTime datetime, int sec) {
    return LocalDateTime.from(datetime.plusSeconds(sec));
  }

  public LocalDateTime firstDayofMonth(LocalDateTime datetime) {
    LocalDateTime firstDay = LocalDateTime.of(datetime.getYear(), datetime.getMonth(), 1, 0, 0);
    return firstDay;
  }

  public LocalDateTime lastDayofMonth(LocalDateTime datetime) {
    LocalDate firstDayofMonth = LocalDate.of(datetime.getYear(), datetime.getMonth(), 1);
    LocalDate lastday = firstDayofMonth.plusDays(firstDayofMonth.lengthOfMonth() - 1);
    return this.dateTodatetime(lastday);
  }

  public boolean isSameYear(LocalDateTime before, LocalDateTime after) {
    int beforeYear = before.getYear();
    int afterYear = after.getYear();
    return beforeYear == afterYear;
  }

  public String getBoardTime(LocalDateTime datetime) {
    if (datetime == null)
      return "";
    String result = toString(datetime, date_kr);
    LocalDateTime now = LocalDateTime.now();
    Long day = ChronoUnit.DAYS.between(datetime, now);
    // log.error("datetime:{}, now:{}", datetime, now);
    // log.error("day:{}", day);
    if (day.compareTo(7L) == -1) {
      if (day.compareTo(1L) == -1) {
        Long hour = ChronoUnit.HOURS.between(datetime, now);
        // log.error("hour:{}", hour);
        if (hour.compareTo(1L) == -1) {
          Long min = ChronoUnit.MINUTES.between(datetime, now);
          // log.error("min:{}", min);
          if (min.compareTo(1L) == -1) {
            Long sec = ChronoUnit.SECONDS.between(datetime, now);
            // log.error("sec:{}", sec);
            result = sec + "초 전";
          } else {
            result = min + "분 전";
          }
        } else {
          result = hour + "시간 전";
        }
      } else {
        result = day + "일 전";
      }
    } else {
      if (isSameYear(datetime, now))
        result = toString(datetime, date_kr_without_year);
    }
    return result;
  }
}
