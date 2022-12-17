package com.jiraintegration.scraper;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.result.JiraResult;

public interface Scraper {

  String getId();

  JiraResult scrape() throws JiraException;
}
