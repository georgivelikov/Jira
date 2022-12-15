package com.jiraintegration.exception;

public class JiraException extends Exception {

  public JiraException(String message) {
    super(message);
  }

  public JiraException(Exception ex) {
    super(ex);
  }

  public JiraException(String message, Exception ex) {
    super(message, ex);
  }
}
