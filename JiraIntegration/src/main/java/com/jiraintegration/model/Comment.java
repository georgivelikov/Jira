package com.jiraintegration.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Comment {
  private String author;
  private String text;
}
