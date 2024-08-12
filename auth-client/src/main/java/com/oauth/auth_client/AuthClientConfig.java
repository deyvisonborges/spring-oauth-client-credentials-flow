package com.oauth.auth_client;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class AuthClientConfig {
  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(
      ClientRegistration
        .withRegistrationId("client-id")
        .tokenUri("http://localhost:9000/oauth2/token") // URL do Authorization Server
        .clientId("client-id")
        .clientName("client-id")
        .clientSecret("{noop}client-secret")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .scope("read")
        .scope("write")
        .build()
    );
  }

  @Bean
  public OAuth2AuthorizedClientService oauth2AuthorizedClientService(
    final ClientRegistrationRepository clientRegistrationRepository
  ) {
    return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
  }

  @Bean
  public AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager(
    ClientRegistrationRepository clientRegistrationRepository,
    OAuth2AuthorizedClientService oauth2AuthorizedClientService
  ) {
    OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder
      .builder()
      .clientCredentials()
      .build();
    var authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
      clientRegistrationRepository,
      oauth2AuthorizedClientService
    );
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    return authorizedClientManager;
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("http://localhost:9000/oauth2/jwks").build();
    return new JwtDecoder() {
      @Override
      public Jwt decode(String token) throws JwtException {
        return jwtDecoder.decode(token);
      }
    };
  }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String jwkSetUri = "http://localhost:9000/.well-known/jwks.json"; // Altere para o URL correto do seu Authorization Server
//        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//    }
//
//    @Bean
//    public JwtDecoderFactory<ClientRegistration> jwtDecoderFactory() {
//        final JwtDecoder decoder = new JwtDecoder() {
//            @Override
//            public Jwt decode(String token) throws JwtException {
//                JWT jwt = JWTParser.parse(token);
//                return createJwt(token, jwt);
//            }
//
//            private Jwt createJwt(String token, JWT parsedJwt) {
//                try {
//                    Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
//                    Map<String, Object> claims = parsedJwt.getJWTClaimsSet().getClaims();
//                    return Jwt.withTokenValue(token)
//                            .headers(h -> h.putAll(headers))
//                            .claims(c -> c.putAll(claims))
//                            .build();
//                } catch (Exception ex) {
//                    if (ex.getCause() instanceof ParseException) {
//                        throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE, "Malformed payload"));
//                    } else {
//                        throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE, ex.getMessage()), ex);
//                    }
//                }
//            }
//        };
//        return context -> decoder;
//    }
}


