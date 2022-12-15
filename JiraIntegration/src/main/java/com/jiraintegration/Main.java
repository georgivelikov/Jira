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
    String[][] mArgs = new String[2][];
    String[] xmlArgs = new String[] { "xml", "100", ".\\repository", "5" };
    String[] jsonArgs = new String[] { "json", "50", ".\\repository", "7" };
    mArgs[0] = xmlArgs;
    mArgs[1] = jsonArgs;
    startMultiple(mArgs);
  }

  private static void startSingle(String[] args) throws JiraException {
    JiraExecutor executor = new JiraExecutor(Executors.newSingleThreadScheduledExecutor());
    JiraConfiguration configuration = loadConfig(args);
    ExecutableScraper jiraScraper = ScraperFactory.getJiraExecutableScraper(configuration);
    executor.execute(jiraScraper);
  }

  private static void startMultiple(String[][] mArgs) throws JiraException {
    JiraExecutor executor = new JiraExecutor(Executors.newScheduledThreadPool(mArgs.length));
    for (String[] args : mArgs ) {
      JiraConfiguration configuration = loadConfig(args);
      ExecutableScraper jiraScraper = ScraperFactory.getJiraExecutableScraper(configuration);
      executor.execute(jiraScraper);
    }
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
