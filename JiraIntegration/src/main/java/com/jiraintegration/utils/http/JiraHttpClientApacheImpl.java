package com.jiraintegration.utils.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class JiraHttpClientApacheImpl implements JiraHttpClient {

  private static final CloseableHttpClient client = HttpClients.custom()
      .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

  @Override
  public JiraHttpResponse executeGetRequest(String url) throws IOException {
    HttpGet request = new HttpGet(url);
    CloseableHttpResponse response = client.execute(request);
    int code = response.getStatusLine().getStatusCode();
    HttpEntity entity = response.getEntity();
    String body = EntityUtils.toString(entity);

    return new JiraHttpResponse(code, body);
  }
}
