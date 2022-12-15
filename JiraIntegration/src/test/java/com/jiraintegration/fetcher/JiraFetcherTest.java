package com.jiraintegration.fetcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jiraintegration.configuration.ConfigurationLoader;
import com.jiraintegration.configuration.JiraConfiguration;
import com.jiraintegration.exception.JiraException;
import com.jiraintegration.utils.http.JiraHttpClient;
import com.jiraintegration.utils.http.JiraHttpResponse;
import com.jiraintegration.utils.resource.ResourceUtils;

@ExtendWith(MockitoExtension.class)
public class JiraFetcherTest {

  private static final String responseBody1 = ResourceUtils.loadResource("response1.json");
  private static final String responseBody2 = ResourceUtils.loadResource("response2.json");
  private static final String responseBody3 = ResourceUtils.loadResource("response3.json");
  private static JiraConfiguration configuration;
  @Mock
  private JiraHttpClient httpClient;

  @BeforeAll
  static void loadConfig() throws JiraException {
    String type = "json";
    String maxResults = "5";
    String location = "..\\repository";
    String interval = "5";
    String[] args = {type, maxResults, location, interval};
    configuration = ConfigurationLoader.load(args);
  }

  @Test
  void jiraFetcherShouldBuildCorrectUrlAndIncreaseStartAtParameter() throws JiraException, IOException {
    int startAt = 0;
    int maxResults = configuration.getMaxResults();

    String mockSearchUrl1 = buildTestUrl(startAt, maxResults);
    when(httpClient.executeGetRequest(eq(mockSearchUrl1))).thenReturn(new JiraHttpResponse(200, responseBody1));

    String mockSearchUrl2 = buildTestUrl(startAt + maxResults, maxResults);
    when(httpClient.executeGetRequest(eq(mockSearchUrl2))).thenReturn(new JiraHttpResponse(200, responseBody2));

    String mockSearchUrl3 = buildTestUrl(startAt + 2 * maxResults, maxResults);
    when(httpClient.executeGetRequest(eq(mockSearchUrl3))).thenReturn(new JiraHttpResponse(200, responseBody3));

    JiraFetcher fetcher = new JiraFetcher(httpClient, maxResults, startAt);

    String payload1 = fetcher.fetch();
    String payload2 = fetcher.fetch();
    String payload3 = fetcher.fetch();

    // Payloads will be returned by the httpClient mock only if the URLs are build correctly in the fetcher and are
    // matching the mock search URLs
    assertEquals(responseBody1, payload1);
    assertEquals(responseBody2, payload2);
    assertEquals(responseBody3, payload3);
    assertEquals(3 * maxResults, fetcher.getStartAt());
  }

  private String buildTestUrl(int startAt, int maxResults) {
    String searchQuery = URLEncoder.encode(JiraFetcher.SEARCH_QUERY, StandardCharsets.UTF_8);
    return String.format(JiraFetcher.SEARCH_URL, JiraFetcher.BASE_URL, searchQuery, maxResults, startAt);
  }
}
