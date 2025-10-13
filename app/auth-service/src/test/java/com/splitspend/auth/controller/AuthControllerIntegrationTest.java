package com.splitspend.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.splitspend.auth.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class AuthControllerIntegrationTest {

  @Autowired MockMvc mockMvc;

  @Test
  void should_Register_Successfully() throws Exception {
    String body = """
                {"email":"test@mail.com","password":"password"}""";
    mockMvc
        .perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("test@mail.com"));
  }

  @Test
  void shouldReturn401_WhenPasswordIncorrect() throws Exception {
    String loginBody = """
            {"email":"test@mail.com","password":"wrongPass"}""";
    mockMvc
        .perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(loginBody))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message").value("Incorrect password"));
  }
}
