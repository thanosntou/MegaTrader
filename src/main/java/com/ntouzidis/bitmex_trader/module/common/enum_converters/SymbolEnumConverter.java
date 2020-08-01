package com.ntouzidis.bitmex_trader.module.common.enum_converters;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SymbolEnumConverter implements Converter<String, Symbol> {

  @Override
  public Symbol convert(String value) {
    return Symbol.valueOf(value);
  }
}
