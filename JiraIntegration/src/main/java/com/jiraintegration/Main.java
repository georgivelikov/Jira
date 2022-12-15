package com.jiraintegration;

import java.util.concurrent.Executors;

import com.jiraintegration.configuration.ConfigurationLoader;
import com.jiraintegration.configuration.JiraConfiguration;
import com.jiraintegration.exception.JiraException;
import com.jiraintegration.scraper.ExecutableScraper;
import com.jiraintegration.scraper.executor.JiraExecutor;
import com.jiraintegration.scraper.factory.ScraperFactory;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

  public static void main(String[] args) throws JiraException {
    JiraConfiguration configuration = loadConfig(args);
    ExecutableScraper jiraScraper = ScraperFactory.getJiraExecutableScraper(configuration);
    JiraExecutor executor = new JiraExecutor(Executors.newSingleThreadScheduledExecutor(), configuration.getInterval());
    executor.execute(jiraScraper);
  }

  private static JiraConfiguration loadConfig(String[] args) {
    JiraConfiguration configuration = null;
    try {
      configuration = ConfigurationLoader.load(args);
    } catch(JiraException ex) {
      log.error(ex);
      System.exit(111);
    }
    return configuration;
  }
}
