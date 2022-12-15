package com.jiraintegration.utils.json;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.jiraintegration.utils.date.DateUtils;

public class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

  @Override
  public JsonElement serialize(ZonedDateTime zonedDateTime, Type type,
      JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(DateUtils.DEFAULT_FORMATTER.format(zonedDateTime));
  }
}
