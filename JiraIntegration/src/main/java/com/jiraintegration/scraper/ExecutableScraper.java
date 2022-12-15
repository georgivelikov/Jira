package com.jiraintegration.scraper;

public interface ExecutableScraper extends Scraper, StoppableScraper, Runnable {

  int getInterval();
}
