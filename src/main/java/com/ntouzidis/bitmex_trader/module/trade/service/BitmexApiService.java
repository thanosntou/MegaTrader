package com.ntouzidis.bitmex_trader.module.trade.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntouzidis.bitmex_trader.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.bitmex_trader.module.common.builder.DataOrderBuilder;
import com.ntouzidis.bitmex_trader.module.common.endpoints.bitmex_api.BitmexUser;
import com.ntouzidis.bitmex_trader.module.common.endpoints.bitmex_api.Instrument;
import com.ntouzidis.bitmex_trader.module.common.endpoints.bitmex_api.Order;
import com.ntouzidis.bitmex_trader.module.common.endpoints.bitmex_api.Position;
import com.ntouzidis.bitmex_trader.module.common.enumeration.OrderStatus;
import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexInstrument;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.bitmex_trader.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.ntouzidis.bitmex_trader.CooperativeApplication.logger;
import static java.util.stream.Collectors.toList;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class BitmexApiService implements IBitmexApiService {

  private final ObjectMapper mapper = new ObjectMapper();
  private final IRestTemplateService restTemplateService;

  @Override
  public List<BitmexOrder> getActiveOrders(User user) {
    return getOrders(user).stream()
        .filter(order -> Arrays.stream(Symbol.values()).anyMatch(p -> p.name().equals(order.getSymbol())))
        .filter(i -> i.getOrdStatus().equals(OrderStatus.New.getValue()))
        .collect(toList());
  }

  @Override
  public List<BitmexPosition> getOpenPositions(User user) {
    return getPositions(user).stream()
        .filter(pos -> Arrays.stream(Symbol.values()).anyMatch(p -> p.equals(pos.getSymbol())))
        .filter(pos -> pos.getEntryPrice() != null)
        .collect(toList());
  }

  @Override
  public String getInstrumentLastPrice(User user, Symbol symbol) {
    return restTemplateService.get(user, Instrument.INSTRUMENT + "?symbol=" + symbol.name(), "")
        .map(httpEntity -> convertToInstrumentPojoList(httpEntity.getBody()).stream().findFirst())
        .orElseThrow(() -> new RuntimeException("Symbol not found"))
        .map(BitmexInstrument::getLastPrice)
        .orElseThrow(() -> new RuntimeException("Instrument price not found"));
  }

  @Override
  public BitmexPosition getSymbolPosition(User user, Symbol symbol) {
    return getOpenPositions(user)
        .stream()
        .filter(i -> i.getSymbol().equals(symbol))
        .findAny()
        .orElseGet(this::emptyPosition);
  }

  @Override
  public Map<String, Object> postPositionLeverage(User user, DataLeverageBuilder dataLeverage) {
    return restTemplateService.post(user, Position.POSITION_LEVERAGE, dataLeverage.get())
        .map(httpEntity -> getMap(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public BitmexOrder postOrderOrder(User user, DataOrderBuilder dataOrder) {
    return restTemplateService.post(user, Order.ORDER, dataOrder.get())
        .map(httpEntity -> convertToOrderPojo(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Map<String, Object> getUserWallet(User user) {
    return restTemplateService.get(user, BitmexUser.USER_WALLET, "")
        .map(httpEntity -> getMap(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public List<Map<String, Object>> getUserWalletHistory(User user) {
    return restTemplateService.get(user, BitmexUser.USER_WALLET_HISTORY, "")
        .map(httpEntity -> getMapList(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public List<Map<String, Object>> getUserWalletSummary(User user) {
    return restTemplateService.get(user, BitmexUser.USER_WALLET_SUMMARY, "")
        .map(httpEntity -> getMapList(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Map<String, Object> getUserMargin(User user) {
    return restTemplateService.get(user, BitmexUser.USER_MARGIN, "")
        .map(httpEntity -> getMap(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrder) {
    restTemplateService.delete(user, Order.ORDER, dataDeleteOrder.get()).orElseThrow(RuntimeException::new);
  }

  @Override
  public void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrder) {
    restTemplateService.delete(user, Order.ORDER_ALL, dataDeleteOrder.get()).orElseThrow(RuntimeException::new);
  }

  private List<BitmexPosition> getPositions(User user) {
    return restTemplateService.get(user, Position.POSITION, "")
        .map(httpEntity -> convertToPositionPojoList(httpEntity.getBody()))
        .orElse(Collections.emptyList());
  }

  private List<BitmexOrder> getOrders(User user) {
    return restTemplateService.get(user, Order.ORDER + "?reverse=true", "")
        .map(httpEntity -> convertToOrderPojoList(httpEntity.getBody()))
        .orElse(Collections.emptyList());
  }

  private BitmexPosition convertToPositionPojo(String body) {
    try {
      return mapper.readValue(body, BitmexPosition.class);
    } catch (IOException e) {
      logger.error("Failed to convert response body to BitmexPosition POJO");
      throw new RuntimeException();
    }
  }

  private BitmexPosition emptyPosition() {
    BitmexPosition position = new BitmexPosition();
    position.setSize(0L);
    return position;
  }

  private List<BitmexPosition> convertToPositionPojoList(String body) {
    return getMapList(body).stream()
        .map(map -> mapper.convertValue(map, BitmexPosition.class))
        .collect(toList());
  }

  private BitmexOrder convertToOrderPojo(String body) {
    return mapper.convertValue(getMap(body), BitmexOrder.class);
  }

  private List<BitmexOrder> convertToOrderPojoList(String body) {
    return getMapList(body)
        .stream()
        .map(map -> mapper.convertValue(map, BitmexOrder.class))
        .collect(toList());
  }

  private List<BitmexInstrument> convertToInstrumentPojoList(String body) {
    return getMapList(body).stream()
        .map(map -> mapper.convertValue(map, BitmexInstrument.class))
        .collect(toList());
  }

  private Map<String, Object> getMap(String responseBody) {
    JsonNode jsonNode;
    try {
      jsonNode = mapper.readTree(responseBody);
      Map<String, Object> map = mapper.convertValue(jsonNode, new TypeReference<>() {});
      return new HashMap<>(map);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Response convertion to map failed");
    }
  }

  private List<Map<String, Object>> getMapList(String responseBody) {
    JsonNode jsonNode;
    try {
      jsonNode = mapper.readTree(responseBody);
      return mapper.convertValue(jsonNode, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Response convertion to a list of map failed");
    }
  }
}
