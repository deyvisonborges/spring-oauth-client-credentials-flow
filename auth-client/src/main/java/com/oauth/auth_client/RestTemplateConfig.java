package com.oauth.auth_client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
//  @Bean
//  public RestTemplate restTemplate(
//          RestTemplateBuilder builder,
//          AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager
//  ) {
//    return builder.additionalRequestCustomizers(request -> {
//      OAuth2AuthorizeRequest auth2AuthorizeRequest = OAuth2AuthorizeRequest
//        .withClientRegistrationId("client-id")
//        .principal("client-id")
//        .build();
//
//      OAuth2AuthorizedClient authorize = clientManager.authorize(auth2AuthorizeRequest);
//
//      if (authorize != null) {
//        OAuth2AccessToken accessToken = authorize.getAccessToken();
//        System.out.println("Access Token: " + accessToken.getTokenValue());
//        request.getHeaders().setBearerAuth(accessToken.getTokenValue());
//      }
//
//    }).build();
//  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
