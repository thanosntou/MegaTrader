package com.ntouzidis.cooperative.module.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataOrderBuilder;
import com.ntouzidis.cooperative.module.common.endpoints.bitmex_api.*;
import com.ntouzidis.cooperative.module.common.enumeration.OrderStatus;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexInstrument;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexOrder;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BitmexService {

  private Logger logger = LoggerFactory.getLogger(BitmexService.class);

  private final RestTemplateService restTemplateService;

  public BitmexService(RestTemplateService restTemplateService) {
    this.restTemplateService = restTemplateService;
  }

  String getInstrumentLastPrice(User user, Symbol symbol) {
    return restTemplateService.get(user, Instrument.INSTRUMENT + "?symbol=" + symbol.name(), "")
        .map(httpEntity -> convertToInstrumentPojoList(httpEntity.getBody()).stream().findFirst())
        .orElseThrow(() -> new RuntimeException("Failed to find the wanted instrument"))
        .map(BitmexInstrument::getLastPrice)
        .orElseThrow(() -> new RuntimeException("Uknown instrument price"));
  }

  BitmexPosition getSymbolPosition(User user, Symbol symbol) {
    return getOpenPositions(user)
        .stream()
        .filter(i -> i.getSymbol().equals(symbol))
        .findAny()
        .orElseThrow(RuntimeException::new);
  }

  private List<BitmexPosition> getPositions(User user) {
    return restTemplateService.get(user, Position.POSITION, "")
        .map(httpEntity -> convertToPositionPojoList(httpEntity.getBody()))
        .orElse(Collections.emptyList());
  }

  public List<BitmexPosition> getOpenPositions(User user) {
    return getPositions(user)
            .stream()
            .filter(pos -> Arrays.stream(Symbol.values()).anyMatch(p -> p.equals(pos.getSymbol())))
            .filter(pos -> pos.getEntryPrice() != null)
            .collect(Collectors.toList());
  }

  Map<String, Object> postPositionLeverage(User user, DataLeverageBuilder dataLeverage) {
    return restTemplateService.post(user, Position.POSITION_LEVERAGE, dataLeverage.get())
        .map(httpEntity -> getMap(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  private List<BitmexOrder> getOrders(User user) {
    return restTemplateService.get(user, Order.ORDER + "?reverse=true", "")
        .map(httpEntity -> convertToOrderPojoList(httpEntity.getBody()))
        .orElse(Collections.emptyList());
  }

  public List<BitmexOrder> getActiveOrders(User user) {
    return getOrders(user)
        .stream()
        .filter(order -> Arrays.stream(Symbol.values()).anyMatch(p -> p.name().equals(order.getSymbol())))
        .filter(i -> i.getOrdStatus().equals(OrderStatus.New.getValue()))
        .collect(Collectors.toList());
  }

  BitmexOrder postOrderOrder(User user, DataOrderBuilder dataOrder) {
    return restTemplateService.post(user, Order.ORDER, dataOrder.get())
        .map(httpEntity -> convertToOrderPojo(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  public Map<String, Object> getUserWallet(User user) {
    return restTemplateService.get(user, BitmexUser.USER_WALLET, "")
        .map(httpEntity -> getMap(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  public List<Map<String, Object>> getUserWalletHistory(User user) {
    return restTemplateService.get(user, BitmexUser.USER_WALLET_HISTORY, "")
        .map(httpEntity -> getMapList(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  public List<Map<String, Object>> getUserWalletSummary(User user) {
    return restTemplateService.get(user, BitmexUser.USER_WALLET_SUMMARY, "")
        .map(httpEntity -> getMapList(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  public Map<String, Object> getUserMargin(User user) {
    return restTemplateService.get(user, BitmexUser.USER_MARGIN, "")
        .map(httpEntity -> getMap(httpEntity.getBody()))
        .orElseThrow(RuntimeException::new);
  }

  void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrder) {
    restTemplateService.delete(user, Order.ORDER, dataDeleteOrder.get()).orElseThrow(RuntimeException::new);
  }

  void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrder) {
    restTemplateService.delete(user, Order.ORDER_ALL, dataDeleteOrder.get()).orElseThrow(RuntimeException::new);
  }

  private BitmexPosition convertToPositionPojo(String body) {
    try {
      return new ObjectMapper().readValue(body, BitmexPosition.class);
    } catch (IOException e) {
      logger.error("Failed to convert response body to BitmexPosition POJO");
      throw new RuntimeException();
    }
  }

  private List<BitmexPosition> convertToPositionPojoList(String body) {
    ObjectMapper mapper = new ObjectMapper();
    List<BitmexPosition> positions = new ArrayList<>();
    getMapList(body).forEach(map -> positions.add(mapper.convertValue(map, BitmexPosition.class)));
    return positions;
  }

  private BitmexOrder convertToOrderPojo(String body) {
    return new ObjectMapper().convertValue(getMap(body), BitmexOrder.class);
  }

  private List<BitmexOrder> convertToOrderPojoList(String body) {
    ObjectMapper mapper = new ObjectMapper();
    List<BitmexOrder> orders = new ArrayList<>();
    getMapList(body).forEach(map -> orders.add(mapper.convertValue(map, BitmexOrder.class)));
    return orders;
  }

  private List<BitmexInstrument> convertToInstrumentPojoList(String body) {
    ObjectMapper mapper = new ObjectMapper();
    List<BitmexInstrument> instruments = new ArrayList<>();
    getMapList(body).forEach(map -> instruments.add(mapper.convertValue(map, BitmexInstrument.class)));
    return instruments;
  }

  private Map<String, Object> getMap(String responseBody) {
    if(responseBody != null) {
      JSONObject jsonObj = new JSONObject(responseBody);
      return new HashMap<>(jsonObj.toMap());
    }
    throw new RuntimeException();
  }

  private List<Map<String, Object>> getMapList(String responseBody) {
    List<Map<String, Object>> myMapList;
    if(responseBody != null) {
      JSONArray jsonArray = new JSONArray(responseBody);
      myMapList = new ArrayList<>();
      for(int i = 0; i < jsonArray.length(); i++){
        JSONObject jsonObj = jsonArray.getJSONObject(i);
        myMapList.add(jsonObj.toMap());
      }
      return myMapList;
    }
    throw new RuntimeException();
  }
}
