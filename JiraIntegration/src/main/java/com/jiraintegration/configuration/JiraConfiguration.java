package com.jiraintegration.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JiraConfiguration {

  private final String type;
  private final int maxResults;
  private final String location;
  private final int interval;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Jira Configuration:\n")
        .append("type: " + type + "\n")
        .append("maxResults: " + maxResults + "\n")
        .append("location: " + location + "\n")
        .append("interval: " + interval);

    return sb.toString();
  }
}
