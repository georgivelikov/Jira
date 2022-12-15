package com.jiraintegration.persister;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Issue;
import com.jiraintegration.result.JiraResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@RequiredArgsConstructor
public abstract class JiraPersister implements Persister {

  protected static final String FILE_PATH_FORMAT = "%s/%s";
  protected static final String FILE_NAME_FORMAT = "issue_%s.%s";
  private final String location;

  @Override
  public JiraResult persist(List<Issue> issues) throws JiraException {
    if (issues.isEmpty()) {
      // No more issues, finished
      return new JiraResult(0, true, true, null);
    }

    for (Issue issue : issues) {
      String filePath = buildFilePath(issue.getId());
      File file = new File(filePath);
      String content = getFileContent(issue);
      try {
        FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
      } catch (IOException ex) {
        log.error(ex);
        throw new JiraException("Error while persisting data");
      }
    }

    return new JiraResult(issues.size(), false, true, null);
  }

  protected abstract String getFileType();

  protected abstract String getFileContent(Issue issue) throws JiraException;

  protected String buildFilePath(String id) {
    String fileName = String.format(FILE_NAME_FORMAT, id, getFileType());
    String filePath = String.format(FILE_PATH_FORMAT, location, fileName);

    return filePath;
  }
}
