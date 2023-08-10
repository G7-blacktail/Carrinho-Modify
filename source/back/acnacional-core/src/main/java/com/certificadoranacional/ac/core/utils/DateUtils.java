package com.certificadoranacional.ac.core.utils;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class DateUtils {

  public static Date toDate(final LocalDate localDate) {
    if (localDate == null) {
      return null;
    }
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date toDate(final LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Time toTime(final LocalTime localTime) {
    if (localTime == null) {
      return null;
    }
    return Time.valueOf(localTime);
  }

  public static LocalDate toLocalDate(final Date date) {
    if (date == null) {
      return null;
    }
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime toLocalDateTime(final Date date) {
    if (date == null) {
      return null;
    }
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static LocalTime toLocalTime(final Time time) {
    if (time == null) {
      return null;
    }
    return time.toLocalTime();
  }

  public static LocalTime toLocalTime(final Date date) {
    if (date == null) {
      return null;
    }
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
  }
}
