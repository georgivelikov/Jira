package com.jiraintegration.scraper.impl;

import java.util.concurrent.FutureTask;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.fetcher.Fetcher;
import com.jiraintegration.parser.Parser;
import com.jiraintegration.persister.Persister;
import com.jiraintegration.scraper.ExecutableScraper;
import com.jiraintegration.scraper.executor.ScraperStopper;
import com.jiraintegration.result.JiraResult;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JiraExecutableScraper extends JiraScraper implements ExecutableScraper {

  private ScraperStopper stopperListener;

  public JiraExecutableScraper(Fetcher fetcher, Parser parser, Persister persister) {
    super(fetcher, parser, persister);
  }

  @Override
  public void run() {
    JiraResult result = null;
    try {
      result = scrape();
    } catch (JiraException ex) {
      log.error(ex);
      result = new JiraResult(0, true, false, ex);
    } finally {
      if(result.isSuccess()) {
        if(result.isFinished()) {
          log.info("JiraScraper finished successfully");
          this.stopperListener.stop();
        } else {
          log.info("Persisted: " + result.getPersistedIssues() + " issues");
        }
      } else {
        // Just stop the scraper if exception happens. Could be extended with recoverable exceptions in the future
        log.warn("JiraScraper failed with exception: " + result.getException().getMessage());
        this.stopperListener.stop();
      }
    }
  }

  @Override
  public void setStopperListener(ScraperStopper stopperListener) {
    this.stopperListener = stopperListener;
  }
}
