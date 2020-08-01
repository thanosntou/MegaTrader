package com.ntouzidis.bitmex_trader.module.common.attribute_converters;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Client;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ClientConverter implements AttributeConverter<Client, String> {

  @Override
  public String convertToDatabaseColumn(Client client) {
    if (client == null) {
      return null;
    }
    return client.getValue();
  }

  @Override
  public Client convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }

    return Stream.of(Client.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
