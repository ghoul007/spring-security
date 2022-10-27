package com.ghoul.springsecurityangular.rest;

import com.ghoul.springsecurityangular.dto.ResponseDTO;
import com.ghoul.springsecurityangular.dto.UserDTO;
import com.ghoul.springsecurityangular.session.SessionRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
  @Autowired
  public AuthenticationManager manager;
  @Autowired
  public SessionRegistration sessionRegistry;

  @PostMapping("/login")
  public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO user) {
    manager.authenticate(
      new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
    );

    final String sessionId = sessionRegistry.registerSession(user.getUsername());
    ResponseDTO response = new ResponseDTO();
    response.setSessionId(sessionId);

    return ResponseEntity.ok(response);
  }
}
