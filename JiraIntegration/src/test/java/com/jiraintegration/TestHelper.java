package com.jiraintegration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.jiraintegration.model.Comment;
import com.jiraintegration.model.Issue;

public class TestHelper {

  public static Issue buildExpectedIssue() {
    String id = "1866101";
    String key = "JSWSERVER-21305";
    String link = "https://jira.atlassian.com/browse/JSWSERVER-21305";
    String summary = "When Lexorank duplicate rank problem is fixed through REST API, it does not clear the failure "
        + "reason";
    String description = "h3. Issue Summary\r\n\r\nThis is reproducible on Data Center: yes\r\nh3. Steps to "
        + "Reproduce\r\n # Deliberately update the database so that two rows in the \"AO_60DB71_LEXORANK\" "
        + "table have the same Rank value. \r\n # Run the Lexorank check in the UI.  It will fail on the last test "
        + "- the duplicate rank test. \r\n # Follow the [https://confluence.atlassian"
        + ".com/jirakb/how-to-fix-duplicate-rank-values-for-a-rank-field-779159224.html] to get the results of the "
        + "integrity checks from the endpoint /rest/greenhopper/1.0/lexorank/integrity.  The last check will fail and"
        + " list the failure reason with the duplicated value.  \r\n # Run the REST API call via Postman to fix the"
        + " duplicate rank. \r\n # Run the integrity check again from the endpoint   /rest/greenhopper/1"
        + ".0/lexorank/integrity.  \r\n\r\nh3. Expected Results\r\n\r\nIt will have fixed the Lexorank "
        + "duplicate and clear the failure reason. \r\nh3. Actual Results\r\n\r\nThe duplicate rank issue is "
        + "fixed and the test passes but it still lists a failure "
        + "reason:\r\n{noformat}\r\n\"name\":\"Duplicate ranks check\",\"description\":\"Checks if "
        + "there are any duplicate rank values for a rank field.\",\"passed\":true,"
        + "\"failureReason\":\"Detected duplicate ranks for rank field 10100 : {0|i0001j:\u003d2}\","
        + "\"fatal\":false  {noformat}\r\nh3. Workaround\r\n\r\n\r\nCurrently there is no known "
        + "workaround for this behavior. A workaround will be added here when available";
    String priority = "Low";
    String issueType = "Bug";
    String reporter = "samann";
    LocalDateTime localDateTime = LocalDateTime.of(2022, 3, 30, 17, 40, 49, 0);
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));

    List<Comment> comments = new ArrayList<>();
    comments.add(new Comment("35da6706bcca", "It looks like the command explained in the KB article referred above "
        + "for fixing the duplicates actually fixes one duplicate at a time. Running the command several times until "
        + "getting no error message for duplicate values, fixes the problem at the end. In my case I had to run the "
        + "fix command around 25 times to fix all duplicates on Jira V.8.20.7. Maybe it helps for those with low "
        + "number of duplicate LexoRank duplicate rank values.\r\n\r\n.  "));
    comments.add(new Comment("3c25206fc48e", "Thanks Akyildiz :)\r\n\r\nI\u0027m actually the reason this defect"
        + " was open since I tried using the REST API to resolve the issue in our production environment and ended up"
        + " doing DB mod.\r\n\r\nGood to know I just need to run the REST API call a few times :)"));
    comments.add(new Comment("610caa47fd3d", "This impacts us on 8.20.2"));

    return Issue.builder()
        .id(id)
        .key(key)
        .link(link)
        .summary(summary)
        .description(description)
        .priority(priority)
        .issueType(issueType)
        .reporter(reporter)
        .createDate(zonedDateTime)
        .comments(comments)
        .build();

  }
}
