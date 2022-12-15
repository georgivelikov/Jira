package com.jiraintegration.scraper.executor;

import com.jiraintegration.scraper.ExecutableScraper;

public interface ScraperStopper {
  void stop(ExecutableScraper task);
}
