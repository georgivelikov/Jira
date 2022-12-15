package com.jiraintegration.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.utils.resource.ResourceUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConfigurationLoader {

  public static JiraConfiguration load(String[] args) throws JiraException {
    JiraConfiguration config;
    if (args.length == 0) {
      log.info("Loading default configuration from local.conf...");
      config = loadConfigurationFromLocalFile();
    } else {
      log.info("Loading configuration from command line arguments...");
      if (args.length != 4) {
        throw new JiraException("Missing argument in starting arguments");
      }

      String type = args[0];
      String maxResults = args[1];
      String location = args[2];
      String interval = args[3];

      config = loadConfiguration(type, maxResults, location, interval);
    }

    log.info(config);
    return config;
  }

  private static JiraConfiguration loadConfigurationFromLocalFile() throws JiraException {
    Properties props = new Properties();
    try (InputStream input = ResourceUtils.loadResourceAsStream("local.conf");) {
      props.load(input);
    } catch (IOException ex) {
      log.error(ex);
      throw new JiraException("Can't load JiraConfiguration from local.conf");
    }

    return loadConfiguration(props.getProperty("type"), props.getProperty("maxResults"), props.getProperty("location"),
        props.getProperty("interval"));
  }

  private static JiraConfiguration loadConfiguration(String type, String maxResults, String location, String interval)
      throws JiraException {
    String typeValue = getType(type);
    int maxResultsValue = getMaxResults(maxResults);
    String locationValue = getLocation(location);
    int intervalValue = getInterval(interval);

    return new JiraConfiguration(typeValue, maxResultsValue, locationValue, intervalValue);
  }

  private static String getType(String type) throws JiraException {
    if (type == null) {
      throw new JiraException("Missing 'type' config");
    }

    String typeValue = type.toLowerCase();
    if (!typeValue.equals("json") && !typeValue.equals("xml")) {
      throw new JiraException("Invalid 'type' config");
    }

    return typeValue;
  }

  private static int getMaxResults(String maxResults) throws JiraException {
    if (maxResults == null) {
      throw new JiraException("Missing 'maxResults' config");
    }

    int maxResultsValue;
    try {
      maxResultsValue = Integer.parseInt(maxResults);
    } catch (NumberFormatException ex) {
      log.error(ex);
      throw new JiraException("Invalid 'maxResults' config");
    }

    return maxResultsValue;
  }

  private static String getLocation(String location) throws JiraException {
    if (location == null) {
      throw new JiraException("Missing 'location' config");
    }

    return location;
  }

  private static int getInterval(String interval) throws JiraException {
    if (interval == null) {
      throw new JiraException("Missing 'interval' config");
    }

    int intervalValue;
    try {
      intervalValue = Integer.parseInt(interval);
    } catch (NumberFormatException ex) {
      log.error(ex);
      throw new JiraException("Invalid 'interval' config");
    }

    return intervalValue;
  }
}
