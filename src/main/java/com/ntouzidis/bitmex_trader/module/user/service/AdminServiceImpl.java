package com.ntouzidis.bitmex_trader.module.user.service;

import com.ntouzidis.bitmex_trader.module.common.enumeration.Symbol;
import com.ntouzidis.bitmex_trader.module.trade.service.IBitmexApiService;
import com.ntouzidis.bitmex_trader.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ntouzidis.bitmex_trader.CooperativeApplication.logger;
import static com.ntouzidis.bitmex_trader.module.common.constants.BitmexConstants.WALLET_BALANCE;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final UserService userService;
  private final IBitmexApiService bitmexApiService;

  @Override
  public double calculateTotalVolume(String traderName) {
    User trader = userService.getTrader(traderName);
    int sum = userService.getEnabledAndNonHiddenFollowers(trader).stream().mapToInt(user -> {
      try {
        return ((Integer) bitmexApiService.getUserMargin(user).get(WALLET_BALANCE));
      } catch (Exception ignored) {
        logger.warn(String.format("Failed to get " + WALLET_BALANCE + " from user margin details: [%s]", user.getUsername()), ignored);
      }
      return 0;
    }).sum();

    return (double) sum / 100000000;
  }

  @Override
  public double calculateActiveVolume(String traderName, Symbol symbol) {
    User trader = userService.getTrader(traderName);
    return userService.getEnabledAndNonHiddenFollowers(trader).stream().mapToDouble(user -> {
      try {
        double userBalance = ((Integer) bitmexApiService.getUserMargin(user).get(WALLET_BALANCE)).doubleValue();
        double userPercentage = (double) user.getQtyPreference(symbol).getValue() / (double) 100;
        return userBalance * userPercentage / 100000000;
      }
      catch (Exception ignored) {
        logger.warn(String.format("Failed to get read balance of follower: [%s]", user.getUsername()), ignored);
      }
      return 0;
    }).sum();
  }
}
