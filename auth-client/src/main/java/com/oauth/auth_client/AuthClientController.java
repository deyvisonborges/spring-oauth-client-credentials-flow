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
        headers.add("Authorization", "Bearer %s".formatted("eyJraWQiOiJlOTgwOTFmMS0yYjdiLTQ2MDktYTY1Yi1mMzkzMGQ3ZDhjNWIiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjbGllbnQtaWQiLCJhdWQiOiJjbGllbnQtaWQiLCJuYmYiOjE3MjIzNDg5MDcsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE3MjIzNDkyMDcsImlhdCI6MTcyMjM0ODkwNywianRpIjoiNDliZWE1NjAtMjJlOC00MTc5LThkNDgtNzE1Zjk4ODc4OTM0In0.ROMbyuXWzZmm2441ZhxYKWtBHjFRAgatsLb8ZGp_o7kNLfI7XnGApckSyhlvooeuHfWquQZJRmj_nmGK7daUtKevv_VCjN1EdPyyJv_df0JhkDioESQZRFGNG_7L1ATGlNOsw6CHY3dWS_mbXndBhmGaJ4dkmUbt3eOE8JMwd5jEx4O34nXdPEEiVjoaFLfd3mg_8UyTJTBbyqVsGzPQZIzvL90CaxJYIkR3M5g07_KWU3MHoH_jzxo5fNRtygv0vkaa5vfR4Sbz4ZLXusDQ5zwHBD-_0fqkZ0kC7FTSDmFjkdDEP9WxRn-o7VkbuN-hAqgM_k3dVYhGkWrvtokZ7Q"));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String resourceUrl = "http://localhost:8081";

        ResponseEntity<String> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
