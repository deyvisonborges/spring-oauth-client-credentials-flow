package com.oauth.auth_resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
        .anyRequest().authenticated())
      .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
        .jwt(Customizer.withDefaults())
      );
    return http.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

    converter.setJwtGrantedAuthoritiesConverter(
      jwt -> {
        List<String> userRoleAuthorities = jwt.getClaimAsStringList("authorities");

        if (userRoleAuthorities == null) {
          userRoleAuthorities = Collections.emptyList();
        }

        JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();

        Collection<GrantedAuthority> scopeAuthorities = scopesConverter.convert(jwt);

        scopeAuthorities
          .addAll(userRoleAuthorities.stream()
            .map(SimpleGrantedAuthority::new)
            .toList());

        return scopeAuthorities;
      }
    );

    return converter;
  }

//    @Bean
//    public JwtDecoder jwtDecoder() {
////   Carrega a chave publica do auithorization server
//        return JwtDecoders.fromIssuerLocation("http://localhost:9000"); // URL do Authorization Server
//    }

  @Bean
  public JwtDecoder jwtDecoder() {

    JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("http://localhost:9000/oauth2/jwks").build();

    return new JwtDecoder() {
      @Override
      public Jwt decode(String token) throws JwtException {
        System.out.println("token: " + token);
        Jwt jwt = jwtDecoder.decode(token);
        System.out.println("jwt: " + jwt);
        return jwt;
      }
    };
  }
}
