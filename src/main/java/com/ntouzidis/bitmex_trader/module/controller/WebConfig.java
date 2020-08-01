package com.ntouzidis.bitmex_trader.module.controller;

import com.ntouzidis.bitmex_trader.module.common.enum_converters.SymbolEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new SymbolEnumConverter());
  }
}
