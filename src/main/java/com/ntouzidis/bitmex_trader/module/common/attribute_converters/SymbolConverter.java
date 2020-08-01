package com.ntouzidis.bitmex_trader.module.common.attribute_converters;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SymbolConverter implements AttributeConverter<Symbol, String> {

  @Override
  public String convertToDatabaseColumn(Symbol symbol) {
    if (symbol == null) {
      return null;
    }
    return symbol.getValue();
  }

  @Override
  public Symbol convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }

    return Stream.of(Symbol.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
