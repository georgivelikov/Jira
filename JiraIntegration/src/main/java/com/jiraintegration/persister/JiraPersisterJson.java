package com.jiraintegration.persister;

import com.jiraintegration.model.Issue;
import com.jiraintegration.utils.json.JsonUtils;

public class JiraPersisterJson extends JiraPersister {

  private static final String FILE_TYPE = "json";

  public JiraPersisterJson(String location) {
    super(location);
  }

  @Override
  protected String getFileType() {
    return FILE_TYPE;
  }

  @Override
  protected String getFileContent(Issue issue) {
    return JsonUtils.toJson(issue);
  }

}
