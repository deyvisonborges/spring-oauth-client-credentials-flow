package com.oauth.auth_resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping
    public String getData() {
        return "This is protected data";
    }

  @CanRead  
  @GetMapping("/test")
  public String getDataTest(@AuthenticationPrincipal Jwt jwt) {
    System.out.println(jwt);
    if (jwt == null) {
      return "JWT is null";
    }
    System.out.println(jwt.getClaims().get("sub").toString());
    return "This is test protected data";
  }
}