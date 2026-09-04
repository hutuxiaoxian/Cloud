package com.haijinhan.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SsoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoClientApplication.class, args);
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser principal) {
        if (principal == null) {

            UserDetailsService service;
            return "Hello, Anonymous! Please login.";
        }
        return "Hello, " + principal.getFullName() + "! Your Subject ID is: " + principal.getSubject();
    }
}