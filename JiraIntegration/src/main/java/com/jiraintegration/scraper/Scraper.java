package com.jiraintegration.scraper;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.result.JiraResult;

public interface Scraper {

  JiraResult scrape() throws JiraException;
}
