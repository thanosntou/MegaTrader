package com.ntouzidis.bitmex_trader.integration_tests;

import com.ntouzidis.bitmex_trader.utils.IntegrationTestMysqlContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ContextConfiguration(initializers = Base.Initializer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class Base {

  @ClassRule
  public static MySQLContainer<IntegrationTestMysqlContainer> mysqlContainer = IntegrationTestMysqlContainer.getInstance();

  @Autowired
  public static MockMvc mockMvc;

  @Autowired
  public PasswordEncoder passwordEncoder;

  public static String rootToken;

  public static String adminToken;

  public static String traderToken;

  public static String followerToken;

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      mysqlContainer.start();
      TestPropertyValues.of(
          "spring.datasource.url=" + mysqlContainer.getJdbcUrl(),
          "spring.datasource.username=" + mysqlContainer.getUsername(),
          "spring.datasource.password=" + mysqlContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }

  static void initializeTokens() throws Exception {
    if (rootToken == null)
      rootToken = obtainAccessToken("root", "root");
//    if (adminToken == null)
//      adminToken = obtainAccessToken();
  }

  public static String obtainAccessToken(String username, String password) throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("grant_type", "password");
    params.add("username", username);
    params.add("password", password);

    ResultActions result = mockMvc.perform(post("/oauth/token")
        .params(params)
        .with(httpBasic("test","kobines"))
        .accept("application/json;charset=UTF-8"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"));

    String resultString = result.andReturn().getResponse().getContentAsString();

    JacksonJsonParser jsonParser = new JacksonJsonParser();
    return jsonParser.parseMap(resultString).get("access_token").toString();
  }
}
