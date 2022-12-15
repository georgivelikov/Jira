package com.jiraintegration.fetcher;

import com.jiraintegration.exception.JiraException;

public interface Fetcher {
  String fetch() throws JiraException;
}
