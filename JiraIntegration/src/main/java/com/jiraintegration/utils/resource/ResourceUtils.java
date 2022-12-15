package com.jiraintegration.utils.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class ResourceUtils {

  public static String loadResource(String path) {
    try (InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(path)) {
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    } catch(IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  public static InputStream loadResourceAsStream(String path) {
    return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
  }
}
