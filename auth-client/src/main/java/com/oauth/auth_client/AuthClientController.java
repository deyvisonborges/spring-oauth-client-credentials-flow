package com.oauth.auth_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthClientController {
    public ResponseEntity<OAuth2AccessTokenResponse> getToken() {
        try {
            String tokenUrl = "http://localhost:9000/oauth2/token";
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("client-id", "client-secret");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            final var token = restTemplate.postForEntity(tokenUrl, request, OAuth2AccessTokenResponse.class);
            System.out.println("TOKEN === " + token.getBody().getAccessToken());
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Falhei ao gerar o token");
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String get() {
        return "Working...";
    }

    @GetMapping("/test")
    public String fetchData() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer %s".formatted(client.getAccessToken().getTokenValue()));
//        headers.add("Authorization", "Bearer %s".formatted(this.getToken()));
        headers.add("Authorization", "Bearer %s".formatted("eyJraWQiOiI4MzVlZDM0MS02ZDlmLTQ0NzMtYmJjNC03Nzc1OTBlYThjYjMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjbGllbnQtaWQiLCJhdWQiOiJjbGllbnQtaWQiLCJuYmYiOjE3MjI0MzQzMzIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE3MjI0MzQ2MzIsImlhdCI6MTcyMjQzNDMzMiwianRpIjoiYzBkMTEwYjYtZWIwMy00MTU5LWE5YmQtZGM2YTFhOThmNWIwIn0.p4TDu1GiZ6HAWBbsW_1tO7buPXCX6pOkZB3QDW9PZ3OoHyi6G2jzXAvBdsNEkkqgaqy3ccuuZY4Zp8gjvzbnZmSdW1c-TR049FMUhd1Ap-BabOs6V4Euejdz70dVv-WyB9yZeoLo94OEEyE1oRAwt5E2mWh6PsjQX5H6MOz4cVrTI9kewz4DEVsBRVtTqyEvAG4r_CS9re6EJU27RpztKEXHILvP5QPBFLc6_LR9Geg-P7FFgkTsmJotrCsO1H8LKX7tgbiJiVO3WIMCz5JUjqteVNIkHlVPeYsAgLw7MUYcAufID0IvAwQqHVI_lBz9kY3LiM6h7PSD_Q9l6SbHhw"));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String resourceUrl = "http://localhost:8081";

        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
