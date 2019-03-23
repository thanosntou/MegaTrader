package com.ntouzidis.cooperative.module.service;

import com.google.common.base.Preconditions;
import com.ntouzidis.cooperative.module.common.builder.DataDeleteOrderBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataLeverageBuilder;
import com.ntouzidis.cooperative.module.common.builder.DataOrderBuilder;
import com.ntouzidis.cooperative.module.common.endpoints.*;
import com.ntouzidis.cooperative.module.common.enumeration.Symbol;
import com.ntouzidis.cooperative.module.user.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BitmexService {

  private Logger logger = LoggerFactory.getLogger(BitmexService.class);

  private final RestTemplateService restTemplateService;

  public BitmexService(RestTemplateService restTemplateService) {
    this.restTemplateService = restTemplateService;
  }

  public List<Map<String, Object>> get_Announcements(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, Announcement.ANNOUNCEMENT, "");

    if (responseOpt.isPresent())
      return getMapList(responseOpt.get().getBody());

    throw new RuntimeException();
  }

  public List<Map<String, Object>> get_chat(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(
            user, Chat.CHAT + "?reverse=true&count=200", ""
    );
    if (responseOpt.isPresent()) {
      return getMapList(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public Map<String, Object> post_chat(User user, String message) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.POST(user, Chat.CHAT, "message=" + message);
    if (responseOpt.isPresent()) {
      return getMap(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public String getInstrumentLastPrice(User user, Symbol symbol) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(
            user,Instrument.INSTRUMENT + "?symbol=" + symbol.name(), ""
    );
    Optional<List<Map<String, Object>>> mapOpt = responseOpt.map(stringHttpEntity ->
            getMapList(stringHttpEntity.getBody())
    );
    if (mapOpt.isPresent()) {
      Preconditions.checkState(symbol.name().equals(mapOpt.get().get(0).get("symbol")));
      return mapOpt.get().get(0).get("lastPrice").toString();
    }
    throw new RuntimeException();
  }

  public Map<String, Object> getUserWallet(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, BitmexUser.USER_WALLET, "");

    if (responseOpt.isPresent()) {
      return getMap(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public List<Map<String, Object>> getUserWalletHistory(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, BitmexUser.USER_WALLET_HISTORY, "");

    if (responseOpt.isPresent()) {
      return getMapList(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public List<Map<String, Object>> getUserWalletSummary(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, BitmexUser.USER_WALLET_SUMMARY, "");

    if (responseOpt.isPresent()) {
      return getMapList(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public Map<String, Object> get_User_Margin(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, BitmexUser.USER_MARGIN, "");

    if (responseOpt.isPresent()) {
      return getMap(responseOpt.get().getBody());
    }
    throw new RuntimeException("");
  }

  public List<Map<String, Object>> get_Order_Order(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, Order.ORDER + "?reverse=true", "");

    if (responseOpt.isPresent()) {
      return getMapList(responseOpt.get().getBody());
    }
    return Collections.emptyList();
  }

  public Map<String, Object> post_Order_Order(User user, DataOrderBuilder dataOrder) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.POST(user, Order.ORDER, dataOrder.get());

    if (responseOpt.isPresent()) {
      return getMap(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public void cancelOrder(User user, DataDeleteOrderBuilder dataDeleteOrder) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.DELETE(user, Order.ORDER, dataDeleteOrder.get());

    if (!responseOpt.isPresent()) {
      throw new RuntimeException();
    }
  }

  public void cancelAllOrders(User user, DataDeleteOrderBuilder dataDeleteOrder) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.DELETE(user, Order.ORDER_ALL, dataDeleteOrder.get());

    if (!responseOpt.isPresent()) {
      throw new RuntimeException();
    }
  }

  public Map<String, Object> getSymbolPosition(User user, Symbol symbol) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, Position.POSITION, "");

    if (responseOpt.isPresent())
      return getMapList(responseOpt.get().getBody())
              .stream()
              .filter(i -> i.get("symbol").equals(symbol.name()))
              .findAny().orElseThrow(RuntimeException::new);

    throw new RuntimeException();
  }

  public List<Map<String, Object>> get_Position(User user) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, Position.POSITION, "");

    if (responseOpt.isPresent()) {
      return getMapList(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public List<Map<String, Object>> get_Position_Leverage(User user, String data) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.GET(user, Position.POSITION_LEVERAGE, data);

    if (responseOpt.isPresent()) {
      return getMapList(responseOpt.get().getBody());
    }
    throw new RuntimeException();
  }

  public Map<String, Object> post_Position_Leverage(User user, DataLeverageBuilder dataLeverage) {
    Optional<HttpEntity<String>> responseOpt = restTemplateService.POST(
            user, Position.POSITION_LEVERAGE, dataLeverage.get()
    );
    if (responseOpt.isPresent()) {
      return getMap(responseOpt.get().getBody());
    }
    throw new RuntimeException();
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
