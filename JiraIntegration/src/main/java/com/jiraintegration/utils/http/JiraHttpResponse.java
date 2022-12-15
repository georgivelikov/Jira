package com.jiraintegration.utils.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JiraHttpResponse {

  private final int code;
  private final String body;
}
