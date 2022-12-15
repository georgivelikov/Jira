package com.jiraintegration.scraper;

import com.jiraintegration.scraper.executor.ScraperStopper;

public interface StoppableScraper {

  void setStopperListener(ScraperStopper stopperListener);
}
