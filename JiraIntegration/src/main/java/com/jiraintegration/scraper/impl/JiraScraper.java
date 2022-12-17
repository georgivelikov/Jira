package com.jiraintegration.scraper.impl;

import java.util.List;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.fetcher.Fetcher;
import com.jiraintegration.model.Issue;
import com.jiraintegration.parser.Parser;
import com.jiraintegration.persister.Persister;
import com.jiraintegration.result.JiraResult;
import com.jiraintegration.scraper.Scraper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JiraScraper implements Scraper {

  private final String id;
  private final Fetcher fetcher;
  private final Parser parser;
  private final Persister persister;

  @Override
  public JiraResult scrape() throws JiraException {
    String payload = fetcher.fetch();
    List<Issue> issues = parser.parse(payload);
    return persister.persist(issues);
  }

}
