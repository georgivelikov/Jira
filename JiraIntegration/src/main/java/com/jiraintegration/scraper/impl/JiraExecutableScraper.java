package com.jiraintegration.scraper.impl;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.fetcher.Fetcher;
import com.jiraintegration.parser.Parser;
import com.jiraintegration.persister.Persister;
import com.jiraintegration.result.JiraResult;
import com.jiraintegration.scraper.ExecutableScraper;
import com.jiraintegration.scraper.executor.ScraperStopper;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class JiraExecutableScraper extends JiraScraper implements ExecutableScraper {

  private final int interval;
  private ScraperStopper stopperListener;

  public JiraExecutableScraper(Fetcher fetcher, Parser parser, Persister persister, int interval) {
    super(fetcher, parser, persister);
    this.interval = interval;
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
      if (result.isSuccess()) {
        if (result.isFinished()) {
          log.info("Jira {} scraper finished successfully", getPersister().getFileType());
          this.stopperListener.stop(this);
        } else {
          log.info("Persisted: {} {} issues", result.getPersistedIssues(), getPersister().getFileType());
        }
      } else {
        // Just stop the scraper if exception happens. Could be extended with recoverable exceptions in the future
        log.warn("Jira {} scraper failed with exception: {}", getPersister().getFileType(),
            result.getException().getMessage());
        this.stopperListener.stop(this);
      }
    }
  }

  @Override
  public void setStopperListener(ScraperStopper stopperListener) {
    this.stopperListener = stopperListener;
  }
}
