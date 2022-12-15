package com.jiraintegration.persister;

import java.util.List;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Issue;
import com.jiraintegration.result.JiraResult;

public interface Persister {

  JiraResult persist(List<Issue> issues) throws JiraException;
}
