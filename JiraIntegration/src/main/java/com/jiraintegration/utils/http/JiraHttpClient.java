package com.jiraintegration.utils.http;

import java.io.IOException;

public interface JiraHttpClient {

  public JiraHttpResponse executeGetRequest(String url) throws IOException;
}
