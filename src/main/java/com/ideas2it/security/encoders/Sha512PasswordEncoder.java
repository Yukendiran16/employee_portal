package com.ideas2it.security.encoders;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512PasswordEncoder implements PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    return hashWithSHA512(rawPassword.toString());
  }

  @Override
  public boolean matches(
      CharSequence rawPassword, String encodedPassword) {
    String hashedPassword = encode(rawPassword);
    return encodedPassword.equals(hashedPassword);
  }

  private String hashWithSHA512(String input) {
    StringBuilder result = new StringBuilder();
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      byte [] digested = md.digest(input.getBytes());
      for (byte b : digested) {
        result.append(Integer.toHexString(0xFF & b));
      }
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Bad algorithm");
    }
    return result.toString();
  }
}
