package com.jiraintegration.persister;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Issue;
import com.jiraintegration.utils.xml.XmlUtils;

public class JiraPersisterXml extends JiraPersister {

  private static final String FILE_TYPE = "xml";

  public JiraPersisterXml(String location) {
    super(location);
  }

  @Override
  public String getFileType() {
    return FILE_TYPE;
  }

  @Override
  protected String getFileContent(Issue issue) throws JiraException {
    return XmlUtils.toXml(issue);
  }
}
