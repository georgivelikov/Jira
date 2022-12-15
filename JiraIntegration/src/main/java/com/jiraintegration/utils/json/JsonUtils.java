package com.jiraintegration.utils.json;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

  private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
      .setPrettyPrinting()
      .create();

  public static String toJson(Object source) {
    return GSON.toJson(source);
  }

  public static <T> T fromJson(String json, Type type) {
    return GSON.fromJson(json, type);
  }
}
