package com.jiraintegration.scraper.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jiraintegration.scraper.ExecutableScraper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JiraExecutor implements ScraperExecutor, ScraperStopper {

  private final ScheduledThreadPoolExecutor executorService;
  private final Map<Runnable, ScheduledFuture<?>> identityMap = new HashMap<>();

  public void execute(ExecutableScraper jiraScraper) {
    jiraScraper.setStopperListener(this);
    ScheduledFuture<?> future = this.executorService.scheduleAtFixedRate(jiraScraper, 0, jiraScraper.getInterval(),
        TimeUnit.SECONDS);
    identityMap.put(jiraScraper, future);
  }

  @Override
  public void stop(ExecutableScraper task) {
    ScheduledFuture<?> future = identityMap.get(task);
    future.cancel(true);
    boolean shouldShutdown = true;
    for (ScheduledFuture<?> f : identityMap.values()) {
      if(!f.isDone()) {
        shouldShutdown = false;
        break;
      }
    }

    if(shouldShutdown) {
      log.info("All scrapers finished. Shutting down Jira Executor...");
      this.executorService.shutdown();
    }

  }
}
