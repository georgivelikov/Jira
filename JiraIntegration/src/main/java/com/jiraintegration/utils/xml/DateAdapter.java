package com.jiraintegration.utils.xml;

import java.time.ZonedDateTime;

import com.jiraintegration.utils.date.DateUtils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, ZonedDateTime> {

  @Override
  public String marshal(ZonedDateTime date) {
    return DateUtils.getStringFromDateTime(date);
  }

  @Override
  public ZonedDateTime unmarshal(String dateStr) {
    return DateUtils.getDateTimeFromString(dateStr);
  }

}
