package com.ntouzidis.cooperative.module.api;

import com.ntouzidis.cooperative.module.user.entity.User;
import com.ntouzidis.cooperative.module.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trader")
public class TraderApiV1Controller {

  @Value("${trader}")
  private String traderName;

  private final UserService userService;

  public TraderApiV1Controller(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/followers")
  public ResponseEntity<?> getFollowers() throws NotFoundException {

    User trader = userService.findByUsername(traderName)
            .orElseThrow(() -> new NotFoundException("Trader not found"));

    List<User> followers = userService.getFollowers(trader);

    return ResponseEntity.ok(followers);
  }
}
