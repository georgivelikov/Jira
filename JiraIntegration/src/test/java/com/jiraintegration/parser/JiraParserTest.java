package com.jiraintegration.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.jiraintegration.TestHelper;
import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Comment;
import com.jiraintegration.model.Issue;
import com.jiraintegration.utils.resource.ResourceUtils;

public class JiraParserTest {

  private static final String payload = ResourceUtils.loadResource("response1.json");

  @Test
  void jsonParserShouldSuccessfullyParsePayload() throws JiraException {
    JiraParser parser = new JiraParser();

    List<Issue> actualIssues = parser.parse(payload);
    assertEquals(5, actualIssues.size());

    // Getting the forth entry from response1.json, because it is the easiest to parse and test
    Issue actualIssue = actualIssues.get(3);
    Issue expectedIssue = TestHelper.buildExpectedIssue();

    assertEquals(expectedIssue.getId(), actualIssue.getId());
    assertEquals(expectedIssue.getKey(), actualIssue.getKey());
    assertEquals(expectedIssue.getLink(), actualIssue.getLink());
    assertEquals(expectedIssue.getSummary(), actualIssue.getSummary());
    assertEquals(expectedIssue.getDescription(), actualIssue.getDescription());
    assertEquals(expectedIssue.getPriority(), actualIssue.getPriority());
    assertEquals(expectedIssue.getIssueType(), actualIssue.getIssueType());
    assertEquals(expectedIssue.getReporter(), actualIssue.getReporter());
    assertEquals(expectedIssue.getComments().size(), actualIssue.getComments().size());
    for (int i = 0; i < expectedIssue.getComments().size(); i++) {
      Comment expectedComment = expectedIssue.getComments().get(i);
      Comment actualComment = actualIssue.getComments().get(i);
      assertEquals(expectedComment.getAuthor(), actualComment.getAuthor());
      assertEquals(expectedComment.getText(), actualComment.getText());
    }
  }
}
