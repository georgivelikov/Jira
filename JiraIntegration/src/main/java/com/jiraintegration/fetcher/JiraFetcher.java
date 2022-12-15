package com.jiraintegration.fetcher;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.Level;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.utils.http.JiraHttpClient;
import com.jiraintegration.utils.http.JiraHttpResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@AllArgsConstructor
public class JiraFetcher implements Fetcher {

  static final String BASE_URL = "https://jira.atlassian.com";
  static final String SEARCH_URL = "%s/rest/api/2/search?jql=%s&maxResults=%d&startAt=%d&fields=summary,"
      + "issuetype,priority,description,reporter,created,comment";
  static final String SEARCH_QUERY =
      "issuetype in (Bug, Documentation, Enhancement) and updated > " + "startOfWeek()";

  private final JiraHttpClient jiraHttpClient;
  private final int maxResults;
  private int startAt;

  @Override
  public String fetch() throws JiraException {
    try {
      String searchQuery = URLEncoder.encode(SEARCH_QUERY, StandardCharsets.UTF_8);
      String searchUrl = String.format(SEARCH_URL, BASE_URL, searchQuery, maxResults, startAt);
      JiraHttpResponse response = jiraHttpClient.executeGetRequest(searchUrl);
      startAt += maxResults;

      return response.getBody();
    } catch (IOException ex) {
      log.error(ex);
      throw new JiraException("Error while fetching data from Jira API");
    }
  }
}
