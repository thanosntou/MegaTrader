package com.ntouzidis.cooperative.module.api_v1;

import com.ntouzidis.cooperative.module.common.pojo.Context;
import com.ntouzidis.cooperative.module.common.pojo.bitmex.BitmexPosition;
import com.ntouzidis.cooperative.module.service.BitmexService;
import com.ntouzidis.cooperative.module.service.TradeService;
import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/follower")
public class FollowerApiV1Controller {

  private final Context context;
  private final UserService userService;
  private final TradeService tradeService;
  private final BitmexService bitmexService;

  public FollowerApiV1Controller(Context context, UserService userService,
                                 TradeService tradeService, BitmexService bitmexService) {
    this.context = context;
    this.userService = userService;
    this.tradeService = tradeService;
    this.bitmexService = bitmexService;
  }


}
