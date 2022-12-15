package com.jiraintegration.utils.xml;

import java.io.StringWriter;

import com.jiraintegration.exception.JiraException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class XmlUtils {

  public static String toXml(Object source) throws JiraException {
    String result;
    StringWriter sw = new StringWriter();
    try {
      JAXBContext context = JAXBContext.newInstance(source.getClass());
      Marshaller marshaller = context.createMarshaller();
      marshaller.marshal(source, sw);
      result = sw.toString();
    } catch (JAXBException ex) {
      log.error(ex);
      throw new JiraException("Error while converting to XML format", ex);
    }

    return result;
  }
}
