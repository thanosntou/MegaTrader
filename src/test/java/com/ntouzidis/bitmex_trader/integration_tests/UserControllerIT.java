package com.ntouzidis.bitmex_trader.integration_tests;

import com.ntouzidis.bitmex_trader.module.common.forms.UserPasswordForm;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.ROOT_CONTROLLER_PATH;
import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.USER_CONTROLLER_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIT extends Base {

  @BeforeAll
  private void beforeClassTests() throws Exception {
    initializeTokens();
  }

  // ---------------------------------- Tests -----------------------------------------

  @Test
  @Order(1)
  void testContainer() {
    assertTrue(mysqlContainer.isRunning());
  }

  @Test
  @Order(2)
  void testReadAll() throws Exception {
    mockMvc.perform(authenticatedGET(ROOT_CONTROLLER_PATH + "/users")
        .header("Authorization", "Bearer " + rootToken))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(3));
  }

  @Test
  @Order(5)
  void testReadByNameNotExist() throws Exception {
    mockMvc.perform(authenticatedGET(USER_CONTROLLER_PATH + "/by-name/someone"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(6)
  void testReadOneNotExist() throws Exception {
    mockMvc.perform(authenticatedGET(USER_CONTROLLER_PATH + "/1000000"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(7)
  void testChangePass() throws Exception {
    mockMvc.perform(authenticatedPUT(USER_CONTROLLER_PATH + "/pass")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsBytes(UserPasswordForm.builder()
                    .newPass("new password")
                    .confirmPass("new password")
                    .build())))
        .andDo(print())
        .andExpect(status().isOk());
  }

}
