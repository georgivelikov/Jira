package com.jiraintegration.result;

import com.jiraintegration.exception.JiraException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JiraResult {

  private final int persistedIssues;
  private final boolean finished;
  private final boolean success;
  private final JiraException exception;

}
