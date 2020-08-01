package com.ntouzidis.bitmex_trader.module.trade.service;

import com.ntouzidis.bitmex_trader.module.user.entity.User;
import org.springframework.http.HttpEntity;

import java.util.Optional;

public interface IRestTemplateService {

  Optional<HttpEntity<String>> get(User user, String path, String data);

  Optional<HttpEntity<String>> post(User user, String path, String data);

  Optional<HttpEntity<String>> delete(User user, String path, String data);
}
