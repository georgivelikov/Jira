package com.jiraintegration.parser;

import java.util.List;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Issue;

public interface Parser {

  List<Issue> parse(String payload) throws JiraException;
}
