package com.ntouzidis.bitmex_trader.integration_tests;

import org.junit.jupiter.api.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.ntouzidis.bitmex_trader.module.common.constants.ControllerPaths.USER_CONTROLLER_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends Base {

  @BeforeAll
  private void beforeClassTests() throws Exception {
    initializeTokens();
  }

  // ---------------------------------- Tests -----------------------------------------

  @Test
  @Order(1)
  public void testContainer() {
    assertTrue(mysqlContainer.isRunning());
  }

  @Test
  @Order(2)
  public void testReadAll() throws Exception {
    mockMvc.perform(GET(USER_CONTROLLER_PATH)
        .header("Authorization", "Bearer " + rootToken))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1));
  }

  @Test
  @Order(3)
  public void testRead() throws Exception {
    mockMvc.perform(GET(USER_CONTROLLER_PATH + "/1"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @Order(4)
  public void testReadByName() throws Exception {
    mockMvc.perform(GET(USER_CONTROLLER_PATH + "/by-name/root"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @Order(5)
  public void testReadByNameNotExist() throws Exception {
    mockMvc.perform(GET(USER_CONTROLLER_PATH + "/by-name/someone"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(6)
  public void testReadOneNotExist() throws Exception {
    mockMvc.perform(GET(USER_CONTROLLER_PATH + "/1000000"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(7)
  public void testChangePass() throws Exception {
    mockMvc.perform(POST(USER_CONTROLLER_PATH + "/pass")
        .param("newPass", "new password")
        .param("confirmPass", "new password"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  public MockHttpServletRequestBuilder GET(String url) {
    return get(url).header("Authorization", "Bearer " + rootToken);
  }

  public MockHttpServletRequestBuilder POST(String url) {
    return post(url).header("Authorization", "Bearer " + rootToken);
  }

}
