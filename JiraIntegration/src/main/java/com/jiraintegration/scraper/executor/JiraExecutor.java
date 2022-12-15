package com.jiraintegration.scraper.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jiraintegration.scraper.ExecutableScraper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JiraExecutor implements ScraperExecutor, ScraperStopper {

  private final ScheduledExecutorService executorService;
  private final int interval;

  public void execute(ExecutableScraper jiraScraper) {
    jiraScraper.setStopperListener(this);
    this.executorService.scheduleAtFixedRate(jiraScraper, 0, interval, TimeUnit.SECONDS);
  }

  @Override
  public void stop() {
    log.info("Shutting down...");
    this.executorService.shutdown();
  }
}
