package com.springboot.springdesignpatterns.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConversionUtils {

  public static String asJsonString(Object entity) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      return objectMapper.writeValueAsString(entity);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }
}
