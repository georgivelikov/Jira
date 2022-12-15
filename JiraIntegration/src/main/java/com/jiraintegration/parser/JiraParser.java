package com.jiraintegration.parser;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import com.jiraintegration.exception.JiraException;
import com.jiraintegration.model.Comment;
import com.jiraintegration.model.Issue;
import com.jiraintegration.utils.date.DateUtils;
import com.jiraintegration.utils.json.JsonFields;
import com.jiraintegration.utils.json.JsonUtils;

public class JiraParser implements Parser {

  private final String LINK_FORMAT = "https://jira.atlassian.com/browse/%s";

  @Override
  public List<Issue> parse(String payload) throws JiraException {
    JsonObject responseObject = JsonUtils.fromJson(payload, JsonObject.class);
    JsonArray issuesJson = responseObject.getAsJsonArray(JsonFields.ISSUES);
    List<Issue> issues = new ArrayList<>();
    issuesJson.forEach(issueElem -> {
      JsonObject issueJson = issueElem.getAsJsonObject();
      Issue issue = parseSingleIssueObject(issueJson);
      issues.add(issue);
    });

    return issues;
  }

  private Issue parseSingleIssueObject(JsonObject issueJson) {
    String id = issueJson.get(JsonFields.ID).getAsString();
    String key = issueJson.get(JsonFields.KEY).getAsString();
    String link = String.format(LINK_FORMAT, key);

    JsonObject fieldsObj = issueJson.get(JsonFields.FIELDS).getAsJsonObject();

    String summary = fieldsObj.get(JsonFields.SUMMARY).getAsString();
    String description = fieldsObj.get(JsonFields.DESCRIPTION).getAsString();
    String priority = getFromObject(fieldsObj, JsonFields.PRIORITY, JsonFields.PRIORITY_NAME);
    String issueType = getFromObject(fieldsObj, JsonFields.ISSUE_TYPE, JsonFields.ISSUE_TYPE_NAME);
    String reporter = getFromObject(fieldsObj, JsonFields.REPORTER, JsonFields.REPORTER_NAME);
    String dateStr = fieldsObj.get(JsonFields.CREATE_DATE).getAsString();
    ZonedDateTime createDate = DateUtils.getDateTimeFromString(dateStr);

    List<Comment> comments = getComments(fieldsObj);

    return Issue.builder()
        .id(id)
        .key(key)
        .link(link)
        .summary(summary)
        .description(description)
        .priority(priority)
        .issueType(issueType)
        .reporter(reporter)
        .createDate(createDate)
        .comments(comments)
        .build();
  }

  private List<Comment> getComments(JsonObject fieldsObj) {
    JsonObject commentHolderObj = fieldsObj.get(JsonFields.COMMENT).getAsJsonObject();
    JsonArray comments = commentHolderObj.get(JsonFields.COMMENTS).getAsJsonArray();
    List<Comment> commentsResult = new ArrayList<>();
    comments.forEach(commentElem -> {
      JsonObject commentObj = commentElem.getAsJsonObject();
      Comment comment = parseSingleCommentObject(commentObj);
      commentsResult.add(comment);
    });

    return commentsResult;
  }

  private Comment parseSingleCommentObject(JsonObject commentJson) {
    JsonObject authorJson = commentJson.get(JsonFields.COMMENT_AUTHOR).getAsJsonObject();
    String authorName = authorJson.get(JsonFields.COMMENT_AUTHOR_NAME).getAsString();
    String text = commentJson.get(JsonFields.COMMENT_TEXT).getAsString();

    return new Comment(authorName, text);
  }

  private String getFromObject(JsonObject fieldsObj, String objectName, String fieldName) {
    JsonElement element = fieldsObj.get(objectName);
    if (element instanceof JsonNull) {
      return null;
    }

    return element.getAsJsonObject().get(fieldName).getAsString();
  }
}
