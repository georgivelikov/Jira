package com.jiraintegration.scraper.factory;

import com.jiraintegration.configuration.JiraConfiguration;
import com.jiraintegration.exception.JiraException;
import com.jiraintegration.fetcher.Fetcher;
import com.jiraintegration.fetcher.JiraFetcher;
import com.jiraintegration.parser.JiraParser;
import com.jiraintegration.parser.Parser;
import com.jiraintegration.persister.JiraPersister;
import com.jiraintegration.persister.JiraPersisterJson;
import com.jiraintegration.persister.JiraPersisterXml;
import com.jiraintegration.persister.Persister;
import com.jiraintegration.scraper.ExecutableScraper;
import com.jiraintegration.scraper.impl.JiraExecutableScraper;
import com.jiraintegration.scraper.Scraper;
import com.jiraintegration.utils.http.JiraHttpClient;
import com.jiraintegration.utils.http.JiraHttpClientApacheImpl;

public class ScraperFactory {

  public static Scraper getJiraScraper(JiraConfiguration configuration) throws JiraException {
    return getJiraExecutableScraper(configuration);
  }

  public static ExecutableScraper getJiraExecutableScraper(JiraConfiguration configuration) throws JiraException {
    JiraHttpClient httpClient = new JiraHttpClientApacheImpl();
    String persistLocation = configuration.getLocation();
    String scraperType = configuration.getType();
    Fetcher fetcher = new JiraFetcher(httpClient, configuration.getMaxResults(), 0);
    Parser parser = new JiraParser();
    Persister persister = getPersister(scraperType, persistLocation);

    return new JiraExecutableScraper(fetcher, parser, persister);
  }

  private static JiraPersister getPersister(String scraperType, String persistLocation) throws JiraException {
    return switch (scraperType) {
      case "json" -> new JiraPersisterJson(persistLocation);
      case "xml" -> new JiraPersisterXml(persistLocation);
      default -> throw new JiraException("Invalid 'type' configuration");
    };
  }
}
