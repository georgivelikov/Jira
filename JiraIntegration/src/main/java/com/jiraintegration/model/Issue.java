package com.jiraintegration.model;

import java.time.ZonedDateTime;
import java.util.List;

import com.jiraintegration.utils.xml.DateAdapter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Issue {

  private String id;
  private String key;
  private String link;
  private String summary;
  private String description;
  private String priority;
  private String issueType;
  private String reporter;
  @XmlJavaTypeAdapter(DateAdapter.class)
  private ZonedDateTime createDate;
  @XmlElementWrapper(name = "comments")
  @XmlElement(name = "comment")
  private List<Comment> comments;
}
