package com.jiraintegration.persister;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.jiraintegration.TestHelper;
import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Issue;
import com.jiraintegration.utils.resource.ResourceUtils;

public class JiraPersisterTest {

  private static final String expectedJson = ResourceUtils.loadResource("persister/expected.json");
  private static final String expectedXml = ResourceUtils.loadResource("persister/expected.xml");

  @Test
  void buildFilePathForJsonShouldReturnCorrect() {
    String location = "repository";
    JiraPersisterJson persisterJson = new JiraPersisterJson(location);
    Issue issue = TestHelper.buildExpectedIssue();
    String actualFilePath = persisterJson.buildFilePath(issue.getId());

    String expectedId = issue.getId();
    String expectedFilePath = "repository/issue_" + expectedId + ".json";

    assertEquals(expectedFilePath, actualFilePath);
  }

  @Test
  void buildFilePathForXmlShouldReturnCorrect() {
    String location = "repository";
    JiraPersisterXml persisterXml = new JiraPersisterXml(location);
    Issue issue = TestHelper.buildExpectedIssue();
    String actualFilePath = persisterXml.buildFilePath(issue.getId());

    String expectedId = issue.getId();
    String expectedFilePath = "repository/issue_" + expectedId + ".xml";

    assertEquals(expectedFilePath, actualFilePath);
  }

  @Test
  void getFileContentForJsonShouldNotThrow() {
    String location = "repository";
    JiraPersisterJson persisterJson = new JiraPersisterJson(location);
    Issue issue = TestHelper.buildExpectedIssue();
    assertDoesNotThrow(() -> persisterJson.getFileContent(issue));
  }

  @Test
  void getFileContentForXmlShouldNotThrow() {
    String location = "repository";
    JiraPersisterXml persisterXml = new JiraPersisterXml(location);
    Issue issue = TestHelper.buildExpectedIssue();
    assertDoesNotThrow(() -> persisterXml.getFileContent(issue));
  }

  @Test
  void getFileContentJsonShouldReturnCorrectString() {
    String location = "repository";
    JiraPersisterJson persisterJson = new JiraPersisterJson(location);
    Issue issue = TestHelper.buildExpectedIssue();
    String actualJson = persisterJson.getFileContent(issue);

    assertEquals(expectedJson, actualJson);
  }

  @Test
  void getFileContentXmlShouldReturnCorrectString() throws JiraException {
    String location = "repository";
    JiraPersisterXml persisterXml = new JiraPersisterXml(location);
    Issue issue = TestHelper.buildExpectedIssue();
    String actualXml = persisterXml.getFileContent(issue);

    assertEquals(expectedXml, actualXml);
  }
}
