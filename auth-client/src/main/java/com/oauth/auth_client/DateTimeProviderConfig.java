//package com.oauth.auth_client;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.auditing.DateTimeProvider;
//
//import java.time.OffsetDateTime;
//import java.util.Optional;
//
//@Configuration
//public class DateTimeProviderConfig {
//
//  @Bean(name = "auditingDateTimeProvider")
//  public DateTimeProviderConfig dateTimeProvider() {
//    return () -> Optional.of(OffsetDateTime.now());
//  }
//
//}