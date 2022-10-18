package com.ideas2it.security.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomIdentityAuthenticationProvider
    implements AuthenticationProvider {

  UserDetails isValidUser(String username, String password) {
    System.out.println("valid user");
    if (username.equalsIgnoreCase("YUKI")
        && password.equals("YUKI@2001")) {
      return User
          .withUsername(username)
          .password("NOT_DISCLOSED")
          .roles("USER_ROLE")
          .build();
    }
    return null;
  }

  @Override
  public Authentication authenticate(Authentication authentication) {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    UserDetails userDetails = isValidUser(username, password);

    if (userDetails != null) {
      return new UsernamePasswordAuthenticationToken(
          username,
          password,
          userDetails.getAuthorities());
    } else {
      throw new BadCredentialsException("Incorrect user credentials !!");
    }
  }

  @Override
  public boolean supports(Class<?> authenticationType) {
    return authenticationType
        .equals(UsernamePasswordAuthenticationToken.class);
  }
}