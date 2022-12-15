package com.jiraintegration.utils.date;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public static ZonedDateTime getDateTimeFromString(String dateStr) {
    return ZonedDateTime.parse(dateStr, DEFAULT_FORMATTER);
  }

  public static String getStringFromDateTime(ZonedDateTime date) {
    return DEFAULT_FORMATTER.format(date);
  }
}
