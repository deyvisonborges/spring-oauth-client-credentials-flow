package com.oauth.auth_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@RestController
public class AuthClientController {
//  public ResponseEntity<OAuth2AccessTokenResponse> getToken() {
//    try {
//      String tokenUrl = "http://localhost:9000/oauth2/token";
//      HttpHeaders headers = new HttpHeaders();
//      headers.setBasicAuth("client-id", "client-secret");
//      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//      body.add("grant_type", "client_credentials");
//      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
//      final var token = restTemplate.postForEntity(tokenUrl, request, OAuth2AccessTokenResponse.class);
//      System.out.println("TOKEN === " + token.getBody().getAccessToken());
//      return token;
//    } catch (Exception e) {
//      throw new RuntimeException("Falhei ao gerar o token");
//    }
//  }

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping
  public String get() {
    return "Working...";
  }

  @GetMapping("/test")
  public Optional<String> fetchData(@AuthenticationPrincipal Jwt jwt) {
    System.out.println("========" + jwt.getTokenValue().toString());
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer %s".formatted(client.getAccessToken().getTokenValue()));
//        headers.add("Authorization", "Bearer %s".formatted(this.getToken()));
        headers.add("Authorization", "Bearer %s".formatted(jwt.getTokenValue()));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String resourceUrl = "http://localhost:8081";

        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        return response.getBody().describeConstable();
//
//    try {
//      return Objects.requireNonNull(restTemplate.getForObject("http://localhost:8081", String.class)).describeConstable();
//    } catch (Exception e) {
//      System.out.println("Erro ao acessar o resource server: " + e.getMessage());
//      return Optional.empty();
//    }
  }
}
