package com.jiraintegration.scraper.executor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.jiraintegration.scraper.ExecutableScraper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JiraExecutor implements ScraperExecutor, ScraperStopper {

  private final ScheduledExecutorService executorService;
  private final Map<String, ScheduledFuture<?>> identityMap = new ConcurrentHashMap<>();

  public void execute(ExecutableScraper jiraScraper) {
    jiraScraper.setStopperListener(this);
    ScheduledFuture<?> future = this.executorService.scheduleAtFixedRate(jiraScraper, 0, jiraScraper.getInterval(),
        TimeUnit.SECONDS);
    identityMap.put(jiraScraper.getId(), future);
  }

  @Override
  public void stop(String scraperId) {
    ScheduledFuture<?> future = identityMap.get(scraperId);
    future.cancel(true);
    identityMap.remove(scraperId);

    if (identityMap.keySet().isEmpty()) {
      log.info("All scrapers finished. Shutting down Jira Executor...");
      this.executorService.shutdown();
    }

  }
}
